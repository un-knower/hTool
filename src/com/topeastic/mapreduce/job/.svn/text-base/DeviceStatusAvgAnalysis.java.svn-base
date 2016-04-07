package com.topeastic.mapreduce.job;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.Logger;

import com.poss.saoss.hadoop.impl.PortalImpl;
import com.topeastic.hadoop.hdfs.HdfsUtils;
import com.topeastic.hadoop.utils.FileUtils;
import com.topeastic.hadoop.utils.StringUtils;
@SuppressWarnings("deprecation")
public class DeviceStatusAvgAnalysis {

	private static final Logger logger = Logger
			.getLogger(DeviceStatusAvgAnalysis.class);
	public String avgResultPath = HdfsUtils.local_avg_result;

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
		String acType = null;// 分节流阀类型和EEV
		String[] hexArray = null;
		String deviceType = null;
		String workModel = null;
		String isRrunning = null;

		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {

			// System.out.println("value    ="+value.toString());
			inputArray = value.toString().split(",");
			// 检验输入的数据是否合法
			if (inputArray.length != 5) {
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
			// 过滤掉当天的数据
			// if (!DateUtils.isToday(inputArray[1])){
			// return;
			// }

			// 将结果F4F5开头的指令传转成16进制的字符串数组数据
			hexArray = StringUtils.getHexString(inputArray[4]);
			if (StringUtils.isRunning(hexArray[2])) {
				isRrunning = "y";
			} else {
				isRrunning = "n";
				return;
			}

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

			// 判断制冷模式下
			if ("10".equals(workModel) || "01".equals(workModel)) {
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
				double outTempRange = StringUtils.calWencha(outPadTemp,
						outTemp, workModel);// 室外温差
				double setTempRange = StringUtils.calWencha(insideTemp,
						setTemp, workModel);// 计算设定温差--设定温度-室内温度
				// System.out.println("insideTempRange    ="+insideTempRange);
				// System.out.println("outTempRange    ="+outTempRange);
				// 对制冷模式下进行分组
				dsh = StringUtils.calExhaustWencha(exhaustTemp, insidePadTemp,
						outPadTemp, workModel);
				if ("10".equals(workModel)) {
					if (outTemp >= 25 && outTemp <= 45) {
						context.write(new Text(inputArray[3] + ";" + workModel
								+ "|"), new Text(electric + ";"
								+ insideTempRange + ";" + outTempRange + ";"
								+ setTempRange + ";" + dsh + ";" + workModel));
					}
				}

				// 对制热模式下进行分组
				if ("01".equals(workModel)) {
					if (outTemp >= -12 && outTemp <= 11) {
						context.write(new Text(inputArray[3] + ";" + workModel
								+ "|"), new Text(electric + ";"
								+ insideTempRange + ";" + outTempRange + ";"
								+ setTempRange + ";" + dsh + ";" + workModel));
					}
				}
			}
		}
	}

	public static class Reduce extends Reducer<Text, Text, Text, Text> {
		// reduce解析map输出，将value中数据按照左右表分别保存，然后求笛卡尔积，输出
		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			Integer count = 0;
			double electric = 0; // 记录工作电流--取平均值
			double inTemp = 0; // 记录室内温差
			double outsideTemp = 0; // 记录室外时的温差
			double setTemp = 0; // 记录设定的温差
			double exhaustTemp = 0; // 记录排气
			for (Text val : values) {
				String[] valArray = val.toString().split(";");
				if (valArray.length != 6) {
					continue;
				}
				electric = electric + Double.parseDouble(valArray[0]);
				inTemp = inTemp + Double.parseDouble(valArray[1]);
				outsideTemp = outsideTemp + Double.parseDouble(valArray[2]);
				setTemp = setTemp + Double.parseDouble(valArray[3]);
				exhaustTemp = exhaustTemp + Double.parseDouble(valArray[4]);
				count++;
			}

			double electricAvg = Math.round(electric / count);
			double inTempAvg = Math.round(inTemp / count);
			double outTempAvg = Math.round(outsideTemp / count);
			double setTempAvg = Math.round(setTemp / count);
			double exhaustTempAvg = Math.round(exhaustTemp / count);

			context.write(key, new Text(electricAvg + "," + inTempAvg + ","
					+ outTempAvg + "," + setTempAvg + "," + exhaustTempAvg
					+ "\r\n"));
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
	 * System.err.println("Usage DeviceStatusAnalysis <int> <out>");
	 * System.exit(2); } Job job = new Job(conf, "DeviceStatusAnalysis");
	 * job.setJarByClass(DeviceStatusAvgAnalysis.class);
	 * job.setMapperClass(Map.class); job.setReducerClass(Reduce.class);
	 * job.setOutputKeyClass(Text.class); job.setOutputValueClass(Text.class);
	 * FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
	 * FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
	 * System.exit(job.waitForCompletion(true) ? 0 : 1); }
	 */

	public void run() throws Exception {
		System.out.println("进入DeviceStatus JOB============");
		Configuration conf = new Configuration();
		String date_name = new SimpleDateFormat("yyyyMMdd").format(new Date(
				new Date().getTime() - 24 * 60 * 60 * 1000));
		String inputPath = HdfsUtils.input_main + date_name + "/";
		// test
		String outputPath = HdfsUtils.output_avg_result;
		HdfsUtils.checkAndDel(outputPath, conf);
		String[] otherArgs = { inputPath, outputPath };
		if (otherArgs.length != 2) {
			System.err.println("Usage DeviceStatusAvgAnalysis <int> <out>");
			System.exit(2);
		}
		Job job = new Job(conf, "DeviceStatusAvgAnalysis");
		job.setJarByClass(DeviceStatusAvgAnalysis.class);
		job.setMapperClass(Map.class);
		// job.setCombinerClass(Reduce.class);
		job.setReducerClass(Reduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		boolean i = job.waitForCompletion(true);
		System.out.println("i=" + i);
		if (i) {
			// logger.debug("start to get file to local   ");
			System.out.println("start to get file to local   ");
			boolean isGet = HdfsUtils.getFromHDFS(outputPath + "/part-r-00000",
					avgResultPath, conf);
			System.out.println("isGet   is   " + isGet);
			// logger.debug("isGet   is   "+isGet);
			// 下载 完成后，解析结果文件，将结果存入mysql 数据库中
			if (isGet) {
				// 重命名操作
				String result = FileUtils.changeFileName(avgResultPath);
				String[] flag = result.split(",");
				if ("true".equals(flag[0])) {
					try {
						readTxtFile(avgResultPath + File.separator + flag[1]
								+ ".txt");
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}
	}

	/**
	 * 将信息推送到mysql数据库
	 * 
	 * @param filePath
	 * @throws Throwable
	 */
	public static void readTxtFile(String filePath) throws Throwable {
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			PortalImpl pi = new PortalImpl();
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					if ("".equals(lineTxt)) {
						continue;
					}
					String[] str = lineTxt.split("[;|,]");
					pi.addAirconStatusInfo("", str[0].trim(), "",
							str[3].trim(), str[4].trim(), "", "",
							str[2].trim(), str[1].trim(), str[6].trim());
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Throwable {

		String path = "F://faultFile//part-r-000001";
		readTxtFile(path);
		// PortalImpl pi=new PortalImpl();
		// pi.addAirconCtrlInfo("", "AIH-W401-2059a0b70485", "", "4.0", "2.0",
		// "13.0", "56.0", "0.0", "0", "0", "01","");
		// pi.addAirconCtrlInfo("", "AIH-W401-2059a0b70485", "", "4.0", "2.0",
		// "13.0", "56.0", "0.0", "0", "0", "01");
	}

}
