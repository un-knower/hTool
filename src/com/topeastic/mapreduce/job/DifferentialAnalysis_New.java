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
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import com.topeastic.hadoop.hdfs.HdfsUtils;
import com.topeastic.hadoop.utils.DateUtils;
import com.topeastic.hadoop.utils.FileUtils;
import com.topeastic.hadoop.utils.StringUtils;
import com.topeastic.mapreduce.job.vo.DeviceStatus;
import com.topeastic.mapreduce.job.vo.FrostPair;
import com.topeastic.mapreduce.job.vo.TimePair;

@SuppressWarnings("deprecation")
public class DifferentialAnalysis_New {
	public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static String plotResultPath = HdfsUtils.local_plot_result;

	public static class Map extends Mapper<Object, Text, Text, Text> {
		// 输入数据格式：1,1422806402340,AC,AIH-W401-2059A0FCB36B,F4F50140490100FE01010101006600010400281A138080808080000000000000000000000000000000FFFFFF0000000000000080008000000000000000000000000000000000000000000000000008CAF4FB
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
			// String pathName = ((FileSplit)
			// context.getInputSplit()).getPath().toString();
			inputArray = value.toString().split(",");
			// 检验输入的数据是否合法
			if (inputArray.length != 5) {
				return;
			}
			// 过滤掉微分周期外的数据 --微分周期为20天
			if (!DateUtils.isPlotPeriod(-20, String.valueOf(inputArray[1]))) {
				return;
			}
			// 过滤掉用户操作时产生的日志
			if (!inputArray[0].equals(inputArray[2])) {
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
			System.out.println("removeFrost    =" + removeFrost);
			System.out.println("removeFrost    ="
					+ StringUtils.isRemoveFrost(removeFrost));
			// 判断制冷模式下
			// workModel="10";
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

				double insideTempRange = StringUtils.calWencha(insideTemp,
						insidePadTemp, workModel);// 室内温差

				// 对制冷模式下进行分组
				System.out.println("workModel    =" + workModel);
				System.out.println("outTemp    =" + outTemp);

				String c = null;
				if ("10".equals(workModel)) {
					// 开始分组， 制冷情况下
					if (outTemp < 24 || outTemp > 51) {
						return;
					}
					c = StringUtils.getCoolGroups(outTemp);
					dsh = StringUtils.calExhaustWencha(exhaustTemp,
							insidePadTemp, outPadTemp, workModel);
					context.write(new Text(inputArray[3] + ";" + workModel
							+ ";" + c + "|"), new Text(insideTempRange + ","
							+ workModel + "," + timeValue + "," + isRrunning
							+ "," + removeFrost));
				}
				// 对制热模式下进行分组
				if ("01".equals(workModel)) {
					dsh = StringUtils.calExhaustWencha(exhaustTemp,
							insidePadTemp, outPadTemp, workModel);
					// 开始分组， 制热情况下
					if (outTemp < -16 || outTemp > 18) {
						return;
					}
					c = StringUtils.getHotGroups(outTemp);
					context.write(new Text(inputArray[3] + ";" + workModel
							+ ";" + c + "|"), new Text(insideTempRange + ","
							+ workModel + "," + timeValue + "," + isRrunning
							+ "," + removeFrost));
				}
			} else {
				return;
			}
		}
	}

	public static class Reduce extends Reducer<Text, Text, Text, Text> {
		// reduce解析map输出，将value中数据按照左右表分别保存，然后求笛卡尔积，输出
		// @SuppressWarnings({ "rawtypes", "unused" })
		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			double inTempRang = 0; // 记录室内温差
			String workModel = "";
			Integer count = 0;
			TreeSet<TimePair> tr = new TreeSet<TimePair>();
			TreeSet<FrostPair> tf = new TreeSet<FrostPair>();

			List<DeviceStatus> dStatusList = new ArrayList<DeviceStatus>();
			String[] valArray = null;
			for (Text val : values) {
				valArray = val.toString().split(",");
				if (valArray.length != 5) {
					return;
				}
				workModel = valArray[1];
				// 如果是制热，需要找出除霜标志位
				if ("01".equals(workModel)) {
					tf.add(new FrostPair(Long.valueOf(valArray[2].trim()),
							StringUtils.isRemoveFrost(valArray[4]), valArray[3]));
				}
				DeviceStatus ds = new DeviceStatus();
				ds.setInTempRang(Double.parseDouble(valArray[0]));
				ds.setWorkModel(valArray[1]);
				ds.setTime(Long.parseLong(valArray[2]));
				dStatusList.add(ds);
				tr.add(new TimePair(Long.parseLong(valArray[2]), valArray[3]));
			}

			// 进行是制热还是制冷的判断
			List<Long> targetTime = new ArrayList<Long>();
			int openPoint = 0;
			boolean isFind = false;
			if ("01".equals(workModel)) {
				List<FrostPair> fpList = new ArrayList<FrostPair>();
				Iterator<FrostPair> iter = tf.iterator();
				while (iter.hasNext()) {
					FrostPair frostPair = iter.next();
					fpList.add(frostPair);
				}
				if (fpList.size() <= 1) {
					return;
				}

				for (int i = 0; i < fpList.size() - 1; i++) {
					FrostPair currentFrostPair = fpList.get(i);
					FrostPair nextFrostPair = fpList.get(i + 1);

					// 如果下一条数据减去前一条数据差值大于10分钟，那么认为开机点是当前时间点
					if (nextFrostPair.getTimeValue()
							- currentFrostPair.getTimeValue() >= 600000) {
						openPoint = i;
						isFind = true;
						break;
					}
					{
						continue;
					}
					/**
					 * 如果前一次是关机状态，后一次是开机状态， 那么从后一次开始遍历有没开机时间达到30分钟，或者4小时
					 * 
					 */

				}

				if (!isFind) {
					openPoint = 0;
					long criticalTimeValue = fpList.get(openPoint)
							.getTimeValue() - 600000;
					if (!DateUtils.isToday(criticalTimeValue + "")) {
						return;
					}

				}
				
				// 判断是否开机半小时
				boolean isHalf = Tools.isRunningHalfHours(openPoint, fpList);
				// 如果不超过半小时，该条数据过滤掉
				if (!isHalf) {
					return;
				}
				// 判断是否开机4小时
				boolean isFourHour = Tools
						.isRunningFourHours(openPoint, fpList);

				// 标记4小时内有没除霜
				List<Integer> inList = Tools.getRemoveFrost(openPoint, fpList);
				if (isFourHour) {
					// 如果没检测到除霜标志位
					if (inList.size() == 0) {
						for (int k = openPoint + 6; k <= fpList.size() - 1; k++) {
							if (fpList.get(k).getIsRun().equals("y")) {
								targetTime.add(fpList.get(k).getTimeValue());
							}
						}
					} else {
						for (int m = 0; m <= inList.size() - 1; m++) {
							int p = inList.get(m) + 5;
							if (p <= fpList.size() - 1) {
								if (fpList.get(p).getIsRun().equals("y")) {
									targetTime
											.add(fpList.get(p).getTimeValue());
								}
							}
						}
					}
				} else {
					// 开机时间不满4小时，且无除霜标志位
					System.out.println("开机时间时长：  不   超过4小时 = ");
					if (inList.size() == 0) {
						for (int n = openPoint + 6; n <= fpList.size() - 1; n++) {
							if (fpList.get(n).getIsRun().equals("y")) {
								targetTime.add(fpList.get(n).getTimeValue());
							}
						}
						// 如果没有除霜标志位
						if (targetTime.size() < 3) {
							return;
						}

					} else {
						for (int s = 0; s <= inList.size() - 1; s++) {
							int q = inList.get(s) + 5;
							if (q < fpList.size() - 1
									&& (fpList.get(q).getIsRun().equals("y"))) {
								targetTime.add(fpList.get(q).getTimeValue());
							}
						}
					}
				}
			} else {
				// 制冷开机时间点
				List<TimePair> trList = new ArrayList<TimePair>();
				Iterator<TimePair> iter = tr.iterator();
				while (iter.hasNext()) {
					TimePair timePair = iter.next();
					trList.add(timePair);
				}
				if (trList.size() <= 1) {
					return;
				}

				for (int i = 0; i < trList.size() - 1; i++) {
					TimePair currentTimePair = trList.get(i);
					TimePair nextPair = trList.get(i + 1);
					if (nextPair.getTimeValue()
							- currentTimePair.getTimeValue() > 600000) {
						openPoint = i;
						isFind = true;
						break;
					} else {
						continue;
					}
				}
				if (!isFind) {
					openPoint = 0;
					// 临界时间点，判断是否超过当天零点
					long criticalTimeValue = trList.get(openPoint)
							.getTimeValue() - 600000;
					if (!DateUtils.isToday(criticalTimeValue + "")) {
						return;
					}
				}

				boolean isHalf = Tools
						.isRunningHalfHoursCool(openPoint, trList);
				// 如果不超过半小时，该条数据过滤掉
				if (isHalf) {
					for (int k = openPoint + 6; k < trList.size(); k++) {
						if (openPoint + 6 <= trList.size() - 1) {
							targetTime.add(trList.get(k).getTimeValue());
						}
					}
				}
			}

			// 如果没有目标采集数据直接返回
			if (targetTime.size() == 0) {
				return;
			}

			// 遍历采集的数据
			for (DeviceStatus d : dStatusList) {
				Long _time = d.getTime();
				// 计算基准
				if (targetTime.contains(_time)) {
					inTempRang = inTempRang + d.getInTempRang();
					count++;
					// ts.add(new TemperaturePair(_time,inTempRang));
				}
			}
			double inTempRangAvg = Math.round(inTempRang / count);
			SimpleDateFormat sf = new SimpleDateFormat(TIME_FORMAT);
			// 系统当前时间
			Date d = new Date();
			String s = sf.format(d);
			Long ls = DateUtils.stringDateConvert2long(s, TIME_FORMAT);
			context.write(key, new Text(String.valueOf(ls) + ";"
					+ inTempRangAvg + ";" + workModel + "\r\n"));
		}
	}

	public void run() throws Exception {
		Configuration conf = new Configuration();
		String date_name = new SimpleDateFormat("yyyyMMdd").format(new Date(
				new Date().getTime() - 24 * 60 * 60 * 1000));
		String inputPath = HdfsUtils.input_main + date_name + "/";
		String outputPath = HdfsUtils.output_plot_result;
		HdfsUtils.checkAndDel(outputPath, conf);
		String[] otherArgs = { inputPath, outputPath };
		if (otherArgs.length != 2) {
			System.err.println("Usage DifferentialAnalysis_New <int> <out>");
			System.exit(2);
		}
		Job job = new Job(conf, "DifferentialAnalysis_New");
		job.setJarByClass(DifferentialAnalysis_New.class);
		job.setMapperClass(Map.class);
		// job.setCombinerClass(Reduce.class);
		job.setReducerClass(Reduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		boolean i = job.waitForCompletion(true);
		if (i) {
			// logger.debug("start to get file to local   ");
			System.out.println("start to get file to local   ");
			boolean isGet = HdfsUtils.getFromHDFS(outputPath + "/part-r-00000",
					plotResultPath, conf);
			System.out.println("isGet   is   " + isGet);
			if (isGet) {
				String[] flag = FileUtils.changeFileName(plotResultPath).split(
						",");
				if ("true".equals(flag[0])) {
					// 上传回hdfs
					HdfsUtils.put2HDFS(plotResultPath + File.separator
							+ flag[1]+".txt", inputPath, conf);
				}
			}
		}
	}

}
