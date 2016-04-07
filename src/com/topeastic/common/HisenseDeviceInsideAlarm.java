package com.topeastic.common;


public class HisenseDeviceInsideAlarm {
	private String[] hexArray = null;

	public HisenseDeviceInsideAlarm(String content) {
		// 将字符串转换成
		// if(content.length()>=162){
		int len = content.length();
		// content = content.substring(32,len-6);
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
		// }
	}

	/**
	 * @description 室内温度传感器是否出现故障
	 * @return boolean
	 */
	public boolean insideTempSensorIsFailed() {
		String temp = Util.hexToBinaryString(hexArray[23]);
		char ch = temp.charAt(0);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}

	/**
	 * @description 室内盘管温度传感器故障
	 * @return boolean
	 */
	public boolean insidePadTempSensorIsFailed() {
		String temp = Util.hexToBinaryString(hexArray[23]);
		char ch = temp.charAt(1);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}

	/**
	 * @description 室内盘管温度传感器故障
	 * @return boolean
	 */
	public boolean insideHumiditySensorIsFailed() {
		String temp = Util.hexToBinaryString(hexArray[23]);
		char ch = temp.charAt(2);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}

	/**
	 * @description 室内排水泵故障
	 * @return boolean
	 */
	public boolean insidePumpIsFailed() {
		String temp = Util.hexToBinaryString(hexArray[23]);
		char ch = temp.charAt(3);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}

	/**
	 * @description 室内风机电机运转异常故障
	 * @return boolean
	 */
	public boolean insideFanIsFailed() {
		String temp = Util.hexToBinaryString(hexArray[23]);
		char ch = temp.charAt(4);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}

	/**
	 * @description 柜机格栅保护告警
	 * @return boolean
	 */
	public boolean cabinetRackGuardAlarm() {
		String temp = Util.hexToBinaryString(hexArray[23]);
		char ch = temp.charAt(5);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}

	/**
	 * @description 室内电压过零检测故障
	 * @return boolean
	 */
	public boolean insideVoltageZeroCheckFailed() {
		String temp = Util.hexToBinaryString(hexArray[23]);
		char ch = temp.charAt(6);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}

	/**
	 * @description 室内外通信故障
	 * @return boolean
	 */
	public boolean in_outCommFailed() {
		String temp = Util.hexToBinaryString(hexArray[23]);
		char ch = temp.charAt(7);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}
		return false;
	}
	
	/**
	 * @description 室内控制板与显示板通信故障
	 * @return boolean
	 */
	public boolean insidePanelAndMonitorCommFailed() {
		String temp = Util.hexToBinaryString(hexArray[24]);
		char ch = temp.charAt(0);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}
		return false;
	}
	
	/**
	 * @description 室内控制板与按键板通信故障
	 * @return boolean
	 */
	public boolean insidePanelAndKeyboardCommFailed() {
		String temp = Util.hexToBinaryString(hexArray[24]);
		char ch = temp.charAt(1);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}
		return false;
	}
	
	/**
	 * @description WIFI控制板与室内控制板通信故障
	 * @return boolean
	 */
	public boolean insidePanelAndWifiCommFailed() {
		String temp = Util.hexToBinaryString(hexArray[24]);
		char ch = temp.charAt(2);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}
		return false;
	}
	
	/**
	 * @description 室内控制板与室内电量板通信故障
	 * @return boolean
	 */
	public boolean insidePanelAndPowerCommFailed() {
		String temp = Util.hexToBinaryString(hexArray[24]);
		char ch = temp.charAt(3);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}
		return false;
	}
	
	/**
	 * @description 室内控制板EEPROM出错
	 * @return boolean
	 */
	public boolean insidePanelEEPROMError() {
		String temp = Util.hexToBinaryString(hexArray[24]);
		char ch = temp.charAt(4);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}
		return false;
	}
	
	

}
