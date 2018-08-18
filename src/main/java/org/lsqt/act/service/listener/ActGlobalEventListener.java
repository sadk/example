package org.lsqt.act.service.listener;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.lsqt.components.context.ContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * 全局事件监听器
 * 注：目前使用
 * @author admin
 *
 */
public class ActGlobalEventListener implements ActivitiEventListener {
	private static final Logger  log = LoggerFactory.getLogger(ActGlobalEventListener.class);
	
	private Map<String, String> handlers = new HashMap<String, String>(); //事件处理器

	@Override
	public void onEvent(ActivitiEvent event) {
		String eventType = event.getType().name();
		log.debug("envent type is ========>" + eventType);

		String eventHandlerBeanId = handlers.get(eventType);
		if (eventHandlerBeanId != null) {
			ApplicationContext springContext = ContextUtil.getSpringContext();
			if (springContext != null) {
				EventHandler handler = (EventHandler) springContext.getBean(eventHandlerBeanId);
				handler.handle(event);
			} else {
				log.error("非WEB环境下执行，没有找到spring容器");
			}
		}
	}

	@Override
	public boolean isFailOnException() {

		return false;
	}

	public Map<String, String> getHandlers() {
		return handlers;
	}

	public void setHandlers(Map<String, String> handlers) {
		this.handlers = handlers;
	}

}
