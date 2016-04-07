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

import com.poss.saoss.hadoop.impl.PortalImpl;
import com.topeastic.hadoop.hdfs.HdfsUtils;
import com.topeastic.hadoop.utils.DateUtils;
import com.topeastic.hadoop.utils.FileUtils;
import com.topeastic.hadoop.utils.PropertiesUtils;
import com.topeastic.hadoop.utils.StringUtils;
import com.topeastic.mapreduce.job.vo.DeviceStatus;
import com.topeastic.mapreduce.job.vo.FrostPair;
import com.topeastic.mapreduce.job.vo.PlotPair;
import com.topeastic.mapreduce.job.vo.ReferenceData;
import com.topeastic.mapreduce.job.vo.TemperaturePair;
import com.topeastic.mapreduce.job.vo.TimePair;

public class RoutinOutburstAnalysis_New {
	public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	// public static String plotResultPath =
	// PropertiesUtils.getConfigProperty("local_plot_result");
	public static String routinBurstResult = HdfsUtils.routin_burst_result;

	// public static String operator_id =
	// PropertiesUtils.getConfigProperty("operator_id");
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
				System.out.println("#########start##############");
				System.out.println("开机但是不是强风 时间点    ="
						+ DateUtils.longConvert2DateString(
								Long.parseLong(inputArray[1]), TIME_FORMAT)
						+ "     ");
				System.out.println("#########end##############");
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
				// System.out.println("insideTempRange    ="+insideTempRange);
				// System.out.println("outTempRange    ="+outTempRange);
				// 对制冷模式下进行分组
				// System.out.println("workModel    ="+workModel);
				// System.out.println("outTemp   ="+outTemp);

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
				System.out.println("other model    =" + workModel);
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
			TreeSet<TemperaturePair> ts = new TreeSet<TemperaturePair>();
			TreeSet<TimePair> tr = new TreeSet<TimePair>();
			TreeSet<FrostPair> tf = new TreeSet<FrostPair>();
			TreeSet<PlotPair> tp = new TreeSet<PlotPair>();

