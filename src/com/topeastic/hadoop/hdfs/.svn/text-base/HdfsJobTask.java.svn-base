package com.topeastic.hadoop.hdfs;

import java.io.IOException;
import java.util.HashMap;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.poss.saoss.hadoop.impl.PortalImpl;
import com.topeastic.hadoop.task.bean.HdfsTask;
import com.topeastic.hadoop.task.dao.impl.HdfsTaskDaoImpl;
import com.topeastic.hadoop.utils.PropertiesUtils;
import com.topeastic.mapreduce.job.BehaviorAnalysisByApp;
import com.topeastic.mapreduce.job.CoolantOutburstAnalysis;
import com.topeastic.mapreduce.job.DeviceStatusAvgAnalysis;
import com.topeastic.mapreduce.job.DifferentialAnalysis;
import com.topeastic.mapreduce.job.DifferentialAnalysis_New;
import com.topeastic.mapreduce.job.RoutinOutburstAnalysis;
import com.topeastic.mapreduce.job.RoutinOutburstAnalysis_New;
import com.topeastic.mapreduce.job.TimeStatisticalAnalysis;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description:
 * @date: 2015-2-17
 * @author sunxing
 */
public class HdfsJobTask extends TimerTask {
	private static final Logger logger = Logger.getLogger(HdfsJobTask.class);
	private static boolean isRunning = false;
	private ServletContext context = null;

	public HdfsJobTask(ServletContext context) {
		this.context = context;
	}

	@Override
	public void run() {
		// ApplicationContext context=new
		// ClassPathXmlApplicationContext("applicationContext.xml");
		// HdfsTaskDaoImpl hdfsTaskDao =
		// (HdfsTaskDaoImpl)context.getBean("hdfsTaskDao");

		PortalImpl pi = new PortalImpl();
		HdfsTask hdfsTaskJob = null;
		HdfsTask hdfsTaskFile = null;
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
			logger.debug("call portal  throwable    " + e.getMessage());
		}

		boolean flag = true;

		while (flag) {
			if (null != hdfsTaskFile
					&& (HdfsStatus.HDFS_IDLE.equals(hdfsTaskFile
							.getTaskStatus()))) {
				hdfsTaskJob.setTaskStatus(HdfsStatus.JOB_RUNING);
				try {
					pi.updateUserInfo(hdfsTaskJob.getId(),
							hdfsTaskJob.getTaskType(),
							hdfsTaskJob.getTaskStatus());
				} catch (Throwable e) {
					flag = false;
					logger.debug("InterruptedException     " + e.getMessage());
				}
				try {
					// ok
					logger.info("==============开始job========");
					new DeviceStatusAvgAnalysis().run();
//
					new DifferentialAnalysis_New().run();
//					// ok
					new RoutinOutburstAnalysis().run();
//					// ok
					new CoolantOutburstAnalysis().run();

					new TimeStatisticalAnalysis().run();
					
//					new BehaviorAnalysisByApp().run();
					
				} catch (Exception e) {
					logger.debug("Exception     " + e.getMessage());
				} finally {
					logger.debug("it's go to finally place");
					hdfsTaskJob.setTaskStatus(HdfsStatus.JOB_IDLE);
					try {
						pi.updateUserInfo(hdfsTaskJob.getId(),
								hdfsTaskJob.getTaskType(),
								hdfsTaskJob.getTaskStatus());
					} catch (Throwable e) {
						logger.debug("call portal  throwable    "
								+ e.getMessage());
					}
					flag = false;
				}
				
			} else {
				logger.debug("it's go to else place");
				logger.debug("hdfs is uploading  waiting...................");
				try {
					Thread.sleep(10 * 1000);
					flag = true;
				} catch (InterruptedException e) {
					flag = false;
					logger.debug("InterruptedException     " + e.getMessage());
				}
			}
		}
		logger.debug("it's leave if place");
		//
		hdfsTaskJob.setTaskStatus(HdfsStatus.JOB_IDLE);
		try {
			pi.updateUserInfo(hdfsTaskJob.getId(), hdfsTaskJob.getTaskType(),
					hdfsTaskJob.getTaskStatus());
		} catch (Throwable e) {
			logger.debug("call portal  throwable    " + e.getMessage());
		}
	}
}
