package com.poss.saoss.appliances;

import poss.client.common.SPBase;
import poss.xml.XMLElement;
import poss.xml.XMLObject;

/**
 * 和产品相关的组装请求XM格式文件的类
 * 
 * @Description:
 * @Company: POSS软件平台 (www.poss.cn)
 * @Copyright: Copyright (c) 2003-2014
 * @version: POSS_2.0
 * @date: 2014年5月31日
 * @author NeilCao (poss2.0@gmail.com)
 */
public class SAAppliancebProductSP extends SPBase
{
	private static final String business = "saappliancebproductserv";

	/**
	 * 添加方法
	 */
	public static final XMLObject packModifyOnline(String pIP, String pRouteNote, String pOP, String pOPPIN, String pCustomer, String pCustomerPIN,
			String pMODULE_NO, String pIS_ONLINE)
	{
		XMLElement information = getInformation();

		information.addAttribute("module_no", format(pMODULE_NO.toUpperCase()));

		information.addAttribute("is_online", format(pIS_ONLINE));

		return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN, business, "modifyonline", information);
		
		
	}
	
	
	/**
	 * 添加方法
	 */
	public static final XMLObject packUpdateVersion(String pIP, String pRouteNote, String pOP, String pOPPIN, String pCustomer, String pCustomerPIN,
			String pMODULE_NO, String pVersion)
	{
		XMLElement information = getInformation();

		information.addAttribute("module_no", format(pMODULE_NO.toUpperCase()));

		information.addAttribute("version", format(pVersion));

		return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN, business, "modifyversion", information);
		
		
	}
	
	/**
	 * 添加方法
	 */
	public static final XMLObject packUpdateModuel(String pIP, String pRouteNote, String pOP, String pOPPIN, String pCustomer, String pCustomerPIN,
			String pMODULE_NO, String pVersion,int pIS_ONLINE)
	{
		XMLElement information = getInformation();

		information.addAttribute("module_no", format(pMODULE_NO.toUpperCase()));

		information.addAttribute("version", format(pVersion));
		information.addAttribute("is_online", format(String.valueOf(pIS_ONLINE)));
		return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN, business, "updatemoduel", information);
		
		
	}
 //--新节点服务器---------------------------------------------------
	/**
	 * 新节点服务器调用上线接口
	 */
	public static final XMLObject packModifyDeviceOnline(String pIP, String pRouteNote, String pOP, String pOPPIN, String pCustomer, String pCustomerPIN,
			String pMODULE_NO,String pVersion,String pHost,String pPort, String pIS_ONLINE)
	{
		XMLElement information = getInformation();

		information.addAttribute("module_no", format(pMODULE_NO.toUpperCase()));
		information.addAttribute("version", format(pVersion));  
		information.addAttribute("ip_address", format(pHost));
		information.addAttribute("ip_port", format(pPort));
		information.addAttribute("is_online", format(pIS_ONLINE));
		return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN, business, "modifyDeviceOnline", information);
		
		
	}
	/**
	 * 新节点服务器调用下线接口
	 */
	public static final XMLObject packModifyDeviceOffline(String pIP, String pRouteNote, String pOP, String pOPPIN, String pCustomer, String pCustomerPIN,
			String pMODULE_NO, String pIS_ONLINE)
	{
		XMLElement information = getInformation();

		information.addAttribute("module_no", format(pMODULE_NO.toUpperCase()));
		information.addAttribute("is_online", format(pIS_ONLINE));
		return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN, business, "modifyDeviceOffline", information);
		
		
	}	
	public static final XMLObject packQuery(String pIP, String pRouteNote, String pOP, String pOPPIN, String pCustomer,
			String pCustomerPIN, String pPRO_ID, String pPRO_NAME, String pPRO_MODEL, String pPRO_STATUS, String pNOTE,
			String pREMARK, String pVERSION, String pMODULE_NO, String pPRO_CODE, String pSTART_DATE,
			String pGUARANTEE_DATE, String pCOMMAND_RESOLVER, String pPROVIDER_ID,String pIS_ONLINE, String pQueryType, String pPage,
			String pSize)
	{
		XMLElement information = getInformationAddPage(pQueryType, pPage, pSize);
		information.addAttribute("pro_id", format(pPRO_ID));
		information.addAttribute("pro_name", format(pPRO_NAME));
		information.addAttribute("pro_model", format(pPRO_MODEL));
		information.addAttribute("pro_status", format(pPRO_STATUS));
		information.addAttribute("note", format(pNOTE));
		information.addAttribute("remark", format(pREMARK));
		information.addAttribute("version", format(pVERSION));
		information.addAttribute("module_no", format(pMODULE_NO));
		information.addAttribute("pro_code", format(pPRO_CODE));
		information.addAttribute("start_date", format(pSTART_DATE));
		information.addAttribute("guarantee_date", format(pGUARANTEE_DATE));
		information.addAttribute("command_resolver", format(pCOMMAND_RESOLVER));
		information.addAttribute("provider_id", format(pPROVIDER_ID));
		information.addAttribute("is_online", format(pIS_ONLINE));
		return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN, business, "query", information);
	}

	
}
