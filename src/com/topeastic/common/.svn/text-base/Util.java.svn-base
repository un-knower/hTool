package com.topeastic.common;

import java.io.ByteArrayOutputStream;

public class Util {

	/**
	 * 将指定byte数组以16进制的形式打印到控制台
	 * 
	 * @param hint
	 *            String
	 * @param b
	 *            byte[]
	 * @return void
	 */
	public static void printHexString(String hint, byte[] b) {
		System.out.print(hint);
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			System.out.print(hex.toUpperCase() + " ");
		}
		System.out.println("");
	}

	/**
	 * 
	 * @param b
	 *            byte[]
	 * @return String
	 */
	public static String Bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}

	/**
	 * 将两个ASCII字符合成一个字节； 如："EF"--> 0xEF
	 * 
	 * @param src0
	 *            byte
	 * @param src1
	 *            byte
	 * @return byte
	 */
	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	/**
	 * 将指定字符串src，以每两个字符分割转换为16进制形式 如："2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF,
	 * 0xD9}
	 * 
	 * @param src
	 *            String
	 * @return byte[]
	 */
	public static byte[] HexString2Bytes(String src) {
		byte[] tmp = src.getBytes();
		byte[] ret = new byte[tmp.length / 2 + 1];
		for (int i = 0; i < src.length(); i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	// 转化字符串为十六进制编码
	public static String toHexString(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str;
	}

	// 转化十六进制编码为字符串
	public static String toStringHex1(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(
						s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "utf-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

	// 转化十六进制编码为字符串
	public static String toStringHex(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(
						s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "utf-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

	/*
	 * 16进制数字字符集
	 */
	private static String hexString = "0123456789ABCDEF";
	/*
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 */
	public static String encode(String str) {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/*
	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）
	 */
	public static String decode(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}

	/**
	 * 
	 * @param args
	 */
	public static boolean isEmpty(String s){
		return ("".equals(s)||null==s)?true:false;
	}
	
	public static String hexToBinaryString(String hexString){
		String binaryString = null;
		if((!Util.isEmpty(hexString))
			&&(hexString.length()==2)){
			//第一位16进制小于8的，需要在转成二进制时在前面补0
			int i1 =  Integer.parseInt(hexString.substring(0,1),16);
			String m = Integer.toBinaryString( Integer.parseInt(hexString.substring(0,1),16));
			if(i1<8){
				String s="";
				for(int i =0;i<4-m.length();i++){
					s="0"+s;
				}
				m=s+m;
			}
			//第二位18进制小于8的，需要在转成二进制时在前面补0
			int i2 =  Integer.parseInt(hexString.substring(1,2),16);
			String n = Integer.toBinaryString( Integer.parseInt(hexString.substring(1,2),16));
			if(i2<8){
				//第二位16进制小于8的，需要在转成二进制时在前面补0
				String t="";
				for(int i =0;i<4-n.length();i++){
					t="0"+t;
				}
				n=t+n;
			}
			
//			binaryString =Integer.toBinaryString( Integer.parseInt(hexString.substring(0,1),16))+
//					Integer.toBinaryString( Integer.parseInt(hexString.substring(1,2),16));
//			binaryString =Integer.toBinaryString( Integer.parseInt(hexString.substring(0,1),16))+s2;
			binaryString = m+n;
		}
		System.out.println(binaryString);
		return binaryString;
	}
	public static void main(String[] args) {
//		byte[] b = HexString2Bytes("2B44EFD9");
//		System.out.println(b);
		//String s = toHexString("AB");
		Integer i = new Integer("0x10");
		int p = i;
		String s = Integer.toBinaryString(p);
		System.out.println(p);
		System.out.println(s);
		
	}

}
