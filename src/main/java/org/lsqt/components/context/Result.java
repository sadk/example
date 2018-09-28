package org.lsqt.components.context;

/**
 * 用于前端统一数据格式
 * @author mingmin.yuan
 *
 * @param <T>
 */
public class Result<T> {
	/**用于标记Controller层调用底层service是否成功**/
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
	
	/**
	 * 真实的业务数据
	 * @return
	 */
	public T getData() {
		return data;
	}
	
	/**
	 * 真实的业务数据
	 * @param data
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * 用于标记Controller层调用底层service是否成功
	 * @return
	 */
	public boolean getIsSuccess() {
		return isSuccess;
	}

	/**
	 * 用于标记Controller层调用底层service是否成功
	 * @param isSuccess
	 */
	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	/**
	 * 冗余字段，用于添加扩展对象返回
	 * @return
	 */
	public Object getHook() {
		return hook;
	}

	/**
	 * 冗余字段，用于添加扩展对象返回
	 * @param hook
	 */
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

