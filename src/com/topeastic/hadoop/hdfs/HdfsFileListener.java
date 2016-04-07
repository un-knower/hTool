package com.topeastic.hadoop.hdfs;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * @Description:
 * @date: 2015-2-17
 * @author sunxing
 */
public class HdfsFileListener implements ServletContextListener {
	    private java.util.Timer timer = null ;  
	    private static Logger logger =  Logger.getLogger(HdfsFileListener.class);
	    public void contextDestroyed(ServletContextEvent event) {
	    }  
	    
	    public void contextInitialized(ServletContextEvent event) {  
	    	timer = new java.util.Timer(true) ;  
	        event.getServletContext().log("定时器已启动。") ;  
	        logger.debug("定時任务 HdfsFileListener  已经启动..........................");
	        timer.schedule(new HdfsFileTask(event.getServletContext()), 0, 24*60*60 * 1000) ; 
	        event.getServletContext().log("已经添加任务调度表。" ) ; 
	        logger.debug("定時任务 HdfsFileListener 已经添加任务调度表..........................");
	    	
	          
	    }  

}
