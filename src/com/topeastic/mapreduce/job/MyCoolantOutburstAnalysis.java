package com.topeastic.mapreduce.job;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.Logger;

import com.poss.saoss.hadoop.impl.PortalImpl;
import com.topeastic.hadoop.hdfs.HdfsUtils;
import com.topeastic.hadoop.utils.DbUtils;
import com.topeastic.hadoop.utils.FileUtils;
import com.topeastic.hadoop.utils.StringUtils;
import com.topeastic.mapreduce.job.vo.DeviceStatus;
import com.topeastic.mapreduce.job.vo.TimePair;

public class MyCoolantOutburstAnalysis {
	public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static String breakBurstResult = HdfsUtils.break_burst_result;
	private static Logger logger = Logger
			.getLogger(MyCoolantOutburstAnalysis.class);

	public static class Map extends Mapper<Object, Text, Text, Text> {
		String[] inputArray = null;
		double electric = 0;// 工作电流
		double insideTemp = 0; // 室内温度--制冷时使用
		double insidePadTemp = 0; // 室内盘管温度--制冷时使用
		double outTemp = 0; // 室外温度--制热时使用
		double outPadTemp = 0; // 室外盘管温度--制热时使用
		double exhaustTemp = 0;// 排气温度
		double setTemp = 0;// 设定温度
		double dsh = 0;// 排气温差
		Long timeValue = 0l;// 时间戳值
		String[] hexArray = null;
		String deviceType = null;
		String workModel = null;
		String shellTempDefend = null;
		String removeFrost = null;
		String isRrunning = null;

		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			// System.out.println("value    ="+value.toString());
			inputArray = value.toString().split(",");
			// 检验输入的数据是否合法
			if (inputArray.length != 5) {
				return;
			}
			// 检验输入的指令是否合法
			if (inputArray[4].length() < 162) {
				return;
			}
			// 获取设备类型 01--空调
			deviceType = inputArray[4].substring(18, 20);
			if (!"01".equals(deviceType)) {
				return;
			}
			// 过滤掉用户操作时产生的日志
			if (!inputArray[0].equals(inputArray[2])) {
				return;
			}
			// 过滤掉当天的数据
			// if (!DateUtils.isToday(inputArray[1])){
			// return;
			// }

			// 将结果F4F5开头的指令传转成16进制的字符串数组数据
			hexArray = StringUtils.getHexString(inputArray[4]);

