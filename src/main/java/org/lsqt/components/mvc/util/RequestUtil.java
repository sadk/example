package org.lsqt.components.mvc.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
	
	/**
	 * 获取Cookie值
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String key) {
		Cookie[] ck = request.getCookies();
		if (ck != null && ck.length > 0) {
			for (Cookie c : ck) {
				if (key.equals(c.getName())) {
					return c.getValue();
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * 获取请求的URI地址
	 * @return
	 */
	public static String getRequestURI(HttpServletRequest request) {
		String uri = request.getRequestURI();

		// bugFix: 去掉工程名前缀，如: http://ip:poart/工程名(也就是context)/user/login
		String ctx = request.getContextPath();
		uri = uri.substring(ctx.length(), uri.length());

		while (true) {
			uri = uri.replace("//", "/");
			uri = uri.replace("\\", "/");

			if (uri.indexOf("//") == -1 && uri.indexOf("\\") == -1) {
				break;
			}
		}

		return uri;
	}
}
