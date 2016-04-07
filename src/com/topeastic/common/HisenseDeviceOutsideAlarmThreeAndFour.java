package com.topeastic.common;

public class HisenseDeviceOutsideAlarmThreeAndFour {
	private String[] hexArray = null;
	
	public HisenseDeviceOutsideAlarmThreeAndFour(String content) {
		// 将字符串转换成
		int len = content.length();
		System.out.println("after substring     " + content);
		// len =content.length();
		if (len % 2 != 0) {
			len = len - 1;
		}
		StringBuffer sb = new StringBuffer();
		String str = null;
		for (int i = 0; i < len; i = i + 2) {
			str = content.substring(i, i + 2);
			sb.append(str).append(";");
		}
		this.hexArray = sb.toString().split(";");
	}
	
	
}
