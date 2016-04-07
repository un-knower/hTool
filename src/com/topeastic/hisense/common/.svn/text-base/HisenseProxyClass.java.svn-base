package com.topeastic.hisense.common;


import com.topeastic.common.BusinessException;
import com.topeastic.hadoop.utils.StringUtils;

public class HisenseProxyClass  implements HisenseDevice{

	  private HisenseDevice realObj;    //目标子类对象
	    
	    public HisenseProxyClass(HisenseDevice realObj) {
	        this.realObj = realObj;
	    }

	@Override
	
	public synchronized HisenseDeviceResponse getAllStatus(String respCommand)
			throws BusinessException {
		doBefore(respCommand);
		return realObj.getAllStatus(respCommand);
	}
	
	/**
     * 目标方法调用前的相关处理
     * 校验响应参数的数据格式和合法性
     */
	//@Before
    private void doBefore(String respCommand) {
    	 System.out.println("doBefore");
    	if(StringUtils.isEmpty(respCommand)){
    		throw new BusinessException(MessageEnum.REQ_IS_NULL.getValue());
			
		}
		if(respCommand.length()!=81){
			throw new BusinessException(MessageEnum.REQ_IS_VALIDE.getValue());
		}
    }
    
    /**
     * 目标方法调用后的相关处理
     */
	//@After
    private void doAfter(String respCommand) {
    	  System.out.println("doAfter");
    }

    /**
     *  ProxyClass proxyObj = new ProxyClass(new RealClass());
        proxyObj.doOperation();    //通过代理对象调用doOperation方法

     */

	
}
