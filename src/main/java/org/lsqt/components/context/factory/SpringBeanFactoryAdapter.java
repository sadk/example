package org.lsqt.components.context.factory;

import org.lsqt.components.context.bean.BeanException;
import org.lsqt.components.context.bean.BeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

public class SpringBeanFactoryAdapter implements BeanFactory{
	private static final Logger log = LoggerFactory.getLogger(SpringBeanFactoryAdapter.class);
	
	private ApplicationContext application;

	public SpringBeanFactoryAdapter(ApplicationContext application) {
		this.application = application;
	}

	public <T> T getBean(Class<T> requiredType) throws BeanException {
		try {
			return application.getBean(requiredType);
		} catch (NoSuchBeanDefinitionException e) {
			log.info("Spring container NoSuchBeanDefinition , class is: {}",requiredType);
			return null;
		}
	}

	public ApplicationContext getApplication() {
		return application;
	}

	public void setApplication(ApplicationContext application) {
		this.application = application;
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(String id) throws BeanException {
		try {
			return (T) application.getBean(id);
		} catch (NoSuchBeanDefinitionException e) {
			log.info("Spring container NoSuchBeanDefinitionException , bean id is: {}",id);
			return null;
		}
	}
}
