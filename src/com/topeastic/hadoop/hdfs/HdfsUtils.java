package com.topeastic.hadoop.hdfs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.topeastic.hadoop.utils.PropertiesUtils;

public class HdfsUtils {

	// hdfs相关路径
	public static final String hdfsUrl = PropertiesUtils
			.getConfigProperty("hdfsUrl");
	public static final String input_main = hdfsUrl
			+ PropertiesUtils.getConfigProperty("input_main");
	public static final String input_weifen = hdfsUrl
			+ PropertiesUtils.getConfigProperty("input_weifen");
	public static final String input_base = hdfsUrl
			+ PropertiesUtils.getConfigProperty("input_base");

	public static final String output_avg_result = hdfsUrl
			+ PropertiesUtils.getConfigProperty("output_avg_result");
	public static final String output_plot_result = hdfsUrl
			+ PropertiesUtils.getConfigProperty("output_plot_result");
	public static final String output_routin_burst_result = hdfsUrl
			+ PropertiesUtils.getConfigProperty("output_routin_burst_result");
	public static final String output_break_burst_result = hdfsUrl
			+ PropertiesUtils.getConfigProperty("output_break_burst_result");
	public static final String output_time_total_result = hdfsUrl
			+ PropertiesUtils.getConfigProperty("output_time_total_result");
	public static final String output_behavior_result = hdfsUrl
			+ PropertiesUtils.getConfigProperty("output_behavior_result");

	public static final String local_avg_result = PropertiesUtils
			.getConfigProperty("local_avg_result");
	public static final String local_plot_result = PropertiesUtils
			.getConfigProperty("local_plot_result");
	public static final String routin_burst_result = PropertiesUtils
			.getConfigProperty("routin_burst_result");
	public static final String break_burst_result = PropertiesUtils
			.getConfigProperty("break_burst_result");
	public static final String time_total_result = PropertiesUtils
			.getConfigProperty("time_total_result");
	public static final String behavior_result = PropertiesUtils
			.getConfigProperty("behavior_result");

	public static final String data_path = PropertiesUtils
			.getConfigProperty("data_path");

	public static final String hadoopUrl = PropertiesUtils
			.getConfigProperty("hadoopUrl");
	public static final String core_site_path = hadoopUrl
			+ PropertiesUtils.getConfigProperty("core_site_path");
	public static final String hdfs_site_path = hadoopUrl
			+ PropertiesUtils.getConfigProperty("hdfs_site_path");

	public static final String redis_url = PropertiesUtils
			.getConfigProperty("redis_url");

	/**
	 * 文件上传
	 * 
	 * @param src
	 * @param dst
	 * @param conf
	 * @return
	 */
	public static boolean put2HDFS(String src, String dst, Configuration conf) {
		Path dstPath = new Path(dst);
		try {
			FileSystem hdfs = dstPath.getFileSystem(conf);
			// FileSystem hdfs = FileSystem.get( URI.create(dst), conf) ;
			hdfs.copyFromLocalFile(false, new Path(src), dstPath);
		} catch (IOException ie) {
			ie.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 文件下载 <br>
	 * done
	 * 
	 * @param src
	 * @param dst
	 * @param conf
	 * @return
	 */
	public static boolean getFromHDFS(String src, String dst, Configuration conf) {
		conf.addResource(new Path(core_site_path));
		conf.addResource(new Path(hdfs_site_path));
		Path dstPath = new Path(dst);
		try {
			// FileSystem dhfs = dstPath.getFileSystem(conf) ;
			FileSystem dhfs = dstPath.getFileSystem(conf);
			dhfs.copyToLocalFile(false, new Path(src), dstPath);
		} catch (IOException ie) {
			ie.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 文件检测并删除 <br>
	 * 删除文件和文件夹
	 * 
	 * @param path
	 * @param conf
	 * @return
	 */
	public static boolean checkAndDel(final String path, Configuration conf) {
		Path dstPath = new Path(path);
		try {
			FileSystem dhfs = dstPath.getFileSystem(conf);
			if (dhfs.exists(dstPath)) {
				dhfs.delete(dstPath, true);
			} else {
				return false;
			}
		} catch (IOException ie) {
			ie.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 在linux磁盘上 上传文件至hdfs上
	 */
	public static void put2HdfsByShell() {
		Process process = null;
		// 脚本路径
		String Shell_path = PropertiesUtils.getConfigProperty("put2Hdfs_path");
		// 参数路径
		String local_path = PropertiesUtils.getConfigProperty("linux_path");
		String dst_path = input_main;

		// //test
		// String Shell_path="/app/data_sh/put2Hdfs.sh";
		// String local_path="/app/data_file/*";
		// String dst_path="/user/hadoop/input_main/";

		// 命令语句
		String command = "sh " + Shell_path + " " + local_path + " " + dst_path;
		try {
			// 执行脚本
			process = Runtime.getRuntime().exec(command);
			process.waitFor();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void downFromHdfs() {

	}

	/**
	 * 根据路径创建一个文件夹
	 * 
	 * @param path
	 * @param conf
	 */
	public static void createDir(String path, Configuration conf) {
		Path dstPath = new Path(path);
		try {
			FileSystem fs = dstPath.getFileSystem(conf);
			fs.mkdirs(dstPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 根据路径创建一个文件，默认会重写覆盖
	 * 
	 * @param path
	 * @param conf
	 */
	public static void createFile(String path, Configuration conf) {
		Path dstPath = new Path(path);
		try {
			FileSystem fs = dstPath.getFileSystem(conf);
			fs.create(dstPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 根据路径删除 文件夹或者文件
	 * 
	 * @param path
	 * @param conf
	 */
	public static void delete(String path, Configuration conf) {
		Path dstPath = new Path(path);
		try {
			FileSystem fs = dstPath.getFileSystem(conf);
			// 删除文件夹 必须为true 删除文件 则可以为true和false。
			fs.delete(dstPath, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// String src = "hdfs://xcloud:9000/user/xcloud/input/core-site.xml" ;
		// String dst = "hdfs://192.168.7.56:9000/user/hadoop/input" ;
		// // String src = "/app/file.txt" ;
		// String src = "F:\\hadoop_files\\test-prepare\\2015050600.TXT" ;
		// boolean status = false ;
		//
		//
		// Configuration conf = new Configuration() ;
		// status = put2HDFS( src, dst , conf) ;
		// System.out.println("status="+status) ;

		// put2HdfsByShell();
		// src = "hdfs://master:9000/user/xcloud/out/loadtable.rb";
		// dst = "/tmp/output" ;
		// status = getFromHDFS( src, dst , conf) ;
		// System.out.println("status="+status) ;
		//
		// src = "hdfs://master:9000/user/xcloud/out/loadtable.rb" ;
		// dst = "/tmp/output/loadtable.rb";
		// status = checkAndDel(dst, conf);
		// System.out.println("status="+status);
	}

}
