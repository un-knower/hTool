/*******************************************************************************
 * Copyright (c) 2003-2015,POSS软件平台(www.poss.cn)
 * File name:PortalImpl.java   Package name:com.poss.saoss.hadoop.impl
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
package com.poss.saoss.hadoop.impl;

import java.util.Date;
import java.util.HashMap;

import poss.client.common.Debug;
import poss.client.common.POSSClientException;
import poss.client.fun.BaseFun;
import poss.util.DATE;
import poss.xml.XMLObject;
import poss.xml.adapter.ElementData;

import com.poss.saoss.appliances.SAAppliancebProductSP;
import com.poss.saoss.appliances.SATestSelfSP;
import com.poss.saoss.hadoop.IPortal;
import com.poss.saoss.log.sp.AirconCtrlInfoSP;
import com.poss.saoss.log.sp.AirconStatusInfoSP;
import com.poss.saoss.log.sp.HadoopAirInfoSP;
import com.poss.saoss.log.sp.HadoopHdfsTaskSP;

/**
 * @Description:
 * @Company: POSS软件平台 (www.poss.cn)
 * @Copyright: Copyright (c) 2003-2015
 * @version: POSS_2.0
 * @date: 2015年5月5日
 * @author qiuziba (poss2.0@gmail.com)
 */
public class PortalImpl implements IPortal {
	private HashMap<String, String> faultModeMap = new HashMap<String, String>();
	private HashMap<String, String> SA_MODULE_PRO_ID = new HashMap<String, String>(); // 模块编号和23位条码关联
	private HashMap<String, Long> currentTimeMap = new HashMap<String, Long>();
	Debug debug = Debug.getInstance();

	/**
	 * 添加空调运行时间和电量数据 方法：addAirInfo 
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
	 * 
	 * @throws POSSClientException
	 **/
	public void addAirInfo(String pAIR_TYPE, String pCREATE_DATE,
			String pRUNTIME, String pENERGY) throws Throwable {

		XMLObject req = HadoopAirInfoSP.packAddSingle("", "", "hadoop", "", "",
				"", pAIR_TYPE, pCREATE_DATE, pRUNTIME, pENERGY);

		ElementData resultData;
		resultData = BaseFun.getResultDataElement(req);
		resultData = BaseFun.codingTransSpecialChar(resultData);

	}

	/**
	 * 添加空调App用户行为数据的方法：addAirBehaviorByAppInfo()
	 * 
	 * @param pAIR_TYPE 空调型号
	 * @param pCREATE_DATE 数据创建的时间
	 * @param pAIR_UP 空调开机次数
	 * @param pAIR_DOWN 空调关机次数
	 * @param pAIR_WARM 空调制热次数
	 * @param pAIR_COLD 空调制冷次数
	 * @param pAIR_BLOW 空调送风次数
	 * @param pAIR_WET 空调除湿次数
	 * @param pAIR_AUTO_MODEL 空调自动次数
	 * @param pAIR_TEMPERATURE 空调调节温度次数
	 * @param pAIR_LOW_SPEED 空调低风次数
	 * @param pAIR_MID_SPEED 空调中风次数
	 * @param pAIR_HIGH_SPEED 空调高风次数
	 * @param pAIR_AUTO_SPEED 空调自动风次数
	 * @param pAIR_QUIET 空调静音次数
	 * @param pAIR_LIGHT 空调灯光调节次数
	 * @param pAIR_UP_DOWN 空调上下风次数
	 * @param pAIR_LEFT_RIGHT 空调左右风次数
	 * @param pAIR_TIME_UP 空调定时开次数
	 * @param pAIR_TIME_DOWN 空调定时关次数
	 * @param pAIR_SLEEP 空调睡眠次数
	 * @param pAIR_HOT 空调电热次数
	 * @param pAIR_STRONG 空调强力次数
	 */
	public void addAirBehaviorByAppInfo(String pAIR_TYPE, String pCREATE_DATE,
			String pAIR_UP, String pAIR_DOWN, String pAIR_WARM,
			String pAIR_COLD, String pAIR_BLOW, String pAIR_WET,
			String pAIR_AUTO_MODEL, String pAIR_TEMPERATURE,
			String pAIR_LOW_SPEED, String pAIR_MID_SPEED,
			String pAIR_HIGH_SPEED, String pAIR_AUTO_SPEED, String pAIR_QUIET,
			String pAIR_LIGHT, String pAIR_UP_DOWN, String pAIR_LEFT_RIGHT,
			String pAIR_TIME_UP, String pAIR_TIME_DOWN, String pAIR_SLEEP,
			String pAIR_HOT, String pAIR_STRONG) {

	}

