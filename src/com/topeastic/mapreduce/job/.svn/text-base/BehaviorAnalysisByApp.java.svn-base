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
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import com.poss.saoss.hadoop.impl.PortalImpl;
import com.topeastic.bean.AirconStatus;
import com.topeastic.hadoop.hdfs.HdfsUtils;
import com.topeastic.hadoop.task.bean.TypeTime;
import com.topeastic.hadoop.utils.FileUtils;
import com.topeastic.hadoop.utils.StringUtils;

/**
 * 用户行为按键统计 从左到右 从上到下
 * 
 * @author root
 * 
 */
@SuppressWarnings("deprecation")
public class BehaviorAnalysisByApp {

	public static String behavior_result = HdfsUtils.behavior_result;

	/**
	 * 分区函数类。根据type确定Partition。
	 */
	public static class FirstPartitioner extends Partitioner<TypeTime, Text> {

		@Override
		public int getPartition(TypeTime arg0, Text arg1, int arg2) {

			return Math.abs(arg0.getType().hashCode() * 127) % arg2;
		}

	}

	// type 相同就属于同一组
	public static class GroupingComparator extends WritableComparator {

		protected GroupingComparator() {
			super(TypeTime.class, true);
		}

		@Override
		// Compare two WritableComparables.
		public int compare(WritableComparable w1, WritableComparable w2) {
			TypeTime ip1 = (TypeTime) w1;
			TypeTime ip2 = (TypeTime) w2;
			String l = ip1.getType();
			String r = ip2.getType();
			return l.compareTo(r);
		}

	}

	public static class Maps extends Mapper<Object, Text, TypeTime, Text> {
		// 输入数据格式：1,1422806402340,AC,AIH-W401-2059A0FCB36B,F4F50140490100FE01010101006600010400281A138080808080000000000000000000000000000000FFFFFF0000000000000080008000000000000000000000000000000000000000000000000008CAF4FB
		String[] inputArray = null;
		String deviceType = null;
		String[] hexArray = null;
		int num = 0;
		TypeTime mapkey = new TypeTime();
		AirconStatus air = new AirconStatus();
		StringBuffer mess = null;

		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			mess = new StringBuffer();
			inputArray = value.toString().split(",");

			// 过滤数据
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

			// 将结果F4F5开头的指令传转成16进制的字符串数组数据
			hexArray = StringUtils.getHexString(inputArray[4]);
			// 是否手机控制
			String flag = StringUtils.hexToBinaryString(hexArray[22]);

			// int app = 0;
			// int mac = 0;
			// int app_mac = 0;
			// int nu = 0;
			// 将既有App操作和遥控器操作的记录过滤
			if ("1".equals(flag.substring(5, 6))
					&& "1".equals(flag.substring(4, 5))) {
				// app_mac++;
				// return;
			}
			if ("0".equals(flag.substring(5, 6))
					&& "0".equals(flag.substring(4, 5))) {
				// nu++;
				// return;
			}
			mapkey.set(inputArray[3], inputArray[1]);
			// 将空调信息计入
			air = AirconStatus.getAirStatus(hexArray);
			air.setWho("nobody");
			if ("1".equals(flag.substring(5, 6))
					&& "0".equals(flag.substring(4, 5))) {
				// app++;
				air.setWho("app");
			}
			if ("0".equals(flag.substring(5, 6))
					&& "1".equals(flag.substring(4, 5))) {
				// mac++;
				air.setWho("mac");
			}

			// 有wifi操作即可
			// if ("1".equals(flag.substring(5, 6))) {
			// mapkey.set(inputArray[3], inputArray[1]);
			// // 将空调信息计入
			// air = AirconStatus.getAirStatus(hexArray);
			// context.write(mapkey, new Text(air.toString()));
			// }

			// 过滤 没有WiFi操作的记录
			// if ("0".equals(flag.substring(5, 6))) {
			// return;
			// }

