package com.topeastic.mapreduce.job.vo;

public class DeviceStatus {
	
	/**基准电流**/
	private double electric;
	
	/**室内温差*/
	private double inTempRang;
	
	/**室外温差**/
	private double outTempRang;
	
	/**设定温差**/
	private double setTempRang;
	
	/**排气温度**/
	private double exhaustTemp;
	
	/**壳体是否温度过热保护**/
	private boolean isShellTempDefend;
	
	/**工作方式，制冷还是制热**/
	private String workModel;
	
	/**时间点**/
	private long time;

	public double getElectric() {
		return electric;
	}

	public void setElectric(double electric) {
		this.electric = electric;
	}

	public double getInTempRang() {
		return inTempRang;
	}

	public void setInTempRang(double inTempRang) {
		this.inTempRang = inTempRang;
	}

	public double getOutTempRang() {
		return outTempRang;
	}

	public void setOutTempRang(double outTempRang) {
		this.outTempRang = outTempRang;
	}

	public double getSetTempRang() {
		return setTempRang;
	}

	public void setSetTempRang(double setTempRang) {
		this.setTempRang = setTempRang;
	}

	public double getExhaustTemp() {
		return exhaustTemp;
	}

	public void setExhaustTemp(double exhaustTemp) {
		this.exhaustTemp = exhaustTemp;
	}

	public boolean isShellTempDefend() {
		return isShellTempDefend;
	}

	public void setShellTempDefend(boolean isShellTempDefend) {
		this.isShellTempDefend = isShellTempDefend;
	}

	public String getWorkModel() {
		return workModel;
	}

	public void setWorkModel(String workModel) {
		this.workModel = workModel;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
}
