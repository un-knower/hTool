/*******************************************************************************
 * Copyright (c) 2003-2015,POSS软件平台(www.poss.cn)
 * File name:IPortal.java   Package name:com.poss.saoss.hadoop
 * Project:HadoopPortalAPI Version:POSS_2.0
 *
 * Description:
 *    TODO
 * Others:
 *
 * History:
 *
 * 1.Date: 2015年5月5日
 *   Author: qiuziba(poss2.0@gmail.com)
 *   Modification: Initial Creation.
 *    
 ******************************************************************************/
package com.poss.saoss.hadoop;

import java.util.HashMap;



/**
 * @Description: 
 * @Company: POSS软件平台 (www.poss.cn)
 * @Copyright: Copyright (c) 2003-2015
 * @version: POSS_2.0
 * @date: 2015年5月5日 
 * @author qiuziba (poss2.0@gmail.com)
 */
public interface IPortal
{
   /**   
    * 添加空调控制数据
    * 方法：addAirconCtrlInfo
    * pCTRL_TIME//控制时间
	* pSA_MODEL//家电型号
	* pMODULE_NO//模块编号
	* pSA_ADDRESS//设备地址
	* pINSIDE_TEMP//室内温度
	* pOUTSIDE_TEMP//室外温度
	* pINSIDE_DUHUM//室内湿度
	* pOUTSIDE_DUHUM//室外湿度
	* pELECTRIC_IAB//工作电流
	* pIS_SEND//是否已推送
	* pLEAK_TYPE//泄露类型
 * @throws Throwable 
    **/
   public void addAirconCtrlInfo(String pSA_MODEL, String pMODULE_NO, String pSA_ADDRESS, 
		   String pINSIDE_TEMP, String pOUTSIDE_TEMP, String pINSIDE_DUHUM, 
		   String pOUTSIDE_DUHUM, String pELECTRIC_IAB, String pIS_SEND, 
		   String pLEAK_TYPE,String pWORK_MODE,String pWORK_CONDITION) throws Throwable;
   /**   
    * 添加空调状态数据
    * 方法：addAirconStatusInfo
    * pCTRL_TIME//控制时间
	* pSA_MODEL//家电型号
	* pMODULE_NO//模块编号
	* pSA_ADDRESS//设备地址
	* pINSIDE_TEMP//室内温度
	* pOUTSIDE_TEMP//室外温度
	* pINSIDE_DUHUM//室内湿度
	* pOUTSIDE_DUHUM//室外湿度
	* pELECTRIC_IAB//工作电流
    **/
   public void addAirconStatusInfo( String pSA_MODEL, String pMODULE_NO, String pSA_ADDRESS,
		   String pINSIDE_TEMP, String pOUTSIDE_TEMP, String pINSIDE_DUHUM,
		   String pOUTSIDE_DUHUM, String pELECTRIC_IAB,String pWORK_MODE,String pEXHAUST_TEMP) throws Throwable;

   /**
    * 根据Id 查询任务列表数据
    * @param id
    * @return
    * @throws Throwable
    */
   public HashMap<String, String> queryHadoopHdfsTask(String id) throws Throwable;
   
   public  boolean updateUserInfo(String id,String taskType,String taskStatus)throws Throwable;
   
	/* 会呼吸的家除湿机和清新机故障
	 * 
	 * @params type 家电类型
	 * @params module_id 模块编号	
	 * @errorStation 故障位置	
	 * @errorDetail 故障内容 
	 */
	public void appearFault(String type, String module_id, String errorStation, String errorDetail) throws Throwable;
	
}
