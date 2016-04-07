package com.topeastic.dao.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import com.topeastic.mapreduce.job.Tools;
import com.topeastic.mapreduce.job.vo.TimePair;

public class CoolProcess implements Processable {

	@Override
	public void run(List<Long> targetTime, List<Long> baseTime,
			int openPoint, boolean isFind,Object...params) {
		List<TimePair> trList = new ArrayList<TimePair>();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Iterator<Object> iter = ((TreeSet)params[0]).iterator();
		 while(iter.hasNext()){
			 TimePair timePair = (TimePair)iter.next();
			 trList.add(timePair);
		 }
		 if(trList.size()<=1){
			 return;
		 }
		 
		for(int i=0;i<trList.size()-1;i++){
			TimePair currentTimePair = trList.get(i);
			TimePair nextPair = trList.get(i+1);
			
			if(nextPair.getTimeValue()-currentTimePair.getTimeValue()>600000){
				openPoint =i;
				 isFind=true;
				 break;
			}else{
				continue;
			}
		}
		if(!isFind){
			openPoint=0;
		}
		boolean isHalf = Tools.isRunningHalfHoursCool(openPoint, trList);
		 //如果不超过半小时，该条数据过滤掉
		 if(isHalf){
			 int num=0;
			 for(int k=openPoint+6;k<trList.size();k++){
				 if(openPoint+6<=trList.size()-1){
					 targetTime.add(trList.get(k).getTimeValue());
				 }
				 if(num<3){
					 baseTime.add(trList.get(k).getTimeValue());
				 }
				 num++;
			 }
		 }

	}

}
