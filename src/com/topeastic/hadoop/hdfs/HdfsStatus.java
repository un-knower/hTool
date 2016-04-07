package com.topeastic.hadoop.hdfs;

public interface HdfsStatus {
	/**HDFS正在上传文件状态 0**/
	public static final String HDFS_RUNING = "0";
	
	/**HDFS未上传文件状态 1**/
	public static final String HDFS_IDLE = "1";
	
	/**mapreduce正在运行job状态 0**/
	public static final String JOB_RUNING = "0";
	
	/**HDFS未运行JOB状态 1**/
	public static final String JOB_IDLE = "1";
	
	/**HDFS的任务id 1**/
	public static final String HDFS = "1";
	
	/**JOB的任务id 2**/
	public static final String JOB = "2";
	
	
}
