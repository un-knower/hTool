package com.poss.saoss.appliances;

import poss.client.common.SPBase;
import poss.xml.*;
import java.util.*;

/**
 * 
 * @Description:
 * @Company: POSS软件平台 (www.poss.cn)
 * @Copyright: Copyright (c) 2003-2014
 * @version: SAOSS1.0
 * @date: 2014年3月28日
 * @author Yilei (303238600.@qq.com)
 */
public final class VSAAppliancesProductSP extends SPBase
{
	/* <<<<< com.poss.saoss.data.device.VSAAppliancesProduct class Info Start
	 * >>>>> Summary: --------------------------------- DataBaseName = saoss
	 * TableName = SAOSS_V_APPLIANCES_PRODUCT TableType = VIEW TableRemark=
	 * 
	 * Detail:
	 * ------------------------------------------------------------------
	 * -------------------------------------- | COLUMN_NAME | TYPE_NAME |
	 * DATA_TYPE | JAVA_CLASS | COLUMN_SIZE | DECIMAL_DIGITS | NO_NULLABLE |
	 * ----
	 * ----------------------------------------------------------------------
	 * ------------------------------ | PRO_ID | VARCHAR | 12 | String | 32 | 0
	 * | true | | PRO_NAME | VARCHAR | 12 | String | 64 | 0 | true | | PRO_MODEL
	 * | VARCHAR | 12 | String | 32 | 0 | true | | PRO_STATUS | VARCHAR | 12 |
	 * String | 32 | 0 | true | | NOTE | VARCHAR | 12 | String | 64 | 0 | false
	 * | | REMARK | VARCHAR | 12 | String | 256 | 0 | false | | VERSION |
	 * VARCHAR | 12 | String | 32 | 0 | false | | MODULE_NO | VARCHAR | 12 |
	 * String | 32 | 0 | false | | PRO_CODE | VARCHAR | 12 | String | 64 | 0 |
	 * false | | START_DATE | VARCHAR | 12 | String | 19 | 0 | true | |
	 * GUARANTEE_DATE | INT | 4 | Long | 10 | 0 | false | | COMMAND_RESOLVER |
	 * VARCHAR | 12 | String | 64 | 0 | false | | PROVIDER_ID | VARCHAR | 12 |
	 * String | 32 | 0 | true | | MODEL_NAME | VARCHAR | 12 | String | 64 | 0 |
	 * false |
	 * ------------------------------------------------------------------
	 * -------------------------------------- <<<<<
	 * com.poss.saoss.data.device.VSAAppliancesProduct class Info End >>>>> */

	private static final String business = "vsaappliancesproductserv";

	public static final XMLObject packQuery(String pIP, String pRouteNote, String pOP, String pOPPIN, String pCustomer,
			String pCustomerPIN, String pPRO_ID, String pPRO_NAME, String pPRO_MODEL, String pPRO_STATUS, String pNOTE,
			String pREMARK, String pVERSION, String pMODULE_NO, String pPRO_CODE, String MIN_START_DATE,
			String MAX_START_DATE, String pGUARANTEE_DATE, String pCOMMAND_RESOLVER, String pPROVIDER_ID,
			String pMODEL_NAME, String pstatusName, String pIS_ONLINE, String pQueryType, String pPage, String pSize)
	{
		XMLElement information = getInformationAddPage(pQueryType, pPage, pSize);
		information.addAttribute("pro_id", format(pPRO_ID));
		information.addAttribute("pro_name", format(pPRO_NAME));
		information.addAttribute("pro_status", format(pPRO_STATUS));
		information.addAttribute("version", format(pVERSION));
		information.addAttribute("module_no", format(pMODULE_NO));
		information.addAttribute("pro_code", format(pPRO_CODE));
		information.addAttribute("min_start_date", format(MIN_START_DATE));
		information.addAttribute("max_start_date", format(MAX_START_DATE));
		information.addAttribute("guarantee_date", format(pGUARANTEE_DATE));
		information.addAttribute("command_resolver", format(pCOMMAND_RESOLVER));
		information.addAttribute("provider_id", format(pPROVIDER_ID));
		information.addAttribute("model_name", format(pMODEL_NAME));
		information.addAttribute("statusName", format(pstatusName));
		information.addAttribute("is_online", format(pIS_ONLINE));
		return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN, business, "query", information);
	}
}
