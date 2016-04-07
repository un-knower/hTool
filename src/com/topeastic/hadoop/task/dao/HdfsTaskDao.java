package com.topeastic.hadoop.task.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.topeastic.hadoop.task.bean.HdfsTask;

public interface HdfsTaskDao {
	
	public  boolean updateHdfsTask(HdfsTask hdfsTask)
		    throws DataAccessException;
		  
//	public  void deleteHdfsTask(HdfsTask hdfsTask)
//		    throws DataAccessException;
	
	public  HdfsTask queryHdfsTaskById(String id)
				    throws DataAccessException;
	
	public  List<HdfsTask> queryHdfsTaskByType(HdfsTask hdfsTask)
		    throws DataAccessException;
}
