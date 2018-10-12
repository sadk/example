package org.lsqt.components.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 上下文核心工具类，需绑定当前请求线程使用
 * @author yuanmm
 *
 */
@SuppressWarnings("unchecked")
public class ContextUtil {
	//表单数据容器
	private static ThreadLocal<Map<String,Object>> THREADLOCAL_FORM_MAP=new ThreadLocal<Map<String,Object>>();
	
	//当前请求线程上下文数据容器
	private static ThreadLocal<Map<String,Object>> THREADLOCAL_CONTEXT_MAP=new ThreadLocal<Map<String,Object>>();
	private static final String KEY_END_FIX="~!@#$%^&*()-_+=[{]}\\|;:'\",<.>/?";
	public static final String CONTEXT_REQUEST_OBJECT  = "context_request_object".concat(KEY_END_FIX);
	public static final String CONTEXT_RESPONSE_OBJECT = "context_response_object".concat(KEY_END_FIX);
	
	// 登陆用户上下文
	public static final String CONTEXT_LOGIN_USER_OBJECT = "context_login_user_object".concat(KEY_END_FIX);
	public static final String CONTEXT_LOGIN_ACCOUNT_OBJECT = "context_login_account_object".concat(KEY_END_FIX);
	public static final String CONTEXT_LOGIN_NAME_OBJECT = "context_login_name_object".concat(KEY_END_FIX);
	public static final String CONTEXT_LOGIN_ID_OBJECT = "context_login_id_object".concat(KEY_END_FIX);
	
	// spring容器上下文
	public static final String CONTEXT_SPRING_HOLDER = "context_spring_container_holder".concat(KEY_END_FIX);
	
	
	

	/**
	 * 获取用户ID
	 * @return 返回null
	 */
	public static  <T> T getLoginId() {
			
		if (THREADLOCAL_CONTEXT_MAP.get() == null) {
			return null;
		}

		T loginId = (T) THREADLOCAL_CONTEXT_MAP.get().get(CONTEXT_LOGIN_ID_OBJECT);
		if (loginId != null) {
			return loginId;
		}
		return null;
	}
	
	/**
	 * 获取用户的登陆姓名
	 * @return 返回null
	 */
	public static String getLoginName() {
		
		if (THREADLOCAL_CONTEXT_MAP.get() == null) {
			return null;
		}

		Object loginName = THREADLOCAL_CONTEXT_MAP.get().get(CONTEXT_LOGIN_NAME_OBJECT);
		if (loginName != null) {
			return loginName.toString();
		}
		return null;
	}
	
	/**
	 * 获取用户的登陆账号
	 * @return 返回null
	 */
	public static String getLoginAccount() {
		
		if (THREADLOCAL_CONTEXT_MAP.get() == null) {
			return null;
		}

		Object loginAccount = THREADLOCAL_CONTEXT_MAP.get().get(CONTEXT_LOGIN_ACCOUNT_OBJECT);
		if (loginAccount != null) {
			return loginAccount.toString();
		}
		return null;
	}
	
	
	/**
	 * 获取用户对象
	 * @return 返回null
	 */
	public static  <T> T getLoginUser() {
			
		if (THREADLOCAL_CONTEXT_MAP.get() == null) {
			return null;
		}

		T loginUser = (T) THREADLOCAL_CONTEXT_MAP.get().get(CONTEXT_LOGIN_USER_OBJECT);
		if (loginUser != null) {
			return loginUser;
		}
		return null;
	}
	
	/**
	 * 获取表单数据
	 * @return 返回empty 
	 */
	public static Map<String, Object> getFormMap() {
		if (THREADLOCAL_FORM_MAP.get() == null) { 
			Map<String, Object> formMap = new ConcurrentHashMap<String, Object>();
			THREADLOCAL_FORM_MAP.set(formMap);
		}
		return THREADLOCAL_FORM_MAP.get();
	}

	
	/**
	 * 获取上下文数据
	 * @return 返回empty 
	 */
	public static Map<String,Object> getContextMap() {
		if (THREADLOCAL_CONTEXT_MAP.get() == null) {
			Map<String, Object> contextMap = new ConcurrentHashMap<String, Object>();
			THREADLOCAL_CONTEXT_MAP.set(contextMap);
		}
		return THREADLOCAL_CONTEXT_MAP.get();
	}
	
	public static <T> T getRequest() {
		if(THREADLOCAL_CONTEXT_MAP.get()!=null) {
			return (T)THREADLOCAL_CONTEXT_MAP.get().get(CONTEXT_REQUEST_OBJECT);
		}
		return null;
	}
	
	public static <T> T getResponse() {
		if(THREADLOCAL_CONTEXT_MAP.get()!=null) {
			return (T)THREADLOCAL_CONTEXT_MAP.get().get(CONTEXT_RESPONSE_OBJECT);
		}
		return null;
	}


	/**
	 * 获取Spring容器
	 * 
	 * @return 返回null
	 */
	public static <T> T getSpringContext() {
		if (THREADLOCAL_CONTEXT_MAP.get() != null) {
			return (T) THREADLOCAL_CONTEXT_MAP.get().get(CONTEXT_SPRING_HOLDER);
		}
		return null;
	}
	
	
	public static void clear() {
		Map<String, Object> formMap = THREADLOCAL_FORM_MAP.get();
		if (formMap != null) {
			formMap.clear();
		}
		THREADLOCAL_FORM_MAP.set(null);

		Map<String, Object> contextMap = THREADLOCAL_CONTEXT_MAP.get();
		if (contextMap != null) {
			contextMap.clear();
		}
		THREADLOCAL_CONTEXT_MAP.set(null);
	}
	
}
