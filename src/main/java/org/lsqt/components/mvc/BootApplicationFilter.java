package org.lsqt.components.mvc;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.mvc.util.ArgsValueBindUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author mm
 *
 */
public class BootApplicationFilter implements Filter{
	private static final Logger log = LoggerFactory.getLogger(BootApplicationFilter.class);
	
	/** 框架总体初使化器 **/
	private ConfigInitialization configInitialization ;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		configInitialization = new ConfigInitialization(filterConfig) ;
		try {
			configInitialization.init();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		DispatcherChain dispatcherChain = new DispatcherChain(configInitialization,filterChain, request, response);
		try {
			dispatcherChain.handle();
		} catch (Exception e) {
			e.printStackTrace();
			//异常视图处理器，显示异常视图!!
			
		} finally {
			ContextUtil.clear();
		}
	}

	@Override
	public void destroy() {
		ContextUtil.clear();
	}
	
}
