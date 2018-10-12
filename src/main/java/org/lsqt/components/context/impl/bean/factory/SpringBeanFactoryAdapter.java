package org.lsqt.components.context.impl.bean.factory;

import org.lsqt.components.context.bean.BeanException;
import org.lsqt.components.context.bean.BeanFactory;
import org.springframework.context.ApplicationContext;

public class SpringBeanFactoryAdapter implements BeanFactory{

	private ApplicationContext application;

	public SpringBeanFactoryAdapter(ApplicationContext application) {
		this.application = application;
	}

	public <T> T getBean(Class<T> requiredType) throws BeanException {
		
		return application.getBean(requiredType);
	}

	public ApplicationContext getApplication() {
		return application;
	}

	public void setApplication(ApplicationContext application) {
		this.application = application;
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(String id) throws BeanException {
		return (T) application.getBean(id);
	}
}
