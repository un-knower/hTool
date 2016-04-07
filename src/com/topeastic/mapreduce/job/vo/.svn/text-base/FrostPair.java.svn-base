package com.topeastic.mapreduce.job.vo;

public class FrostPair implements Comparable{

	private long timeValue;
	
	private boolean status;
	
	private String isRun;
	
	public FrostPair(long timeValue, boolean status,String isRun){
		this.timeValue=timeValue;
		this.status = status;
		this.isRun = isRun;
	}
	public long getTimeValue() {
		return timeValue;
	}

	public void setTimeValue(long timeValue) {
		this.timeValue = timeValue;
	}

	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public String getIsRun() {
		return isRun;
	}
	public void setIsRun(String isRun) {
		this.isRun = isRun;
	}
	@Override
	public int compareTo(Object obj) {
		FrostPair tp =(FrostPair)obj;
		
		int num = new Long(this.timeValue).compareTo(new Long(tp.timeValue));
		
		return num;
	}
	
}
