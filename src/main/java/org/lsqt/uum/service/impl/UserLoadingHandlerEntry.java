package org.lsqt.uum.service.impl;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Component;
import org.lsqt.components.context.bean.BeanFactory;
import org.lsqt.components.context.permission.HandlerEntry;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.uum.model.User;
import org.lsqt.uum.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加载用户数据
 * 
 * @author mm
 *
 */
@Component
public class UserLoadingHandlerEntry implements HandlerEntry {
	private static final Logger log = LoggerFactory.getLogger(UserLoadingHandlerEntry.class);
	
	@Override
	public boolean isEnable() {
		return true;
	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public Object handle(Object context) throws Exception {
		if (context instanceof BeanFactory) {
			UserService userService = ((BeanFactory) context).getBean(UserService.class);
			if (ContextUtil.getLoginId() != null) { //注：匿名访问，可能没有用户ID!!!
				User loginUserDetail = userService.getById(Long.valueOf(ContextUtil.getLoginId()), true);
				if(loginUserDetail!=null) {
					ContextUtil.getContextMap().put(ContextUtil.CONTEXT_LOGIN_USER_OBJECT, loginUserDetail);
					
					if (StringUtil.isNotBlank(loginUserDetail.getAppCode())) {
						ContextUtil.getContextMap().put(ContextUtil.CONTEXT_LOGIN_APP_CODE_OBJECT,loginUserDetail.getAppCode());
					}
					
					if(StringUtil.isNotBlank(loginUserDetail.getTenantCode())) {
						ContextUtil.getContextMap().put(ContextUtil.CONTEXT_LOGIN_TENANT_CODE_OBJECT, loginUserDetail.getTenantCode());
					}
				}
			} 
		}
		return null;
	}
}