			// 判断是否开机，静音，睡眠状态，三者满足其一，此条数据过滤掉
			if (StringUtils.isRunning(hexArray[2])) {
				isRrunning = "y";
			} else {
				isRrunning = "n";
				return;
			}
			removeFrost = hexArray[46];// 除霜标志位
			if (!StringUtils.isStrongWind(hexArray[0])
					&& StringUtils.isRunning(hexArray[2])) {
				return;
			}
			if (StringUtils.isSleep(hexArray[1])
					|| StringUtils.isMute(hexArray[20])) {
				return;
			}
			// 获取工作模式，制冷还是制热
			workModel = StringUtils.getWoreMode(hexArray[2]);
			if (workModel.length() != 4) {
				return;
			} else {
				workModel = workModel.substring(2, workModel.length());
			}
//			System.out.println("removeFrost    =" + removeFrost);
//			System.out.println("removeFrost    ="
//					+ StringUtils.isRemoveFrost(removeFrost));
			// 判断制冷模式下
			if ("10".equals(workModel) || "01".equals(workModel)) {
				timeValue = Long.parseLong(inputArray[1]);
				electric = StringUtils.getIabElectric(hexArray[39]);
				insideTemp = StringUtils
						.getInsideActualTemperature(hexArray[4]); // 获取实际室内温度
				
				insidePadTemp = StringUtils
						.getInsidePadTemperature(hexArray[5]);// 获取室内盘管温度

				outTemp = StringUtils.getOutsideEnviTemperature(hexArray[28],
						workModel);// 获取实际室外温度
				outPadTemp = StringUtils.getOutsideCodenTemperature(
						hexArray[29], workModel);// 获取实际室外冷凝器温度
				exhaustTemp = StringUtils
						.getCompressorExhaustTemperature(hexArray[30]);// 获取排气温度
				setTemp = StringUtils.getDeviceSetTemperature(hexArray[3]);// 获取设定温度
				shellTempDefend = hexArray[51];
				System.out.println("模式为： "+("10".equals(workModel)?"制冷":"制热"));
				System.out.println("室内温度insideTemp："+insideTemp);
				System.out.println("室内盘管温度insidePadTemp："+insidePadTemp);
				
				double insideTempRange = StringUtils.calWencha(insideTemp,
						insidePadTemp, workModel);// 室内温差
				System.out.println("室外温度outTemp："+outTemp);
				System.out.println("室外冷凝器温度outPadTemp："+outPadTemp);
				double outTempRange = StringUtils.calWencha(outPadTemp,
						outTemp, workModel);// 室外温差
				double setTempRange = StringUtils.calWencha(insideTemp,
						setTemp, workModel);// 计算设定温差--设定温度-室内温度
				 System.out.println("室内温差insideTempRange    ="+insideTempRange);
				 System.out.println("室外温差outTempRange    ="+outTempRange);
				// 对制冷模式下进行分组
				// String c = null;
				if ("10".equals(workModel)) {

					dsh = StringUtils.calExhaustWencha(exhaustTemp,
							insidePadTemp, outPadTemp, workModel);
					context.write(new Text(inputArray[3] + ";" + workModel
							+ "|"), new Text(electric + ";" + insideTempRange
							+ ";" + outTempRange + ";" + setTempRange + ";"
							+ dsh + ";" + shellTempDefend + ";" + workModel
							+ ";" + timeValue + ";" + isRrunning + ";"
							+ removeFrost));

				}

				// 对制热模式下进行分组
				if ("01".equals(workModel)) {
					dsh = StringUtils.calExhaustWencha(exhaustTemp,
							insidePadTemp, outPadTemp, workModel);

					context.write(new Text(inputArray[3] + ";" + workModel
							+ "|"), new Text(electric + ";" + insideTempRange
							+ ";" + outTempRange + ";" + setTempRange + ";"
							+ dsh + ";" + shellTempDefend + ";" + workModel
							+ ";" + timeValue + ";" + isRrunning + ";"
							+ removeFrost));
				}
			}
		}
	}

	public static class Reduce extends Reducer<Text, Text, Text, Text> {
		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			double electric = 0; // 记录工作电流--取平均值
			double inTempRang = 0; // 记录室内温差
			double outTempRang = 0; // 记录室外时的温差
			double setTempRang = 0; // 记录设定的温差
			double exhaustTemp = 0; // 记录排气
			int shellNum = 0; // 记录壳体温度过高保护计数
			String workModel = "";
			String modelNo = key.toString().split(";")[0];

			TreeSet<TimePair> tr = new TreeSet<TimePair>();
			List<DeviceStatus> dStatusList = new ArrayList<DeviceStatus>();
			for (Text val : values) {
				String[] valArray = val.toString().split(";");
				if (valArray.length != 10) {
					return;
				}
				workModel = valArray[6];

				DeviceStatus ds = new DeviceStatus();
				ds.setElectric(Double.parseDouble(valArray[0]));
				ds.setExhaustTemp(Double.parseDouble(valArray[4]));
				ds.setInTempRang(Double.parseDouble(valArray[1]));
				ds.setOutTempRang(Double.parseDouble(valArray[2]));
				ds.setSetTempRang(Double.parseDouble(valArray[3]));
				ds.setShellTempDefend(StringUtils
						.compressorShellTempDefend(valArray[5]));
				ds.setWorkModel(valArray[6]);
				ds.setTime(Long.parseLong(valArray[7]));
				dStatusList.add(ds);
				tr.add(new TimePair(Long.parseLong(valArray[7]), valArray[8]));
			}

			List<Long> targetTime = new ArrayList<Long>();
			List<TimePair> trList = new ArrayList<TimePair>();
			Iterator<TimePair> iter = tr.iterator();
			while (iter.hasNext()) {
				TimePair timePair = iter.next();
				trList.add(timePair);
			}
			
			if (trList.size() <= 1) {
				return;
			}
			
			int openPoint = 0;
			boolean isFind = false;
//			for (int i = 0; i < trList.size() - 2; i++) {
//				TimePair currentTimePair = trList.get(i);
//				TimePair nextPair = trList.get(i + 1);
//				TimePair Pair=trList.get(i + 2);
//				if (nextPair.getTimeValue() - currentTimePair.getTimeValue() > 240000&&nextPair.getTimeValue() - currentTimePair.getTimeValue()<360000) {
//					if(Pair.getTimeValue() - nextPair.getTimeValue() > 240000&&Pair.getTimeValue() - nextPair.getTimeValue()<360000){
////						targetTime.add(Pair.getTimeValue());
//						openPoint=i;
//						isFind = true;
//						break;
//					}
//				} 
//			}
			for (int i = 0; i < trList.size() - 1; i++) {
				TimePair currentTimePair = trList.get(i);
				TimePair nextPair = trList.get(i + 1);
				if (nextPair.getTimeValue() - currentTimePair.getTimeValue() > 600000) {
					openPoint = i;
					isFind = true;
					break;
				} else {
					continue;
				}
			}

			if (!isFind) {
				openPoint = 0;
			}

			if (openPoint + 2 < trList.size()) {
				for (int k = openPoint + 2; k < trList.size(); k++) {
					targetTime.add(trList.get(k).getTimeValue());
				}
			}

			// 如果没有目标采集数据直接返回
			if (targetTime.size() == 0) {
				return;
			}
			Integer count = 0;
			for (DeviceStatus d : dStatusList) {
				long _time = d.getTime();
				if (targetTime.contains(_time)) {
					electric = electric + d.getElectric();
					inTempRang = inTempRang + d.getInTempRang();
					outTempRang = outTempRang + d.getOutTempRang();
					setTempRang = setTempRang + d.getSetTempRang();
					exhaustTemp = exhaustTemp + d.getExhaustTemp();
					if (d.isShellTempDefend()) {
						shellNum++;
					}
					count++;
				}
			}
			System.out.println("：  count = " + count);
			double electricAvg = Math.round(electric / count) / 5;
			double inTempRangAvg = Math.round(inTempRang / count);
			double outTempRangAvg = Math.round(outTempRang / count);
			double setTempRangAvg = Math.round(setTempRang / count);
			double exhaustTempAvg = Math.round(exhaustTemp / count);
			System.out.println("室内温差    inTempRangAvg=" + inTempRangAvg);
			System.out.println("设定温差    setTempRangAvg=" + setTempRangAvg);
			System.out.println("室外温差    outTempRangAvg=" + outTempRangAvg);
			System.out.println("排气温差    exhaustTempAvg=" + exhaustTempAvg);

			// 计算各个温差参数--制冷突发性泄露
			PortalImpl pi = new PortalImpl();
			if ("10".equals(workModel)) {
				if ((inTempRangAvg <= 2.5) && (setTempRangAvg >= 2.0)) {
					// 满足任何一个分支可判定出现突发性泄露
					if (shellNum >= 1 || exhaustTempAvg >= 50) {
						context.write(key, new Text(electricAvg + ","
								+ inTempRangAvg + "," + outTempRangAvg + ","
								+ setTempRangAvg + "," + exhaustTempAvg
								+ "\r\n"));
						return;
					}

					if (outTempRangAvg <= 2) {
						context.write(key, new Text(electricAvg + ","
								+ inTempRangAvg + "," + outTempRangAvg + ","
								+ setTempRangAvg + "," + exhaustTempAvg
								+ "\r\n"));
						return;
					}
				}
			}

			// 计算各个温差参数--制热突发性泄露
			if ("01".equals(workModel)) {
				if ((inTempRangAvg <= 5) && (setTempRangAvg >= 2.0)) {
					// 满足任何一个分支可判定出现突发性泄露
					if (shellNum >= 1 || exhaustTempAvg >= 45) {
						context.write(key, new Text(electricAvg + ","
								+ inTempRangAvg + "," + outTempRangAvg + ","
								+ setTempRangAvg + "," + exhaustTempAvg
								+ "\r\n"));
						return;
					}
				}

			}
			// 发送故障恢复信息
			try {
//				pi.appearFault("aircon", modelNo, "37", "0");
			} catch (Throwable e) {
				logger.debug("call  pi.appearFault()    exception      message    ="
						+ e.getMessage());
			}
		}
	}

	public void run() throws Exception {
		Configuration conf = new Configuration();
		String date_name=new SimpleDateFormat("yyyyMMdd").format(new Date(new Date().getTime()-24*60*60*1000));
		String inputPath = HdfsUtils.input_main+date_name+"/";
		String outputPath = HdfsUtils.output_break_burst_result;
		HdfsUtils.checkAndDel(outputPath, conf);
		String[] otherArgs = { inputPath, outputPath };
		if (otherArgs.length != 2) {
			System.err.println("Usage CoolantOutburstAnalysis_New <int> <out>");
			System.exit(2);
		}
		Job job = new Job(conf, "MyCoolantOutburstAnalysis");
		job.setJarByClass(MyCoolantOutburstAnalysis.class);
		job.setMapperClass(Map.class);
		// job.setCombinerClass(Reduce.class);
		job.setReducerClass(Reduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.setNumReduceTasks(1);

		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		boolean i = job.waitForCompletion(true);
		if (i) {
//			logger.debug("start to get file to local   ");
			System.out.println("start to get file to local   ");
			boolean isGet = HdfsUtils.getFromHDFS(outputPath + "/part-r-00000",
					breakBurstResult, conf);
			System.out.println("isGet   is   " + isGet);
//			 logger.debug("isGet   is   "+isGet);
			// 下载 完成后，解析结果文件，将结果存入mysql 数据库中 (LEAK_TYPE=0)
			if (isGet) {
				// 重命名操作
				String[] flag = FileUtils.changeFileName(breakBurstResult)
						.split(",");
				if ("true".equals(flag[0])) {
					try {
						System.out.println("==================推送信息至mysql数据库===============");
						String Path=breakBurstResult
								+ File.separator + flag[1] + ".txt";
						System.out.println("=====文件地址为："+Path+"============");
						DbUtils.readTxtFileForOutburst(Path);
					} catch (Throwable e) {
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			new MyCoolantOutburstAnalysis().run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
