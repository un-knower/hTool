package com.xinlianfeng.android.livehome.hisense;

public interface IProtocolNative {
	 public String  Hisense_build(String applianceCmd,int ntype,int address);
	 public String  Hisense_Parse(String applianceCmd,int device_type);
}