			context.write(mapkey, new Text(air.toString()));
			// context.write(mapkey, new Text(app+","+mac+","+app_mac+","+nu));

		}

	}

	public static class Reduce extends Reducer<TypeTime, Text, Text, Text> {

		public void reduce(TypeTime key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			// int app = 0;
			// int mac = 0;
			// int app_mac = 0;
			// int nu = 0;
			// for (Text val : values) {
			// String[] num = val.toString().split(",");
			// app += Integer.parseInt(num[0]);
			// mac += Integer.parseInt(num[1]);
			// app_mac += Integer.parseInt(num[2]);
			// nu += Integer.parseInt(num[3]);
			//
			// }
			// context.write(new Text(key.getType() + "|"), new Text("app:" +
			// app
			// + "mac:" + mac + "app_mac:" + app_mac + "nu:" + nu));
			AirconStatus last_status = null;
			AirconStatus now_status = new AirconStatus();
			// 开关机
			int up = 0;
			int down = 0;
			// 制热
			int warm = 0;
			// 制冷
			int cold = 0;
			// 送风
			int songfeng = 0;
			// 除湿
			int wet = 0;
			// 自动
			int auto_model = 0;
			// 温度
			int temperature = 0;
			// 风速
			int low_speed = 0;
			int mid_speed = 0;
			int high_speed = 0;
			int auto_speed = 0;
			// 静音
			int quiet = 0;
			// 亮度
			int light = 0;
			// 上下风
			int up_down = 0;
			// 左右风
			int left_right = 0;
			// 定时开机
			int time_up = 0;
			// 定时关机
			int time_down = 0;
			// 睡眠模式
			int sleep = 0;
			// 电热
			int hot = 0;
			// 强力
			int strong = 0;
			for (Text airStatusVal : values) {

				AirconStatus airStatus = StringUtils.getAir(airStatusVal
						.toString());
				// if(AirconStatus.wrongStatus(airStatus))
				// continue;
				// 判断
				now_status = airStatus;

				AirconStatus tmp_status = AirconStatus.initAir(airStatus);

				// 手机App操作

				if ("app".equals(now_status.getWho())) {
					if (null != last_status) {
						// 比对2条数据
						// 上条记录为开机
						if (last_status.isRunning()) {
							// 现在为开机 即开->开
							if (now_status.isRunning()) {

								if (now_status.isUpDownWind() != last_status
										.isUpDownWind()) {
									up_down++;
								}
								if (now_status.isLeftRightWind() != last_status
										.isLeftRightWind()) {
									left_right++;
								}
								if (now_status.isTimeToUp() != last_status
										.isTimeToUp()) {
									time_up++;
								}

								if (now_status.isTimeToDown() != last_status
										.isTimeToDown()) {
									time_down++;
								}
								if(now_status.isSleep()&&!now_status.getLight().equals("0000000")){
									light++;
								}
								if(!now_status.isSleep()&&!now_status.getLight().equals(last_status.getLight())){
									light++;
								}
								// 模式的比较
								if (now_status.getModel().equals(
										last_status.getModel())) {
									if(now_status.getModel().equals("0001")){
										if(now_status.isHot()!=last_status.isHot()){
											hot++;
										}
									}

									if (now_status.getTemperature() != last_status
											.getTemperature()) {
										temperature++;
									}
									if (!last_status.isStrong()
											&& now_status.isStrong()) {
										strong++;
									}
									if (!last_status.isSleep()
											&& now_status.isSleep()) {
										sleep++;
									}
									if (!last_status.isQuiet()
											&& now_status.isQuiet()) {
										quiet++;
									}
									if (last_status.isStrong()
											&& !now_status.isStrong()
											&& !now_status.isSleep()
											&& !now_status.isQuiet()&&now_status.getSpeed().equals("auto")) {
										strong++;
									}

									if (last_status.isSleep()
											&& !now_status.isSleep()
											&& !now_status.isStrong()
											&& !now_status.isQuiet()&&now_status.getSpeed().equals("0000010")) {
										sleep++;
									}

									// 根据强力关 睡眠关 静音关比较风速
									if ((!now_status.isStrong())
											&& (!now_status.isSleep())
											&& (!now_status.isQuiet())
											&& (!now_status.getSpeed().equals(
													last_status.getSpeed()))) {
										switch (now_status.getSpeed()) {
										case "auto":
											auto_speed++;
											break;
										case "0000010":
											low_speed++;
											break;
										case "0000011":
											mid_speed++;
											break;
										case "0000100":
											high_speed++;
											break;
										default:
											break;
										}
									}

								} else {
									if (now_status.isSleep()) {
										sleep++;
									}
									if (now_status.isStrong()) {
										strong++;
									}
									if (now_status.isQuiet()) {
										quiet++;
									}
									// 模式发生了变化
									switch (now_status.getModel()) {
									// 送风
									case "0000":
										songfeng++;
										break;
									// 制热
									case "0001":
										warm++;
										if(now_status.isHot()){
											hot++;
										}
										break;
									// 制冷
									case "0010":
										cold++;
										break;
									// 除湿
									case "0011":
										wet++;
										break;
									// 自动送风
									// 自动制热
									// 自动制冷
									// 自动除湿
									default:
										auto_model++;
										break;
									}
									// 当前状态跟切换后初始化的状态比较
									if (now_status.getTemperature() != tmp_status
											.getTemperature()) {
										temperature++;
									}
									if ((!now_status.isStrong())
											&& (!now_status.isSleep())
											&& (!now_status.isQuiet())
											&& (!now_status.getSpeed().equals(
													tmp_status.getSpeed()))) {
										switch (now_status.getSpeed()) {
										case "auto":
											auto_speed++;
											break;
										case "0000010":
											low_speed++;
											break;
										case "0000011":
											mid_speed++;
											break;
										case "0000100":
											high_speed++;
											break;
										default:
											break;
										}
									}
								}

							} else {
								// 开 -> 关
								// 判断是否为定时关 ： 判断定时关的剩余时间是否为0
								if (last_status.isTimeToDown()
										&& "00000000000".equals(now_status
												.getAirTimeDown())) {
									time_down++;
								} else {
									down++;
								}
								if (now_status.isUpDownWind() != last_status
										.isUpDownWind()) {
									up_down++;
								}
								if(now_status.isLeftRightWind()!=last_status.isLeftRightWind()){
									left_right++;
								}
								// 其他比较
								if (now_status.isTimeToUp()) {
									time_up++;
								}
								if (now_status.getModel().equals(
										last_status.getModel())) {
									if (now_status.getTemperature() != last_status
											.getTemperature()) {
										temperature++;
									}
									if(!now_status.getSpeed().equals(
											last_status.getSpeed())){
										switch (now_status.getSpeed()) {
										case "auto":
											auto_speed++;
											break;
										case "0000010":
											low_speed++;
											break;
										case "0000011":
											mid_speed++;
											break;
										case "0000100":
											high_speed++;
											break;
										default:
											break;
										}
									}
								} else {
									switch (now_status.getModel()) {
									// 送风
									case "0000":
										songfeng++;
										break;
									// 制热
									case "0001":
										warm++;
										break;
									// 制冷
									case "0010":
										cold++;
										break;
									// 除湿
									case "0011":
										wet++;
										break;
									// 自动送风
									// 自动制热
									// 自动制冷
									// 自动除湿
									default:
										auto_model++;
										break;
									}
									if (now_status.getTemperature() != tmp_status
											.getTemperature()) {
										temperature++;
									}
								}

							}
						} else {
//now							// 现在为开机 关->开
							if (now_status.isRunning()) {
								// 判断是否为定时开 ：判断定时开的声音时间是否为0
								if (last_status.isTimeToUp()
										&& "00000000000".equals(now_status
												.getAirTimeUp())) {
									time_up++;
								} else {
									up++;
								}

								// 其他比较
								if (now_status.isTimeToUp()) {
									time_up++;
								}
								if (now_status.isTimeToDown()) {
									time_down++;
								}
								if (now_status.isUpDownWind() != last_status
										.isUpDownWind()) {
									up_down++;
								}
								if (now_status.isLeftRightWind() != tmp_status
										.isLeftRightWind()) {
									left_right++;
								}
								if (now_status.isSleep()) {
									sleep++;
								}
								if (now_status.isStrong()) {
									strong++;
								}
						
								if (now_status.isHot()) {
									hot++;
								}
								if(now_status.isQuiet()){
									quiet++;
								}
								if(now_status.isSleep()&&!now_status.getLight().equals("0000000")){
									light++;
								}
								if(!now_status.isSleep()&&!now_status.getLight().equals(last_status.getLight())){
									light++;
								}
								if (now_status.getModel().equals(
										last_status.getModel())) {
									
									if (now_status.getTemperature() != last_status
											.getTemperature()) {
										temperature++;
									}
									if ((!now_status.isStrong())
											&& (!now_status.isSleep())
											&& (!now_status.isQuiet())
											&& (!now_status.getSpeed().equals(
													last_status.getSpeed()))) {
										switch (now_status.getSpeed()) {
										case "auto":
											auto_speed++;
											break;
										case "0000010":
											low_speed++;
											break;
										case "0000011":
											mid_speed++;
											break;
										case "0000100":
											high_speed++;
											break;
										default:
											break;
										}
									}
									
								} else {
									switch (now_status.getModel()) {
									// 送风
									case "0000":
										songfeng++;
										break;
									// 制热
									case "0001":
										warm++;
										break;
									// 制冷
									case "0010":
										cold++;
										break;
									// 除湿
									case "0011":
										wet++;
										break;
									// 自动送风
									// 自动制热
									// 自动制冷
									// 自动除湿
									default:
										auto_model++;
										break;
									}
									if (now_status.getTemperature() != tmp_status
											.getTemperature()) {
										temperature++;
									}
								}
								if ((!now_status.isStrong())
										&& (!now_status.isSleep())
										&& (!now_status.isQuiet())
										&& (!now_status.getSpeed().equals(
												tmp_status.getSpeed()))) {
									switch (now_status.getSpeed()) {
									case "auto":
										auto_speed++;
										break;
									case "0000010":
										low_speed++;
										break;
									case "0000011":
										mid_speed++;
										break;
									case "0000100":
										high_speed++;
										break;
									default:
										break;
									}
								}

							} else {
								// 关 -> 关
								
								if (now_status.getModel().equals(
										last_status.getModel())) {
									if (now_status.getTemperature() != last_status
											.getTemperature()) {
										temperature++;
									}
									if(!now_status.getSpeed().equals(last_status.getSpeed())){
										switch (now_status.getSpeed()) {
										case "auto":
											auto_speed++;
											break;
										case "0000010":
											low_speed++;
											break;
										case "0000011":
											mid_speed++;
											break;
										case "0000100":
											high_speed++;
											break;
										default:
											break;
										}
									}
								} else {
									switch (now_status.getModel()) {
									// 送风
									case "0000":
										songfeng++;
										break;
									// 制热
									case "0001":
										warm++;
										break;
									// 制冷
									case "0010":
										cold++;
										break;
									// 除湿
									case "0011":
										wet++;
										break;
									// 自动送风
									// 自动制热
									// 自动制冷
									// 自动除湿
									default:
										auto_model++;
										break;
									}
									if(!now_status.getSpeed().equals(tmp_status.getSpeed())){
										switch (now_status.getSpeed()) {
										case "auto":
											auto_speed++;
											break;
										case "0000010":
											low_speed++;
											break;
										case "0000011":
											mid_speed++;
											break;
										case "0000100":
											high_speed++;
											break;
										default:
											break;
										}
									}
									if (now_status.getTemperature() != tmp_status
											.getTemperature()) {
										temperature++;
									}
								}
								if (last_status.isTimeToUp() != now_status
										.isTimeToUp()) {
									time_up++;
								}
								if( now_status.isUpDownWind() != last_status.isUpDownWind()){
									up_down++;
								}
								if(now_status.isLeftRightWind()!=last_status.isLeftRightWind()){
									left_right++;
								}
							}
						}

					} else {
						if (now_status.isRunning()) {
							up++;
							switch (now_status.getModel()) {
							// 送风
							case "0000":
								songfeng++;
								break;
							// 制热
							case "0001":
								warm++;
								break;
							// 制冷
							case "0010":
								cold++;
								break;
							// 除湿
							case "0011":
								wet++;
								break;
							// 自动送风
							// 自动制热
							// 自动制冷
							// 自动除湿
							default:
								auto_model++;
								break;
							}
							if (now_status.isHot()) {
								hot++;
							}
							if (now_status.isLeftRightWind()) {
								left_right++;
							}
							if (now_status.isQuiet()) {
								quiet++;
							}
							if (now_status.isSleep()) {
								sleep++;
							}
							if (now_status.isStrong()) {
								strong++;
							}
							if (now_status.isTimeToDown()) {
								time_down++;
							}
							if (now_status.isTimeToUp()) {
								time_up++;
							}
							if (now_status.isUpDownWind()) {
								up_down++;
							}
							if (now_status.getTemperature() != tmp_status
									.getTemperature()) {
								temperature++;
							}
							if (!now_status.getSpeed().equals(
									tmp_status.getSpeed())) {
								switch (now_status.getSpeed()) {
								case "auto":
									auto_speed++;
									break;
								case "0000010":
									low_speed++;
									break;
								case "0000011":
									mid_speed++;
									break;
								case "0000100":
									high_speed++;
									break;
								case "0000001":
									quiet++;
									break;
								default:
									break;
								}
							}

						} else {
							down++;
							if (now_status.getTemperature() != tmp_status
									.getTemperature()) {
								temperature++;
							}
							if (now_status.isTimeToUp()) {
								time_up++;
							}
						}
					}
				}

				last_status = now_status;

			}

			// String behavior = "开机次数：" + up + "|关机次数：" + down + "|制热次数：" +
			// warm
			// + "|制冷次数：" + cold + "|送风次数：" + songfeng + "|除湿次数：" + wet
			// + "|自动次数：" + auto_model + "|温度调节次数：" + temperature
			// + "|小风次数：" + low_speed + "|中风次数：" + mid_speed + "|强风次数："
			// + high_speed + "|自动风次数：" + auto_speed + "|静音次数：" + quiet
			// + "|亮度调节次数：" + light + "|上下风设置次数：" + up_down + "|左右风设置次数："
			// + left_right + "|定时开机设置次数：" + time_up + "|定时关机设置次数："
			// + time_down + "|睡眠设置次数：" + sleep + "|电热设置次数：" + hot
			// + "|强力设置次数：" + strong;
			String behavior = up + "|" + down + "|" + warm + "|" + cold + "|"
					+ songfeng + "|" + wet + "|" + auto_model + "|"
					+ temperature + "|" + low_speed + "|" + mid_speed + "|"
					+ high_speed + "|" + auto_speed + "|" + quiet + "|" + light
					+ "|" + up_down + "|" + left_right + "|" + time_up + "|"
					+ time_down + "|" + sleep + "|" + hot + "|" + strong;
			String runningTime = new SimpleDateFormat("yyyyMMdd")
					.format(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
			context.write(new Text(key.getType() + "|"), new Text(runningTime
					+ "|" + behavior));
			/*
			 * for (AirconStatus airStatus : values) { context.write(new
			 * Text(key.getType() + "|"), new Text(airStatus.toString())); }
			 */
		}
	}

	public void run() throws Exception {
		Configuration conf = new Configuration();
		String date_name = new SimpleDateFormat("yyyyMMdd").format(new Date(
				new Date().getTime() - 24 * 60 * 60 * 1000));
		String inputPath = HdfsUtils.input_main + date_name + "/";
		String outputPath = HdfsUtils.output_behavior_result;
		HdfsUtils.checkAndDel(outputPath, conf);
		String[] otherArgs = { inputPath, outputPath };
		if (otherArgs.length != 2) {
			System.err.println("Usage TimeStatisticalAnalysis <int> <out>");
			System.exit(2);
		}
		Job job = new Job(conf, "BehaviorAnalysis");
		job.setJarByClass(BehaviorAnalysisByApp.class);
		job.setMapperClass(Maps.class);
		job.setReducerClass(Reduce.class);
		// 二次排序
		job.setPartitionerClass(FirstPartitioner.class);
		job.setGroupingComparatorClass(GroupingComparator.class);

		job.setMapOutputKeyClass(TypeTime.class);
		job.setMapOutputValueClass(Text.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.setNumReduceTasks(1);

		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		boolean i = job.waitForCompletion(true);
		if (i) {
			boolean isGet = HdfsUtils.getFromHDFS(outputPath + "/part-r-00000",
					behavior_result, conf);
			if (isGet) {
				// 重命名操作
				String[] flag = FileUtils.changeFileName(behavior_result)
						.split(",");
				if ("true".equals(flag[0])) {
					// 推送信息
					putToMysql(behavior_result + File.separator + flag[1]
							+ ".txt");
				}
			}
		}
	}

	@SuppressWarnings("resource")
	public static void putToMysql(String filePath) {
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
					String[] values = lineTxt.split("\\|");
					// values size 为 22 21个功能模块
					try {
						pi.addAirBehaviorByAppInfo(values[0], values[1].trim(),
								values[2], values[3], values[4], values[5],
								values[6], values[7], values[8], values[9],
								values[10], values[11], values[12], values[13],
								values[14], values[15], values[16], values[17],
								values[18], values[19], values[20], values[21],
								values[22]);

					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				System.out.println("=============================");
				System.out.println("=======消息推送成功！！！！！=======");
				System.out.println("=============================");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (true != false) {
			System.out.println("asd");
		}

	}

}
