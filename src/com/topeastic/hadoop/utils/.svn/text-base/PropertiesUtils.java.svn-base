package com.topeastic.hadoop.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;

import poss.util.Path;

/**
 * 读取Properties文件中配置项的例子
 * 
 * @author sunxing @ Date: 2015-2-2 18:38:40
 */
public class PropertiesUtils {

	public static String getConfigProperty(String key) {
		Properties prop = new Properties();
		String file_path = Path.getPath() + File.separator
				+ "config.properties";
		System.out.println(file_path);
		FileInputStream in;
		try {
			in = new FileInputStream(file_path);

			System.out.println(in == null);
			// InputStream in
			// =ClassLoader.getSystemResourceAsStream("config.properties");
			String value = null;
			prop.load(in);
			value = prop.getProperty(key).trim();
			System.out.println("value     =" + value);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void getLog4jProperty() {
		Properties prop = new Properties();
		String file_path = Path.getPath() + File.separator + "log4j.xml";
		System.out.println(file_path);
		try {
			DOMConfigurator.configure(file_path);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		getConfigProperty("output_avg_result");
	}

}
