package com.poss.saoss.log.sp;

import poss.client.common.SPBase;
import poss.xml.*;
import java.util.*;


public final class AirconCtrlInfoSP extends SPBase
{
/*
<<<<< com.poss.saoss.data.log.TAirconCtrlInfo class Info Start >>>>>
Summary:
---------------------------------
DataBaseName = BOSS_DATA
TableName  = HADOOP_AIRCON_CTRL_INFO
TableType  = TABLE
TableRemark= 设备控制数据表

Detail:
--------------------------------------------------------------------------------------------------------
| COLUMN_NAME         | TYPE_NAME | DATA_TYPE | JAVA_CLASS | COLUMN_SIZE | DECIMAL_DIGITS | NO_NULLABLE |
--------------------------------------------------------------------------------------------------------
| AUTO_ID             | VARCHAR   | 12        | String     | 32          | 0              | true        |
| CTRL_TIME           | BIGINT    | -5        | Long       | 19          | 0              | true        |
| SA_MODEL            | VARCHAR   | 12        | String     | 64          | 0              | true        |
| MODULE_NO           | VARCHAR   | 12        | String     | 32          | 0              | true        |
| SA_ADDRESS          | VARCHAR   | 12        | String     | 256         | 0              | true        |
| INSIDE_TEMP         | DOUBLE    | 8         | Double     | 22          | 0              | true        |
| OUTSIDE_TEMP        | DOUBLE    | 8         | Double     | 22          | 0              | true        |
| INSIDE_DUHUM        | DOUBLE    | 8         | Double     | 22          | 0              | true        |
| OUTSIDE_DUHUM       | DOUBLE    | 8         | Double     | 22          | 0              | true        |
| ELECTRIC_IAB        | DOUBLE    | 8         | Double     | 22          | 0              | true        |
| IS_SEND             | INT       | 4         | Long       | 10          | 0              | true        |
| LEAK_TYPE           | INT       | 4         | Long       | 10          | 0              | true        |
--------------------------------------------------------------------------------------------------------
<<<<< com.poss.saoss.data.log.TAirconCtrlInfo class Info End >>>>>
*/


private static final String business = "airconctrlinfoserv";
public static final XMLObject packAddList(String pIP,String pRouteNote, String pOP,String pOPPIN,String pCustomer, String pCustomerPIN,ArrayList<XMLElement> pInfoList)
{
    return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN,business, "add", pInfoList);
}

public static final XMLObject packAddSingle(String pIP,String pRouteNote, String pOP,String pOPPIN,String pCustomer, String pCustomerPIN,
        /* String pAUTO_ID,  */ String pCTRL_TIME, String pSA_MODEL, String pMODULE_NO, String pSA_ADDRESS, String pINSIDE_TEMP,
        String pOUTSIDE_TEMP, String pSET_TEMP, String pEXHAUST_TEMP, String pELECTRIC_IAB,
        String pIS_SEND, String pLEAK_TYPE,String pWORK_MODE,String pWORK_CONDITION)
{
    XMLElement information = getInformation();
//     information.addAttribute("auto_id", format(pAUTO_ID));//编号
   // information.addAttribute("ctrl_time", format(pCTRL_TIME));//控制时间
    information.addAttribute("sa_model", format(pSA_MODEL));//家电型号
    information.addAttribute("module_no", format(pMODULE_NO));//模块编号
    information.addAttribute("sa_address", format(pSA_ADDRESS));//设备地址
    information.addAttribute("inside_temp", format(pINSIDE_TEMP));//室内温度
    information.addAttribute("outside_temp", format(pOUTSIDE_TEMP));//室外温度
    
    information.addAttribute("set_temp", format(pSET_TEMP));//设定温差
    information.addAttribute("exhaust_temp", format(pEXHAUST_TEMP));//排气温度
    
    information.addAttribute("electric_iab", format(pELECTRIC_IAB));//工作电流
    information.addAttribute("is_send", format(pIS_SEND));//是否已推送
    information.addAttribute("leak_type", format(pLEAK_TYPE));//泄露类型
    information.addAttribute("work_mode", format(pWORK_MODE));//工作模式
    information.addAttribute("work_condition", format(pWORK_CONDITION));//工况
    return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN,business, "add", information);
    }

