package org.lsqt.cms.service.impl;

import org.lsqt.components.context.annotation.Component;
import org.lsqt.components.context.annotation.OnStarted;

@Component
@OnStarted(order=2)
public class Mq2ServiceImpl {

	@OnStarted(order = 2,text="rabitmq启动，服务于蜂巢")
	public void started() {
		//System.out.println("Mq2ServiceImpl.......");
		if(true) {
			//throw new RuntimeException();
		}
	}
}
