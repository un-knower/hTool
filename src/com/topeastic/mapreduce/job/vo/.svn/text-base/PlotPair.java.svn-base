package com.topeastic.mapreduce.job.vo;

@SuppressWarnings("rawtypes")
public class PlotPair implements Comparable{

	private long timeValue;
	
	private double plotValue;
	
	public PlotPair(long timeValue,double plotValue){
		this.timeValue=timeValue;
		this.plotValue = plotValue;
	}
	
	public long getTimeValue() {
		return timeValue;
	}

	public void setTimeValue(long timeValue) {
		this.timeValue = timeValue;
	}

	public double getPlotValue() {
		return plotValue;
	}

	public void setPlotValue(double plotValue) {
		this.plotValue = plotValue;
	}

	@Override
	public int compareTo(Object obj) {
		PlotPair tp =(PlotPair)obj;
		
		int num = new Long(this.timeValue).compareTo(new Long(tp.timeValue));
		
		return num;
	}

}
