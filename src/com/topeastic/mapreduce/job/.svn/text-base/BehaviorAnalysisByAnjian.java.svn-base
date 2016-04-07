package com.topeastic.mapreduce.job;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.junit.Test;

import com.topeastic.bean.AirconStatus;
import com.topeastic.hadoop.hdfs.HdfsUtils;
import com.topeastic.hadoop.utils.FileUtils;
import com.topeastic.hadoop.utils.StringUtils;

/**
 * 用户行为按键统计 从左到右 从上到下
 * 
 * @author root
 * 
 */
public class BehaviorAnalysisByAnjian {

	// public static String timeTotalResult = HdfsUtils.time_total_result;

	public static class TypeTime implements WritableComparable<TypeTime> {

		String type;
		String time;

		public String getType() {
			return type;
		}

		public void set(String type, String time) {
			this.type = type;
			this.time = time;
		}

		public String getTime() {
			return time;
		}

		@Override
		public void readFields(DataInput arg0) throws IOException {
			// TODO Auto-generated method stub
			type = arg0.readUTF();
			time = arg0.readUTF();
		}

		@Override
		public void write(DataOutput arg0) throws IOException {
			// TODO Auto-generated method stub
			arg0.writeUTF(type);
			arg0.writeUTF(time);
		}

		@Override
		public int compareTo(TypeTime o) {
			if (!type.equals(o.type)) {
				return type.compareTo(o.type);
			} else if (!time.equals(o.time)) {
				return time.compareTo(o.time);
			} else
				return 0;
		}

		@Override
		public int hashCode() {
			return type.hashCode() * 2 + time.hashCode();
		}

		@Override
		public boolean equals(Object other) {
			if (other == null) {
				return false;
			}
			if (this == other) {
				return true;
			}
			if (other instanceof TypeTime) {
				TypeTime some = (TypeTime) other;
				return some.type == type && some.time == time;
			} else {
				return true;
			}
		}

	}

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
		private final TypeTime mapkey = new TypeTime();
		AirconStatus air = new AirconStatus();
		StringBuffer mess = null;

		@SuppressWarnings("deprecation")
		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			mess = new StringBuffer();
			inputArray = value.toString().split(",");

