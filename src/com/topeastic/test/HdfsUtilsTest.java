package com.topeastic.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HdfsUtilsTest {

	/**F:\hadoop_files\hadoop-prepare\CDN服务器\Config\WebContent\BSSServer
	 * @param args
	 */
	public static void main(String[] args) {
		String src = "hdfs://192.168.7.34:9000/user/hadoop/output_time_total/";
		String dst = "/opt/hadoop_dir/result/time_total/";
		Configuration conf = new Configuration();
		boolean isget = getFromHDFS(src + "/part-r-00000", dst, conf);
		if (isget) {
			System.out.println("文件下载至本地成功");
			String[] flag = changeFileName(dst).split(",");
			if ("true".equals(flag[0])) {
				// 推送信息
				System.out.println("重命名OK");
				System.out.println("开始推送信息");
			}
		} else {
			System.out.println("文件下载失败！！");
		}
		System.out.println("asdas");
	}

	public static boolean getFromHDFS(String src, String dst, Configuration conf) {
		conf.addResource(new Path("/opt/modules/hadoop/hadoop-1.0.3/conf/core-site.xml"));
		conf.addResource(new Path("/opt/modules/hadoop/hadoop-1.0.3/conf/hdfs-site.xml"));
		Path dstPath = new Path(dst);
		try {
			// FileSystem dhfs = dstPath.getFileSystem(conf) ;
			FileSystem dhfs = dstPath.getFileSystem(conf);
			dhfs.copyToLocalFile(false, new Path(src), dstPath);
		} catch (IOException ie) {
			ie.printStackTrace();
			return false;
		}
		return true;
	}

	public static String changeFileName(String path) {
		String dateString = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());
		List<File> fileList;
		try {
			fileList = getFileList(path);
		} catch (FileNotFoundException e) {
			return "false";
		} catch (IOException e) {
			return "false";
		}
		for (File file : fileList) {
			if (file.getName().contains("part-r")
					&& !file.getName().contains(".crc")) {
				String fileName = file.getParent() + File.separator
						+ dateString + ".txt";
				File newFile = new File(fileName);
				if (file.renameTo(newFile)) {
					continue;
				} else {
					return "false";
				}

			}
		}
		return "true," + dateString;
	}

	public static List<File> getFileList(String filepath)
			throws FileNotFoundException, IOException {
		List<File> fileList = new ArrayList<File>();
		File file = new File(filepath);
		File[] tempList = file.listFiles();
		List<File> subFilelist = null;
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				fileList.add(tempList[i]);
			}
			if (tempList[i].isDirectory()) {
				subFilelist = getFileList(tempList[i].getAbsolutePath());
			}
		}
		if (null != subFilelist) {
			for (File f : subFilelist) {
				fileList.add(f);
			}
		}
		return fileList;
	}

}
