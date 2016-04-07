package com.topeastic.hadoop.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import poss.util.Path;

/**
 * 
 * 2011-12-13
 * 
 * @author <a href="mailto:sunxing@huawei.com">sunxing</a>
 * 
 */
public final class JdbcUtils {
//	private static String url = "jdbc:mysql://localhost:3306/jdbc";
//	private static String user = "root";
//	private static String password = "";
	private static DataSource myDataSource = null;

	private JdbcUtils() {
	}

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// myDataSource = new MyDataSource2();
			Properties prop = new Properties();
			// prop.setProperty("driverClassName", "com.mysql.jdbc.Driver");
			// prop.setProperty("user", "user");
//
//			InputStream is = JdbcUtils.class.getClassLoader()
//					.getResourceAsStream("dbcpconfig.properties");
			FileInputStream fs=new FileInputStream(Path.getPath()+File.separator+"dbcpconfig.properties");
			prop.load(fs);
			myDataSource = BasicDataSourceFactory.createDataSource(prop);
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static DataSource getDataSource() {
		return myDataSource;
	}

	public static Connection getConnection() throws SQLException {
		// return DriverManager.getConnection(url, user, password);
		return myDataSource.getConnection();
	}

	public static void free(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (conn != null)
					try {
						conn.close();
						// myDataSource.free(conn);
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			String [] values="AIH-W401-2059a0b7a1ed|	20150102|52799|0.0".split("\\|");
			execute(""+new Date().getTime(),values);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("asdadas");
			e.printStackTrace();
		}
	}
	
	public static void execute(String id,String[] values){
		try {
			Connection conn=getConnection();
			Statement st=conn.createStatement();
			String sql="insert into HADOOP_AIR_INFO values('"+id+"','"+values[0]+"','"+values[1].trim()+"',"+values[2]+","+values[3]+")";
			
			st.executeUpdate(sql);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("asdadas");
			e.printStackTrace();
		}
	}
}
