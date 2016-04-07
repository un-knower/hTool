package com.topeastic.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

public class WordCount {

	public static class Map extends MapReduceBase implements
			Mapper<LongWritable, Text, Text, Text> {
		private final static Text one = new Text("1");
		private Text word = new Text();
		static int lines=0;
		public void map(LongWritable key, Text value,
				OutputCollector<Text, Text> output, Reporter reporter)
				throws IOException {
			lines++;
			String line = value.toString();
			String[] str = line.split("-");
			Date date = new Date(Integer.valueOf(str[0]) - 1900,
					Integer.valueOf(str[1]) - 1, Integer.valueOf(str[2]));
			word.set(new SimpleDateFormat("YYYY-MM-dd").format(date));
			one.set(new SimpleDateFormat("YYYY-MM-dd").format(new Date(date
					.getTime() + 24 * 60 * 60 * 1000)));
			output.collect(word, one);
		}
	}

	public static class Reduce extends MapReduceBase implements
			Reducer<Text, Text, Text, Text> {
		String[] date=new String[3];
		public void reduce(Text key, Iterator<Text> values,
				OutputCollector<Text, Text> output, Reporter reporter)
				throws IOException {
			String next_date = values.next().toString();
			String current_date = key.toString();
			String[] date_temp = { current_date, next_date };
			if (date[0] == null) {
				date[0]=date_temp[0];
				date[1]=date_temp[0];
				date[2]=date_temp[1];
				return;
			}
			System.out.println(date[0]+"$"+date[1]+"$"+date[2]);
			System.out.println(date_temp[0]+"@"+date_temp[1]);
			if (date[2].equals(date_temp[0])) {
				date[1] = date_temp[0];
				date[2] = date_temp[1];
				return;
			} else {
				if (date[0].equals(date[1])) {
					date[0]=date_temp[0];
					date[1]=date_temp[0];
					date[2]=date_temp[1];
					return ;
				}else{
					Text output_key=new Text(date[0]);
					Text output_value=new Text(date[1]);
				
					date[0]=date_temp[0];
					date[1]=date_temp[0];
					date[2]=date_temp[1];
					
					System.out.println("输出一项："+output_key+","+output_value);
					output.collect(output_key, output_value);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		JobConf conf = new JobConf(WordCount.class);
		conf.setJobName("wordcount");

		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);

		conf.setMapperClass(Map.class);
		conf.setCombinerClass(Reduce.class);
		conf.setReducerClass(Reduce.class);

		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);

		FileInputFormat.setInputPaths(conf, new Path(
				"hdfs://192.168.7.56:9000/user/asd"));
		FileOutputFormat.setOutputPath(conf, new Path(
				"hdfs://192.168.7.56:9000/user/test01"));

		JobClient.runJob(conf);
	}

}
