package com.topeastic.hadoop.hdfs;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;

/**
 * @Description:
 * @date: 2015-2-17
 * @author sunxing
 */
public class HdfsJobListener implements ServletContextListener {
	private java.util.Timer timer = null;
	private static Logger logger = Logger.getLogger(HdfsJobListener.class);

	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
	}

	public void contextInitialized(ServletContextEvent event) {
		timer = new java.util.Timer(true);
		event.getServletContext().log(" HdfsJobListener   定时器已启动。");
		logger.debug(" HdfsJobListener  定时器已启动");
		timer.schedule(new HdfsJobTask(event.getServletContext()), 20* 1000,
				24*60*60 * 1000);
		event.getServletContext().log("已经添加任务调度表。");
		logger.debug("已经添加任务调度表 HdfsJobListener");
	}

}
