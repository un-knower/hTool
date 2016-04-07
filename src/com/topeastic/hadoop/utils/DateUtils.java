package com.topeastic.hadoop.utils;

import java.io.File;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

public class DateUtils {
	public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static boolean isToday(String longDate){
		 //时间点只取当天零点至晚上23:59:59
		//时间点过滤，突发性泄露只计算当天的数据
		SimpleDateFormat sf = new SimpleDateFormat(TIME_FORMAT);
		//系统当前时间
    	Date d = new Date();
    	String s = sf.format(d);
    	Long ls = stringDateConvert2long(s,TIME_FORMAT);
    	String dataString = longConvert2DateString(Long.valueOf(ls),"yyyy-MM-dd HH:mm:ss");
    	
	    String zeroDateString  = dataString.split(" ")[0]+ " 00:00:00";
		String lastDateString  =dataString.split(" ")[0]+ " 23:59:59";
		Long zeroDateLong = stringDateConvert2long(zeroDateString,TIME_FORMAT);
		Long lastDateLong = stringDateConvert2long(lastDateString,TIME_FORMAT);
		
		if((Long.valueOf(longDate)>=zeroDateLong)&&
				(Long.valueOf(longDate)<=lastDateLong)){
			return true;
		}
		
		return false;
	}
	
	/**
	 * //返回的是字符串型的时间，输入的
	 * @param day
	 * @param x
	 * @return
	 */
	public static String addDateMinut(String day, int x) {
			//是String day, int x 
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制   
	        //引号里面个格式也可以是 HH:mm:ss或者HH:mm等等，很随意的，不过在主函数调用时，要和输入的变 
	        //量day格式一致 
	        Date date = null;    
	        try {    
	            date = format.parse(day);    
	        } catch (Exception ex) {    
	            ex.printStackTrace();    
	        }    
	        if (date == null)    
	            return "";    
	        System.out.println("front:" + format.format(date)); //显示输入的日期   
	        Calendar cal = Calendar.getInstance();    
	        cal.setTime(date);    
	        cal.add(Calendar.MINUTE, x);// 24小时制    
	        date = cal.getTime();    
	        System.out.println("after:" + format.format(date));  //显示更新后的日期  
	        cal = null;    
	        return format.format(date);    
	   
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
     * 获取当前日期的前几天
     * @param days
     */
    
    public static String getBeforeCurrentDay(int days){
    	 Format f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
         Calendar c = Calendar.getInstance(); 

    	c.add(Calendar.DATE, days); 
    	return f.format(c.getTime());
        

    }
    
    /**
     * 将日期转成成字符串   2015-04-01 10:24:06-->20150401102406
     * @param args
     */
    public static String convertDate2Sting(Date d){
    	SimpleDateFormat sdf =   new SimpleDateFormat(TIME_FORMAT);
	     String str = sdf.format(d).replace("-", "").replace(":", "").replace(" ", "");
	     return str;
    }
    
    /**
     * 获取零点时刻的long 值   2015-04-01 23:59:59-->1437438432842
     * @param args
     */
    public static long getTodayZeroTime(Date d ){
    	SimpleDateFormat sf = new SimpleDateFormat(TIME_FORMAT);
    	String s = sf.format(d);
    	String lastDateString  =s.split(" ")[0]+ " 23:59:59";
    	return stringDateConvert2long(lastDateString,TIME_FORMAT);
    }
    
    /**
     * 记录首次开机和最后一次关机时间
     * @param sb1
     * @return
     */
    public static HashMap<String, Long> getFirstAndLastTime(StringBuffer sb1,String type){
    	String[] s1 = sb1.toString().split(";");
    	Map<String, Long> map = Collections.synchronizedMap(new HashMap<String, Long>());
    	long[] l = null;
    	if(s1.length<1){
    		return null;
    	}else{
    		l = new long[s1.length];
    		//关机时间点排序
			for(int i=0; i<s1.length;i++){
				l[i]=Long.parseLong(s1[i]);
			}
			if(l.length>0){
				Arrays.sort(l);
			}else{
				return null;
			}
    	}
    	if("on".equals(type)){
    		map.put("firstOn",  l[0]);
        	map.put("lastOn",  l[l.length-1]);
    	}else{
    		map.put("firstOff",  l[0]);
        	map.put("lastOff",  l[l.length-1]);
    	}
    	return (HashMap<String, Long>) map;
    	
    }
    
    public static boolean isPlotPeriod(int days,String longDate){
		 //时间点只取当天零点至晚上23:59:59
		//时间点过滤，突发性泄露只计算当天的数据
		SimpleDateFormat sf = new SimpleDateFormat(TIME_FORMAT);
		//系统当前时间
    	Date d = new Date();
    	String s = sf.format(d);
    	Long ls = stringDateConvert2long(s,TIME_FORMAT);
    	String dataString = longConvert2DateString(Long.valueOf(ls),"yyyy-MM-dd HH:mm:ss");
		String lastDateString  =dataString.split(" ")[0]+ " 23:59:59";
		
		String beforePeroidDateString =getBeforeCurrentDay(days);
		String zeroDateString  = beforePeroidDateString.split(" ")[0]+ " 00:00:00";
		Long zeroDateLong = stringDateConvert2long(zeroDateString,TIME_FORMAT);
		Long lastDateLong = stringDateConvert2long(lastDateString,TIME_FORMAT);
		System.out.println("zeroDateLong    ="+zeroDateLong);
		System.out.println("longDate    ="+longDate);
		System.out.println("lastDateLong    ="+lastDateLong);
		if((Long.valueOf(longDate)>=zeroDateLong)&&
				(Long.valueOf(longDate)<=lastDateLong)){
			return true;
		}
		
		return false;
	}
    
    public static String dateDirfunc(){
    	Date d = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	test1();
    	return sdf.format(d).replace("-", "");
    }
    
    public static void test1(){
    	File file = new File("F:\\local1\\ewewqq\\2015042401.TXT");
    	System.out.println(file.getAbsolutePath());
    	System.out.println(file.getName());
    	System.out.println(file.getPath());
    	System.out.println(file.getParent());
    }
    public static void main(String[] args){
//    	SimpleDateFormat sf = new SimpleDateFormat(TIME_FORMAT);
//    	Date d = new Date();
//    	String s = sf.format(d);
//    	Long l = stringDateConvert2long(s,TIME_FORMAT);
//    	System.out.println(s);
//    	System.out.println(l);
//    	boolean b  = isToday(l+"");
//    	System.out.println(b);
//    	String[] s2 = s.split(" ");
//    	System.out.println(s2[0]);
//    	String s = getBeforeCurrentDay(-5);
//    	
//    	System.out.println(s);
    	
    	//System.out.println(addDateMinut("2013-08-29 08:16:36",-61));
    	
//    	System.out.println(longConvert2DateString(1422806403366l, "yyyy-MM-dd HH:mm:ss"));
    	String s = longConvert2DateString(1429846146921l, "yyyy-MM-dd HH:mm:ss");
    	System.out.println(s);
//    	long l = DateUtils.stringDateConvert2long(s,"yyyy-MM-dd HH:mm:ss");
//    	System.out.println(l);
    	//System.out.println("value ="+getNextNumber());

    }
}