	/**
	 * 添加空调控制数据 方法：addAirconCtrlInfo pCTRL_TIME//控制时间 pSA_MODEL//家电型号
	 * pMODULE_NO//模块编号 pSA_ADDRESS//设备地址 pINSIDE_TEMP//室内温度 pOUTSIDE_TEMP//室外温度
	 * pINSIDE_DUHUM//室内湿度 pOUTSIDE_DUHUM//室外湿度 pELECTRIC_IAB//工作电流
	 * pIS_SEND//是否已推送 pLEAK_TYPE//泄露类型
	 * 
	 * @throws POSSClientException
	 **/
	public void addAirconCtrlInfo(String pSA_MODEL, String pMODULE_NO,
			String pSA_ADDRESS, String pINSIDE_TEMP, String pOUTSIDE_TEMP,
			String pSET_TEMP, String pEXHAUST_TEMP, String pELECTRIC_IAB,
			String pIS_SEND, String pLEAK_TYPE, String pWORK_MODE,
			String pWORK_CONDITION) throws Throwable {
		XMLObject req = AirconCtrlInfoSP.packAddSingle("", "", "hadoop", "",
				"", "", "", pSA_MODEL, pMODULE_NO, pSA_ADDRESS, pINSIDE_TEMP,
				pOUTSIDE_TEMP, pSET_TEMP, pEXHAUST_TEMP, pELECTRIC_IAB,
				pIS_SEND, pLEAK_TYPE, pWORK_MODE, pWORK_CONDITION);
		ElementData resultData;
		resultData = BaseFun.getResultDataElement(req);
		resultData = BaseFun.codingTransSpecialChar(resultData);

	}

	/**
	 * 添加空调状态数据 方法：addAirconStatusInfo pCTRL_TIME//控制时间 pSA_MODEL//家电型号
	 * pMODULE_NO//模块编号 pSA_ADDRESS//设备地址 pINSIDE_TEMP//室内温度 pOUTSIDE_TEMP//室外温度
	 * pINSIDE_DUHUM//室内湿度 pOUTSIDE_DUHUM//室外湿度 pELECTRIC_IAB//工作电流
	 **/
	public void addAirconStatusInfo(String pSA_MODEL, String pMODULE_NO,
			String pSA_ADDRESS, String pINSIDE_TEMP, String pOUTSIDE_TEMP,
			String pINSIDE_DUHUM, String pOUTSIDE_DUHUM, String pELECTRIC_IAB,
			String pWORK_MODE, String pEXHAUST_TEMP) throws Throwable {

		XMLObject req = AirconStatusInfoSP.packAddSingle("", "hadoop", "", "",
				"", "", pSA_MODEL, pMODULE_NO, pSA_ADDRESS, pINSIDE_TEMP,
				pOUTSIDE_TEMP, pINSIDE_DUHUM, pOUTSIDE_DUHUM, pELECTRIC_IAB,
				pWORK_MODE, pEXHAUST_TEMP);
		ElementData resultData;
		resultData = BaseFun.getResultDataElement(req);
		resultData = BaseFun.codingTransSpecialChar(resultData);
	}

	/**
	 * 根据任务Id查询 任务状态表
	 * 
	 * @throws Throwable
	 */
	public HashMap<String, String> queryHadoopHdfsTask(String id)
			throws Throwable {
		if (id == null || id.equals("")) {
			throw new NullPointerException(" ID is null!");
		}
		XMLObject req = HadoopHdfsTaskSP.packQuery("", "hadoop", "", "", "",
				"", id, "", "", "Single", "1", "1");

		ElementData resultData;
		resultData = BaseFun.getResultDataElement(req);
		resultData = BaseFun.codingTransSpecialChar(resultData);

		HashMap<String, String> hs = new HashMap<String, String>();
		ElementData result = resultData.getElementData("row");
		hs.put("id", result.getAttributeValue("id"));
		hs.put("taskType", result.getAttributeValue("task_type"));
		hs.put("taskStatus", result.getAttributeValue("task_status"));
		return hs;
	}

	/**
	 * 更新 任务状态
	 * 
	 * @throws Throwable
	 */
	public boolean updateUserInfo(String id, String taskType, String taskStatus)
			throws Throwable {
		if (id == null || id.equals("")) {
			throw new NullPointerException(" ID is null!");
		}
		XMLObject packResult = HadoopHdfsTaskSP.packUpdate("", "hadoop", "",
				"", "", "", id, taskType, taskStatus);
		ElementData resultData;
		resultData = BaseFun.getResultDataElement(packResult);
		resultData = BaseFun.codingTransSpecialChar(resultData);
		return true;

	}

