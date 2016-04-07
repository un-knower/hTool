/**
 * 
 */
package com.topeastic.common;

import java.math.BigInteger;
import java.util.Arrays;

import com.topeastic.hisense.common.MessageEnum;

/**
 * @author Sunxing
 *
 */
public class HisenseDeviceStatus {
	private byte[] conByte = null;
	private String[] hexArray = null;

	private HisenseDeviceInsideAlarm hisenseDeviceInsideAlarm;

	private HisenseDeviceOutsideAlarmOneAndTwo hisenseDeviceOutsideAlarmOneAndTwo;

	private HisenseDeviceStatus() {
	}

	public HisenseDeviceStatus(String content) {
		System.out.println("before substring     " + content.length());
		// 将字符串转换成
		if (content.length() >= 162) {
			int len = content.length();
			content = content.substring(32, len - 6);
			System.out.println("after substring     " + content);
			len = content.length();
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
		// 封装告警信息的工具类
		HisenseDeviceInsideAlarm hisenseDeviceInsideAlarm = new HisenseDeviceInsideAlarm(
				content);
		HisenseDeviceOutsideAlarmOneAndTwo hisenseDeviceOutsideAlarmOneAndTwo = new HisenseDeviceOutsideAlarmOneAndTwo(
				content);
		this.hisenseDeviceInsideAlarm = hisenseDeviceInsideAlarm;
		this.hisenseDeviceOutsideAlarmOneAndTwo = hisenseDeviceOutsideAlarmOneAndTwo;

	}

	public HisenseDeviceStatus(byte[] content) {
		this.conByte = content;
	}

	/**
	 * @description 返回设备的设置温度信息
	 * @return double
	 */
	public double getDeviceSetTemperature() {
		String temp = Util.hexToBinaryString(hexArray[3]);
	
		if (null != temp) {
			return Integer.parseInt(temp, 2);
		} else {
			return 0;
		}
	}

	/**
	 * @description 返回室内实际温度
	 * @return double
	 */
	public double getInsideActualTemperature() {
		String temp = Util.hexToBinaryString(hexArray[4]);
		if (null != temp) {
				return Integer.parseInt(temp, 2);
		} else {
			throw new BusinessException(MessageEnum.REQ_IS_NULL.getValue());
		}
	}
	
	/**
	 * @description 实时开机的分钟值
	 * @return double
	 */
	public double getRunningMiniute() {
		String temp = Util.hexToBinaryString(hexArray[15]);
		temp =temp.substring(0, 6);
		if (null != temp) {
			return Integer.parseInt("00"+temp, 2);
		} else {
			throw new BusinessException(MessageEnum.REQ_IS_NULL.getValue());

		}
	}
	
	/**
	 * @description 实时开机的小时值
	 * @return double
	 */
	public  double getRunningHour() {
		String temp = Util.hexToBinaryString(hexArray[14]);
		temp =temp.substring(0, 6);
		if (null != temp) {
			return Integer.parseInt("00"+temp, 2);
		} else {
			throw new BusinessException(MessageEnum.REQ_IS_NULL.getValue());

		}
	}

	/**
	 * @description 返回空调室内盘管温度
	 * @return double
	 */
	public double getInsidePadTemperature() {
		String temp = Util.hexToBinaryString(hexArray[5]);
		if (null != temp) {
				return Integer.parseInt(temp, 2);
		} else {
				throw new BusinessException(MessageEnum.REQ_IS_NULL.getValue());
		}
	}
	
	/**
	 * @description 返回空调室内湿度设定值
	 * @return double
	 */
	public double getInsideSetHumidity() {
		String temp = Util.hexToBinaryString(hexArray[6]);
		if (null != temp) {
			return Integer.parseInt(temp, 2);
		} else {
			throw new BusinessException(MessageEnum.REQ_IS_NULL.getValue());

		}
	}
	
	/**
	 * @description 单相电源电流或三相电源的Iab电流
	 * @return double
	 */
	public double getIabElectric() {
		String temp1 = Util.hexToBinaryString(hexArray[39]);
		if (null != temp1) {
			return Integer.parseInt(temp1, 2)/5;
		} else {
			throw new BusinessException(MessageEnum.REQ_IS_NULL.getValue());

		}
	}

	/**
	 * @description 返回空调室内实际湿度
	 * @return double
	 */
	public double getInsideActualHumidity() {
		String temp1 = Util.hexToBinaryString(hexArray[7]);
		if (null != temp1) {
			return Integer.parseInt(temp1, 2);
		} else {
			throw new BusinessException(MessageEnum.REQ_IS_NULL.getValue());

		}
	}

	/**
	 * @description 返回空调工作模式
	 * @return double
	 */
	public String getWoreMode() {
		String temp = Util.hexToBinaryString(hexArray[2]);
		temp = temp.substring(0, 4);
		return temp;
	}
	
	/**
	 * @description 返回空调是否开机
	 * @return double
	 */
	public boolean isRunning() {
		String temp = Util.hexToBinaryString(hexArray[2]);
		char ch = temp.charAt(4);
		if("1".equals(String.valueOf(ch))){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * @description 返回空调是否在除霜
	 * @return boolean
	 */
	public boolean isRemoveFrost() {
		String temp = Util.hexToBinaryString(hexArray[46]);
		char ch = temp.charAt(6);
		if("1".equals(String.valueOf(ch))){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * @description 返回空调工作模式
	 * @return double
	 */
	public boolean isShutdown() {
		String temp = Util.hexToBinaryString(hexArray[2]);
		char ch = temp.charAt(4);
		if ("0".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}

	/**
	 * @description 室外环境温度
	 * @return double
	 */
	public double getOutsideEnviTemperature() {
		String temp = Util.hexToBinaryString(hexArray[28]);
		if (null != temp) {
			return Integer.parseInt(temp, 2);
		} else {
			throw new BusinessException(MessageEnum.REQ_IS_NULL.getValue());

		}
	}

	/**
	 * @description 室外冷凝器温度
	 * @return double
	 */
	public double getOutsideCodenTemperature() {
		String temp = Util.hexToBinaryString(hexArray[29]);
		if (null != temp) {
			return Integer.parseInt(temp, 2);
		} else {
			throw new BusinessException(MessageEnum.REQ_IS_NULL.getValue());

		}
	}

	/**
	 * @description 压缩机排气温度
	 * @return double
	 */
	public double getCompressorExhaustTemperature() {
		String temp = Util.hexToBinaryString(hexArray[30]);
		if (null != temp) {
			return Integer.parseInt(temp, 2);
		} else {
			throw new BusinessException(MessageEnum.REQ_IS_NULL.getValue());

		}
	}

	/**
	 * @description 目标排气温度
	 * @return double
	 */
	public double getTargetExhaustTemperature() {
		String temp = Util.hexToBinaryString(hexArray[31]);
		if (null != temp) {
			return Integer.parseInt(temp, 2);
		} else {
			throw new BusinessException(MessageEnum.REQ_IS_NULL.getValue());

		}
	}

	/**
	 * @description 返回空调是否静音
	 * @return double
	 */
	public boolean isMute() {
		String temp = Util.hexToBinaryString(hexArray[20]);
		char ch = temp.charAt(5);
		if(!("0".equals(String.valueOf(ch)))){
			return true;
		}
		return false;
	}

	/**
	 * @description 返回空调是否睡眠
	 * @return double
	 */
	public boolean isSleep() {
		String temp = Util.hexToBinaryString(hexArray[1]);
		if(!("00000000".equals(temp))){
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Temp=");
		sb.append(getDeviceSetTemperature());
		return super.toString();
	}

	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static void main1(String[] args) {
		String s = "F4F50140490100FE01010101006600010400281A138080808080000000000000000000000000000000FFFFFF0000000000000080008000000000000000000000000000000000000000000000000008CAF4FB";
		int len = s.length();
		s = s.substring(30, len - 8);
		System.out.println(s);
		// int t = 0x08;
		// //Integer m = Integer.parseInt("0x"+"FF", 16);
		// //BigInteger m = BigInteger("0x"+"FF",16);
		Integer m = Integer.parseInt("A", 16); // 转成0x开头的整数 有效代码
		System.out.println(m);

		String iStr2 = Integer.toBinaryString(m); // 转成二进制 有效代码
		System.out.println(iStr2);

		// System.out.println(Arrays.toString(b));
		// changeArray("F4F50140490100FE01010101006600010400281A138080808080000000000000000000000000000000FFFFFF0000000000000080008000000000000000000000000000000000000000000000000008CAF4FB");
		// HisenseDeviceStatus hd = new
		// HisenseDeviceStatus("F4F50140490100FE01010101006600010400281A138080808080000000000000000000000000000000FFFFFF0000000000000080008000000000000000000000000000000000000000000000000008CAF4FB");

	}

	public static void changeArray(String s) {
		int len = s.length();
		s = s.substring(30, len - 6);
		len = s.length();
		System.out.println(len);
		if (len % 2 != 0) {
			len = len - 1;
		}
		StringBuffer sb = new StringBuffer();
		String str = null;
		for (int i = 0; i < len; i = i + 2) {
			str = s.substring(i, i + 2);
			sb.append(str);
			sb.append(";");

		}
		String[] hexArray = sb.toString().split(";");
		for (int i = 0; i < hexArray.length; i++) {
			System.out.println(hexArray[i]);
		}
	}

	/**
	 * 
	 * @param hexString
	 * @return char[]
	 * @description 将1个字节的16进制数转换成二进制的字节流
	 */
	public static char[] hexToBinaryChar(String hexString) {
		char[] binaryChar = null;
		String binaryString = Util.hexToBinaryString(hexString);
		binaryChar = binaryString.toCharArray();
		return binaryChar;
	}

	public HisenseDeviceInsideAlarm getHisenseDeviceInsideAlarm() {
		return hisenseDeviceInsideAlarm;
	}

	public void setHisenseDeviceInsideAlarm(
			HisenseDeviceInsideAlarm hisenseDeviceInsideAlarm) {
		this.hisenseDeviceInsideAlarm = hisenseDeviceInsideAlarm;
	}

	public static void main(String[] args) {
		// String s = hexToBinaryString("AA");
		// System.out.println(s);
		// Integer m = Integer.parseInt( s,2); // 转成0x开头的整数 有效代码
		// System.out.println(m);
//		HisenseDeviceStatus hd = new HisenseDeviceStatus(
//				"F4F50140490100FE01010101006600010400281A138080808080000000000000000000000000000000FFFFFF0000000000000080008000000000000000000000000000000000000000000000000008CAF4FB");
		//第十个字节表示设备类型 01--空调  15--除湿机 18--空气净化机(清新机)  19--全热交换机
		// System.out.println(hd.getDeviceSetTemperature());
		// // System.out.println(hd.getInnerActualTemperature());
		// System.out.println(hd.getInsidePadTemperature());
		// System.out.println(hd.getInsideSetHumidity());
		// System.out.println(hd.getInsideActualHumidity());isShutdown
		// System.out.println(hd.getWoreMode());
		// System.out.println(hd.isShutdown());
		// System.out.println(hd.getWoreMode());
//		System.out.println(hd.getHisenseDeviceInsideAlarm()
//				.insideTempSensorIsFailed());
//		String s = "F4F50140490100FE01010101006600010400281A191a80808080000000000000000000000000000000FFFFFF1e1b0000000000007008000000000000000000000000000000000000000000000000008CAF4FBC";
//		System.out.println(s.length());
//		String s1 = "0102";
//		 System.out.println(s1.substring(2,s1.length()));
		System.out.println( Integer.parseInt("000000111", 2));
//		System.out.println( Integer.parseInt("11111001", 2));
//		System.out.println( Integer.valueOf("11111001", 2));
		System.out.println(getComplement("000000111"));
		
		System.out.println( 249-256);
		
	}
	
	//获取到二进制的补码
	public static String getComplement(String s){
		char[] ch = s.toCharArray();
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<ch.length;i++){
			if(ch[i]=='0'){
				ch[i]='1';
			}else{
				ch[i]='0';
			}
			sb.append(ch[i]);
		}
		return sb.toString();
	}


}