			List<DeviceStatus> dStatusList = new ArrayList<DeviceStatus>();
			String[] valArray = null;
			for (Text val : values) {
				valArray = val.toString().split(",");
				//长度必须为11或者3
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
			List<Long> targetTime = new ArrayList<Long>();
			List<Long> baseTime = new ArrayList<Long>();
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
						System.out
								.println("************打印开始*********************");
						System.out.println("制热时开机时间点：  moduleNO = "
								+ key.toString());

						System.out.println("制热时开机时间点 数值：  timeValue = "
								+ currentFrostPair.getTimeValue());
						System.out.println("制热时开机时间点 i=   ("
								+ i
								+ ")：  time = "
								+ DateUtils.longConvert2DateString(
										currentFrostPair.getTimeValue(),
										TIME_FORMAT) + "     "
								+ currentFrostPair.getIsRun());
						System.out
								.println("===========================================");
						System.out.println("制热时开机时间点 数值：  timeValue = "
								+ nextFrostPair.getTimeValue());
						System.out.println("制热时开机时间点 i=   ("
								+ i
								+ ")：  time = "
								+ DateUtils.longConvert2DateString(
										nextFrostPair.getTimeValue(),
										TIME_FORMAT) + "     "
								+ nextFrostPair.getIsRun());
						System.out
								.println("*************打印结束*****************************");
						openPoint = i;
						isFind = true;
						break;
					}
					else{
						continue;
					}
				}

				if (!isFind) {
					openPoint = 0;
					FrostPair currentFrostPair = fpList.get(openPoint);
					FrostPair nextFrostPair = fpList.get(openPoint + 1);
					System.out.println("************打印开始*********************");
					System.out.println("制热时开机时间点：  moduleNO = "
							+ key.toString());

					System.out.println("制热时开机时间点 数值：  timeValue = "
							+ currentFrostPair.getTimeValue());
					System.out.println("制热时开机时间点 i=   ("
							+ openPoint
							+ ")：  time = "
							+ DateUtils.longConvert2DateString(
									currentFrostPair.getTimeValue(),
									TIME_FORMAT) + "     "
							+ currentFrostPair.getIsRun());
					System.out
							.println("===========================================");
					System.out.println("制热时开机时间点 数值：  timeValue = "
							+ nextFrostPair.getTimeValue());
					System.out.println("制热时开机时间点 i=   ("
							+ openPoint
							+ ")：  time = "
							+ DateUtils.longConvert2DateString(
									nextFrostPair.getTimeValue(), TIME_FORMAT)
							+ "     " + nextFrostPair.getIsRun());
					System.out
							.println("*************打印结束*****************************");
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
					System.out.println("开机时间时长：  超过4小时 = ");
					if (inList.size() == 0) {
						System.out.println("除霜次数：  cishu = " + inList.size());
						int cnt = 0;
						for (int k = openPoint + 6; k < fpList.size() - 1; k++) {
							if (fpList.get(k).getIsRun().equals("y")) {
								targetTime.add(fpList.get(k).getTimeValue());
								if (cnt < 3) {
									baseTime.add(fpList.get(k).getTimeValue());
								}
								cnt++;
							}
						}
					} else {
						for (int m = 0; m < inList.size() - 1; m++) {
							int p = inList.get(m) + 5;
							if (p <= fpList.size() - 1) {
								if (fpList.get(p).getIsRun().equals("y")) {
									targetTime
											.add(fpList.get(p).getTimeValue());
								}
							}
						}
						int c = 0;
						// 如果只有1次除霜标志，以首次为基准
						if (inList.size() == 1) {
							c = inList.get(0);
						} else {
							c = inList.get(1);
						}
						for (int k = 1; k <= 3; k++) {
							baseTime.add(fpList.get(c + k).getTimeValue());
						}
					}
				} else {
					// 开机时间不满4小时，且无除霜标志位
					System.out.println("开机时间时长：  不   超过4小时 = ");
					if (inList.size() == 0) {
						for (int n = openPoint + 6; n < fpList.size() - 1; n++) {
							if (fpList.get(n).getIsRun().equals("y")) {
								targetTime.add(fpList.get(n).getTimeValue());
							}
						}
						// 如果没有除霜标志位
						if (targetTime.size() < 3) {
							return;
						}
						for (int t = 0; t < 3; t++) {
							baseTime.add(targetTime.get(t));
						}
					} else {
						for (int s = 0; s < inList.size() - 1; s++) {
							int q = inList.get(s) + 5;
							if (q < fpList.size() - 1
									&& (fpList.get(q).getIsRun().equals("y"))) {
								targetTime.add(fpList.get(q).getTimeValue());
							}
						}
						// 如果检测到有除霜标志位，那么以除霜结束后三组数据作为基准
						int d = 0;
						// 如果只有1次除霜标志，以首次为基准
						if (inList.size() == 1) {
							d = inList.get(0);
						} else {
							d = inList.get(1);
						}
						for (int k = 1; k <= 3; k++) {
							if (d + k <= fpList.size() - 1) {
								baseTime.add(fpList.get(d + k).getTimeValue());
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
						System.out.println("制冷时开机时间点：  moduleNO = "
								+ key.toString());
						System.out.println("制冷时开机时间点：  timeCurrent = "
								+ DateUtils.longConvert2DateString(
										currentTimePair.getTimeValue(),
										TIME_FORMAT));
						System.out.println("制冷时开机时间点：  timeNext = "
								+ DateUtils.longConvert2DateString(
										nextPair.getTimeValue(), TIME_FORMAT));
						openPoint = i;
						isFind = true;
						break;
					} else {
						continue;
					}
				}
				if (!isFind) {
					openPoint = 0;
					TimePair currentTimePair = trList.get(openPoint);
					TimePair nextPair = trList.get(openPoint + 1);
					System.out.println("************打印开始*********************");
					System.out.println("制冷时开机时间点：  moduleNO = "
							+ key.toString());

					System.out.println("制冷时开机时间点 数值：  timeValue = "
							+ currentTimePair.getTimeValue());
					System.out
							.println("制冷时开机时间点 i=   ("
									+ openPoint
									+ ")：  time = "
									+ DateUtils.longConvert2DateString(
											currentTimePair.getTimeValue(),
											TIME_FORMAT) + "     "
									+ currentTimePair.getStatus());
					System.out
							.println("===========================================");
					System.out.println("制冷时开机时间点 数值：  timeValue = "
							+ nextPair.getTimeValue());
					System.out.println("制冷时开机时间点 i=   ("
							+ openPoint
							+ ")：  time = "
							+ DateUtils.longConvert2DateString(
									nextPair.getTimeValue(), TIME_FORMAT)
							+ "     " + nextPair.getStatus());
					System.out
							.println("*************打印结束*****************************");
				}
				boolean isHalf = Tools
						.isRunningHalfHoursCool(openPoint, trList);
				// 如果不超过半小时，该条数据过滤掉
				if (isHalf) {
					int num = 0;
					for (int k = openPoint + 6; k < trList.size(); k++) {
						if (openPoint + 6 <= trList.size() - 1) {
							targetTime.add(trList.get(k).getTimeValue());
						}
						if (num < 3) {
							baseTime.add(trList.get(k).getTimeValue());
						}
						num++;
					}
				}
			}

			// 如果没有目标采集数据直接返回
			if (targetTime.size() == 0) {
				System.out.println("没有目标采集数据：  moduleNO = " + key.toString());
				System.out.println("工况：  outTemp = "
						+ valArray[valArray.length - 1]);
				return;
			}
			// 计算基准数据，连续开机15分钟的数据
			double inTempRangBase = 0;
			double outTempRangBase = 0;
			// 如果采样的时间点小于半小时，及小于6个样本，那么此次判断不成立
			if (targetTime.size() <= 3 && "10".equals(workModel)) {
				System.out.println("采样时间组数小于8：  moduleNO = " + key.toString());
				return;
			}
			if (baseTime.size() < 1) {
				System.out
						.println("baseTime is null：  key = " + key.toString());
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
					System.out.println("baseTime     ="
							+ DateUtils.longConvert2DateString(_time,
									TIME_FORMAT));
				}

				if (targetTime.contains(_time)) {
					electric = electric + d.getElectric();
					// if(d.getInTempRang()<=10){
					// inTempRang = inTempRang+ d.getInTempRang();
					// System.out.println("d.getInTempRang()<=10     ="+d.getInTempRang());
					// count1++;
					// }
					inTempRang = inTempRang + d.getInTempRang();
					System.out.println("d.getInTempRang()     ="
							+ d.getInTempRang());
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
				System.out
						.println("redis exsit reference record..............................");
			} else {
				System.out
						.println("redis no reference record..............................");
				inTempRangBase = inTempRangBase / baseTime.size();
				outTempRangBase = outTempRangBase / baseTime.size();
				String refValue = inTempRangBase + ";" + outTempRangBase;
				Tools.addReferenceDataFromRedis(keyValue, refValue);
			}

			System.out.println("inTempRangBase     =" + inTempRangBase);
			System.out.println("outTempRangBase      =" + outTempRangBase);
			System.out.println("baseTime.size()     =" + baseTime.size());
			// double electricAvg =Math.round(electric / count2)/5;
			double electricAvg = electric / count / 5;
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
			if ("10".equals(workModel)) {
				System.out.println("室内温差    inTempRangAvg=" + inTempRangAvg);
				System.out.println("设定温差    setTempRangAvg=" + setTempRangAvg);
				System.out.println("室外温差    outTempRangAvg=" + outTempRangAvg);
				System.out.println("排气温差    exhaustTempAvg=" + exhaustTempAvg);
				System.out.println("室内基准    inTempRangBase     ="
						+ inTempRangBase);
				System.out.println("室外基准    outTempRangBase      ="
						+ outTempRangBase);

				if (((inTempRangAvg <= 10) && (inTempRangAvg >= 3))
						&& (setTempRangAvg >= 2)
						&& ((outTempRangAvg - outTempRangBase) <= 7)) {
					// 如果排气温度大于或等于50度
					if (exhaustTempAvg >= 50) {
						context.write(key, new Text(electricAvg + ","
								+ inTempRangAvg + "," + outTempRangAvg + ","
								+ setTempRangAvg + "," + exhaustTempAvg + "\n"));
						return;
					}
					// 室内温差与本机基准相差9度以上
					if (Math.abs(inTempRangAvg - inTempRangBase) >= 9) {
						context.write(key, new Text(electricAvg + ","
								+ inTempRangAvg + "," + outTempRangAvg + ","
								+ setTempRangAvg + "," + exhaustTempAvg + "\n"));
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
											+ exhaustTempAvg + "\n"));
									return;
								}
							}
						}
					}
				}
			}

			// 计算各个温差参数--制热常规性泄露
			if ("01".equals(workModel)) {
				if (((inTempRangAvg <= 22) && (inTempRangAvg >= 5.5))
						&& (setTempRangAvg >= 2)) {
					// 如果排气温度大于或等于50度
					if (exhaustTempAvg >= 50) {
						context.write(key, new Text(electricAvg + ","
								+ inTempRangAvg + "," + outTempRangAvg + ","
								+ setTempRangAvg + "," + exhaustTempAvg + "\n"));
						return;
					}
					// 室内温差与本机基准相差9度以上
					if (Math.abs(outTempRangAvg - outTempRangBase) >= 9) {
						context.write(key, new Text(electricAvg + ","
								+ inTempRangAvg + "," + outTempRangAvg + ","
								+ setTempRangAvg + "," + exhaustTempAvg + "\n"));
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
											+ exhaustTempAvg + "\n"));
									return;
								}

							}
						}
					}
				}
			}
		}
	}

	/**
	 * @param args
	 */
	/*
	 * public static void main(String[] args) throws Exception { // TODO
	 * Auto-generated method stub Configuration conf = new Configuration();
	 * String[] otherArgs = new GenericOptionsParser(conf, args)
	 * .getRemainingArgs(); if (otherArgs.length != 2) {
	 * System.err.println("Usage RoutinOutburstAnalysis <int> <out>");
	 * System.exit(2); } Job job = new Job(conf, "RoutinOutburstAnalysis");
	 * job.setJarByClass(RoutinOutburstAnalysis_New.class);
	 * job.setMapperClass(Map.class); job.setReducerClass(Reduce.class);
	 * job.setOutputKeyClass(Text.class); job.setOutputValueClass(Text.class);
	 * FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
	 * FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
	 * System.exit(job.waitForCompletion(true) ? 0 : 1); }
	 */

	public void run() throws Exception {
		Configuration conf = new Configuration();
		String date_name=new SimpleDateFormat("yyyyMMdd").format(new Date(new Date().getTime()-24*60*60*1000));
		String inputPath = HdfsUtils.input_main+date_name+"/";
		String outputPath =HdfsUtils.output_routin_burst_result;
		HdfsUtils.checkAndDel(outputPath, conf);
		String[] otherArgs = { inputPath, outputPath };
		if (otherArgs.length != 2) {
			System.err.println("Usage RoutinOutburstAnalysis <int> <out>");
			System.exit(2);
		}
		Job job = new Job(conf, "RoutinOutburstAnalysis");
		job.setJarByClass(RoutinOutburstAnalysis_New.class);
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
						readTxtFile(routinBurstResult + File.separator
								+ flag[1]);
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 解析txt文件
	 * 
	 * @return F:\faultFile
	 * @throws Throwable
	 * @throws IOException
	 */
	public static void readTxtFile(String filePath) throws Throwable {

		String encoding = "GBK";
		File file = new File(filePath);
		PortalImpl pi = new PortalImpl();
		HashMap<String, String> hs = new HashMap<String, String>();
		if (file.isFile() && file.exists()) { // 判断文件是否存在
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					file), encoding);// 考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			HashMap<String, String> map = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				System.out.println("lineTxt    =" + lineTxt);
				if ("".equals(lineTxt)) {
					continue;
				}
				String[] str = lineTxt.split("[;|,]");
				while (str.length > 1) {
					map = new HashMap<String, String>();
					map.put("modelNo", str[0].trim());
					map.put("work_mode", str[1].trim());
					map.put("work_condition", str[2].trim());
					map.put("electric_iab", str[3].trim());
					map.put("inside_temp", str[4].trim());
					map.put("outside_temp", str[5].trim());
					map.put("set_temp", str[6].trim());
					map.put("exhaust_temp", str[7].trim());

					pi.addAirconCtrlInfo("", str[0].trim(), "", str[5].trim(),
							str[5].trim(), str[6].trim(), str[7].trim(),
							str[3].trim(), "0", "01", str[1].trim(),
							str[2].trim());
				}
			}
			read.close();
		} else {
			System.out.println("找不到指定的文件");
		}

	}

	public static void main(String[] args) throws Throwable {
		// 已确认修改
		String path = "E://part-r-00000";
		readTxtFile(path);

	}

}
