package org.lsqt.sms.model;

/**
 * 用于前端统一数据格式
 * @author mingmin.yuan
 *
 * @param <T>
 */
public class Result<T> {
	private boolean isSuccess = false;
	private String code;
	private String desc;
	private String status;
	
	private T data;
	
	private Object hook ;
	
	public static <T> Result<T> ok(T data, String... msg) {
		Result<T> r = new Result<>();
		r.setIsSuccess(true);
		r.setData(data);
		if (msg != null && msg.length > 0) {
			r.setDesc(msg[0]);
		} else {
			r.setDesc("请求成功");
		}
		return r;
	}
	
	public static <T> Result<T> ok(String msg) {
		return ok(null,msg);
	}
	
	public static <T> Result<T> fail(T data, String... msg) {
		Result<T> r = new Result<>();
		r.setIsSuccess(false);
		r.setData(data);
		if (msg != null && msg.length > 0) {
			r.setDesc(msg[0]);
		} else {
			r.setDesc("请求失败");
		}
		return r;
	}
	
	public static <T> Result<T> fail(String msg) {
		return fail(null, msg);
	}
	
	public static <T> Result<T> fail() {
		return fail(null, "");
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Object getHook() {
		return hook;
	}

	public void setHook(Object hook) {
		this.hook = hook;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}

