package com.poss.saoss.log.sp;

import poss.client.common.SPBase;
import poss.xml.*;
import java.util.*;


public final class HadoopHdfsTaskSP extends SPBase
{
/*
<<<<< com.poss.saoss.data.log.THadoopHdfsTask class Info Start >>>>>
Summary:
---------------------------------
DataBaseName = BOSS_DATA
TableName  = HADOOP_HDFS_TASK
TableType  = TABLE
TableRemark= 任务状态表

Detail:
--------------------------------------------------------------------------------------------------------
| COLUMN_NAME         | TYPE_NAME | DATA_TYPE | JAVA_CLASS | COLUMN_SIZE | DECIMAL_DIGITS | NO_NULLABLE |
--------------------------------------------------------------------------------------------------------
| ID                  | VARCHAR   | 12        | String     | 32          | 0              | true        |
| TASK_TYPE           | VARCHAR   | 12        | String     | 10          | 0              | false       |
| TASK_STATUS         | VARCHAR   | 12        | String     | 10          | 0              | false       |
--------------------------------------------------------------------------------------------------------
<<<<< com.poss.saoss.data.log.THadoopHdfsTask class Info End >>>>>
*/


private static final String business = "hadoophdfstaskserv";
public static final XMLObject packAddList(String pIP,String pRouteNote, String pOP,String pOPPIN,String pCustomer, String pCustomerPIN,ArrayList<XMLElement> pInfoList)
{
    return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN,business, "add", pInfoList);
}

public static final XMLObject packAddSingle(String pIP,String pRouteNote, String pOP,String pOPPIN,String pCustomer, String pCustomerPIN,
        /* String pID,  */ String pTASK_TYPE, String pTASK_STATUS)
{
    XMLElement information = getInformation();
//     information.addAttribute("id", format(pID));//任务ID
    information.addAttribute("task_type", format(pTASK_TYPE));//任务类型
    information.addAttribute("task_status", format(pTASK_STATUS));//任务状态
    return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN,business, "add", information);
    }

public static final XMLObject packDelList(String pIP,String pRouteNote, String pOP,String pOPPIN,String pCustomer, String pCustomerPIN,
        ArrayList<XMLElement> pInfoList)
{
    return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN,business, "del", pInfoList);
}

public static final XMLObject packDelSingle(String pIP,String pRouteNote, String pOP,String pOPPIN,String pCustomer, String pCustomerPIN,
        String pID /* , String pTASK_TYPE */  /* , String pTASK_STATUS */ )
{
    XMLElement information = getInformation();
    information.addAttribute("id", format(pID));//任务ID
//    information.addAttribute("task_type", format(pTASK_TYPE));//任务类型
//    information.addAttribute("task_status", format(pTASK_STATUS));//任务状态
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
            String pID, String pTASK_TYPE, String pTASK_STATUS, String pQueryType,String pPage,String pSize)
{
    XMLElement information = getInformationAddPage(pQueryType,pPage,pSize);
    information.addAttribute("id", format(pID));//任务ID
    information.addAttribute("task_type", format(pTASK_TYPE));//任务类型
    information.addAttribute("task_status", format(pTASK_STATUS));//任务状态
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
        String pID,  /* String pTASK_TYPE,  */  /* String pTASK_STATUS,  */ 
         /* String pNewID,  */ String pNewTASK_TYPE, String pNewTASK_STATUS)
{
    XMLElement information = getInformation();
    information.addAttribute("id", format(pID));//任务ID
 //     information.addAttribute("task_type", format(pTASK_TYPE));//任务类型
 //     information.addAttribute("task_status", format(pTASK_STATUS));//任务状态

         //     information.addAttribute("new_id", format(pNewID));//任务ID
    information.addAttribute("new_task_type", format(pNewTASK_TYPE));//任务类型
    information.addAttribute("new_task_status", format(pNewTASK_STATUS));//任务状态
    return getXMLObject(pIP, pRouteNote, pOP, pOPPIN, pCustomer, pCustomerPIN,business, "update", information);
}

}
