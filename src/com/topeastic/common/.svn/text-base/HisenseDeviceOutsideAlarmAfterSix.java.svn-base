package com.topeastic.common;

public class HisenseDeviceOutsideAlarmAfterSix {
	    private String[] hexArray = null;
		
		public HisenseDeviceOutsideAlarmAfterSix(String content) {
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
		 * @description 逆变器PWM初始化故障
		 * @return boolean
		 */
		public boolean pwm_Inverter_Init_Failure() {
			String temp = Util.hexToBinaryString(hexArray[54]);
			char ch = temp.charAt(0);
			if ("1".equals(String.valueOf(ch))) {
				return true;
			}

			return false;
		}
		
		/**
		 * @description PFC_PWM逻辑设置故障
		 * @return boolean
		 */
		public boolean PFC_PWM_Inverter_Logic_Failure() {
			String temp = Util.hexToBinaryString(hexArray[54]);
			char ch = temp.charAt(1);
			if ("1".equals(String.valueOf(ch))) {
				return true;
			}

			return false;
		}
		
		/**
		 * @description PFC_PWM初始化故障
		 * @return boolean
		 */
		public boolean PFC_PWM_Inverter_Init_Failure() {
			String temp = Util.hexToBinaryString(hexArray[54]);
			char ch = temp.charAt(2);
			if ("1".equals(String.valueOf(ch))) {
				return true;
			}

			return false;
		}
		
		/**
		 * @description 温度异常
		 * @return boolean
		 */
		public boolean Temperature_Anomalies() {
			String temp = Util.hexToBinaryString(hexArray[54]);
			char ch = temp.charAt(3);
			if ("1".equals(String.valueOf(ch))) {
				return true;
			}

			return false;
		}
		
		/**
		 * @description 电流采样电阻不平衡调整故障
		 * @return boolean
		 */
		public boolean resistance_Adjustment() {
			String temp = Util.hexToBinaryString(hexArray[54]);
			char ch = temp.charAt(4);
			if ("1".equals(String.valueOf(ch))) {
				return true;
			}

			return false;
		}
		
		/**
		 * @description 电机参数设置故障
		 * @return boolean
		 */
		public boolean Motor_Parameter_Setting() {
			String temp = Util.hexToBinaryString(hexArray[54]);
			char ch = temp.charAt(5);
			if ("1".equals(String.valueOf(ch))) {
				return true;
			}

			return false;
		}
		
		/**
		 * @description MCE故障
		 * @return boolean
		 */
		public boolean MCE_Fault() {
			String temp = Util.hexToBinaryString(hexArray[54]);
			char ch = temp.charAt(6);
			if ("1".equals(String.valueOf(ch))) {
				return true;
			}

			return false;
		}
		
		/**
		 * @description 驱动EEPROM故障
		 * @return boolean
		 */
		public boolean EEPROM_Fault() {
			String temp = Util.hexToBinaryString(hexArray[54]);
			char ch = temp.charAt(7);
			if ("1".equals(String.valueOf(ch))) {
				return true;
			}

			return false;
		}
		
		/**
		 * @description 室外盘管过载禁升频
		 * @return boolean
		 */
		public boolean outdoor_Overload_Ban() {
			String temp = Util.hexToBinaryString(hexArray[55]);
			char ch = temp.charAt(0);
			if ("1".equals(String.valueOf(ch))) {
				return true;
			}

			return false;
		}
		
		/**
		 * @description 室外盘管过载降频
		 * @return boolean
		 */
		public boolean outdoor_Down_Ban() {
			String temp = Util.hexToBinaryString(hexArray[55]);
			char ch = temp.charAt(1);
			if ("1".equals(String.valueOf(ch))) {
				return true;
			}

			return false;
		}
		
		/**
		 * @description 室内盘管过载禁升频
		 * @return boolean
		 */
		public boolean indoor_Overload_Ban() {
			String temp = Util.hexToBinaryString(hexArray[55]);
			char ch = temp.charAt(2);
			if ("1".equals(String.valueOf(ch))) {
				return true;
			}

			return false;
		}
		
		/**
		 * @description 室内盘管过载降频
		 * @return boolean
		 */
		public boolean indoor_Down_Ban() {
			String temp = Util.hexToBinaryString(hexArray[55]);
			char ch = temp.charAt(3);
			if ("1".equals(String.valueOf(ch))) {
				return true;
			}

			return false;
		}
		
		/**
		 * @description 压降排气过载禁升频
		 * @return boolean
		 */
		public boolean Exhaust_Overload_Ban() {
			String temp = Util.hexToBinaryString(hexArray[55]);
			char ch = temp.charAt(4);
			if ("1".equals(String.valueOf(ch))) {
				return true;
			}

			return false;
		}
		
		/**
		 * @description 压降排气过载降频
		 * @return boolean
		 */
		public boolean Exhaust_Down_Ban() {
			String temp = Util.hexToBinaryString(hexArray[55]);
			char ch = temp.charAt(5);
			if ("1".equals(String.valueOf(ch))) {
				return true;
			}

			return false;
		}
		
		/**
		 * @description 室内盘管冻结禁升频
		 * @return boolean
		 */
		public boolean Indoo_Freeze_an_Upscaling() {
			String temp = Util.hexToBinaryString(hexArray[55]);
			char ch = temp.charAt(6);
			if ("1".equals(String.valueOf(ch))) {
				return true;
			}

			return false;
		}
		
		/**
		 * @description 室内盘管冻结降频
		 * @return boolean down
		 */
		public boolean Indoo_Freeze_an_Down() {
			String temp = Util.hexToBinaryString(hexArray[55]);
			char ch = temp.charAt(7);
			if ("1".equals(String.valueOf(ch))) {
				return true;
			}

			return false;
		}
		
		
		

		/**
		 * @description 室内外通信降频
		 * @return boolean down
		 */
		public boolean Communication_Down() {
			String temp = Util.hexToBinaryString(hexArray[56]);
			char ch = temp.charAt(0);
			if ("1".equals(String.valueOf(ch))) {
				return true;
			}

			return false;
		}
		
		/**
		 * @description 模块温度过载限频
		 * @return boolean down
		 */
		public boolean Temperature_Overload() {
			String temp = Util.hexToBinaryString(hexArray[56]);
			char ch = temp.charAt(1);
			if ("1".equals(String.valueOf(ch))) {
				return true;
			}

			return false;
		}
		
		/**
		 * @description 变调率限频
		 * @return boolean down
		 */
		public boolean Modulation_FrequencyLimit() {
			String temp = Util.hexToBinaryString(hexArray[56]);
			char ch = temp.charAt(2);
			if ("1".equals(String.valueOf(ch))) {
				return true;
			}

			return false;
		}
		
		/**
		 * @description 相电流限频
		 * @return boolean down
		 */
		public boolean Phase_Current_Limit() {
			String temp = Util.hexToBinaryString(hexArray[56]);
			char ch = temp.charAt(3);
			if ("1".equals(String.valueOf(ch))) {
				return true;
			}

			return false;
		}
		
		/**
		 * @description 并用节电保护禁升频
		 * @return boolean down
		 */
		public boolean Power_Saver_Ban_Upscaling() {
			String temp = Util.hexToBinaryString(hexArray[56]);
			char ch = temp.charAt(4);
			if ("1".equals(String.valueOf(ch))) {
				return true;
			}

			return false;
		}
		
		/**
		 * @description 并用节电保护降频
		 * @return boolean down
		 */
		public boolean Power_Saver_Ban_Down() {
			String temp = Util.hexToBinaryString(hexArray[56]);
			char ch = temp.charAt(5);
			if ("1".equals(String.valueOf(ch))) {
				return true;
			}

			return false;
		}
		
		/**
		 * @description 过电流保护禁升频
		 * @return boolean down
		 */
		public boolean Overcurrent_Ban_Upscaling() {
			String temp = Util.hexToBinaryString(hexArray[56]);
			char ch = temp.charAt(6);
			if ("1".equals(String.valueOf(ch))) {
				return true;
			}

			return false;
		}
		
		
		/**
		 * @description 过电流保护降频
		 * @return boolean down
		 */
		public boolean Overcurrent_Ban_Down() {
			String temp = Util.hexToBinaryString(hexArray[56]);
			char ch = temp.charAt(7);
			if ("1".equals(String.valueOf(ch))) {
				return true;
			}

			return false;
		}
		
}
