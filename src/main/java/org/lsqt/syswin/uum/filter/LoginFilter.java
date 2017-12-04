package org.lsqt.syswin.uum.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lsqt.syswin.uum.controller.CodeUtil;
import org.lsqt.syswin.uum.controller.Result;
import org.lsqt.syswin.uum.model.User;

public class LoginFilter  implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		Cookie[] ck = request.getCookies();
		if (ck != null && ck.length > 0) {

			String sidValue = getValue(CodeUtil.SID, request);

			if (sidValue != null) {
				String key = CodeUtil.decode(sidValue); //用于获取密码和db里校验
				String loginNo = CodeUtil.decode(key);
				
				/*
				User user = db.executeQueryForObject("select * from t_user_info where login_no=?", User.class, username);
				if (user == null) {
					return Result.fail("没有找到登陆账号为%s的用户");
				}
				*/
			}
		}
	}
	
	
	private String getValue(String key,HttpServletRequest request) {
		Cookie[] ck = request.getCookies();
		if (ck != null && ck.length > 0) {
			for (Cookie c : ck) {
				if(key.equals(c.getName())){
					return c.getValue();
				}
			}
		}
		return null;
	}

	@Override
	public void destroy() {
		
		
	}
}
