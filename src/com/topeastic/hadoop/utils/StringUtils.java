package com.topeastic.hadoop.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.topeastic.bean.AirconStatus;

/**
 * @deprecated 对log日志分析输出
 * @author sunxing
 * 
 */
public class StringUtils {

	

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String dateToStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(date);
		return str;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date strToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static boolean isEmpty(String s) {
		return (null == s) || "".equals(s) ? true : false;
	}

	/**
	 * @description 单相电源电流或三相电源的Iab电流
	 * @return double
	 */
	public static double getIabElectric(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			return Integer.parseInt(temp, 2) / 5;
		} else {
			return 0;

		}
	}

	/**
	 * @description 获取某点的电压
	 * @param hexArray
	 * @return
	 */
	public static double getVoltage(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			return Integer.parseInt(temp, 2);
		} else {
			return 0;
		}
	}

	/**
	 * @description 单相电源电流或三相电源的Uab电压
	 * @return
	 */
	public static double getUxxVoltage(String Ua, String Ub) {
		String a = hexToBinaryString(Ua);
		String b = hexToBinaryString(Ub);
		if (null != a && null != b) {
			return Math.abs(Integer.parseInt(a, 2) - Integer.parseInt(b, 2));
		} else {
			return 0;
		}
	}

	/**
	 * @description 返回设备的设置温度信息
	 * @return double
	 */
	public static double getDeviceSetTemperature(String hexArray) {
		String temp = hexToBinaryString(hexArray);
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
	public static double getInsideActualTemperature(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		// temp = getComplement(temp);
		if (null != temp) {
			// char ch = temp.charAt(0);
			// if(ch =='1'){
			// return (Integer.parseInt(temp, 2)+1)-256;
			// }else{
			// return Integer.parseInt(temp, 2);
			// }
			return Integer.parseInt(temp, 2);

		} else {
			return 0;
		}
	}

	/**
	 * @description 返回空调室内盘管温度
	 * @return double
	 */
	public static double getInsidePadTemperature(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		// temp = getComplement(temp);
		if (null != temp) {
			// char ch = temp.charAt(0);
			// if(ch =='1'){
			// return (Integer.parseInt(temp, 2)+1)-256;
			// }else{
			// return Integer.parseInt(temp, 2);
			// }
			return Integer.parseInt(temp, 2);

		} else {
			return 0;
		}
	}

	/**
	 * @description 室外环境温度
	 * @return double
	 */
	public static double getOutsideEnviTemperature(String hexArray,
			String workModel) {
		String temp = hexToBinaryString(hexArray);
		// temp = getComplement(temp);
		if (null != temp) {
			if ("01".equals(workModel)) {

				char ch = temp.charAt(0);
				// temp = getComplement(temp);
				if (ch == '1') {
					return (Integer.parseInt(temp, 2) + 1) - 256;
				} else {
					return Integer.parseInt(temp, 2);
				}
			}
			return Integer.parseInt(temp, 2);

		} else {
			return 0;
		}
	}

	/**
	 * @description 室外冷凝器温度
	 * @return double
	 */
	public static double getOutsideCodenTemperature(String hexArray,
			String workModel) {
		String temp = hexToBinaryString(hexArray);
		// temp = getComplement(temp);
		if (null != temp) {
			if ("01".equals(workModel)) {
				// temp = getComplement(temp);
				char ch = temp.charAt(0);
				if (ch == '1') {
					return (Integer.parseInt(temp, 2) + 1) - 256;
				} else {
					return Integer.parseInt(temp, 2);
				}
			}
			return Integer.parseInt(temp, 2);

		} else {
			return 0;
		}
	}

	/**
	 * @description 压缩机排气温度
	 * @return double
	 */
	public static double getCompressorExhaustTemperature(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (null != temp) {
			return Integer.parseInt(temp, 2);
		} else {
			return 0;

		}
	}

	/**
	 * @description 返回空调是否开机
	 * @return double
	 */
	public static boolean isRunning(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		char ch = temp.charAt(4);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @deprecated 返回电源是否为单相电源
	 * @param hexArray
	 * @return
	 */
	public static boolean isSingle(String hexArray){
		if("80".equals(hexArray)){
			return true;
		}
		return false;
	}
	
	/**
	 * @deprecated 获取电压值   高位-低位
	 * @param high
	 * @param low
	 * @return
	 */
	public static int Uxx(String high,String low){
		return knowTrueForm(high)-knowTrueForm(low);
	}
	
	public static double Ixx(String hexArray){
		return (double)knowTrueForm(hexArray) / 5;
	}

	/**
	 * @description 返回空调是否在除霜
	 * @return boolean
	 */
	public static boolean isRemoveFrost(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		// String temp = hexArray;
		char ch = temp.charAt(6);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @description 返回空调是否睡眠
	 * @return boolean
	 */
	public static boolean isSleep(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		if (!("00000000".equals(temp))) {
			return true;
		}
		return false;
	}

	/**
	 * @description 返回空调是否静音
	 * @return double
	 */
	public static boolean isMute(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		char ch = temp.charAt(5);
		if (!("0".equals(String.valueOf(ch)))) {
			return true;
		}
		return false;
	}

	/**
	 * @description 压缩机管壳温度保护
	 * @return boolean
	 */
	public static boolean compressorShellTempDefend(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		char ch = temp.charAt(2);
		if ("1".equals(String.valueOf(ch))) {
			return true;
		}

		return false;
	}

	/**
	 * @description是否是强风
	 * @return boolean
	 */
	public static boolean isStrongWind(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		temp = temp.substring(1, 7);
		if ("000100".equals(temp)) {
			return true;
		}
		return false;
	}

	/**
	 * @description 实时开机的分钟值
	 * @return double
	 */
	public static double getRunningMiniute(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		temp = temp.substring(0, 6);
		if (null != temp) {
			return Integer.parseInt(temp, 2);
		} else {
			return 0;

		}
	}

	/**
	 * @description 实时开机的小时值
	 * @return double
	 */
	public static double getRunningHour(String hexArray) {
		String temp = hexToBinaryString(hexArray);
		temp = temp.substring(0, 5);
		if (null != temp) {
			return Integer.parseInt(temp, 2);
		} else {
			return 0;

		}
	}

	/**
	 * @description 返回空调工作模式
	 * @return double
	 */
	public static String getWoreMode(String command) {
		String temp = hexToBinaryString(command);
		return temp.substring(0, 4);
	}

	/**
	 * @description 返回空调工作模式
	 * @return double
	 */
	public static String getDeviceType(String command) {
		return command.substring(0, 4);
	}

	/**
	 * @Description 将16进制的字符流转成二进制的字符流
	 * @param hexString
	 * @return String
	 */
	public static String hexToBinaryString(String hexString) {
		String binaryString = null;
		if ((!isEmpty(hexString)) && (hexString.length() == 2)) {
			// 第一位16进制小于8的，需要在转成二进制时在前面补0
			int i1 = Integer.parseInt(hexString.substring(0, 1), 16);
			String m = Integer.toBinaryString(Integer.parseInt(
					hexString.substring(0, 1), 16));
			if (i1 < 8) {
				String s = "";
				for (int i = 0; i < 4 - m.length(); i++) {
					s = "0" + s;
				}
				m = s + m;
			}
			// 第二位18进制小于8的，需要在转成二进制时在前面补0
			int i2 = Integer.parseInt(hexString.substring(1, 2), 16);
			String n = Integer.toBinaryString(Integer.parseInt(
					hexString.substring(1, 2), 16));
			if (i2 < 8) {
				// 第二位16进制小于8的，需要在转成二进制时在前面补0
				String t = "";
				for (int i = 0; i < 4 - n.length(); i++) {
					t = "0" + t;
				}
				n = t + n;
			}
			binaryString = m + n;
		}
		return binaryString;
	}

	/**
	 * 将16进制字符串转成字符数组
	 * 
	 * @param command
	 * @return
	 */
	public static String[] getHexString(String command) {
		int len = command.length();
		command = command.substring(32, len - 6);
		len = command.length();
		if (len % 2 != 0) {
			len = len - 1;
		}
		StringBuffer sb = new StringBuffer();
		String str = null;
		for (int i = 0; i < len; i = i + 2) {
			str = command.substring(i, i + 2);
			sb.append(str).append(";");
		}
		return sb.toString().split(";");
	}
	
	

	/**
	 * 将字符串内容序列化成一个字符串数组
	 * 
	 * @param s
	 * @return
	 */
	public static String[] strToArray(String s) {
		String[] strArrray = s.split("\\|");
		String[] str1 = strArrray[0].split(";");
		String[] str2 = strArrray[1].split(",");

		int strLen1 = str1.length;// 保存第一个数组长度
		int strLen2 = str2.length;// 保存第二个数组长度
		str1 = Arrays.copyOf(str1, strLen1 + strLen2);// 扩容
		System.arraycopy(str2, 0, str1, strLen1, strLen2);// 将第二个数组与第一个数组合并
		System.out.println(Arrays.toString(str1));// 输出数组
		return str1;

	}

	/**
	 * 通过室外温度获取分组标志
	 * 
	 * @param d
	 * @return
	 */
	public static String getCoolGroups(double d) {

		// 如果是制冷
		if (d >= 25 && d <= 29) {
			return "c1";
		}
		if (d >= 30 && d <= 34) {
			return "c2";
		}
		if (d >= 35 && d <= 39) {
			return "c3";
		}
		if (d >= 40 && d <= 44) {
			return "c4";
		}
		if (d >= 45 && d <= 49) {
			return "c5";
		}

		return null;

	}

	public static String getHotGroups(double d) {
		if (d >= -15 && d <= -11) {
			return "h1";
		}
		if (d >= -10 && d <= -6) {
			return "h2";
		}
		if (d >= -5 && d <= -1) {
			return "h3";
		}
		if (d >= 0 && d <= 4) {
			return "h4";
		}
		if (d >= 5 && d <= 9) {
			return "h5";
		}

		if (d >= 10 && d <= 14) {
			return "h6";
		}
		return null;
	}

	/**
	 * @description //获取到二进制的补码
	 * @return double
	 */

	public static String getComplement(String s) {
		char[] ch = s.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ch.length; i++) {
			if (ch[i] == '0') {
				ch[i] = '1';
			} else {
				ch[i] = '0';
			}
			sb.append(ch[i]);
		}
		return sb.toString();
	}

	/**
	 * 校验三个值是否递减
	 * 
	 * @param args
	 */

	public static boolean isDecrease(double d1, double d2, double d3) {
		if (d2 < d1) {
			if (d3 < d2) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * @description 计算温差
	 * @return double
	 */
	public static double calWencha(double t1, double t2, String workModel) {
		if ("10".equals(workModel)) {
			return (t1 - t2) > 0 ? t1 - t2 : 0;
		} else {
			return (t2 - t1) > 0 ? t2 - t1 : 0;
		}

	}

	/**
	 * @description 计算室内温差
	 * @return double
	 */
	public static double calInsideWencha(double t1, double t2, String workModel) {
		if ("10".equals(workModel)) {
			return (t1 - t2) > 0 ? t1 - t2 : 0;
		} else {
			return (t2 - t1) > 0 ? t2 - t1 : 0;
		}

	}

	/**
	 * @description 计算室外温差
	 * @return double
	 */
	public static double calExhaustWencha(double t1, double t2, double t3,
			String workModel) {
		if ("01".equals(workModel)) {
			return (t1 - t2) > 0 ? t1 - t2 : 0;
		} else {
			return (t1 - t3) > 0 ? t1 - t3 : 0;
		}

	}

	/**
	 * @description 计算设定温差
	 * @return double
	 */
	public static double calSetWencha(double t1, double t2, String workModel) {
		if ("10".equals(workModel)) {
			return (t2 - t1) > 0 ? t2 - t1 : 0;
		} else {
			return (t1 - t2) > 0 ? t1 - t2 : 0;
		}

	}
	
	/**
	 * 已知16进制补码
	 * 
	 * @param str
	 * @return
	 */
	public static int knowComplement(String str) {
		String temp = hexToBinaryString(str);
		if ('0' == (temp.charAt(0))) {
			return Integer.parseInt(temp.substring(1, temp.length()), 2);
		} else {
			int value = Integer.parseInt(temp, 2) - 1;
			char[] chars = Integer.toBinaryString(value).toCharArray();
			for (int i = 1; i < chars.length; i++) {
				if ('1' == chars[i]) {
					chars[i] = '0';
				} else {
					chars[i] = '1';
				}
			}
			return -Integer.parseInt(
					new String(chars).substring(1, temp.length()), 2);
		}
	}
	
	/**
	 * 已知16进制原码
	 * 
	 * @param str
	 * @return
	 */
	public static int knowTrueForm(String str) {
		String temp = hexToBinaryString(str);
		if ('0' == (temp.charAt(0))) {
			return Integer.parseInt(temp.substring(1, temp.length()), 2);
		} else {
			return -Integer.parseInt(temp.substring(1, temp.length()), 2);
		}
	}
	
	public static AirconStatus getAir(String val) {
		AirconStatus air = new AirconStatus();
		String[] value = val.split(",");
		air.setRunning("true".equals(value[0].split("=")[1]) ? true : false);
		air.setModel(value[1].split("=")[1]);
		air.setTemperature(Integer.parseInt(value[2].split("=")[1]));
		air.setSpeed(value[3].split("=")[1]);
		air.setLight(value[4].split("=")[1]);
		air.setUpDownWind("true".equals(value[5].split("=")[1]) ? true : false);
		air.setLeftRightWind("true".equals(value[6].split("=")[1]) ? true
				: false);
		air.setTimeToUp("true".equals(value[7].split("=")[1]) ? true : false);
		air.setAirTimeUp(value[8].split("=")[1]);
		air.setTimeToDown("true".equals(value[9].split("=")[1]) ? true : false);
		air.setAirTimeDown(value[10].split("=")[1]);
		air.setSleep("true".equals(value[11].split("=")[1]) ? true : false);
		air.setHot("true".equals(value[12].split("=")[1]) ? true : false);
		air.setStrong("true".equals(value[13].split("=")[1]) ? true : false);
		air.setQuiet("true".equals(value[14].split("=")[1]) ? true : false);
		air.setWho(value[15].split("=")[1]);
		return air;
	}

	public static void main(String[] args) {

		System.out.println(getRunningHour("01"));
		System.out.println(getRunningMiniute("01"));

	}

}
