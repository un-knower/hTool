package com.topeastic.mapreduce.job.vo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class TemperaturePair implements Comparable
{
	
	private long timeValue;
	
	private double inTempRange;
	
	

	public TemperaturePair(long timeValue, double inTempRange){
		this.timeValue = timeValue;
		this.inTempRange = inTempRange;
	}
	public long getTimeValue() {
		return timeValue;
	}

	public void setTimeValue(long timeValue) {
		this.timeValue = timeValue;
	}

	public double getInTempRange() {
		return inTempRange;
	}

	public void setInTempRange(double inTempRange) {
		this.inTempRange = inTempRange;
	}
	@Override
	public int compareTo(Object obj) {
		TemperaturePair tp =(TemperaturePair)obj;
		
		int num = new Long(this.timeValue).compareTo(new Long(tp.timeValue));
		
		return num;
	}
	
	  public String toString()
	    {
	        return timeValue+"::"+inTempRange;
	    }

	public static void main(String[] args){
		TreeSet<TemperaturePair> ts = new TreeSet<TemperaturePair>();
		ts.add(new TemperaturePair(1422806402340l, 2));
		ts.add(new TemperaturePair(1422806402440l, 3));
		ts.add(new TemperaturePair(1422806402346l, 2));
		ts.add(new TemperaturePair(1422806402344l, 3));
		ts.add(new TemperaturePair(1422806402444l, 4));
		System.out.println(ts);
		
		 Iterator<TemperaturePair> it = ts.iterator();
		 List<TemperaturePair> list = new ArrayList<TemperaturePair>();
		  while(it.hasNext()){
			  TemperaturePair tp = it.next();
			  //System.out.println(it.next().getTimeValue());
			  System.out.println(tp.getTimeValue()+":::::"+tp.getInTempRange());
			  list.add(tp);
		  }
		  System.out.println("start to chech list ------------------------");
		  for(TemperaturePair t:list){
			  System.out.println(t.getTimeValue()+":::::"+t.getInTempRange());
		  }
		  



	}
	
	
}
