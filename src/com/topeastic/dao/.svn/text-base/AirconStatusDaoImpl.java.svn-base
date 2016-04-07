package com.topeastic.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;




import java.util.UUID;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.topeastic.bean.AirconCtrlInfo;
import com.topeastic.bean.AirconStatusInfo;

public class AirconStatusDaoImpl  implements AirconStatusInfoDao {
	    private SqlMapClient sqlMapClient;
		public void setSqlMapClient(SqlMapClient sqlMapClient) {
			this.sqlMapClient = sqlMapClient;
		}
	
		
		/**
		 * 查询所有数据
		 */
		public List<AirconStatusInfo> queryAllAirconStatusInfo(AirconStatusInfo airconStatusInfo) {
			 List list = null;
		        try {
		            list = sqlMapClient.queryForList("queryAllAirconStatusInfo",airconStatusInfo);
		        } catch (SQLException e) {
		        }
		        return list;
		}
	
	/**
	 * 添加操作
	 */
	public boolean insertAirconStatusInfo(AirconStatusInfo airconStatusInfo) {
		 boolean b;
		 String dateStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		 airconStatusInfo.setId(dateStr);
	        try {
	            sqlMapClient.insert("insertAirconStatusInfo", airconStatusInfo);
	            b = true;
	        } catch (SQLException e) {
	            b = false;
	        }
	        return b ;
	}

	/**
	 * 删除操作
	 */
	public boolean deleteAirconStatusInfoById(String  id) {	
	        boolean b;
	        try{
	            sqlMapClient.delete("deleteAirconStatusInfoById", id);
	            b = true;
	        }catch(SQLException e){
	            b = false;
	        }
	        return b;
	}

	/**
	 * 修改操作
	 */
	public boolean updateAirconStatusInfo(AirconStatusInfo airconStatusInfo) {
		 boolean b ;
	        try {
	            sqlMapClient.update("updateAirconStatusInfo", airconStatusInfo);
	            b = true;
	        } catch (Exception e) {
	            b = false;
	        }
	        return b;
	}

	
	/**
	 * 根据Id进行查询操作
	 */
	public AirconStatusInfo selectAirconStatusInfoById(String  id) {	
		AirconStatusInfo air=null;
		try {
			air =(AirconStatusInfo) sqlMapClient.queryForObject("selectAirconStatusInfoById", id);
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		return air;
	}
	
}
