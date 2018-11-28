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
}
