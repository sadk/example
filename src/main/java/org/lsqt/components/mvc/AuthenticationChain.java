package org.lsqt.components.mvc;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.uum.util.CodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 主要用来判断是否启用登陆、是否已登陆
 * @author mm
 *
 */
public class AuthenticationChain implements Chain{
	private static final Logger log = LoggerFactory.getLogger(AuthenticationChain.class);
	
	private boolean enable = true;
	private int order = 700;
	private int state = STATE_NO_WORK;
	
	
	private Configuration configuration;
	private HttpServletRequest request;
	private HttpServletResponse response;

	
	
	public AuthenticationChain(Configuration configuration,HttpServletRequest request,HttpServletResponse response) {
		this.configuration = configuration;
		this.request = request;
		this.response = response;
	}


	
	public boolean isEnable() {
		return this.enable;
	}


	
	public int getOrder() {
		 
		return this.order;
	}

	public int getState() {
		 
		return this.state;
	}

	
	public Object handle() throws IOException {
		
		if (!this.configuration.isEnableLogin()) {
			this.state = STATE_IS_CONTINUE_TO_EXECUTE;
		} else {
			if (!isLogined() && !isAnonymous()) { // 没有登陆并且不是非匿名访问,返回到登陆页
				response.sendRedirect(request.getContextPath() + "/login.jsp");
				this.state = STATE_IS_REDIRECTED;
			}
		}
		return null;
	}

	/**
	 * 用户是否登陆(目前以cookie的方法)
	 * @return
	 */
	private boolean isLogined() {
		String uri = request.getRequestURI();
		System.out.println(uri);
		
		List<String> uidList = null;
		
		// 获取第一个cookie
		Cookie[] cookies = request.getCookies();
		if(cookies == null) {
			return false;
		}
		
		for (Cookie e : cookies) {
			if (CodeUtil.UID.equals(e.getName())) {
				String uidValue = CodeUtil.simpleDecode(e.getValue());
				if (StringUtil.isNotBlank(uidValue)) {
					uidList = StringUtil.split(uidValue, ",");
				}
				break;
			}
		}

		// 获取第一个cookie
		List<String> uid2List = null;
		if (uidList != null) {
			for (Cookie e : cookies) {
				if (e.getName().equals(CodeUtil.simpleEncode(uidList.get(0) + "," + uidList.get(2)))) {
					String value = CodeUtil.simpleDecode(e.getValue());
					if (StringUtil.isNotBlank(value)) {
						uid2List = StringUtil.split(value, ",");
					}
					break;

				}
			}
		}

		// 比较密码+时间戳相等
		if ((uidList != null && uid2List != null) && (uidList.size() == 5 && uid2List.size() == 2)) {
			String accPwd1 = uidList.get(1);
			String accTime1 = uidList.get(2);

			String accPwd2 = uid2List.get(0);
			String accTime2 = uid2List.get(1);

			if (accPwd1.equals(accPwd2) && accTime1.equals(accTime2)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 匿名访问的URL直接(放行)让后续的Filter或Servlet处理
	 * @param request
	 * @return
	 */
	private boolean isAnonymous() {
		Set<String> uriSet = this.configuration.getURIAnonymous();
		if (ArrayUtil.isBlank(uriSet)) {
			return false;
		}

		for (String pattern : uriSet) {
			boolean isMatch = Pattern.matches(pattern, request.getRequestURI());
			if (isMatch) {
				return true;
			}
		}
		return false;
	}
}

