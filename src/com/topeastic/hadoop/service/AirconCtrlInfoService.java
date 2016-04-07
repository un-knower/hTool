package com.topeastic.hadoop.service;

import java.util.List;

import com.topeastic.bean.AirconCtrlInfo;

public interface AirconCtrlInfoService {
	public boolean isInsert(AirconCtrlInfo airconCtrlInfo);
	public boolean isDelete(String  id );
	public boolean update(AirconCtrlInfo airconCtrlInfo);
	public AirconCtrlInfo queryById(String  id );
	public List<AirconCtrlInfo> queryAll();
}
