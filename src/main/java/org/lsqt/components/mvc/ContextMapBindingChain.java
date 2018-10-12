package org.lsqt.components.mvc;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.uum.util.CodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 上下文对象、和数据
 * @author mm
 *
 */
public class ContextMapBindingChain implements Chain{
	private static final Logger log = LoggerFactory.getLogger(ContextMapBindingChain.class);
	
	private boolean enable = true;
	private int order = 400;
	private int state = STATE_DO_NEXT_NOT_ALLOW;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public ContextMapBindingChain(HttpServletRequest request,HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
	@Override
	public boolean isEnable() {
		return enable;
	}

	@Override
	public int getOrder() {
		return this.order;
	}

	@Override
	public int getState() {
		return this.state;
	}
	
	@Override
	public Object handle() throws Exception {
		ContextUtil.getContextMap().put(ContextUtil.CONTEXT_REQUEST_OBJECT, request);
		ContextUtil.getContextMap().put(ContextUtil.CONTEXT_RESPONSE_OBJECT, response);
		
		// 获取第一个cookie
		Cookie[] cookies = request.getCookies();
		if(cookies == null) {
			return null;
		}
		
		List<String> uidList = null;
		for (Cookie e : cookies) {
			if (CodeUtil.UID.equals(e.getName())) {
				String uidValue = CodeUtil.simpleDecode(e.getValue());
				if (StringUtil.isNotBlank(uidValue)) {
					uidList = StringUtil.split(uidValue, ",");
				}
				break;
			}
		}
		
		// 绑定用户ID或账号到上下文
		if (ArrayUtil.isNotBlank(uidList)) {
			ContextUtil.getContextMap().put(ContextUtil.CONTEXT_LOGIN_NAME_OBJECT, uidList.get(0));
		}	
		
		this.state = STATE_DO_NEXT_CONTINUE;
		
		return null;
	}
}
