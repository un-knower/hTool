package com.poss.saoss.log.sp;

import poss.client.common.SPBase;
import poss.xml.*;
import java.util.*;


public final class HadoopAirInfoSP extends SPBase
{
/*
<<<<< poss.sdbc.dbt.TableClass class Info Start >>>>>
Summary:
---------------------------------
DataBaseName = BOSS_DATA
TableName  = HADOOP_AIR_INFO
TableType  = TABLE
TableRemark= 

Detail:
--------------------------------------------------------------------------------------------------------
| COLUMN_NAME         | TYPE_NAME | DATA_TYPE | JAVA_CLASS | COLUMN_SIZE | DECIMAL_DIGITS | NO_NULLABLE |
--------------------------------------------------------------------------------------------------------
| AIR_ID              | VARCHAR   | 12        | String     | 32          | 0              | true        |
| AIR_TYPE            | VARCHAR   | 12        | String     | 32          | 0              | false       |
| CREATE_DATE         | BIGINT    | -5        | Long       | 19          | 0              | false       |
| RUNTIME             | INT       | 4         | Long       | 10          | 0              | false       |
| ENERGY              | DOUBLE    | 8         | Double     | 22          | 0              | false       |
--------------------------------------------------------------------------------------------------------
<<<<< poss.sdbc.dbt.TableClass class Info End >>>>>
*/


private static final String business = "hadoopairinfoserv";
public static final XMLObject packAddList(String pIP,String pRouteNote, String pOP,String pOPPIN,String pCustomer, String pCustomerPIN,ArrayList<XMLElement> pInfoList)
{
    return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN,business, "add", pInfoList);
}

public static final XMLObject packAddSingle(String pIP,String pRouteNote, String pOP,String pOPPIN,String pCustomer, String pCustomerPIN,
        /* String pAIR_ID,  */ String pAIR_TYPE, String pCREATE_DATE, String pRUNTIME, String pENERGY)
{
    XMLElement information = getInformation();
//     information.addAttribute("air_id", format(pAIR_ID));//编号ID
    information.addAttribute("air_type", format(pAIR_TYPE));//空调类型
    information.addAttribute("create_date", format(pCREATE_DATE));//创建时间
    information.addAttribute("runtime", format(pRUNTIME));//运行时间
    information.addAttribute("energy", format(pENERGY));//消耗电量
    return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN,business, "add", information);
    }

public static final XMLObject packDelList(String pIP,String pRouteNote, String pOP,String pOPPIN,String pCustomer, String pCustomerPIN,
        ArrayList<XMLElement> pInfoList)
{
    return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN,business, "del", pInfoList);
}

public static final XMLObject packDelSingle(String pIP,String pRouteNote, String pOP,String pOPPIN,String pCustomer, String pCustomerPIN,
        String pAIR_ID /* , String pAIR_TYPE */  /* , String pCREATE_DATE */  /* , String pRUNTIME */  /* , String pENERGY */ )
{
    XMLElement information = getInformation();
    information.addAttribute("air_id", format(pAIR_ID));//编号ID
//    information.addAttribute("air_type", format(pAIR_TYPE));//空调类型
//    information.addAttribute("create_date", format(pCREATE_DATE));//创建时间
//    information.addAttribute("runtime", format(pRUNTIME));//运行时间
//    information.addAttribute("energy", format(pENERGY));//消耗电量
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
            String pAIR_ID, String pAIR_TYPE, String pMinCREATE_DATE, String pMaxCREATE_DATE, String pMinRUNTIME, String pMaxRUNTIME, String pENERGY, String pQueryType,String pPage,String pSize)
{
    XMLElement information = getInformationAddPage(pQueryType,pPage,pSize);
    information.addAttribute("air_id", format(pAIR_ID));//编号ID
    information.addAttribute("air_type", format(pAIR_TYPE));//空调类型
    information.addAttribute("min_create_date", format(pMinCREATE_DATE));//创建时间
    information.addAttribute("max_create_date", format(pMaxCREATE_DATE));//创建时间
    information.addAttribute("min_runtime", format(pMinRUNTIME));//运行时间
    information.addAttribute("max_runtime", format(pMaxRUNTIME));//运行时间
    information.addAttribute("energy", format(pENERGY));//消耗电量
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
        String pAIR_ID,  /* String pAIR_TYPE,  */  /* String pCREATE_DATE,  */  /* String pRUNTIME,  */  /* String pENERGY,  */ 
         /* String pNewAIR_ID,  */ String pNewAIR_TYPE, String pNewCREATE_DATE, String pNewRUNTIME, String pNewENERGY)
{
    XMLElement information = getInformation();
    information.addAttribute("air_id", format(pAIR_ID));//编号ID
 //     information.addAttribute("air_type", format(pAIR_TYPE));//空调类型
 //     information.addAttribute("create_date", format(pCREATE_DATE));//创建时间
 //     information.addAttribute("runtime", format(pRUNTIME));//运行时间
 //     information.addAttribute("energy", format(pENERGY));//消耗电量

         //     information.addAttribute("new_air_id", format(pNewAIR_ID));//编号ID
    information.addAttribute("new_air_type", format(pNewAIR_TYPE));//空调类型
    information.addAttribute("new_create_date", format(pNewCREATE_DATE));//创建时间
    information.addAttribute("new_runtime", format(pNewRUNTIME));//运行时间
    information.addAttribute("new_energy", format(pNewENERGY));//消耗电量
    return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN,business, "update", information);
}

}
