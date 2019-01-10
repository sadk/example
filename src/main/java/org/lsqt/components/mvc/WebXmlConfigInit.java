package org.lsqt.components.mvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;

import org.lsqt.components.context.Order;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 初使化链，可排序 (取web.xml的配置)
 * @author mm
 *
 */
public class WebXmlConfigInit  implements Order,Initialization {
	private static final Logger log = LoggerFactory.getLogger(WebXmlConfigInit.class);
	private int order = 100 ;
	
	private FilterConfig filterConfig;
	
	private static Set<String> URI_STATIC=new HashSet<>();
	private static Set<String> URI_ESCAPE=new HashSet<>();
	private static Set<String> URI_ANONYMOUS=new HashSet<>();
	
	private static String LOGIN_ENABLED ;
	
	public WebXmlConfigInit(FilterConfig filterConfig) {
		this.filterConfig = filterConfig ;
	}
	
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
 
	public void init() {
		
		String staticRes = filterConfig.getInitParameter("static");
		if (StringUtil.isNotBlank(staticRes)) {
			URI_STATIC.addAll(contextWrap(filterConfig,StringUtil.split(String.class, staticRes, ",",true)));
		}
		
		String escape = filterConfig.getInitParameter("escape");
		if (StringUtil.isNotBlank(escape)) {
			URI_ESCAPE.addAll(contextWrap(filterConfig,StringUtil.split(String.class, escape, ",",true)));
		}
		
		String anonymous = filterConfig.getInitParameter("anonymous");
		if (StringUtil.isNotBlank(anonymous)) {
			URI_ANONYMOUS.addAll(contextWrap(filterConfig,StringUtil.split(String.class, anonymous, ",",true)));
		}
		
		LOGIN_ENABLED = filterConfig.getInitParameter("login");
 
	}

	/**
	 * 包装URI,如果应用布署有上下文路径
	 * @param filterConfig
	 * @param list 匿名URI
	 * @return
	 */
	private List<String> contextWrap(FilterConfig filterConfig, List<String> list) {
		List<String> pathList = new ArrayList<>();
		if (ArrayUtil.isNotBlank(list)) {
			String contextPath = filterConfig.getServletContext().getContextPath();
			for (String p : list) {
				String wrapUrl = contextPath.concat(p);
				pathList.add(wrapUrl);
				log.info("匿名访问url : {} ", wrapUrl);
			}
		}
		return pathList;
	}
	 
	
	/**
	 * 是否启用登陆
	 * @return
	 */
	public boolean isLoginEnabled() {
		return "true".equalsIgnoreCase(LOGIN_ENABLED) 
				|| "on".equalsIgnoreCase(LOGIN_ENABLED)
				|| "yes".equalsIgnoreCase(LOGIN_ENABLED);
	}
	
	/**
	 * 获取脱离容器管理的URI（供后续servlet或filter使用）
	 * @return
	 */
	public Set<String> getURIEscape() {
		return URI_ESCAPE;
	}

	/**
	 * 获取匿名访问的URI
	 * @return
	 */
	public Set<String> getURIAnonymous() {
		return URI_ANONYMOUS;
	}
	
	/**
	 * 获取静态资源访问的URI,如js脚本目录 ：/scripts/.* 
	 * @return
	 */
	public Set<String> getURIStatic() {
		return URI_STATIC;
	}
}
