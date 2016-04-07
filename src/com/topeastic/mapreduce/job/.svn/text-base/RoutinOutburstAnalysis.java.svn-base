package com.topeastic.mapreduce.job;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.log4j.Logger;

import com.poss.saoss.hadoop.impl.PortalImpl;
import com.topeastic.dao.factory.AbstractFactory;
import com.topeastic.dao.factory.MakeCoolFactory;
import com.topeastic.dao.factory.MakeWarmlFactory;
import com.topeastic.dao.factory.Processable;
import com.topeastic.hadoop.hdfs.HdfsUtils;
import com.topeastic.hadoop.utils.ConstantParam;
import com.topeastic.hadoop.utils.DateUtils;
import com.topeastic.hadoop.utils.DbUtils;
import com.topeastic.hadoop.utils.FileUtils;
import com.topeastic.hadoop.utils.PropertiesUtils;
import com.topeastic.hadoop.utils.StringUtils;
import com.topeastic.mapreduce.job.vo.DeviceStatus;
import com.topeastic.mapreduce.job.vo.FrostPair;
import com.topeastic.mapreduce.job.vo.PlotPair;
import com.topeastic.mapreduce.job.vo.ReferenceData;
import com.topeastic.mapreduce.job.vo.TimePair;

public class RoutinOutburstAnalysis {
	public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static String routinBurstResult = HdfsUtils.routin_burst_result;
	private static Logger logger = Logger
			.getLogger(RoutinOutburstAnalysis.class);

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
			String pathName = ((FileSplit) context.getInputSplit()).getPath()
					.toString();
			// 判断是否是微分结果文件
			if (pathName.contains("part-r")) {
				inputArray = value.toString().replace(" ", "").split("\\|");
				if (inputArray.length != 2) {
					return;
				} else {
					context.write(new Text(inputArray[0] + "|"), new Text(
							inputArray[1]));
					return;
				}
			}
			inputArray = value.toString().split(",");
			// 检验输入的数据是否合法
			if (inputArray.length != 5) {
				return;
			}
			// 过滤掉微分周期外的数据 --微分周期为10天
			// if(!DateUtils.isPlotPeriod(String.valueOf(inputArray[1]))){
			// return;
			// }
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
			if ("10".equals(workModel) || "01".equals(workModel)) {
				timeValue = Long.parseLong(inputArray[1]);
				electric = StringUtils.getIabElectric(hexArray[39]) / 5;
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

				double insideTempRange = StringUtils.calWencha(insideTemp,
						insidePadTemp, workModel);// 室内温差
				double outTempRange = StringUtils.calWencha(outPadTemp,
						outTemp, workModel);// 室外温差
				double setTempRange = StringUtils.calWencha(insideTemp,
						setTemp, workModel);// 计算设定温差--设定温度-室内温度

				String c = null;
				if ("10".equals(workModel)) {
					// 开始分组， 制冷情况下
					if (outTemp <= 24 || outTemp >= 50) {
						return;
					}
					c = StringUtils.getCoolGroups(outTemp);
					dsh = StringUtils.calExhaustWencha(exhaustTemp,
							insidePadTemp, outPadTemp, workModel);
					context.write(new Text(inputArray[3] + ";" + workModel
							+ ";" + c + "|"), new Text(electric + ","
							+ insideTempRange + "," + outTempRange + ","
							+ setTempRange + "," + dsh + "," + shellTempDefend
							+ "," + workModel + "," + timeValue + ","
							+ isRrunning + "," + removeFrost + "," + outTemp));
				}

				// 对制热模式下进行分组
				if ("01".equals(workModel)) {
					dsh = StringUtils.calExhaustWencha(exhaustTemp,
							insidePadTemp, outPadTemp, workModel);
					// 开始分组， 制热情况下
					if (outTemp <= -16 || outTemp >= 15) {
						return;
					}
					c = StringUtils.getHotGroups(outTemp);
					if (null == c) {
						return;
					}
					context.write(new Text(inputArray[3] + ";" + workModel
							+ ";" + c + "|"), new Text(electric + ","
							+ insideTempRange + "," + outTempRange + ","
							+ setTempRange + "," + dsh + "," + shellTempDefend
							+ "," + workModel + "," + timeValue + ","
							+ isRrunning + "," + removeFrost + "," + outTemp));
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
			double electric = 0; // 记录工作电流--取平均值
			double inTempRang = 0; // 记录室内温差
			double outTempRang = 0; // 记录室外时的温差
			double setTempRang = 0; // 记录设定的温差
			double exhaustTemp = 0; // 记录排气
			String workModel = "";
			String keyValue = key.toString();
			String modelNo = key.toString().split(";")[0];
			TreeSet<TimePair> tr = new TreeSet<TimePair>();
			TreeSet<FrostPair> tf = new TreeSet<FrostPair>();
			TreeSet<PlotPair> tp = new TreeSet<PlotPair>();

			List<DeviceStatus> dStatusList = new ArrayList<DeviceStatus>();
			String[] valArray = null;
			for (Text val : values) {
				valArray = val.toString().split(",");
				if (valArray.length != 11 && valArray.length != 3) {
					return;
				}

				// 如果是微分的数据
				if (valArray.length == 3) {
					workModel = valArray[2];
					if (DateUtils.isPlotPeriod(-40, (valArray[0]))) {
						tp.add(new PlotPair(Long.valueOf(valArray[0].trim()),
								Double.parseDouble(valArray[1].trim())));
					}

				} else {
					workModel = valArray[6];
					// 如果是制热，需要找出除霜标志位
					if ("01".equals(workModel)) {
						tf.add(new FrostPair(Long.valueOf(valArray[7].trim()),
								StringUtils.isRemoveFrost(valArray[9]),
								valArray[8]));
					}
					DeviceStatus ds = new DeviceStatus();
					ds.setElectric(Double.parseDouble(valArray[0]));
					ds.setExhaustTemp(Double.parseDouble(valArray[4]));
					ds.setInTempRang(Double.parseDouble(valArray[1]));
					ds.setOutTempRang(Double.parseDouble(valArray[2]));
					ds.setSetTempRang(Double.parseDouble(valArray[3]));
					ds.setShellTempDefend(StringUtils
							.compressorShellTempDefend(valArray[5]));
					ds.setWorkModel(valArray[6]);
					// System.out.println("reduce    time="+valArray[7]);
					ds.setTime(Long.parseLong(valArray[7]));
					dStatusList.add(ds);
					tr.add(new TimePair(Long.parseLong(valArray[7]),
							valArray[8]));
				}
			}

			// 进行是制热还是制冷的判断
			// 采集的目标数据
			List<Long> targetTime = new ArrayList<Long>();
			// 采集的基准数据
			List<Long> baseTime = new ArrayList<Long>();
			int openPoint = 0;
			boolean isFind = false;
			System.out.println("=================一开始的数据大小为："+targetTime.size());
			//数据采集 
			//制热
			if ("01".equals(workModel)) {
				// new MapreduceHandler().funcWarm(targetTime, baseTime, tf,
				// openPoint, isFind);
				AbstractFactory factory = new MakeWarmlFactory();
				Processable p = factory.create();
				p.run(targetTime, baseTime, openPoint, isFind, tf);

			} else {
				//制冷 模式下的数据采集
				// new MapreduceHandler().funcCool(targetTime, baseTime, tr,
				// openPoint, isFind);
				AbstractFactory factory = new MakeCoolFactory();
				Processable p = factory.create();
				p.run(targetTime, baseTime, openPoint, isFind, tr);
			}
			
			System.out.println("==================经过处理之后的数据大小为："+targetTime.size());
			// 如果没有目标采集数据直接返回
			if (targetTime.size() == 0) {
				return;
			}
			// 计算基准数据，连续开机15分钟的数据
			double inTempRangBase = 0;
			double outTempRangBase = 0;
			//~~~~~~
			// 如果采样的时间点小于半小时，及小于6个样本，那么此次判断不成立
			if (targetTime.size() <= 3 && "10".equals(workModel)) {
				return;
			}
			if (baseTime.size() < 1) {
				return;
			}
			// 遍历采集的数据
			Integer count = 0;
			for (DeviceStatus d : dStatusList) {
				Long _time = d.getTime();
				// 计算基准
				if (baseTime.contains(_time)) {
					inTempRangBase = inTempRangBase + d.getInTempRang();
					outTempRangBase = outTempRangBase + d.getOutTempRang();
				}

				if (targetTime.contains(_time)) {
					electric = electric + d.getElectric();
					inTempRang = inTempRang + d.getInTempRang();
					logger.debug("d.getInTempRang()     =" + d.getInTempRang());
					outTempRang = outTempRang + d.getOutTempRang();
					setTempRang = setTempRang + d.getSetTempRang();
					exhaustTemp = exhaustTemp + d.getExhaustTemp();
					count++;

				}
			}
			// 从redis里面去获取基准数据，如果没有，那么使用当天的数据作为基准数据
			ReferenceData referenceData = Tools
					.getReferenceDataFromRedis(keyValue);
			if (null != referenceData) {
				inTempRangBase = referenceData.getInTempRangBase();
				outTempRangBase = referenceData.getOutTempRangBase();
				logger.debug("redis exsit reference record..............................");
			} else {
				logger.debug("redis no reference record..............................");
				inTempRangBase = inTempRangBase / baseTime.size();
				outTempRangBase = outTempRangBase / baseTime.size();
				String refValue = inTempRangBase + ";" + outTempRangBase;
//				先屏蔽..
				Tools.addReferenceDataFromRedis(keyValue, refValue);
			}
			// double electricAvg =Math.round(electric / count2)/5;
			double electricAvg = Math.round(electric / count);
			double inTempRangAvg = Math.round(inTempRang / count);
			double outTempRangAvg = Math.round(outTempRang / count);
			double setTempRangAvg = Math.round(setTempRang / count);
			double exhaustTempAvg = Math.round(exhaustTemp / count);
			// 排序微分值
			List<PlotPair> plotList = new ArrayList<PlotPair>();
			Iterator<PlotPair> iterPlot = tp.iterator();
			while (iterPlot.hasNext()) {
				PlotPair plotPair = iterPlot.next();
				plotList.add(plotPair);
			}
			// 计算各个温差参数--制冷突发性泄露
			PortalImpl pi = new PortalImpl();
			logger.debug("mapreduce      " + key.toString());
			logger.debug("室内温差    inTempRangAvg=" + inTempRangAvg);
			logger.debug("设定温差    setTempRangAvg=" + setTempRangAvg);
			logger.debug("室外温差    outTempRangAvg=" + outTempRangAvg);
			logger.debug("排气温差    exhaustTempAvg=" + exhaustTempAvg);
			logger.debug("室内基准    inTempRangBase     =" + inTempRangBase);
			logger.debug("室外基准    outTempRangBase      =" + outTempRangBase);
			if (ConstantParam.Cooling.equals(workModel)) {
				if (((inTempRangAvg <= 10) && (inTempRangAvg >= 3))
						&& (setTempRangAvg >= 2)
						&& ((outTempRangAvg - outTempRangBase) <= 7)) {
					// 如果排气温度大于或等于50度
					if (exhaustTempAvg >= 50) {
						context.write(key, new Text(electricAvg + ","
								+ inTempRangAvg + "," + outTempRangAvg + ","
								+ setTempRangAvg + "," + exhaustTempAvg
								+ "\r\n"));
						return;
					}

					// 小条件4
					if (Math.abs(inTempRangAvg - inTempRangBase) >= 7) {
						context.write(key, new Text(electricAvg + ","
								+ inTempRangAvg + "," + outTempRangAvg + ","
								+ setTempRangAvg + "," + exhaustTempAvg
								+ "\r\n"));
						return;
					}
					// 室内温差与本机基准相差9度以上
					if (Math.abs(inTempRangAvg - inTempRangBase) >= 9) {
						context.write(key, new Text(electricAvg + ","
								+ inTempRangAvg + "," + outTempRangAvg + ","
								+ setTempRangAvg + "," + exhaustTempAvg
								+ "\r\n"));
						return;
					}
					// 微分值，一个大的判定周期内至少要3个微分值
					if (plotList.size() < 2) {
						return;
					} else {
						for (int i = 0; i < plotList.size() - 1; i++) {
							double curPlotValue = plotList.get(i)
									.getPlotValue();
							double nextPlotValue = plotList.get(i + 1)
									.getPlotValue();
							if (i + 1 <= plotList.size() - 1) {
								// double thirdPlotValue =
								// plotList.get(i+1).getPlotValue();
								// if(StringUtils.isDecrease(curPlotValue,nextPlotValue,thirdPlotValue)){
								// context.write(key, new Text(electricAvg + ","
								// + inTempRangAvg +
								// ","+outTempRangAvg+","+setTempRangAvg+","+exhaustTempAvg+"\r\n"));
								// return;
								// }
								if (nextPlotValue < curPlotValue) {
									context.write(key, new Text(electricAvg
											+ "," + inTempRangAvg + ","
											+ outTempRangAvg + ","
											+ setTempRangAvg + ","
											+ exhaustTempAvg + "\r\n"));
									return;
								}
							}
						}

					}
				}
			}

			// 计算各个温差参数--制热常规性泄露
			if (ConstantParam.Heating.equals(workModel)) {
				if (((inTempRangAvg <= 22) && (inTempRangAvg >= 5.5))
						&& (setTempRangAvg >= 2)) {
					// 如果排气温度大于或等于50度
					if (exhaustTempAvg >= 50) {
						context.write(key, new Text(electricAvg + ","
								+ inTempRangAvg + "," + outTempRangAvg + ","
								+ setTempRangAvg + "," + exhaustTempAvg
								+ "\r\n"));
						return;
					}

					// 小条件4
					if (Math.abs(inTempRangAvg - inTempRangBase) >= 6) {
						context.write(key, new Text(electricAvg + ","
								+ inTempRangAvg + "," + outTempRangAvg + ","
								+ setTempRangAvg + "," + exhaustTempAvg
								+ "\r\n"));
						return;
					}
					// 室内温差与本机基准相差9度以上
					if (Math.abs(outTempRangAvg - outTempRangBase) >= 9) {
						context.write(key, new Text(electricAvg + ","
								+ inTempRangAvg + "," + outTempRangAvg + ","
								+ setTempRangAvg + "," + exhaustTempAvg
								+ "\r\n"));
						return;
					}
					// 微分判定
					if (plotList.size() < 2) {
						return;
					} else {
						for (int i = 0; i < plotList.size() - 1; i++) {
							double curPlotValue = plotList.get(i)
									.getPlotValue();
							double nextPlotValue = plotList.get(i + 1)
									.getPlotValue();
							if (i + 1 <= plotList.size() - 1) {
								// double thirdPlotValue =
								// plotList.get(i+2).getPlotValue();
								// if(StringUtils.isDecrease(curPlotValue,nextPlotValue,thirdPlotValue)){
								// context.write(key, new Text(electricAvg + ","
								// + inTempRangAvg +
								// ","+outTempRangAvg+","+setTempRangAvg+","+exhaustTempAvg+"\r\n"));
								// return;
								// }
								if (nextPlotValue < curPlotValue) {
									context.write(key, new Text(electricAvg
											+ "," + inTempRangAvg + ","
											+ outTempRangAvg + ","
											+ setTempRangAvg + ","
											+ exhaustTempAvg + "\r\n"));
									return;
								}
							}
						}
					}
				}
			}

			// 发送故障恢复信息
			try {
				pi.appearFault("aircon", modelNo, "85", "0");
				logger.debug("modelNo    =" + modelNo + "       errorcode="
						+ 85);
			} catch (Throwable e) {
				logger.debug("call  pi.appearFault()    exception      message    ="
						+ e.getMessage());
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
	}

	public void run() throws Exception {
		Configuration conf = new Configuration();
		String date_name=new SimpleDateFormat("yyyyMMdd").format(new Date(new Date().getTime()-24*60*60*1000));
		String inputPath = HdfsUtils.input_main+date_name+"/";
		String outputPath = HdfsUtils.output_routin_burst_result;
		HdfsUtils.checkAndDel(outputPath, conf);
		String[] otherArgs = { inputPath, outputPath };
		if (otherArgs.length != 2) {
			System.err.println("Usage RoutinOutburstAnalysis <int> <out>");
			System.exit(2);
		}
		Job job = new Job(conf, "RoutinOutburstAnalysis");
		job.setJarByClass(RoutinOutburstAnalysis.class);
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
					routinBurstResult, conf);
			System.out.println("isGet   is   " + isGet);
			// logger.debug("isGet   is   "+isGet);
			// 下载 完成后，解析结果文件，将结果存入mysql 数据库中 (泄露类型是1)
			if (isGet) {

				// 重命名操作
				String[] flag = FileUtils.changeFileName(routinBurstResult)
						.split(",");
				if ("true".equals(flag[0])) {

					// AirconStatusInfoDao dao = (AirconStatusInfoDao)
					// DAOFactory.getAirconCtrlInstance();
					try {
						System.out.println("=========推送至数据库======");
						DbUtils.readTxtFile(routinBurstResult + File.separator
								+ flag[1]);

					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
