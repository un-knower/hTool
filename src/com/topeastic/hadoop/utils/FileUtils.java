package com.topeastic.hadoop.utils;

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

public class FileUtils {

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
	 * java 执行shell 命令
	 * 
	 * @param shellString
	 */
	public static boolean callShell(String shellString) {
		boolean isSuccess = false;
		try {
			Process process = Runtime.getRuntime().exec(shellString);
			int exitValue = process.waitFor();
			if (0 != exitValue) {
				System.out.println("call shell failed. error code is :"
						+ exitValue);
				return isSuccess;
			} else {
				isSuccess = true;
			}
		} catch (Throwable e) {
			System.out.println("call shell failed. " + e);
			return isSuccess;
		}
		return isSuccess;
	}

	/**
	 * 将制定目录下的文件名中包含part-r 改成日期字符串的文件名
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static String changeFileName(String path) {
		String dateString = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		List<File> fileList;
		try {
			fileList = FileUtils.getFileList(path);
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
		return "true,"+dateString;
	}

	/**
	 * 读取某个文件夹下的所有文件
	 */
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

	// 获取层级的方法
	public static String getLevel(int level) {
		// A mutable sequence of characters.
		StringBuilder sb = new StringBuilder();
		for (int l = 0; l < level; l++) {
			sb.append("|--");
		}
		return sb.toString();
	}

	public static void getAllFiles(File dir, int level) {
		// System.out.println(getLevel(level)+dir.getName());
		level++;
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				// 这里面用了递归的算法
				getAllFiles(files[i], level);
			} else {

				System.out.println("filename =" + files[i].getAbsolutePath());
				// list.add(files[i]);
			}
		}
	}

	/**
	 * 文件上传 从windows上
	 * 
	 * @param src
	 * @param dst
	 * @param conf
	 * @return
	 */
	public static boolean put2HDFS(String src, String dst, Configuration conf) {
		Path dstPath = new Path(dst);
		try {
			FileSystem hdfs = dstPath.getFileSystem(conf);
			// FileSystem hdfs = FileSystem.get( URI.create(dst), conf);
			hdfs.copyFromLocalFile(false, new Path(src), dstPath);
		} catch (IOException ie) {
			ie.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 文件上传 从linux硬盘上
	 * 
	 * @return
	 */
	public static boolean put2HDFSByShell() {
		String hdfs_path = PropertiesUtils.getConfigProperty("hdfs_path");

		return true;
	}

	/**
	 * 文件下载至
	 * 
	 * @param src
	 * @param dst
	 * @param conf
	 * @return
	 */
	public static boolean downFromHDFS(String src, String dst,
			Configuration conf) {
		Path srcPath = new Path(src);
		try {
			FileSystem hdfs = srcPath.getFileSystem(conf);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		// changeFileName("E:\\output");
		// List<File> fileList = getFileList("E:\\logs");
		// for(File f:fileList){
		// System.out.println("filename      =" +f.getAbsolutePath());
		// }
		File dir = new File("E:\\logs");
		getAllFiles(dir, 0);
		// List<File> fileList2 = getAllFiles(dir,0);//0表示最顶层
		// for(File f:fileList2){
		// System.out.println("filename2      =" +f.getAbsolutePath());
		// }

	}

}
