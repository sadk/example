package org.lsqt.components.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.lsqt.components.db.execute.util.CacheReflectUtil;
import org.lsqt.components.mvc.spi.exception.ApplicationException;
import org.lsqt.components.mvc.util.ViewResolveFtlUtil;
import org.lsqt.components.util.bean.BeanUtil;
import org.lsqt.components.util.file.IOUtil;
import org.lsqt.components.util.file.PathUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.components.util.reflect.ClassLoaderUtil;

import net.sf.jxls.transformer.XLSTransformer;

/**
 * 上下文核心工具类，需绑定当前请求进程使用
 * @author yuanmm
 *
 */
@Deprecated
public class ContextUtil2 {
	//表单数据容器
	private static ThreadLocal<Map<String,Object>> THREADLOCAL_FORM_MAP=new ThreadLocal<Map<String,Object>>();
	
	//当前请求线程上下文数据容器
	private static ThreadLocal<Map<String,Object>> THREADLOCAL_CONTEXT_MAP=new ThreadLocal<Map<String,Object>>();
	private static final String KEY_END_FIX="~!@#$%^&*()-_+=[{]}\\|;:'\",<.>/?";
	public static final String CONTEXT_REQUEST_OBJECT  = "context_request_object".concat(KEY_END_FIX);
	public static final String CONTEXT_RESPONSE_OBJECT = "context_response_object".concat(KEY_END_FIX);
	
	public static final String CONTEXT_LOGIN_NAME_OBJECT = "context_login_name_object".concat(KEY_END_FIX);
	public static final String CONTEXT_LOGIN_ID_OBJECT = "context_login_id_object".concat(KEY_END_FIX);
	
	/**
	 * 获取用户的登陆名
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
	 * 获取用户ID
	 * @return 返回null
	 */
	public static  <T> T getLoginId() {
			
		if (THREADLOCAL_CONTEXT_MAP.get() == null) {
			return null;
		}

		@SuppressWarnings("unchecked")
		T loginId = (T) THREADLOCAL_CONTEXT_MAP.get().get(CONTEXT_LOGIN_ID_OBJECT);
		if (loginId != null) {
			return loginId;
		}
		return null;
	}
	
	//当前请求上下文cookie数据容器
	
	//当前请求上下文文件专用容器
	public interface File {
		/**
		 * 模板文件放在classes目录下
		 * @param fileName 文件名，如："/user/通讯录.xls"
		 * @param key 
		 * @param model
		 */
		void put(String fileName, String key, Object model);
		void put(String fileName, String templatePath,String key, Object model);
		void put(File file);
		
		InputStream get(String name);
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
	
