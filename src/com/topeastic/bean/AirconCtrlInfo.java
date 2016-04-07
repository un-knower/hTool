package com.topeastic.bean;

public class AirconCtrlInfo {
	/**主键**/
	private String autoId;
	/**控制时间**/
	private Long ctrlTime;
	/**用户id**/
	private String userId;
	/**家电型号**/   
	private String saModel;
	/**模块编号**/
	private String moduleNo;
	
	/**模块地址**/
	private String saAddress;
	/**室内温度**/
	private Integer insideTemp;
	/**室外温度**/
	private Integer outsideTemp;
	/**室内湿度**/
	private Integer insideDuhm;
	/**室外湿度**/
	private Integer outsideDuhm;

	/**工作电流**/
	private Integer electric;	
	/**是否已经推送**/
	private String isSend;
	
	private Integer leakType;
	
	private String workModel;
	
	private String workCondition;
	
	public String getAutoId() {
		return autoId;
	}
	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}
	public Long getCtrlTime() {
		return ctrlTime;
	}
	public void setCtrlTime(Long ctrlTime) {
		this.ctrlTime = ctrlTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSaModel() {
		return saModel;
	}
	public void setSaModel(String saModel) {
		this.saModel = saModel;
	}
	public String getModuleNo() {
		return moduleNo;
	}
	public void setModuleNo(String moduleNo) {
		this.moduleNo = moduleNo;
	}
	
	public String getSaAddress() {
		return saAddress;
	}
	public void setSaAddress(String saAddress) {
		this.saAddress = saAddress;
	}
	public Integer getInsideTemp() {
		return insideTemp;
	}
	public void setInsideTemp(Integer insideTemp) {
		this.insideTemp = insideTemp;
	}
	public Integer getOutsideTemp() {
		return outsideTemp;
	}
	public void setOutsideTemp(Integer outsideTemp) {
		this.outsideTemp = outsideTemp;
	}
	public Integer getInsideDuhm() {
		return insideDuhm;
	}
	public void setInsideDuhm(Integer insideDuhm) {
		this.insideDuhm = insideDuhm;
	}
	public Integer getOutsideDuhm() {
		return outsideDuhm;
	}
	public void setOutsideDuhm(Integer outsideDuhm) {
		this.outsideDuhm = outsideDuhm;
	}
	public Integer getElectric() {
		return electric;
	}
	public void setElectric(Integer electric) {
		this.electric = electric;
	}
	public String getIsSend() {
		return isSend;
	}
	public void setIsSend(String isSend) {
		this.isSend = isSend;
	}
	public Integer getLeakType() {
		return leakType;
	}
	public void setLeakType(Integer leakType) {
		this.leakType = leakType;
	}
	public String getWorkModel() {
		return workModel;
	}
	public void setWorkModel(String workModel) {
		this.workModel = workModel;
	}
	public String getWorkCondition() {
		return workCondition;
	}
	public void setWorkCondition(String workCondition) {
		this.workCondition = workCondition;
	}
	
	
	
}
