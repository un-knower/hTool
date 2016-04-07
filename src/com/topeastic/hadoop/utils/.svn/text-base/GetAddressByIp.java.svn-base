package com.topeastic.hadoop.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.sf.json.JSONObject;

/**
 * 
 * @author sunxing 2015.02.09
 *
 */
public class GetAddressByIp {

	/**
	 * @param IP
	 * @return
	 * */
	public static String getAddressByIp(String IP) {
		String resout = "";
		try {
			String jsonString = getJsonContent("http://ip.taobao.com/service/getIpInfo.php?ip="
					+ IP);
			System.out.println(jsonString);
			JSONObject obj = JSONObject.fromObject(jsonString);
			JSONObject obj2 = (JSONObject) obj.get("data");
			// String code = (String) obj.get("code");
			String code = String.valueOf(obj.get("code"));
			if (code.equals("0")) {
				resout = obj2.get("country") + "--" + obj2.get("area") + "--"
						+ obj2.get("city") + "--" + obj2.get("isp");
			} else {
				resout = "IP地址有误";
			}
		} catch (Exception e) {
			e.printStackTrace();
			resout = "获取IP地址异常：" + e.getMessage();
		}
		return resout;
	}

	public static String getJsonContent(String urlStr) {
		try {
			// 获取HttpURLConnection连接对象
			URL url = new URL(urlStr);
			HttpURLConnection httpConn = (HttpURLConnection) url
					.openConnection();
			// 设置连接属性
			httpConn.setConnectTimeout(3000);
			httpConn.setDoInput(true);
			httpConn.setRequestMethod("GET");
			// 获取相应码
			int respCode = httpConn.getResponseCode();
			if (respCode == 200) {
				return convertStream2Json(httpConn.getInputStream());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	private static String convertStream2Json(InputStream inputStream) {
		String jsonString = "";
		// ByteArrayOutputStream相当于内存输出流
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		// 将输入流转移到内存输出流中
		try {
			while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, len);
			}
			// 将内存流转换为字符串
			jsonString = new String(out.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

	/**
	 * @验证ip地址的格式是否正确
	 * @param args
	 */
	public static boolean validateIp(String ip){
		String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		
		Pattern pat = Pattern.compile(rexp);  
		
		Matcher mat = pat.matcher(ip);  
		
		boolean ipAddress = mat.find();
		
		System.out.println(ipAddress);
		
		return ipAddress;
	}
	
	public static void main(String[] args) {

		String result = getAddressByIp("58.255.202.114");
		System.out.println(result);
		//validateIp("58.255.202.114");

	}

}
