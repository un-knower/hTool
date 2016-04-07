package com.xinlianfeng.android.livehome.hisense;


public class ProtocolNative implements IProtocolNative{
	private static final String TAG = "ProtocolNative";
	private static final String libpath = Thread.currentThread().getContextClassLoader().getResource("").getFile()+"libsmart.so";

	static {
		//System.loadLibrary("libhisense.so");
		System.load(libpath);
	}
	public static native String crcData(byte[] value,int value_length,String crc);
    public static native String buildPara(String strat,int moblie_type,int moblie_address,int device_type,int device_address);
    public static native String parsePara(String buf,int device_type);
    public String  Hisense_build(String applianceCmd,int ntype,int address){
    	return buildPara(applianceCmd,0xfe,0x1,ntype,address);
    }
    public String  Hisense_Parse(String applianceCmd,int device_type){
    	return parsePara(applianceCmd,device_type);
    }
}
    