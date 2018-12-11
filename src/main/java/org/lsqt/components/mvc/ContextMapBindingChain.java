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
	private int state = STATE_NO_WORK;
	
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
		if (ArrayUtil.isNotBlank(uidList) && uidList.size()>=6) {
			ContextUtil.getContextMap().put(ContextUtil.CONTEXT_LOGIN_ACCOUNT_OBJECT, uidList.get(0));
			ContextUtil.getContextMap().put(ContextUtil.CONTEXT_LOGIN_ID_OBJECT, uidList.get(3));
			ContextUtil.getContextMap().put(ContextUtil.CONTEXT_LOGIN_NAME_OBJECT, uidList.get(4));
			ContextUtil.getContextMap().put(ContextUtil.CONTEXT_LOGIN_TENANT_CODE_OBJECT, uidList.get(5));
		}	
		
		this.state = STATE_IS_CONTINUE_TO_EXECUTE;
		
		return null;
	}
}

