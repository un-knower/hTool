package com.topeastic.common;

public class HisenseDeviceOutsideAlarmOneAndTwo {
	private String[] hexArray = null;
	
	public HisenseDeviceOutsideAlarmOneAndTwo(String content) {
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
	
	/**
	 * @description 室内外机模式冲突
	 * @return boolean
	 */
	public boolean insideOut_machineModeCollision() {
		String temp = Util.hexToBinaryString(hexArray[48]);
		char ch = temp.charAt(0);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description 室外EEPROM出错
	 * @return boolean
	 */
	public boolean outsideEEPROMError() {
		String temp = Util.hexToBinaryString(hexArray[48]);
		char ch = temp.charAt(1);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description 室外盘管温度传感器故障
	 * @return boolean
	 */
	public boolean outsidePadTempSensorFailed() {
		String temp = Util.hexToBinaryString(hexArray[48]);
		char ch = temp.charAt(2);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description 排气温度传感器故障
	 * @return boolean
	 */
	public boolean exhaustTempSensorFailed() {
		String temp = Util.hexToBinaryString(hexArray[48]);
		char ch = temp.charAt(3);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description 室外环境温度传感器故障
	 * @return boolean
	 */
	public boolean outsideEnvioTempSensorFailed() {
		String temp = Util.hexToBinaryString(hexArray[48]);
		char ch = temp.charAt(4);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description 电压变压器故障
	 * @return boolean
	 */
	public boolean voltageTransformerFailed() {
		String temp = Util.hexToBinaryString(hexArray[48]);
		char ch = temp.charAt(5);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description 电流互感器故障
	 * @return boolean
	 */
	public boolean electricTransformerFailed() {
		String temp = Util.hexToBinaryString(hexArray[48]);
		char ch = temp.charAt(6);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description 室外控制与驱动通信故障
	 * @return boolean
	 */
	public boolean outsideCtrolDriverCommFailed() {
		String temp = Util.hexToBinaryString(hexArray[48]);
		char ch = temp.charAt(7);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description IPM模块过流保护
	 * @return boolean
	 */
	public boolean ipmModuleOvercurrentGuard() {
		String temp = Util.hexToBinaryString(hexArray[49]);
		char ch = temp.charAt(0);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**AC
	 * @description IPM模块过热保护
	 * @return boolean
	 */
	public boolean ipmModuleHotGuard() {
		String temp = Util.hexToBinaryString(hexArray[49]);
		char ch = temp.charAt(1);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**AC
	 * @description 交流电过电压保护
	 * @return boolean
	 */
	public boolean ac_Over_voltageGuard() {
		String temp = Util.hexToBinaryString(hexArray[49]);
		char ch = temp.charAt(2);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description 交流电欠电压保护
	 * @return boolean
	 */
	public boolean ac_Under_voltageGuard() {
		String temp = Util.hexToBinaryString(hexArray[49]);
		char ch = temp.charAt(3);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description 母线交流电欠电压保护
	 * @return boolean
	 */
	public boolean main_Over_voltageGuard() {
		String temp = Util.hexToBinaryString(hexArray[49]);
		char ch = temp.charAt(4);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description 母线交流电欠电压保护
	 * @return boolean
	 */
	public boolean main_Under_voltageGuard() {
		String temp = Util.hexToBinaryString(hexArray[49]);
		char ch = temp.charAt(5);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description 母线交流电欠电压保护
	 * @return boolean
	 */
	public boolean pfc_Over_electricGuard() {
		String temp = Util.hexToBinaryString(hexArray[49]);
		char ch = temp.charAt(6);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}
		return false;
	}
	
	/**
	 * @description 室外机最大电流保护
	 * @return boolean
	 */
	public boolean outMachine_maxElectricGuard() {
		String temp = Util.hexToBinaryString(hexArray[49]);
		char ch = temp.charAt(7);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}
		return false;
	}
}
