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
import org.apache.hadoop.util.GenericOptionsParser;

import com.topeastic.hadoop.hdfs.HdfsUtils;
import com.topeastic.hadoop.utils.DateUtils;
import com.topeastic.hadoop.utils.PropertiesUtils;
import com.topeastic.hadoop.utils.StringUtils;


public class DifferentialAnalysis_Average {
	public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static String plotResultPath = PropertiesUtils.getConfigProperty("local_plot_result");
//	public static String plotResultPath = PropertiesUtils.getConfigProperty("local_plot_result");
//	public static String routinBurstResult = PropertiesUtils.getConfigProperty("routin_burst_result");
//	public static String operator_id = PropertiesUtils.getConfigProperty("operator_id");
	public static class Map extends Mapper<Object, Text, Text, Text> {
		// 输入数据格式：1,1422806402340,AC,AIH-W401-2059A0FCB36B,F4F50140490100FE01010101006600010400281A138080808080000000000000000000000000000000FFFFFF0000000000000080008000000000000000000000000000000000000000000000000008CAF4FB
		String[] inputArray = null;
		Long timeValue = 0l;//时间戳值
		String[] valueArray =null;
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {			
			//String pathName = ((FileSplit) context.getInputSplit()).getPath().toString();
			inputArray = value.toString().split("\\|");
			//检验输入的数据是否合法
			if (inputArray.length != 2) {
				return;
			}
			//过滤掉微分周期外的数据 --微分周期为20天
			valueArray = inputArray[1].split(";");
			if(valueArray.length<3){
				return;
			}
			if(!DateUtils.isPlotPeriod(-20,String.valueOf(valueArray[0]))){
				return;
			}

			context.write(new Text(valueArray[0]),
					new Text(valueArray[0] + ","+valueArray[1]+","+valueArray[2]));
		}
	}

	public static class Reduce extends Reducer<Text, Text, Text, Text> {
		// reduce解析map输出，将value中数据按照左右表分别保存，然后求笛卡尔积，输出
		//@SuppressWarnings({ "rawtypes", "unused" })
		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			double inTempRang = 0; // 记录室内温差
			String workModel = "";
			Integer count = 0;
			String[] valArray=null;
			for (Text val : values) {
				valArray = val.toString().split(",");
				if(valArray.length !=3){
					return;
				}
				workModel = valArray[2];
				inTempRang = Double.parseDouble(valArray[1]);
			}
			double inTempRangAvg = Math.round(inTempRang / count);
			SimpleDateFormat sf = new SimpleDateFormat(TIME_FORMAT);
			//系统当前时间
	    	Date d = new Date();
	    	String s = sf.format(d);
	    	Long ls = DateUtils.stringDateConvert2long(s,TIME_FORMAT);
			context.write(key, new Text(String.valueOf(ls) + ";"+inTempRangAvg+";"+workModel+"\r\n"));
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args)
				.getRemainingArgs();
		if (otherArgs.length != 2) {
			System.err.println("Usage DifferentialAnalysis_Average <int> <out>");
			System.exit(2);
		}
		Job job = new Job(conf, "DifferentialAnalysis_Average");
		job.setJarByClass(DifferentialAnalysis_Average.class);
		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
	
	
	public void run() throws Exception {
		Configuration conf = new Configuration();

		String inputPath = "hdfs://192.168.7.56:9000/user/hadoop/input_wenfen";
		String outputPath = "hdfs://192.168.7.56:9000/user/hadoop/output_weifen_average";
		//HdfsUtils.checkAndDel(outputPath, conf);
		String[] otherArgs = { inputPath, outputPath };
		if (otherArgs.length != 2) {
			System.err.println("Usage DifferentialAnalysis_Average <int> <out>");
			System.exit(2);
		}
		Job job = new Job(conf, "DifferentialAnalysis_Average");
		job.setJarByClass(DifferentialAnalysis_Average.class);
		job.setMapperClass(Map.class);
		// job.setCombinerClass(Reduce.class);
		job.setReducerClass(Reduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		boolean i = job.waitForCompletion(true);
		if (i) {
			System.out.println("start to get file to local   ");
			boolean isGet = HdfsUtils.getFromHDFS(outputPath + "/part-r-00000", plotResultPath,conf);
			System.out.println("isGet   is   "+isGet);
			String dst = "hdfs://192.168.7.35:9000/user/hadoop/input_main";
			if(isGet){
				File resultFile = new File( plotResultPath+File.separator+"part-r-00000"); 
				SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		        String str = sdf.format(new Date()).replace("-", "").replace(" ", "").replace(":", "");
		        File newFile =  new File(resultFile.getAbsoluteFile()+"_"+str);
		        if(resultFile.renameTo(newFile)){
		        	HdfsUtils.put2HDFS(newFile.getAbsolutePath(), dst, conf);
		        }
				//AirconStatusInfoDao dao = (AirconStatusInfoDao) DAOFactory.getAirconCtrlInstance();
			}
		}
	}
	
	
}
