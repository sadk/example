package org.lsqt.uum.controller;

public class Result{
	private boolean isSuccess = false;
	private int status; // 一般定义0为正常态
	private String message;
	private Object data ;
	
	private Result(){
	}

	public static Result ok(){
		Result rs = new Result();
		rs.setMessage("");
		rs.setStatus(0);
		rs.setIsSuccess(true);
		return rs;
	}
	
	public static Result ok(String msg){
		Result rs = new Result();
		rs.setMessage(msg);
		rs.setStatus(0);
		rs.setIsSuccess(true);
		return rs;
	}
	
	public static Result ok(Object data){
		Result rs = new Result();
		if(data instanceof String) {
			rs.setMessage((String)data);
		}
		rs.setStatus(0);
		rs.setIsSuccess(true);
		rs.setData(data);
		return rs;
	}
	
	public static Result ok(String msg,Object data){
		Result rs = new Result();
		rs.setMessage(msg);
		rs.setStatus(0);
		rs.setIsSuccess(true);
		rs.setData(data);
		return rs;
	}
	
	public static Result fail(){
		Result rs = new Result();
		rs.setMessage("");
		rs.setStatus(1);
		rs.setIsSuccess(false);
		return rs;
	}
	
	public static Result fail(String msg){
		Result rs = new Result();
		rs.setMessage(msg);
		rs.setStatus(1);
		rs.setIsSuccess(false);
		return rs;
	}
	
	public static Result fail(Exception ex){
		Result rs = new Result();
		rs.setMessage(ex.getMessage());
		rs.setStatus(1);
		rs.setIsSuccess(false);
		return rs;
	}
	
	public static Result fail(Exception ex,Object data){
		Result rs = new Result();
		rs.setMessage(ex.getMessage());
		rs.setStatus(1);
		rs.setIsSuccess(false);
		rs.setData(data);
		return rs;
	}
	
	public static Result fail(String exceptionMsg,Object data){
		Result rs = new Result();
		rs.setMessage(exceptionMsg);
		rs.setStatus(1);
		rs.setIsSuccess(false);
		rs.setData(data);
		return rs;
	}
	
	public boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
