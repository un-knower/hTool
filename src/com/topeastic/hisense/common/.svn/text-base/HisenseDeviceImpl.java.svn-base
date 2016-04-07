package com.topeastic.hisense.common;


import com.topeastic.common.BusinessException;
import com.topeastic.hadoop.utils.StringUtils;
import com.xinlianfeng.android.livehome.hisense.ProtocolNative;

public class HisenseDeviceImpl implements  HisenseDevice{

	@Override
	public HisenseDeviceResponse getAllStatus(String respCommand)
			throws BusinessException {
		HisenseDeviceResponse  response = new  HisenseDeviceResponse();
		response.setResult(false);
//		//校验响应参数的数据格式和合法性
//		if(StringUtils.isEmpty(respCommand)){
//			response.setErrorMsg(MessageEnum.REQ_IS_NULL.getValue());
//			return response;
//		}
//		if(respCommand.length()!=81){
//			response.setErrorMsg(MessageEnum.REQ_IS_VALIDE.getValue());
////			return response;
//		}
		ProtocolNative protocolNative = new ProtocolNative();
		String cmd = protocolNative.Hisense_Parse(respCommand, 1);
		
		//空调响应结果
		
		return null;
	}

}
