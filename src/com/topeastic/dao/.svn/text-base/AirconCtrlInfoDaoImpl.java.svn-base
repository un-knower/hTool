package com.topeastic.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;




import java.util.UUID;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.topeastic.bean.AirconCtrlInfo;

public class AirconCtrlInfoDaoImpl  implements AirconCtrlInfoDao {
	    private SqlMapClient sqlMapClient;
		public void setSqlMapClient(SqlMapClient sqlMapClient) {
			this.sqlMapClient = sqlMapClient;
		}
	
		
		/**
		 * 查询所有数据
		 */
		public List<AirconCtrlInfo> queryAll() {
			 List list = null;
			 AirconCtrlInfo airconCtrlInfo = new AirconCtrlInfo();
		        try {
		            list = sqlMapClient.queryForList("queryAll",airconCtrlInfo);
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        return list;
		}
	
	/**
	 * 添加操作
	 */
	public boolean isInsert(AirconCtrlInfo airconCtrlInfo) {
		 boolean b;
		 String DateStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		 airconCtrlInfo.setAutoId(DateStr);
	        try {
	            sqlMapClient.insert("insertAir", airconCtrlInfo);
	            b = true;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            b = false;
	        }
	        return b ;
	}

	/**
	 * 删除操作
	 */
	public boolean isDelete(String  id) {	
	        boolean b;
	        try{
	            sqlMapClient.delete("deleteAir", id);
	            b = true;
	        }catch(SQLException e){
	            e.printStackTrace();
	            b = false;
	        }
	        return b;
	}

	/**
	 * 修改操作
	 */
	public boolean update(AirconCtrlInfo airconCtrlInfo) {
		 boolean b ;
	        try {
	            sqlMapClient.update("update", airconCtrlInfo);
	            b = true;
	        } catch (Exception e) {
	            b = false;
	        }
	        return b;
	}

	
	/**
	 * 根据Id进行查询操作
	 */
	public AirconCtrlInfo queryById(String  id) {	
		AirconCtrlInfo air=null;
		try {
			air =(AirconCtrlInfo) sqlMapClient.queryForObject("selectAirById", id);
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		return air;
	}

	
}