	@SuppressWarnings("unchecked")
	public static <T> T getRequest() {
		if(THREADLOCAL_CONTEXT_MAP.get()!=null) {
			return (T)THREADLOCAL_CONTEXT_MAP.get().get(CONTEXT_REQUEST_OBJECT);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getResponse() {
		if(THREADLOCAL_CONTEXT_MAP.get()!=null) {
			return (T)THREADLOCAL_CONTEXT_MAP.get().get(CONTEXT_RESPONSE_OBJECT);
		}
		return null;
	}
	
	public static void clear() {
		THREADLOCAL_FORM_MAP.get().clear();
		THREADLOCAL_FORM_MAP.set(null);
		
		THREADLOCAL_CONTEXT_MAP.get().clear();
		THREADLOCAL_CONTEXT_MAP.set(null);
	}
	
	/**
	 * 为了不引入servlet包，利用反射获取请求的值
	 * @author yuanmm
	 *
	 */
	private static class RequestUtil {
		
		private static final String request_getParamterNames = "parameterNames";
		//private static final String request_ getParameter = "parameter" ;
		
		public static final Long getLong(String key){
			return null;
		}
		
		public static final Long getLong(String key,Long defaultValue){
			return defaultValue;
		}
		
		public static final Long[] getLongArray(String key,Long ... defaultValue){
			
			return defaultValue;
		}
		
		public static final Long[] getLongArray(String key,String split,Long ...defaultValue) {
			return defaultValue;
		}
		
		
		public static final Date getDate(String key){
			
			return null;
		}
		
		/**
		 * 获取请求页面的所有请求相关的信息
		 * @return
		
		@SuppressWarnings("rawtypes")
		public final static StringBuilder getRequestInfo() {
			Object request = ContextUtil.getRequest() ;
			request.getClass().getMethod(request_getParamterNames).in;
			
			StringBuilder sb = new StringBuilder();
			sb.append("--------------Debuging request.getParameterNames()---------\n");
			java.util.Enumeration enumeration = request.getParameterNames();
			while (enumeration.hasMoreElements()) {
				String paramName = (String) enumeration.nextElement();
				sb.append(StringUtil.formatMsg("Request Parameter - %1 = %2.\n",
						paramName, request.getParameter(paramName)));
			}

			sb.append("-----------Debuging request.getAttributeNames()---------\n");
			enumeration = request.getAttributeNames();
			while (enumeration.hasMoreElements()) {
				String attrName = (String) enumeration.nextElement();
				if (!attrName.endsWith("exception")) {
					sb.append(StringUtil.formatMsg(
							"Request Attribute - %1 = %2.\n", attrName,
							request.getAttribute(attrName)));
				}
			}

			sb.append("-----------Debuging request.getHeaderNames()---------------\n");
			enumeration = request.getHeaderNames();
			while (enumeration.hasMoreElements()) {
				String headerName = (String) enumeration.nextElement();
				sb.append(StringUtil.formatMsg("Request Header - %1 = %2.\n",
						headerName, request.getHeader(headerName)));
			}

			return sb;
		}
		 */
	}
	
	public static void main(String args[]){
		System.out.println(System.getProperties());
	}
	
	
	public static class FileContext implements File {

		@Override
		public void put(String fileName, String key, Object model) {
			
			if(fileName.lastIndexOf(".")==-1) {
				throw new ApplicationException("请指定文件后缀名");
			}
			
			// Excel导出
			if(fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
				final Object response = ContextUtil2.getResponse();
				
				try {
					if(ClassLoaderUtil.classForName("javax.servlet.http.HttpServletResponse").isAssignableFrom(response.getClass())) {
						//设置文件下载头部
						Method method = response.getClass().getMethod("setHeader", String.class,String.class);
						method.invoke(response, "Content-Type","application/octet-stream");
						String temp = java.net.URLEncoder.encode(fileName.substring(fileName.lastIndexOf("/")+1,fileName.length()),"UTF-8") ;
						method.invoke(response, "Content-Disposition","attachment;filename=\"" + temp + "\"");
					 
						//res.setHeader("Content-Type", "application/octet-stream");
						//res.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
					}
				} catch (Exception ex) {
					throw new ApplicationException("下载文件，设置响应头出错!",ex);
				}
				
				java.io.File dir = new java.io.File(ContextUtil2.class.getResource("/").getPath());
				String tplPath  = dir.getAbsolutePath()+fileName;
				
				InputStream in = null;
				OutputStream out = null;
				try {
					in = new FileInputStream(tplPath);
					if (in.available() == 0) {
						in.close();
						throw new FileNotFoundException("Excel模板文件不存在：" + tplPath);
					}
				
				
					XLSTransformer transformer = new XLSTransformer();
					Map<String,Object> dataMap = new HashMap<>();
					dataMap.put(key, model);
					Workbook workbook = transformer.transformXLS(in, dataMap);
					
					Method method = response.getClass().getMethod("getOutputStream");
					Object outPutStream = method.invoke(response);
					out = (OutputStream) outPutStream;
					
					workbook.write(out);
				} catch (Exception e) {
					e.printStackTrace();
					throw new ApplicationException(e);
				} finally {
					IOUtil.close(in, out);
				}
			}
			
			//文本文件导出
			else if(fileName.endsWith(".txt")) {
				final Object response = ContextUtil2.getResponse();
				
				try {
					if(ClassLoaderUtil.classForName("javax.servlet.http.HttpServletResponse").isAssignableFrom(response.getClass())) {
						//设置文件下载头部
						Method method = response.getClass().getMethod("setHeader", String.class,String.class);
						method.invoke(response, "Content-Type","application/octet-stream");
						String temp = java.net.URLEncoder.encode(fileName.substring(fileName.lastIndexOf("/")+1,fileName.length()),"UTF-8") ;
						method.invoke(response, "Content-Disposition","attachment;filename=\"" + temp + "\"");
					 
					}
				} catch (Exception ex) {
					throw new ApplicationException("下载文件，设置响应头出错!",ex);
				}
				
				java.io.File dir = new java.io.File(ContextUtil2.class.getResource("/").getPath());
				String tplPath  = dir.getAbsolutePath()+fileName;
				
				InputStream in = null;
				OutputStream out = null;
				try {
					in = new FileInputStream(tplPath);
					if (in.available() == 0) {
						in.close();
						throw new FileNotFoundException("Excel模板文件不存在：" + tplPath);
					}
				
				
					XLSTransformer transformer = new XLSTransformer();
					Map<String,Object> dataMap = new HashMap<>();
					dataMap.put(key, model);
					Workbook workbook = transformer.transformXLS(in, dataMap);
					
					Method method = response.getClass().getMethod("getOutputStream");
					Object outPutStream = method.invoke(response);
					out = (OutputStream) outPutStream;
					
					workbook.write(out);
				} catch (Exception e) {
					e.printStackTrace();
					throw new ApplicationException(e);
				} finally {
					IOUtil.close(in, out);
				}
			}
		}

		@Override
		public void put(String fileName,String templatePath, String key, Object model) {
			
			
		}
		
		public void put(File file) {
			
		}
		
		@Override
		public InputStream get(String name) {
			
			return null;
		}
		
	}
	public static File file =  new FileContext(); 
}
