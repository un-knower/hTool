package com.topeastic.hadoop.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import poss.util.Path;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class IbatisUtil {

	
	public static SqlMapClient getSqlMapClient(){
		FileReader reader ;   
        try { 
        	String file_path=Path.getPath()+File.separator+"sqlMapConfig.xml";
        	System.out.println(file_path);
            reader = new FileReader(file_path);
            System.out.println(reader==null);
            SqlMapClient sqlmap = SqlMapClientBuilder.buildSqlMapClient(reader);
            return sqlmap;
         
        } catch (IOException e) {   
            // TODO Auto-generated catch block   
            e.printStackTrace();   
        } 
        return null;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(getSqlMapClient().toString());
	}

}
