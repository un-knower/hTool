package com.topeastic.hadoop.service;

import java.util.List;

import com.topeastic.bean.AirconCtrlInfo;
import com.topeastic.dao.AirconCtrlInfoDao;

public class AirconCtrlInfoServiceImpl implements AirconCtrlInfoService {
    
	//声明DAO接口对象  
	AirconCtrlInfoDao airconCtrlInfoDao;
	
	public boolean isInsert(AirconCtrlInfo airconCtrlInfo) {
		
		return airconCtrlInfoDao.isInsert(airconCtrlInfo);
	}

	
	public boolean isDelete(String  id) {
	
		return airconCtrlInfoDao.isDelete(id);
	}

	
	public boolean update(AirconCtrlInfo airconCtrlInfo) {
		
		return airconCtrlInfoDao.update(airconCtrlInfo);
	}

	
	public AirconCtrlInfo queryById(String  id) {
	
		return airconCtrlInfoDao.queryById(id);
	}

	
	public List<AirconCtrlInfo> queryAll() {
		
		return airconCtrlInfoDao.queryAll();
	}
	
	
	
	public AirconCtrlInfoDao getAirconCtrlInfoDao() {
		return airconCtrlInfoDao;
	}

	public void setAirconCtrlInfoDao(AirconCtrlInfoDao airconCtrlInfoDao) {
		this.airconCtrlInfoDao = airconCtrlInfoDao;
	}
}
