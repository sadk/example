package org.lsqt.components.mvc;

import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.lsqt.components.util.collection.ArrayUtil;

/**
 * 匿名访问
 * @author mm
 *
 */
public class AnonymousAccessChain implements Chain {
	private int order = 200;
	private boolean enable = true; 
	private int state = STATE_NO_WORK;
	
	private Configuration configuration;
	private HttpServletRequest request;
	
	
	public AnonymousAccessChain(Configuration configuration,HttpServletRequest request) {
		this.configuration = configuration;
		this.request = request;
	}
	
	
	public boolean isEnable() {
		return enable;
	}

	
	public int getOrder() {
		return this.order;
	}

	
	public int getState() {
		return this.state;
	}

	
	public Object handle() throws Exception {
		// 静态资源URI 或脱离容器处理的URI
		if (isAccessNext(this.configuration.getURIStatic()) || isAccessNext(this.configuration.getURIEscape())) { 
			this.state = STATE_IS_STATIC_OR_ESCAPE_ACCESS;
		}
		
		return null;
	}

	/**
	 * 是否允许往下执行
	 * @param uriSet 静态资源URI或脱离容器处理的URI
	 * @return 
	 */
	private boolean isAccessNext(Set<String> uriSet) {
		if(ArrayUtil.isBlank(uriSet)) {
			return false;
		}
		for(String pattern: uriSet) {
			boolean isMatch = Pattern.matches(pattern, request.getRequestURI());
			if(isMatch){
				return true;
			}
		}
		return false;
	}
	

	 
}

