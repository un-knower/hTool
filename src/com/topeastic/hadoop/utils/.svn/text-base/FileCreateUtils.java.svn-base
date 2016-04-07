package com.topeastic.hadoop.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FileCreateUtils {

	/**
	 * 生成一定大小范围的随机数
	 */
	public static void test(){
		 int max=20;
	        int min=10;
	        Random random = new Random();

	        int s = random.nextInt(max)%(max-min+1) + min;
	        System.out.println(s);
 
	}
	
	public static int test(int m, int n){
		 int max=m;
	        int min=n;
	        Random random = new Random();

	        int s = random.nextInt(max)%(max-min+1) + min;
	        //System.out.println(s);
	        return s;
	        

	}
	
	/**
	 * 生成指定大小的文件
	 */
	public static void createFile1(){
		  try{
	            File dirFile = new File("F:\\local2");
	            if(!dirFile.exists()){
	                if(!dirFile.mkdir())
	                    throw new Exception("目录不存在，创建失败！");
	             }
	            StringBuffer sb = new StringBuffer();
	             for(int i=3;i<4;i++){
	            	 sb.delete(0, sb.length());
	            	 File file = new File("F:\\local2\\file"+i+".txt");
	            	  if(!file.exists()){
	 	            	 if(!file.createNewFile())
	 		                    throw new Exception("文件不存在，创建失败！");
	 	            }
	            	  int rd=0; 
	            	  String m ="";
	            	  String s1 = "F4F50140490100FE01010101006600010400281A 13 80 80808080000000000000000000000000000000FFFFFF 0000 0000000000 80008000000000000000000000000000000000000000000000000008CAF4FB";
	            	 while(file.length()<(100*1024*1024)){
	            		 //sb.delete(0, sb.length());//先清除上一个组的数据 
	            		// StringBuffer sb = new StringBuffer();
	            	    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	            	    Date d = new Date();
	            	    String s = sf.format(d);
	            	    Long l = DateUtils.stringDateConvert2long(s,"yyyy-MM-dd hh:mm:ss");
	            		 rd=Math.random()>0.5?1:0; 			//随机产生0,1表示工作模式，0：制冷  1：制热
	            		 m=Math.random()>0.5?"C":"W"; 			//随机产生0,1表示工作模式，C：制冷  W：制热
	            		 //sb.append("1,14228064023"+test(6,4)+test(10,6)+",");
	            		 sb.append("1,"+l+",");
	            		 System.out.println("l      ="+l);
	            		 sb.append("AC,AIH-W401-2059A0FCB36B"+test(10,6)+",");
	            		 sb.append("F4F50140490100FE01010101006600010400281A");
	            		 sb.append(Integer.toHexString(test(30,25))+"");
	            		 sb.append(Integer.toHexString(test(30,25))+"");
	            		 sb.append("80808080000000000000000000000000000000FFFFFF");
	            		 sb.append(Integer.toHexString(test(42,25)));
	            		 sb.append(Integer.toHexString(test(42,25))+"000000000000");
	            		 sb.append(Integer.toHexString(test(10,6))+"00800000000000000000000000000000000000000000000008CAF4FB");
	            		 
	            		 sb.append(m).append( System.getProperty("line.separator"));
	            		
	            		 //str = sb.toString();
	            		 byte bytes[]=new byte[1024];   
	            		 bytes=sb.toString().getBytes();//新加的  
	            		 int b=sb.toString().length();//改   
	            		 //sb.delete(0, sb.length());
	            		 FileOutputStream fos=new FileOutputStream(file);
	            		 fos.write(bytes,0,b);  
	            		 fos.flush();
	            		 fos.close();
//	            		 OutputStream os = new FileOutputStream(file);		
//	            		 os.write(sb.toString().getBytes());
//	            		 os.flush();		
//	            		 os.close();
	            		 int t = test(3,1);
	            		 Thread.sleep(t*1000);
	            	 }
	             }
	           
	         }catch(Exception e){
	             System.out.println(e.getMessage());
	         }
	}
	
	
	public static void main(String[] args) {
		
//		for(int i =0; i<4;i++){
//			test(15,10);
//		}
		createFile1();
//		int rd=0; 
//		for(int i=0;i<10;i++){
//			rd=Math.random()>0.5?1:0; 
//			System.out.println(rd);
//		}
		
//		byte[] b = hexStringToBytes("F4F50140240100FE012701010066000108FFFFFF00000069006900E00407010000000000000000000006B8F4FB");
//		System.out.println(b);
		
	}
	
	public static byte[] hexStringToBytes(String hexString) {
		   if (hexString == null || hexString.equals("")) {    
		       return null;    
		   }    
		   hexString = hexString.toUpperCase();    
		   int length = hexString.length() / 2;    
		   char[] hexChars = hexString.toCharArray();    
		   byte[] d = new byte[length];    
		   for (int i = 0; i < length; i++) {    
		       int pos = i * 2;    
		       d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));    
		   }    
		   return d;    
		}  
	
	private static byte charToByte(char c) {    
		   return (byte) "0123456789ABCDEF".indexOf(c);    
		}   


	 


}
