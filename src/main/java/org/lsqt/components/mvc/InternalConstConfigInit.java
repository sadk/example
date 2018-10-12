package org.lsqt.components.mvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;

import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 内部固定配置 （静态资源、js\css等)
 * @author mm
 *
 */
public class InternalConstConfigInit implements Order,Initialization {
	private static final Logger log = LoggerFactory.getLogger(InternalConstConfigInit.class);
	private int order = 500;
	
	private FilterConfig filterConfig;
	private static Set<String> URI_STATIC=new HashSet<>();
	
	
	public InternalConstConfigInit(FilterConfig filterConfig) {
		this.filterConfig = filterConfig ;
	}
	
	public void init () {
		String staticRes = filterConfig.getInitParameter("static");
		if (StringUtil.isNotBlank(staticRes)) {
			URI_STATIC.addAll(contextWrap(filterConfig,StringUtil.split(String.class, staticRes, ",",true)));
		}
		
		
		// 静态资源访问的URL
		URI_STATIC.add(".*.ico");
		URI_STATIC.add(".*.css");
		URI_STATIC.add(".*.js");
		URI_STATIC.add(".*.html");
		URI_STATIC.add(".*.swf");
		URI_STATIC.add(".*.png");
		URI_STATIC.add(".*.gif");
		URI_STATIC.add(".*.jpg");
		URI_STATIC.add(".*.jpeg");
		URI_STATIC.add(".*.woff2");
		
		URI_STATIC.add(".*.txt");
		URI_STATIC.add(".*.xml");
		URI_STATIC.add(".*.pdf");
		URI_STATIC.add(".*.xls");
		URI_STATIC.add(".*.xlsx");
		URI_STATIC.add(".*.ppt");
		URI_STATIC.add(".*.doc");
	}
	
	/**
	 * 静态资源直接返回响应
	 * @param request
	 * @return
	 */
	public boolean isStatic(HttpServletRequest request) {
		if(ArrayUtil.isBlank(URI_STATIC)) {
			return false;
		}
		for(String pattern: URI_STATIC) {
			boolean isMatch = Pattern.matches(pattern, request.getRequestURI());
			if(isMatch){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 包装URI,如果应用布署有上下文路径
	 * @param filterConfig
	 * @param list 匿名URI
	 * @return
	 */
	private List<String> contextWrap(FilterConfig filterConfig,List<String> list) {
		List<String> pathList = new ArrayList<>();
		if (ArrayUtil.isNotBlank(list)) {
			String contextPath = filterConfig.getServletContext().getContextPath();
			for(String p: list) {
				String wrapUrl=contextPath+p;
				pathList.add(wrapUrl);
				log.info("静态资源url : {} ", wrapUrl);
			}
		}
		return pathList;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}

