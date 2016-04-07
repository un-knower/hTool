package com.topeastic.hadoop.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static void test1(){
		 SimpleDateFormat sdf =   new SimpleDateFormat(TIME_FORMAT);
	     String str = sdf.format(new Date()).replace("-", "").replace(":", "").replace(" ", "");
	     System.out.println(str);
	}
	
	public static void test2(){
		File f = new File("F:\\local1\\1\\file1.txt");
		 SimpleDateFormat sdf =   new SimpleDateFormat(TIME_FORMAT);
	     String dateString = sdf.format(new Date()).replace("-", "").replace(":", "").replace(" ", "");
		 String fileName = f.getParent()+File.separator+dateString+".txt";
		 File newFile = new File(fileName);
		boolean  b  = f.renameTo(newFile);
		if(b){
			System.out.println("reName success");
		}
		if(newFile.exists()){
			System.out.println(newFile.getAbsolutePath());
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test2();
	}

}
