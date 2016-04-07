package com.topeastic.mapreduce.job.vo;

public class TimePair  implements Comparable{

	private long timeValue;
	
	private String status;
	
	public  TimePair(long timeValue, String status) {
		this.timeValue=timeValue;
		this.status = status;
	}

	@Override
	public int compareTo(Object obj) {
		TimePair tp =(TimePair)obj;
		
		int num = new Long(this.timeValue).compareTo(new Long(tp.timeValue));
		
		return num;
	}

	public long getTimeValue() {
		return timeValue;
	}

	public void setTimeValue(long timeValue) {
		this.timeValue = timeValue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
