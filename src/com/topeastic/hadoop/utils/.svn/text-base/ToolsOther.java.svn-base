package com.topeastic.hadoop.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.topeastic.mapreduce.job.vo.FrostPair;



public class ToolsOther {

	// 短日期格式
		 public static String DATE_FORMAT = "yyyy-MM-dd";
		 
		 // 长日期格式
		 public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	 /**
     * 将日期格式的字符串转换为长整型
     * 
     * @param date
     * @param format
     * @return
     */
    public static long stringDateConvert2long(String date, String format) {
     try {
      if (StringUtils.isNotBlank(date)) {
       if (StringUtils.isBlank(format))
        format = TIME_FORMAT;

       SimpleDateFormat sf = new SimpleDateFormat(format);
       return sf.parse(date).getTime();
      }
     } catch (ParseException e) {
      e.printStackTrace();
     }
     return 0l;
    }

    

    /**
     * 将长整型数字转换为日期格式的字符串
     * 
     * @param time
     * @param format
     * @return
     */
    public static String longConvert2DateString(long time, String format) {
     if (time > 0l) {
      if (StringUtils.isBlank(format))
       format = TIME_FORMAT;

      SimpleDateFormat sf = new SimpleDateFormat(format);
      Date date = new Date(time);

      return sf.format(date);
     }
     return "";
    }


    /**
     * 获取当前系统的日期
     * 
     * @return
     */
    public static long curTimeMillis() {
     return System.currentTimeMillis();
    }

    /**
    * 字符串转换成日期
    * @param str
    * @return date
    */
    public static Date strToDate(String str) {
      
       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       Date date = null;
       try {
        date = format.parse(str);
       } catch (ParseException e) {
        e.printStackTrace();
       }
       return date;
    }

    /**
     * 判断字符串中是否包含数字
     * @param s
     * @return
     */
    public boolean checkForDigit(String s){  
    	boolean  b=false;  
    	for(int i=0;i<s.length();i++)  
    	{ if(Character.isDigit(s.charAt(i)))     {
    		b=true;  
    		break;
    		}   
    	}    
    	return  b;
    	}
    
    public static String replaceStrHanzi(String value){
    	String str = "123abc,.?:你好efc";

        String reg = "[\u4e00-\u9fa5]";

        Pattern pat = Pattern.compile(reg);  

        Matcher mat=pat.matcher(str); 

        String repickStr = mat.replaceAll("");
        repickStr = repickStr.replaceAll("[\\p{Punct}\\p{Space}]+", ""); ;
        System.out.println("去中文后:"+repickStr);
        return repickStr;


    }
    
	@SuppressWarnings("unused")
	public static void main1(String[] args) {
		String dataString = longConvert2DateString(1418837578316L,"yyyy-MM-dd HH:mm:ss");
		String dataString1 = longConvert2DateString(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss");
		String zeroDateString  = dataString1.split(" ")[0]+ " 00:00:00";
		String lastDateString  =dataString1.split(" ")[0]+ " 23:59:59";
		
		Long zeroDateLong = stringDateConvert2long(zeroDateString,TIME_FORMAT);
		Long lastDateLong = stringDateConvert2long(lastDateString,TIME_FORMAT);
		System.out.println(zeroDateLong);
		System.out.println(lastDateLong);
		System.out.println(System.currentTimeMillis());
		//replaceStrHanzi("123abc,.?:你好efc");

	}
	
	/**
	 * 判断是否运行4小时
	 * @return
	 */
	public static boolean isRunningFourHours(int timePoint,List<FrostPair> fpList){
		if(fpList.size()<48){
			return false;
		}else{
			if((timePoint+48)<fpList.size()-1){
				int number = 0;
				for(int i=timePoint+1;i<=(i+48)-1;i++){
					if(fpList.get(i).getIsRun().equals("n")){
						number++;
					}
				}
				if(number==0){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}
		
	}
	
	/**
	 * 判断是否运行4小时
	 * @return
	 */
	public static boolean isRunningHalfHours(int timePoint,List<FrostPair> fpList){
		if(fpList.size()<6){
			return false;
		}else{
			if((timePoint+6)<=fpList.size()-1){
				int number = 0;
				for(int i=timePoint+1;i<=(timePoint+6)-1;i++){
					if(fpList.get(i).getIsRun().equals("n")){
						number++;
					}
				}
				if(number==0){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}
		
	}
	
	/**
	 * 判断4小时内是否有除霜标志位
	 * @return
	 */
	public static List<Integer> getRemoveFrost(int timePoint,List<FrostPair> fpList){
		List<Integer> inList= new ArrayList<Integer>();
	
		for(int i=timePoint+1;i<=fpList.size()-1;i++){
			if(fpList.get(i).isStatus()&&!(fpList.get(i+1).isStatus())){
				//System.out.println("制热时开机除霜标志结束第"+(i+1)+"时间点：  removeFrost time = "+DateUtils.longConvert2DateString(fpList.get(i+1).getTimeValue(), TIME_FORMAT));
		 		inList.add(i+1);
		 	}
		}
		return inList;
		
	}
	
	public static void main(String[] args){
		List<Long> l = new ArrayList<Long>();
		l.add(11110L);
		l.add(11111L);
		l.add(11112L);
		Long s = 10000L;
		String s1 = "11110";
		Long l1 = Long.valueOf(s1);
		if(l.contains(l1)){
			System.out.println("l1");
		}
	}

}