public static final XMLObject packDelList(String pIP,String pRouteNote, String pOP,String pOPPIN,String pCustomer, String pCustomerPIN,
        ArrayList<XMLElement> pInfoList)
{
    return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN,business, "del", pInfoList);
}

public static final XMLObject packDelSingle(String pIP,String pRouteNote, String pOP,String pOPPIN,String pCustomer, String pCustomerPIN,
        String pAUTO_ID /* , String pCTRL_TIME */  /* , String pSA_MODEL */  /* , String pMODULE_NO */  /* , String pSA_ADDRESS */  /* , String pINSIDE_TEMP */  /* , String pOUTSIDE_TEMP */  /* , String pINSIDE_DUHUM */  /* , String pOUTSIDE_DUHUM */  /* , String pELECTRIC_IAB */  /* , String pIS_SEND */  /* , String pLEAK_TYPE */ )
{
    XMLElement information = getInformation();
    information.addAttribute("auto_id", format(pAUTO_ID));//编号
//    information.addAttribute("ctrl_time", format(pCTRL_TIME));//控制时间
//    information.addAttribute("sa_model", format(pSA_MODEL));//家电型号
//    information.addAttribute("module_no", format(pMODULE_NO));//模块编号
//    information.addAttribute("sa_address", format(pSA_ADDRESS));//设备地址
//    information.addAttribute("inside_temp", format(pINSIDE_TEMP));//室内温度
//    information.addAttribute("outside_temp", format(pOUTSIDE_TEMP));//室外温度
//    information.addAttribute("inside_duhum", format(pINSIDE_DUHUM));//室内湿度
//    information.addAttribute("outside_duhum", format(pOUTSIDE_DUHUM));//室外湿度
//    information.addAttribute("electric_iab", format(pELECTRIC_IAB));//工作电流
//    information.addAttribute("is_send", format(pIS_SEND));//是否已推送
//    information.addAttribute("leak_type", format(pLEAK_TYPE));//泄露类型
    return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN,business, "del", information);
}

/**
 * 查询接口（大于等于min_xxxx；小于等于max_xxxx；#不等于）
 * @param pIP 访问地址
 * @param pRouteNote 访问的系统
 * @param pOP 操作员编号
 * @param pOPPIN 操作员PIN（可自定义）
 * @param pCustomer 用户编号
 * @param pCustomerPIN 用户PIN（可自定义）
 * @return
 */
public static final XMLObject packQuery(String pIP,String pRouteNote, String pOP,String pOPPIN,String pCustomer, String pCustomerPIN,
            String pAUTO_ID, String pMinCTRL_TIME, String pMaxCTRL_TIME, String pSA_MODEL, String pMODULE_NO, String pSA_ADDRESS,
            String pINSIDE_TEMP, String pOUTSIDE_TEMP, String pSET_TEMP, String pEXHAUST_TEMP, 
            String pELECTRIC_IAB, String pIS_SEND, String pLEAK_TYPE, String pWORK_MODE,String pWORK_CONDITION,
            String pQueryType,String pPage,String pSize)
{
    XMLElement information = getInformationAddPage(pQueryType,pPage,pSize);
    information.addAttribute("auto_id", format(pAUTO_ID));//编号
    information.addAttribute("min_ctrl_time", format(pMinCTRL_TIME));//控制时间
    information.addAttribute("max_ctrl_time", format(pMaxCTRL_TIME));//控制时间
    information.addAttribute("sa_model", format(pSA_MODEL));//家电型号
    information.addAttribute("module_no", format(pMODULE_NO));//模块编号
    information.addAttribute("sa_address", format(pSA_ADDRESS));//设备地址
    information.addAttribute("inside_temp", format(pINSIDE_TEMP));//室内温度
    information.addAttribute("outside_temp", format(pOUTSIDE_TEMP));//室外温度
    information.addAttribute("set_temp", format(pSET_TEMP));//设定温差
    information.addAttribute("exhaust_temp", format(pEXHAUST_TEMP));//排气温度
    information.addAttribute("electric_iab", format(pELECTRIC_IAB));//工作电流
    information.addAttribute("is_send", format(pIS_SEND));//是否已推送
    information.addAttribute("leak_type", format(pLEAK_TYPE));//泄露类型
    information.addAttribute("work_mode", format(pWORK_MODE));//工作模式
    information.addAttribute("work_condition", format(pWORK_CONDITION));//工况
    return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN,business, "query", information);
}

