package org.lsqt.components.mvc;

import java.io.IOException;

import javax.servlet.Filter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 支持跨域过滤器
 * @author Administrator
 *
 */
public class CORSFilter implements Filter{

	@Override
	public void destroy() {
		 
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
			// CORS "pre-flight" request
			response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.addHeader("Access-Control-Allow-Headers", "Authorization, Cache-Control, Content-Type, Pragma, X-Prototype-Version, X-Requested-With");
			response.addHeader("Access-Control-Expose-Headers", "x-json");
			response.addHeader("Access-Control-Max-Age", "1");
		} else {
			filterChain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		 
		
	}

}
