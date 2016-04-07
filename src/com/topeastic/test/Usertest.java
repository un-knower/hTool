package com.topeastic.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import com.topeastic.hadoop.hdfs.HdfsFileTask;
import com.topeastic.hadoop.hdfs.HdfsUtils;
import com.topeastic.hadoop.utils.DbUtils;
import com.topeastic.hadoop.utils.FileUtils;
import com.topeastic.hadoop.utils.PropertiesUtils;
import com.topeastic.mapreduce.job.CoolantOutburstAnalysis;
import com.topeastic.mapreduce.job.MyCoolantOutburstAnalysis;
import com.topeastic.mapreduce.job.RoutinOutburstAnalysis;

public class Usertest {
	
	/**
	 * 测试文件的上传--整体性的
	 * 
	 * 结果： 正确
	 */
	@Test
	public void textfile_up(){
		String sourcePath = PropertiesUtils.getConfigProperty("linux_path");
		Configuration conf = new Configuration();
		conf.set("hadoop.job.user", "hadoop");
		String dst = PropertiesUtils.getConfigProperty("hdfs_input_main");
		File dir = new File(sourcePath);
		HdfsFileTask.getAllFiles(dir,0,dst,conf);
	}
	
	/**
	 * failed
	 */
	@Test
	public void testput(){
		String src=PropertiesUtils.getConfigProperty("linux_path")+"/2015050600.TXT";
		String dst=PropertiesUtils.getConfigProperty("hdfs_input_main");
		Configuration conf = new Configuration();
		conf.set("hadoop.job.user", "hadoop");
		FileUtils.put2HDFS(src, dst, conf);
	}
	
	/**
	 * 测试文件检测删除\
	 * 
	 * 结果：检测文件  删除文件和文件夹
	 */
	@Test
	public void testcheckanddel(){
		HdfsUtils.checkAndDel(PropertiesUtils.getConfigProperty("hdfs_output_tufa"),  new Configuration());
	}
	
	
	/**
	 * 测试文件下发到本地系统--linux
	 */
	@Test
	public void testdown(){
		String breakBurstResult = PropertiesUtils.getConfigProperty("break_burst_result");
		String outputPath = PropertiesUtils.getConfigProperty("hdfs_output_tufa");
		Configuration conf = new Configuration();
		conf.set("hadoop.job.user", "hadoop");
		if("true".equals((FileUtils.changeFileName(breakBurstResult)).split(",")[0])){
			//logger.debug("start to get file to local   ");
			System.out.println("start to get file to local   ");
			boolean isGet = HdfsUtils.getFromHDFS(outputPath + "/part-r-00000", breakBurstResult,conf);
			System.out.println("isGet   is   "+isGet);
			//logger.debug("isGet   is   "+isGet);
			//下载 完成后，解析结果文件，将结果存入mysql 数据库中  (LEAK_TYPE=0)
			if(isGet){
				//File resultFile = new File( breakBurstResult+File.separator+"part-r-00000");
				try {
						DbUtils.readTxtFileForOutburst(breakBurstResult+File.separator+"part-r-00000");
					} catch (Throwable e) {
				}
			}
		}
	}
	
	@Test
	public void testchang(){
		String breakBurstResult = PropertiesUtils.getConfigProperty("break_burst_result");
		System.out.println(FileUtils.changeFileName(breakBurstResult));
	}
	
	@Test
	public void testPath(){
		String breakBurstResult = PropertiesUtils.getConfigProperty("linux_path");
		File file = new File(breakBurstResult);
		Path path=new Path(breakBurstResult);
		System.out.println(file.toString());
		System.out.println(path.toString());
	}
	
	@Test
	public void testsh(){
		try {
			Runtime.getRuntime().exec("asd");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testdate(){
		BufferedReader errReader=null;
		StringBuffer errMsg = new StringBuffer();
		Thread asd=new Thread(){
			
		};
	}
	@Test
	public void testcool(){
		//测试突发情况
//			new MyCoolantOutburstAnalysis().run();
//			new CoolantOutburstAnalysis().run();
			try {
//				new MyCoolantOutburstAnalysis().run();
//				new CoolantOutburstAnalysis().run();
				DbUtils.readTxtFileForOutburst("F:\\hadoop_files\\test-prepare\\data_file\\source\\20160115104134.txt");
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@Test
	public void testTime() throws InterruptedException{
		while(true){
			Thread.sleep(1000);
			System.out.println(new Date(System.currentTimeMillis()).toLocaleString());
			Thread.interrupted();
		}
	}
	
	
	@Test
	public void testroutin(){
		try {
			new RoutinOutburstAnalysis().run();
//			DbUtils.readTxtFile("F:\\hadoop_files\\test-prepare\\data_file\\source\\20160121100047.txt");
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
