package com.topeastic.hadoop.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import org.apache.log4j.Logger;
import com.poss.saoss.hadoop.impl.PortalImpl;
import com.topeastic.dao.AirconStatusDaoImpl;
import com.topeastic.dao.factory.DAOFactory;

public class DbUtils {

	// public void insertAvg
	private static Logger logger = Logger.getLogger(DbUtils.class);
	public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static boolean insertAirconStatus(File file) throws Exception {
		if (file.exists()) {
			AirconStatusDaoImpl airconStatusInfoDao = (AirconStatusDaoImpl) DAOFactory
					.getAirconStatusInstance();
		}
		return true;

	}

	/**
	 * 解析结果文件，推送到portal端的数据库
	 * 
	 * @param filePath
	 */
	public static void readTxtFile(String filePath) throws Throwable {

		String encoding = "GBK";
		File file = new File(filePath);
		PortalImpl pi = new PortalImpl();
		// System.out.println("filename      ="+file.getName());
		logger.debug("filename      =" + file.getName());
		if (file.isFile() && file.exists()) {
			// 判断文件是否存在
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					file), encoding);// 考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			// HashMap<String, String> map = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				if ("".equals(lineTxt)) {
					continue;
				}
				String[] str = lineTxt.split("[;|,]");
				// map = new HashMap<String, String>();
				// map.put("modelNo", str[0].trim());
				// map.put("work_mode", str[1].trim());
				// map.put("work_condition", str[2].trim());
				// map.put("electric_iab", str[3].trim());
				// map.put("inside_temp", str[4].trim());
				// map.put("outside_temp", str[5].trim());
				// map.put("set_temp", str[6].trim());
				// map.put("exhaust_temp", str[7].trim());

				pi.addAirconCtrlInfo("", str[0].trim(), "", str[5].trim(),
						str[5].trim(), str[6].trim(), str[7].trim(),
						str[3].trim(), "0", "1", str[1].trim(), str[2].trim());
				// 发送故障信息
				logger.debug("begin to send  errorcode to portal    time:"
						+ DateUtils.longConvert2DateString(
								System.currentTimeMillis(), TIME_FORMAT));
				logger.debug("modelNo     " + str[0].trim());
				pi.appearFault("aircon", str[0].trim(), "85", "1");
				logger.debug("finish  send  errorcode to portal    time:"
						+ DateUtils.longConvert2DateString(
								System.currentTimeMillis(), TIME_FORMAT));

			}
			read.close();
		}

	}

	/**
	 * 解析突发性泄露结果文件，推送到portal端的数据库
	 * 
	 * @param filePath
	 */
	public static void readTxtFileForOutburst(String filePath) throws Throwable {
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			PortalImpl pi = new PortalImpl();
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				// HashMap<String, String> map = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					if ("".equals(lineTxt)) {
						continue;
					}
					String[] str = lineTxt.split("[;|,]");

					// map = new HashMap<String, String>();
					// map.put("modelNo", str[0].trim());
					// map.put("work_mode", str[1].trim());
					// map.put("electric_iab", str[2].trim());
					// map.put("inside_temp", str[3].trim());
					// map.put("outside_temp", str[4].trim());
					// map.put("set_temp", str[5].trim());
					// map.put("exhaust_temp", str[6].trim());

					pi.addAirconCtrlInfo("", str[0].trim(), "", str[3].trim(),
							str[4].trim(), str[5].trim(), str[6].trim(),
							str[2].trim(), "0", "0", str[1].trim(), "");
					// 发送故障信息
					System.out.println("================发送故障信息==============");
					pi.appearFault("aircon", str[0].trim(), "37", "1");
				}
				read.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