			// 过滤数据
			// 检验输入的数据是否合法
			if (inputArray.length != 7) {
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
			// 是否手机控制 是否遥控器控制
			String flag = StringUtils.hexToBinaryString(hexArray[22]);
			air = AirconStatus.getAirStatus(hexArray);
			air.setWho("nobody");
			if ("1".equals(flag.substring(5, 6))
					&& "0".equals(flag.substring(4, 5))) {
				air.setWho("app");
			}
			if ("0".equals(flag.substring(5, 6))
					&& "1".equals(flag.substring(4, 5))) {
				air.setWho("mac");
			}

			context.write(mapkey, new Text(air.toString()));
		}

	}

	public static class Reduce extends Reducer<TypeTime, Text, Text, Text> {

		@SuppressWarnings("deprecation")
		public void reduce(TypeTime key, Iterable<Text> values,
				Context context) throws IOException, InterruptedException {
			AirconStatus last_status = null;
			AirconStatus now_status = new AirconStatus();
			Text value = new Text();
			String result = "";
			// 制冷按键
			int cold = 0;
			// 制热按键
			int warm = 0;
			// 加按键
			int up = 0;
			// 减按键
			int down = 0;
			// 开关机按键
			int boot = 0;
			// 除湿按键
			int wet = 0;
			// 风速按键
			int wind = 0;
			// 快速冷热
			int coldWarm = 0;
			// 自动智能
			int intelligent = 0;
			// 上下风向
			int upWind = 0;
			// 左右风向
			int leftWind = 0;
			// 净化
			int clean = 0;
			// 辅热 体感
			int somatosensory = 0;
			// 睡眠 定时
			int sleep_clock = 0;
			// 亮度 显示切换
			int brightness_show = 0;
			// 节能 双模
			int energySaving_dualMode = 0;

			for (Text airStatusVal : values) {
				AirconStatus airStatus = StringUtils.getAir(airStatusVal
						.toString());
				
				// 判断
				now_status = airStatus;

				AirconStatus tmp_status = AirconStatus.initAir(airStatus);
				// mac操作
				
				if ("mac".equals(now_status.getWho())) {
					
				}
				last_status = now_status;
				
			}
			result = "cold:" + cold + "|" + "up:" + up + "|" + "boot:" + boot
					+ "|" + "warm:" + warm + "|" + "down:" + down + "|"
					+ "wet:" + wet + "|" + "wind:" + wind + "|" + "coldWarm:"
					+ coldWarm + "|" + "intelligent:" + intelligent + "|"
					+ "upWind:" + upWind + "|" + "leftWind:" + leftWind + "|"
					+ "clean:" + clean + "|" + "somatosensory:" + somatosensory
					+ "|" + "sleep_clock:" + sleep_clock + "|"
					+ "brightness_show:" + brightness_show + "|"
					+ "energySaving_dualMode:" + energySaving_dualMode;
			value.set(result);
			context.write(new Text(key.getType() + "|"), value);

			// Date date = null;
			// for (Text val : values) {
			// String[] status = val.toString().split("\\|");
			// date = new Date(Long.parseLong(status[1]));
			// if ("1".equals(status[0])) {
			// context.write(new Text(key.getType()), new Text("开机时间为："
			// + date.toLocaleString()));
			// } else {
			// context.write(new Text(key.getType()), new Text("关机时间为："
			// + date.toLocaleString()));
			// }
			// }
		}
	}

	public void run() throws Exception {
		Configuration conf = new Configuration();

		String inputPath = HdfsUtils.input_main;
		String outputPath = HdfsUtils.output_time_total_result;
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
		job.setMapOutputValueClass(String[].class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.setNumReduceTasks(1);

		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		boolean i = job.waitForCompletion(true);
		// if(i){
		// boolean isGet = HdfsUtils.getFromHDFS(outputPath + "/part-r-00000",
		// timeTotalResult, conf);
		// if(isGet){
		// // 重命名操作
		// String[] flag = FileUtils.changeFileName(timeTotalResult)
		// .split(",");
		// if("true".equals(flag[0])){
		// //推送信息
		//
		// }
		// }
		// }
	}

	public static boolean isChange(String last_one, String next_one) {
		return last_one.equals(next_one);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int num = 0;
		System.out.println(++num);

	}

	public static Map<String, String> initMapStatus(String[] status) {

		Map map = new HashMap<String, String>();
		map.put("room_wind_speed", StringUtils.hexToBinaryString(status[0]));
		map.put("sleep_model", StringUtils.hexToBinaryString(status[1]));
		map.put("work_model", StringUtils.hexToBinaryString(status[2]));
		map.put("setRoomT", StringUtils.hexToBinaryString(status[3]));
		map.put("setRoomH", StringUtils.hexToBinaryString(status[6]));
		map.put("T_add", StringUtils.hexToBinaryString(status[9]));
		map.put("T_show", StringUtils.hexToBinaryString(status[10]));
		map.put("clock", StringUtils.hexToBinaryString(status[11]));
		map.put("wet", StringUtils.hexToBinaryString(status[18]));
		map.put("room_status1", StringUtils.hexToBinaryString(status[19]));
		map.put("room_status2", StringUtils.hexToBinaryString(status[20]));
		map.put("room_status3", StringUtils.hexToBinaryString(status[21]));
		map.put("warm", StringUtils.hexToBinaryString(status[46]));
		map.put("wet_clock", StringUtils.hexToBinaryString(status[46]));
		map.put("light", StringUtils.hexToBinaryString(status[61]));
		return map;
	}

	@SuppressWarnings("deprecation")
	public static long getEndTime(long start_time) {
		Date date = new Date(start_time);
		int days = date.getDate();
		int hours = date.getHours();
		int mins = date.getMinutes();
		if (hours == 23 && mins >= 29) {
			date.setDate(days + 1);
			date.setHours(23);
			date.setMinutes(30);
			date.setSeconds(0);
			return date.getTime();
		} else {
			date.setHours(23);
			date.setMinutes(30);
			date.setSeconds(0);
			return date.getTime();
		}
	}
}
