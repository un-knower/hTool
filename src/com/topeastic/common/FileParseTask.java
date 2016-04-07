package com.topeastic.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;

public class FileParseTask extends TimerTask {
	private static final Logger logger = Logger.getLogger(FileParseTask.class); 
	private ServletContext context = null;

//	private static final String sourcePath = PropertiesUtils.getConfigProperty("log_source_path");
//	private static final String inputPath = PropertiesUtils.getConfigProperty("log_input_path");



	public FileParseTask(ServletContext context) {
		this.context = context;
	}

	/**
	 * 获取某个文件夹下面的所有文件 并放到List里面
	 * 
	 * @param path
	 *            为文件夹的路径
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getTextFile(String path) {
		File parentFile = new File(path);
		File[] childrenFile = parentFile.listFiles();
		ArrayList txtFile = new ArrayList();
		if (childrenFile != null && childrenFile.length > 0) {
			for (int i = 0; i < childrenFile.length; i++) {
				if (childrenFile[i].getName().endsWith(".txt"))
					txtFile.add(childrenFile[i]);
			}
		}
		return txtFile;
	}

	/**
	 * 获取某个文件夹下面的所有文件 并放到List里面
	 * 进行格式重新读取后，写入新的文件
	 * @param list，path
	 *  path 为文件夹的路径
	 */
	@SuppressWarnings("rawtypes")
	public static void writeFile(List list, String path) throws Exception {
		if (list != null && list.size() > 0) {
			File mkFile = new File(path);
			mkFile.mkdirs();// 如果存在文件夹，就不会再创建，如果不存在就创建文件夹
			for (int i = 0; i < list.size(); i++) {
				File file = (File) list.get(i);

				BufferedReader in = new BufferedReader(new InputStreamReader(
						new FileInputStream(file), "gbk"));
				OutputStreamWriter out = new OutputStreamWriter(
						new FileOutputStream(path + "/" + file.getName()));
				String s = "";
				while (s != null&&(s = in.readLine()) != "") {
					String[] str = s.split(",");
					
					String cmd="+KTZD:testid,weak,aged,cool,1,1,26,128,128,31,32,128,0,0,0,0,off,0,0,0,0,0,0,0,0,auto,sweep,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
					
					logger.debug("解析指令"+cmd);
					// 得到 第一个逗号之前的字符串下标
					String result = cmd.substring(0, cmd.indexOf(","));
					int index = result.length();
					// 截取第一个逗号后面的字符数组
					String backStr = cmd.substring(index + 1, cmd.length());
					String[] backStrArray = backStr.split(",");
					logger.debug("第一个逗号后面的字符"+backStr);
					String newsstr = "";

					StringBuffer news = new StringBuffer();
					int num = 0;
					for (int j = 0; j < str.length - 1; j++) {
						num = j;
						news = news.append(str[j]);
					}
					if (num < str.length - 1) {
						  newsstr = news.toString() + "," + backStrArray[0]
						  + "," + backStrArray[1]+","+backStrArray[2]
						  +","+backStrArray[3]+","+backStrArray[4]+","+backStrArray[5]
						  +","+backStrArray[6]  +",体感室内温度"+backStrArray[7]  +",室内实际温度"+backStrArray[8];
						  System.out.println("写入一行数据"+newsstr);
						  logger.debug("写入一行数据"+newsstr);
					}
					out.write(newsstr + "\r\n");
				}
				in.close();
				out.flush();
				out.close();
				// file.delete(); //读取完后删除此文件
			}
		}
	}

	@Override
	public void run() {
		
	}
}
