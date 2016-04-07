package com.topeastic.hadoop.hdfs;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.poss.saoss.hadoop.impl.PortalImpl;
import com.topeastic.hadoop.task.bean.HdfsTask;
import org.apache.hadoop.conf.Configuration;

/**
 * @Description:
 * @date: 2015-2-17
 * @author sunxing
 */
public class HdfsFileTask extends TimerTask {
	private static Logger logger = Logger.getLogger(HdfsFileListener.class);
	private static boolean isRunning = false;
	private ServletContext context = null;


	public HdfsFileTask(ServletContext context) {
		this.context = context;
	}


	@Override
	public void run() {

		   PortalImpl pi=new PortalImpl();
		   HdfsTask hdfsTaskJob=null;
		   HdfsTask hdfsTaskFile=null;
		try {
				HashMap<String, String> hs = pi.queryHadoopHdfsTask(HdfsStatus.JOB);
				String id = hs.get("id");
				String taskType = hs.get("taskType");
				String taskStatus = hs.get("taskStatus");
				hdfsTaskJob = new HdfsTask(id, taskType, taskStatus);
				HashMap<String, String> hs2 = pi
						.queryHadoopHdfsTask(HdfsStatus.HDFS);
				String id2 = hs2.get("id");
				String taskType2 = hs2.get("taskType");
				String taskStatus2 = hs2.get("taskStatus");
				hdfsTaskFile = new HdfsTask(id2, taskType2, taskStatus2);
		} catch (Throwable e) {
				e.printStackTrace();
		}
		
		//HdfsTask hdfsTaskJob = hdfsTaskDao.queryHdfsTaskById(HdfsStatus.JOB);
		//HdfsTask hdfsTaskFile = hdfsTaskDao.queryHdfsTaskById(HdfsStatus.HDFS);
		boolean flag = true;
		while(flag){
		if (null != hdfsTaskJob
				&& (HdfsStatus.JOB_IDLE.equals(hdfsTaskJob.getTaskStatus()))) {
			logger.debug("hdfs uploading task is starting.......................");
			hdfsTaskFile.setTaskStatus(HdfsStatus.HDFS_RUNING);
			try {
				pi.updateUserInfo(hdfsTaskFile.getId(), hdfsTaskFile.getTaskType(), hdfsTaskFile.getTaskStatus());
			} catch (Throwable e) {
				flag = false;
				logger.debug("call portal exception     "+e.getMessage());
			}
			// 开始上传文件到HDFS
			//本地系统数据路径
			String date_name=new SimpleDateFormat("yyyyMMdd").format(new Date(new Date().getTime()-24*60*60*1000));
			String sourcePath = HdfsUtils.data_path+"/"+date_name;
//			String sourcePath = HdfsUtils.data_path;
			logger.debug("log_source_path is......................."+ sourcePath);
			Configuration conf = new Configuration();
			conf.set("hadoop.job.user", "hadoop");
			//hdfs目标数据路径
			String dst = HdfsUtils.input_main+date_name+"/";
			File dir = new File(sourcePath);
			//删除原来在hdfs上的数据
			HdfsUtils.delete(dst, conf);
			//创建这样的文件夹
			HdfsUtils.createDir(dst, conf);
			
			//开始上传
			getAllFiles(dir,0,dst,conf);
			
			//linux
//			HdfsUtils.put2HdfsByShell();
			
			// 执行完上传任务后，将状态置为空闲
			logger.debug("beging update hdfs status    ");
			hdfsTaskFile.setTaskStatus(HdfsStatus.HDFS_IDLE);
			try {
				pi.updateUserInfo(hdfsTaskFile.getId(), hdfsTaskFile.getTaskType(), hdfsTaskFile.getTaskStatus());
			} catch (Throwable e) {	
				e.printStackTrace();
			}
			flag = false;
		
	}else{
			logger.debug("mapreduse is running...................");
			try {
				Thread.sleep(10*1000);
				flag = true;
			} catch (InterruptedException e) {
				flag = false;
				logger.debug("InterruptedException     "+e.getMessage());
			}
		}
	}
  }
	/**
	 * 递归获取指定目录下的所有文件
	 * @param dir
	 * @param level
	 * @param dst
	 * @param conf
	 */
	public static void getAllFiles(File dir,int level,String dst,Configuration conf){
		
		level++;
		File[] files=dir.listFiles();
		for(int i=0;i<files.length;i++)
		{
			if(files[i].isDirectory())
			{
				//这里面用了递归的算法
				getAllFiles(files[i],level,dst,conf);
			}
			else {
				if (files[i].exists()&&files[i].getName().contains(".TXT")) {
					logger.debug("uploading filename is    "+ files[i].getName());
					//if(file.getAbsolutePath().contains(DateUtils.dateDirfunc()))
					boolean status =HdfsUtils.put2HDFS(files[i].getAbsolutePath(), dst, conf);
					logger.debug("uploading status is    " + status);
			}
				//list.add(files[i]);
			}	
		}
	}
}
