package com.topeastic.hadoop.task.dao.impl;

import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.topeastic.hadoop.task.bean.HdfsTask;
import com.topeastic.hadoop.task.dao.HdfsTaskDao;
import com.topeastic.hadoop.utils.IbatisUtil;

public class HdfsTaskDaoImpl implements HdfsTaskDao {

	private SqlMapClient sqlMapClient;

	public void setSqlMapClient() {

		this.sqlMapClient = IbatisUtil.getSqlMapClient();
	}

	public HdfsTaskDaoImpl() {
		this.sqlMapClient = IbatisUtil.getSqlMapClient();
	}

	@Override
	public boolean updateHdfsTask(HdfsTask hdfsTask) throws DataAccessException {

		try {
			sqlMapClient.update("updateHdfsTask", hdfsTask);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public HdfsTask queryHdfsTaskById(String id) throws DataAccessException {
		HdfsTask hdfsTask = null;
		try {
			hdfsTask = (HdfsTask) sqlMapClient.queryForObject(
					"queryHdfsTaskById", id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hdfsTask;
	}

	@Override
	public List<HdfsTask> queryHdfsTaskByType(HdfsTask hdfsTask)
			throws DataAccessException {
		List<HdfsTask> list = null;

		try {
			list = sqlMapClient.queryForList("queryHdfsTaskByType", hdfsTask);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
