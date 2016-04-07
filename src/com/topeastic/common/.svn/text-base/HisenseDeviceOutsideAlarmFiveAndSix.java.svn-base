package com.topeastic.common;

public class HisenseDeviceOutsideAlarmFiveAndSix {
   private String[] hexArray = null;
	
	public HisenseDeviceOutsideAlarmFiveAndSix(String content) {
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
	 * @description 逆变器直流过电压故障
	 * @return boolean
	 */
	public boolean insideOut_machineModeCollision() {
		String temp = Util.hexToBinaryString(hexArray[52]);
		char ch = temp.charAt(0);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description 逆变器直流低电压故障
	 * @return boolean
	 */
	public boolean inverterDC_Over_VoltageGuard() {
		String temp = Util.hexToBinaryString(hexArray[52]);
		char ch = temp.charAt(1);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description 逆变器交流过电流故障
	 * @return boolean 
	 */
	public boolean inverterAC_Over_VoltageGuard() {
		String temp = Util.hexToBinaryString(hexArray[52]);
		char ch = temp.charAt(2);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description 失步检出
	 * @return boolean 
	 */
	public boolean stall_Detection() {
		String temp = Util.hexToBinaryString(hexArray[52]);
		char ch = temp.charAt(3);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description 速度推定脉冲检出法检出欠相故障
	 * @return boolean 
	 */
	public boolean speed_Detection_Method() {
		String temp = Util.hexToBinaryString(hexArray[52]);
		char ch = temp.charAt(4);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description 电流推定脉冲检出法检出欠相故障
	 * @return boolean 
	 */
	public boolean current_Detection_Method() {
		String temp = Util.hexToBinaryString(hexArray[52]);
		char ch = temp.charAt(5);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description 逆变器IPM故障-边沿
	 * @return boolean 
	 */
	public boolean IPM_Inverter_Fault_Edge() {
		String temp = Util.hexToBinaryString(hexArray[52]);
		char ch = temp.charAt(6);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description 逆变器IPM故障-电平
	 * @return boolean 
	 */
	public boolean IPM_Inverter_Fault_Level() {
		String temp = Util.hexToBinaryString(hexArray[52]);
		char ch = temp.charAt(7);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	
	/**
	 * @description PFC_IPM故障-边沿
	 * @return boolean 
	 */
	public boolean PFC_IP_Fault_Edge() {
		String temp = Util.hexToBinaryString(hexArray[53]);
		char ch = temp.charAt(0);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description PFC_IPM故障-电平
	 * @return boolean 
	 */
	public boolean PFC_IP_Fault_Level() {
		String temp = Util.hexToBinaryString(hexArray[53]);
		char ch = temp.charAt(1);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description PFC停电检出故障
	 * @return boolean 
	 */
	public boolean PFC_Power_Failure_Detection() {
		String temp = Util.hexToBinaryString(hexArray[53]);
		char ch = temp.charAt(2);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description PFC过电流检出故障
	 * @return boolean 
	 */
	public boolean PFC_Overcurrent_Fault_Detection() {
		String temp = Util.hexToBinaryString(hexArray[53]);
		char ch = temp.charAt(3);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description 直流电压检出异常
	 * @return boolean 
	 */
	public boolean voltage_DC_Detection() {
		String temp = Util.hexToBinaryString(hexArray[53]);
		char ch = temp.charAt(4);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description PFC低电压（有效值）检出故障
	 * @return boolean  
	 */
	public boolean Low_voltage_DC_Detection() {
		String temp = Util.hexToBinaryString(hexArray[53]);
		char ch = temp.charAt(5);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description AD偏置异常检出故障
	 * @return boolean  
	 */
	public boolean AD_Bias_Anomaly_Detection() {
		String temp = Util.hexToBinaryString(hexArray[53]);
		char ch = temp.charAt(6);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
	
	/**
	 * @description 逆变器PWM逻辑设置故障
	 * @return boolean  
	 */
	public boolean PWM_Inverter_Logic() {
		String temp = Util.hexToBinaryString(hexArray[53]);
		char ch = temp.charAt(7);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}
}
