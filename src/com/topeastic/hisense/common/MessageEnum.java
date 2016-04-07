package com.topeastic.hisense.common;

public enum MessageEnum {
	REQ_IS_NULL("请求参数为空"), REQ_IS_VALIDE("请求参数非法");
	private final String value; 
    public String getValue(){ 
        return value; 
    } 
    MessageEnum(String value){ 
        this.value=value; 
    } 
    
    
    public static void main(String[] args) { 
    
        for (MessageEnum item : MessageEnum.values()) { 
            System.out.println(item+" "+item.REQ_IS_NULL.getValue()); 
        } 
    }

}
