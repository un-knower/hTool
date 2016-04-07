package com.topeastic.hisense.webservice;

public class StudentImpl implements Student {

	@Override
	public void addStudent(String name) {
		System.out.println(" 欢迎  " + name + "  你加入Spring家庭! "); 

	}

}
