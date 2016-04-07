package com.topeastic.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String cmd = "sh ./hadoop.sh";
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(cmd);
			// 下面这条语句跟上面的一条是一样的效果
			// process=new ProcessBuilder(cmd).start();
			// process.waitFor();
			// 输出执行的结果-- 输出和错误的语句信息
//			OutInfo(process);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void OutInfo(Process process) {
		String str = "";
		BufferedReader result = null, error = null;
		try {

			result = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			error = new BufferedReader(new InputStreamReader(
					process.getErrorStream()));
			while ((str = result.readLine()) != null) {
				// 成功提示
				// 此处的成功提示不需要显示 测试显示
				System.out.println(str);
			}
			while ((str = error.readLine()) != null) {
				// 存在错误提示
				System.out.println(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			if (result != null) {
				try {
					result.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (error != null) {
				try {
					error.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// 释放process
			if (process != null)
				process.destroy();
		}
	}
}
