package org.lsqt.components.util.http;

import java.io.IOException;
import java.util.Map;


/**
 * HTTPClient常用的方法定义
 * @author yuanmm
 *
 */
public interface HttpClientRequest {
	/**
	 * http请求方法
	 * @author yuanmm
	 *
	 */
	public static enum Method {
		GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;
	}
	
	public void execute(String url) throws IOException ;
	
	public String executeForString(String url) throws IOException ;
	
	public String executeForString(String url,Method method) throws IOException ;
	
	public String executeForString(String url,Map<String,Object> params) throws IOException ;
	
	public String executeForString(String url,Map<String,Object> params,Method method) throws IOException ;
	
	public String executeForString(String url,Method method,String proxyHost,String proxyPort) throws IOException ;
	
	public String executeForString(String url,Map<String,Object> params,String proxyHost,String proxyPort) throws IOException;
	
	public String executeForString(String url,Map<String,Object> params,Method method,String proxyHost,String proxyPort) throws IOException ;
	
}
