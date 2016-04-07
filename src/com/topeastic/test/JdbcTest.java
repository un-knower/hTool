package com.topeastic.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JdbcTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("119.29.94.71的connect为"+getConn("119.29.94.71").toString());
		System.out.println("10.104.130.68的connect为"+getConn("10.104.130.68").toString());
	}
	
	
	public static Connection getConn(String Url) {
	    String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://"+Url+":3306/boss_data?useUnicode=true&amp;characterEncoding=UTF-8";
	    String username = "root";
	    String password = "sun";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}

}
