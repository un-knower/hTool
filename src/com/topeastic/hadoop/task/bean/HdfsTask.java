package com.topeastic.hadoop.task.bean;

public class HdfsTask {

	/** 任务id **/
	private String id;

	/** 任务类型 **/
	private String taskType;

	/** 任务状态 **/
	private String taskStatus;

	// 构造函数
	public HdfsTask(String id, String taskType, String taskStatus) {
		this.id = id;
		this.taskType = taskType;
		this.taskStatus = taskStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
}
