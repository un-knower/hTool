package com.topeastic.mapreduce.job.vo;

public class ReferenceData {

private double inTempRangBase;
	
	private double outTempRangBase;
	
	public ReferenceData() {
	}
	
	public ReferenceData(double inTempRangBase, double outTempRangBase) {
		this.inTempRangBase = inTempRangBase;
		this.outTempRangBase = outTempRangBase;
	}
	public double getInTempRangBase() {
		return inTempRangBase;
	}


	public void setInTempRangBase(double inTempRangBase) {
		this.inTempRangBase = inTempRangBase;
	}


	public double getOutTempRangBase() {
		return outTempRangBase;
	}


	public void setOutTempRangBase(double outTempRangBase) {
		this.outTempRangBase = outTempRangBase;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
