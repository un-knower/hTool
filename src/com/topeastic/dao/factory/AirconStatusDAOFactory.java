package com.topeastic.dao.factory;

import java.util.List;
import com.topeastic.bean.AirconStatusInfo;
import com.topeastic.dao.AirconStatusDaoImpl;
import com.topeastic.dao.AirconStatusInfoDao;

public class AirconStatusDAOFactory implements AirconStatusInfoDao {

	private AirconStatusDaoImpl  airconStatusdao = null ;
	
	public AirconStatusDAOFactory(){
		this.airconStatusdao = new AirconStatusDaoImpl();
	}

	@Override
	public boolean insertAirconStatusInfo(AirconStatusInfo airconStatusInfo) {
		return this.airconStatusdao.insertAirconStatusInfo(airconStatusInfo);
	}

	@Override
	public boolean deleteAirconStatusInfoById(String id) {
		return this.airconStatusdao.deleteAirconStatusInfoById(id);
	}

	@Override
	public boolean updateAirconStatusInfo(AirconStatusInfo airconStatusInfo) {
		return this.airconStatusdao.updateAirconStatusInfo(airconStatusInfo);
	}

	@Override
	public AirconStatusInfo selectAirconStatusInfoById(String id) {
		return this.airconStatusdao.selectAirconStatusInfoById(id);
	}

	@Override
	public List<AirconStatusInfo> queryAllAirconStatusInfo(AirconStatusInfo airconStatusInfo) {
		return this.airconStatusdao.queryAllAirconStatusInfo(airconStatusInfo);
	}

}
