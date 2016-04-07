package com.topeastic.hadoop.utils;


import java.lang.reflect.Method;
import java.util.Arrays;

import com.topeastic.bean.AirconStatusInfo;

public class ReflectHelper {

	/**
	 * 通过反射将字符串内容序列化成类
	 * @param c
	 * @param str
	 * @return
	 * @throws Exception
	 */
	 public static <E> Object reflectObj(Class<E> c,String str) throws Exception{  
		 		str = "AIH-W401-2059A0FCB36B8;10 0.0,27.515206682373098,27.49296780181338,26.0,0.0";
		       String[] strArrray = str.split(" ");  
		       String[] str1 = strArrray[0].split(";");
		       String[] str2 = strArrray[1].split(",");
		       
		       int strLen1=str1.length;//保存第一个数组长度        
		       int strLen2=str2.length;//保存第二个数组长度
		       str1= Arrays.copyOf(str1,strLen1+ strLen2);//扩容         
		       System.arraycopy(str2, 0, str1, strLen1,strLen2 );//将第二个数组与第一个数组合并         
		       System.out.println(Arrays.toString(str1));//输出数组
		       Class classType = Class.forName(c.getName());  
		       Method[] methodArray = classType.getDeclaredMethods();  
		        E e = c.newInstance();  
		        int j = 0;  
		         for(int i=0;i<methodArray.length;i++){  
		            if((methodArray[i].getName().substring(0,3).equalsIgnoreCase("set"))){  
		               if(j<str1.length){  
		                     Class[] type = methodArray[i].getParameterTypes();  
		                    System.out.println(type[0].getName());  
		                     if(type[0].getName().equals("java.lang.String")){  
		                        methodArray[i].invoke(e,str1[j]);
		                    }  
		                    if(type[0].getName().equals("java.lang.Integer")){  
		                        methodArray[i].invoke(e,Integer.parseInt(str1[j]));  
		                   }  
		                    if(type[0].getName().equals("double")){  
		                        methodArray[i].invoke(e,Double.parseDouble((str1[j])));  
		                    }  
		                     j++;  
		                }  
		            }  
		        }  
		        return e;
		    }  

	 
	public static void main(String[] args) throws Exception {

		
		@SuppressWarnings("unused")
		Object b = reflectObj( new AirconStatusInfo().getClass(),"");
	}

	
}
