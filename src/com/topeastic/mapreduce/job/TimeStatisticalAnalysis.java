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
import com.topeastic.hadoop.task.bean.TypeTime;
import com.topeastic.hadoop.hdfs.HdfsUtils;
import com.topeastic.hadoop.utils.FileUtils;
import com.topeastic.hadoop.utils.StringUtils;

/**
 * 设备（空调）运行时间统计 & 电量统计
 * 
 * @author root
 * 
 */
@SuppressWarnings("deprecation")
public class TimeStatisticalAnalysis {

	public static String timeTotalResult = HdfsUtils.time_total_result;

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

	public static class Map extends Mapper<Object, Text, TypeTime, Text> {
		// 输入数据格式：1,1422806402340,AC,AIH-W401-2059A0FCB36B,F4F50140490100FE01010101006600010400281A138080808080000000000000000000000000000000FFFFFF0000000000000080008000000000000000000000000000000000000000000000000008CAF4FB
		String[] inputArray = null;
		String deviceType = null;
		String[] hexArray = null;
		int Uab = 0;
		double Iab = 0;
		private TypeTime mapkey = new TypeTime();

		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
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
			// 将结果F4F5开头的指令传转成16进制的字符串数组数据
			hexArray = StringUtils.getHexString(inputArray[4]);
			mapkey.set(inputArray[3], inputArray[1]);
			if (StringUtils.isSingle(hexArray[37])) {
				// 单相电源
				Uab = StringUtils.Uxx(hexArray[33], hexArray[34]);
				Iab = StringUtils.Ixx(hexArray[39]);
			} else {
				// 三相电源
				
			}
			// 判断当前设备的开关机状态
			if (StringUtils.isRunning(hexArray[2])) {
				// 电压为0或电流为0时，先做舍弃处理。后面再做分析处理
//				if (Uab == 0 || Iab == 0) {
//					
//					return;
//				}
				// 获取当前设备的时间
				context.write(mapkey, new Text("1|" + inputArray[1] + "|" + Uab
						+ "|" + Iab));
			} else {
				context.write(mapkey, new Text("0|" + inputArray[1]));
			}

		}

	}

	public static class Reduce extends Reducer<TypeTime, Text, Text, Text> {

		public void reduce(TypeTime key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			long now_time = 0;
			long time = 0;
			long last_time = 0;
			// 功率
			double p = 0;
			// 时间
			double t = 0;
			// 电量
			double w = 0;
			for (Text val : values) {
				String[] status = val.toString().split("\\|");
				if ("1".equals(status[0])) {
					now_time = Long.parseLong(status[1]);
					if (last_time != 0) {
						t = (now_time - last_time) / 1000;
						w += p * t;
						time += t;
					}
					// 功率
					if (status[2] != null && status[3] != null) {
						p = Double.parseDouble(status[2])
								* Double.parseDouble(status[3]);
					}

					last_time = now_time;

				} else {
					now_time = Long.parseLong(status[1]);
					if (last_time != 0) {
						t = (now_time - last_time) / 1000;
						w += p * t;
						time += t;
					}
					last_time = 0;
				}
			}
			if (now_time == last_time) {
				// 获取那天的截止时间
				t = 5*60;
				w += p * t;
				time += t;
			}
//			if (time != 0 && w == 0) {
//				return;
//			}

			String date = new SimpleDateFormat("yyyyMMdd").format(new Date(
					new Date().getTime() - 24 * 60 * 60 * 1000));
			double W = w / 3600000;
			if (W > 0 && W < 0.05) {
				context.write(new Text(key.getType() + "|"), new Text(date
						+ "|" + time + "|0.1"));
			} else {
				context.write(new Text(key.getType() + "|"), new Text(date
						+ "|" + time + "|" + String.format("%.1f", W)));
			}
//  		测试代码
//			Date date = null;
//			for (Text val : values) {
//				String[] status = val.toString().split("\\|");
//				date = new Date(Long.parseLong(status[1]));
//				if ("1".equals(status[0])) {
//					context.write(new Text(key.getType()), new Text("开机时间为："
//							+ date.toLocaleString() + "电压为：" + status[2]
//							+ "电流为：" + status[3]));
//				} else {
//					context.write(new Text(key.getType()), new Text("关机时间为："
//							+ date.toLocaleString()));
//				}
//			}
		}
	}

	public void run() throws Exception {
		Configuration conf = new Configuration();
		String date_name=new SimpleDateFormat("yyyyMMdd").format(new Date(new Date().getTime()-24*60*60*1000));
		String inputPath = HdfsUtils.input_main+date_name+"/";
		String outputPath = HdfsUtils.output_time_total_result;
		HdfsUtils.checkAndDel(outputPath, conf);
		String[] otherArgs = { inputPath, outputPath };
		if (otherArgs.length != 2) {
			System.err.println("Usage TimeStatisticalAnalysis <int> <out>");
			System.exit(2);
		}
		Job job = new Job(conf, "TimeStatisticalAnalysis");
		job.setJarByClass(TimeStatisticalAnalysis.class);
		job.setMapperClass(Map.class);
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
		System.out.println("======job结果："+i+"========");
		if (i) {
			System.out.println("=======开始下载结果.......========");
			boolean isGet = HdfsUtils.getFromHDFS(outputPath + "/part-r-00000",
					timeTotalResult, conf);
			System.out.println("下载结果："+isGet+"========");
			if (isGet) {
				// 重命名操作
				System.out.println("=======下载成功，重命名操作....==========");
				String[] flag = FileUtils.changeFileName(timeTotalResult)
						.split(",");
				if ("true".equals(flag[0])) {
					// 推送信息
					System.out.println("==========重命名成功，推送到数据库....========");
					putToMysql(timeTotalResult + File.separator + flag[1]
							+ ".txt");
				}
			}
		}
	}

	/**
	 * 推送信息到mysql
	 * 
	 * @param filePath
	 */
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
					try {
						pi.addAirInfo(values[0], values[1].trim(), values[2],
								values[3]);
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String time = new SimpleDateFormat("yyyyMMdd").format(new Date(
				new Date().getTime() - 24 * 60 * 60 * 1000));
		System.out.println(time);

	}

}
