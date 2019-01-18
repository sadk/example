package org.lsqt.uum.service.impl;

import org.lsqt.components.context.annotation.Component;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.bean.BeanFactory;
import org.lsqt.components.context.permission.HandlerEntry;
import org.lsqt.uum.service.UserService;

/**
 * 加载用户数据
 * @author mm
 *
 */
@Component
public class UserLoadingHandlerEntry implements HandlerEntry {
	 
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

			//System.out.println("用来加载用户数据到上下文: " + userService.getById(1L));
		}
		return null;
	}


}

