package com.topeastic.dao.factory;

import java.util.List;

import com.topeastic.bean.AirconCtrlInfo;
import com.topeastic.dao.AirconCtrlInfoDao;
import com.topeastic.dao.AirconCtrlInfoDaoImpl;

public class AirconCtrlDAOFactory implements AirconCtrlInfoDao {

	private AirconCtrlInfoDaoImpl  airconCtrldao = null ;
	
	public AirconCtrlDAOFactory(){
		this.airconCtrldao = new AirconCtrlInfoDaoImpl();
	}

	@Override
	public boolean isInsert(AirconCtrlInfo airconCtrlInfo) {
		return this.airconCtrldao.isInsert(airconCtrlInfo);
	}

	@Override
	public boolean isDelete(String id) {
		return this.airconCtrldao.isDelete(id);
	}

	@Override
	public boolean update(AirconCtrlInfo airconCtrlInfo) {
		return this.airconCtrldao.update(airconCtrlInfo);
	}

	@Override
	public AirconCtrlInfo queryById(String id) {
		return this.airconCtrldao.queryById(id);
	}

	@Override
	public List<AirconCtrlInfo> queryAll() {
		return this.airconCtrldao.queryAll();
	}

}