	/*
	 * 会呼吸的家除湿机和清新机故障
	 * 
	 * @params type 家电类型 aircon
	 * 
	 * @params module_id 模块编号
	 * 
	 * @errorStation 故障位置 37
	 * 
	 * @errorDetail 故障内容 1:有故障 0：故障解除
	 */
	public void appearFault(String type, String module_id, String errorStation,
			String errorDetail) {
		
		try {
			System.out.println("=============开始查询23位条形码==========");
			String proId = getProID(module_id);
			System.out.println("===============proId："+proId+"=================");
			if (null == proId || proId.equals("")) {
				proId = module_id;
			}
			System.out.println("===============proId："+proId+"=================");
			// 当盒子上报正常状态时：
			if ((errorDetail.equals("0") || errorDetail.trim().length() == 0)) {
				// 它的上一状态是故障的，现在转正常了，需要推送一次消息
				if (null != faultModeMap.get(module_id + "_CODE_"
						+ errorStation)
						&& "ERROR".equals(faultModeMap.get(module_id + "_CODE_"
								+ errorStation))) {
					XMLObject req = SATestSelfSP.packAddSingle("127.0.0.1",
							"SmartHome", "", "", "Hadoop", "Hadoop", "0", type,
							getProID(module_id), module_id, module_id,
							"SUCCESS", errorStation, errorDetail, "",
							"DISABLED", "", "");
					ElementData rList;
					rList = BaseFun.getResultDataElement(req);
					rList = BaseFun.codingTransSpecialChar(rList);
				}
				// 如果以前有错误记录，那么清除掉
				faultModeMap.remove(module_id + "_CODE_" + errorStation);
				currentTimeMap.put(module_id, 0l);
				return; // 无错误
			}
			System.out.println("初始currentTimeMap："+currentTimeMap.get(module_id));
			if (null == currentTimeMap.get(module_id)
					|| currentTimeMap.get(module_id) == 0l) {
				System.out.println("currentTimeMap添加故障信息  型号和时间");
				currentTimeMap.put(module_id, currentTime());
				System.out.println("添加currentTimeMap："+currentTimeMap.get(module_id));
			} else if (currentTimeMap.get(module_id) > 0l
					&& twoTimeDiff(currentTimeMap.get(module_id)) < 60) {
				System.out.println("===========重复上报错误信息！！！==========");
				//近期是指 一小时以内
				return; // 近期已经报警过了，无须上报
			}
			System.out.println("========开始执行sql。。。。。========");
			XMLObject req = SATestSelfSP.packAddSingle("127.0.0.1",
					"SmartHome", "", "", "Hadoop", "Hadoop", "0", type,
					getProID(module_id), module_id, module_id, "FAILURE",
					errorStation, errorDetail, "", "ENABLED", "", "");
			ElementData rList;
			rList = BaseFun.getResultDataElement(req);
			rList = BaseFun.codingTransSpecialChar(rList);
			System.out.println("rList的size为："+rList.size());
			System.out.println("currentTimeMap添加故障信息  类型和值");
			faultModeMap.put(module_id + "_CODE_" + errorStation, "ERROR");
		} catch (Throwable e) {
			debug.outThrowable("Portal Exception", e);
		}
	}

	/**
	 * 获取家电的23位条形码
	 * 
	 * @param module_id
	 * @return
	 * @throws Throwable
	 */
	private String getProID(String module_id) {
		System.out.println("获取家电的23位条形码");
		String result = SA_MODULE_PRO_ID.get(module_id);
		System.out.println("结果为："+result);
		try {
			if (null == result || result.trim().length() == 0) {
				// 通过查找编号来获取数据库信息
				XMLObject request = SAAppliancebProductSP.packQuery(
						"127.0.0.1", "SmartHome", "", "", "", "", "", "", "",
						"", "", "", "", module_id, "", "", "", "", "", "",
						"Single", "1", "1");
				ElementData resultData;

				resultData = BaseFun.getResultDataElement(request);
				resultData = BaseFun.codingTransSpecialChar(resultData);

				if (resultData.size() > 0) {
					ElementData sa = resultData.getElementData("row");
					result = sa.getAttributeValue("pro_code");

					if (result.trim().length() == 0) {
						result = "";
					}

					if (result.length() > 0) {
						SA_MODULE_PRO_ID.put(module_id, result);
					}
				} else {
					result = "";
				}
			}
		} catch (Throwable e) {
			result = "";
		}
		return result;
	}

	// 获取当前时间
	public static long currentTime() {
		Date currentDate = new Date();
		return currentDate.getTime();
	}

	// 获取当前时间以上一记录时间差值
	public static long twoTimeDiff(long beforeTime) {
		Date currentDate = new Date();
		long diffTime = (currentDate.getTime() - beforeTime) / (1000 * 60);
		return diffTime;
	}
}
