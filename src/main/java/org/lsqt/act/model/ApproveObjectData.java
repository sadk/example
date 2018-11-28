package org.lsqt.act.model;

/**
 * 审批对象数据结果集(因要显示错误信息，做的一个wrapper)
 * @author mmyuan
 *
 */
public class ApproveObjectData {
	
	private Object originalData; // 原始数据
	private Object mappedData; // 映射后的数据
	
	private String message;

	private int status = -1;
	
	public static final int STATUS_OK=200;
	public static final int STATUS_FAIL = -1;

	public Object getOriginalData() {
		return originalData;
	}


	public void setOriginalData(Object originalData) {
		this.originalData = originalData;
	}


	public Object getMappedData() {
		return mappedData;
	}


	public void setMappedData(Object mappedData) {
		this.mappedData = mappedData;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}
	
}
