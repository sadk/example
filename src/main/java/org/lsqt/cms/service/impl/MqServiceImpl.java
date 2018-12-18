package org.lsqt.cms.service.impl;

import org.lsqt.components.context.annotation.Component;
import org.lsqt.components.context.annotation.OnStarted;
import org.lsqt.components.context.bean.BeanFactory;
import org.springframework.context.ApplicationContext;

@Component
@OnStarted(order = 1)
public class MqServiceImpl{

	@OnStarted(order = 1)
	public Object started(BeanFactory context) {
		//System.out.println("Mq1ServiceImpl.......");
		ApplicationContext ctx;  
		return System.currentTimeMillis();
	}

}