/**
 * 修改接口（大于等于min_xxxx；小于等于max_xxxx；#不等于；$不修改；%模糊查询通配符）
 * @param pIP 访问地址
 * @param pRouteNote 访问的系统
 * @param pOP 操作员编号
 * @param pOPPIN 操作员PIN（可自定义）
 * @param pCustomer 用户编号
 * @param pCustomerPIN 用户PIN（可自定义）
 * @return
 */
public static final XMLObject packUpdate(String pIP,String pRouteNote, String pOP,String pOPPIN,String pCustomer, String pCustomerPIN,
        String pAUTO_ID,  /* String pCTRL_TIME,  */  /* String pSA_MODEL,  */  /* String pMODULE_NO,  */  /* String pSA_ADDRESS,  */  /* String pINSIDE_TEMP,  */  /* String pOUTSIDE_TEMP,  */  /* String pINSIDE_DUHUM,  */  /* String pOUTSIDE_DUHUM,  */  /* String pELECTRIC_IAB,  */  /* String pIS_SEND,  */  /* String pLEAK_TYPE,  */ 
         /* String pNewAUTO_ID,  */ String pNewCTRL_TIME, String pNewSA_MODEL, 
         String pNewMODULE_NO, String pNewSA_ADDRESS, String pNewINSIDE_TEMP,
         String pNewOUTSIDE_TEMP, String pNewSET_TEMP, String pNewEXHAUST_TEMP,
         String pNewELECTRIC_IAB, String pNewIS_SEND, String pNewLEAK_TYPE,String pNewWORK_MODE,String pNewWORK_CONDITION)
{
    XMLElement information = getInformation();
    information.addAttribute("auto_id", format(pAUTO_ID));//编号
 //     information.addAttribute("ctrl_time", format(pCTRL_TIME));//控制时间
 //     information.addAttribute("sa_model", format(pSA_MODEL));//家电型号
 //     information.addAttribute("module_no", format(pMODULE_NO));//模块编号
 //     information.addAttribute("sa_address", format(pSA_ADDRESS));//设备地址
 //     information.addAttribute("inside_temp", format(pINSIDE_TEMP));//室内温度
 //     information.addAttribute("outside_temp", format(pOUTSIDE_TEMP));//室外温度
 //     information.addAttribute("inside_duhum", format(pINSIDE_DUHUM));//室内湿度
 //     information.addAttribute("outside_duhum", format(pOUTSIDE_DUHUM));//室外湿度
 //     information.addAttribute("electric_iab", format(pELECTRIC_IAB));//工作电流
 //     information.addAttribute("is_send", format(pIS_SEND));//是否已推送
 //     information.addAttribute("leak_type", format(pLEAK_TYPE));//泄露类型

         //     information.addAttribute("new_auto_id", format(pNewAUTO_ID));//编号
    information.addAttribute("new_ctrl_time", format(pNewCTRL_TIME));//控制时间
    information.addAttribute("new_sa_model", format(pNewSA_MODEL));//家电型号
    information.addAttribute("new_module_no", format(pNewMODULE_NO));//模块编号
    information.addAttribute("new_sa_address", format(pNewSA_ADDRESS));//设备地址
    information.addAttribute("new_inside_temp", format(pNewINSIDE_TEMP));//室内温度
    information.addAttribute("new_outside_temp", format(pNewOUTSIDE_TEMP));//室外温度
    
    information.addAttribute("new_set_temp", format(pNewSET_TEMP));//设定温差
    information.addAttribute("new_exhaust_temp", format(pNewEXHAUST_TEMP));//排气温度
    
    information.addAttribute("new_electric_iab", format(pNewELECTRIC_IAB));//工作电流
    information.addAttribute("new_is_send", format(pNewIS_SEND));//是否已推送
    information.addAttribute("new_leak_type", format(pNewLEAK_TYPE));//泄露类型
    information.addAttribute("new_work_mode", format(pNewWORK_MODE));//工作模式
    information.addAttribute("new_work_condition", format(pNewWORK_CONDITION));//工况
    return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN,business, "update", information);
}

}
