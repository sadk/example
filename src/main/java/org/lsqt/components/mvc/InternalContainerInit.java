package org.lsqt.components.mvc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.lsqt.components.context.bean.BeanFactory;
import org.lsqt.components.context.impl.bean.factory.AnnotationBeanFactory;
import org.lsqt.components.context.impl.bean.factory.SpringBeanFactoryAdapter;
import org.lsqt.components.mvc.impl.AnnotationUrlMappingRoute;
import org.lsqt.components.mvc.impl.UrlMappingRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 内部容器初使化
 * @author mm
 *
 */
public class InternalContainerInit implements Order,Initialization {
	private static final Logger log = LoggerFactory.getLogger(InternalContainerInit.class);
	
	private int order = 600;
	
	private static ExecutorService  EXECUTOR = Executors.newSingleThreadExecutor(); 
	
	private static volatile boolean IS_CONTAINER_INITED = false;
	
	/**
	 * 应用框架内部容器
	 */
	private BeanFactory beanFactory;
	
	/**
	 * 应用框架Web层路由器
	 */
	private UrlMappingRoute urlMappingRoute;

	

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public void init() {
		EXECUTOR.execute(() -> {
			initAsynchronous();
			finished();
			log.info("Application startup successful.");
		});
	}
	
	/**
	 * 异步初使化
	 */
	private void initAsynchronous() {
		beanFactory = new AnnotationBeanFactory();
		AnnotationBeanFactory factory = ((AnnotationBeanFactory) beanFactory);

		// 框架容器
		factory.buildBeanMeta();
		factory.buildBean();
		factory.buildBeanDependency();
		log.info("Internal IOC Container initially successful");
		
		// 注册spring容器
		ApplicationContext app = new ClassPathXmlApplicationContext("classpath:application.xml");
		BeanFactory springBeanFactory = new SpringBeanFactoryAdapter(app);
		factory.regist(springBeanFactory);
		log.info("Spring Container initially successful");
		
		
		//MVC
		urlMappingRoute = new AnnotationUrlMappingRoute(factory.getBeanDefinitions()).buildUrlMapping();
		log.info("Internal MVC Component initially successful");
		
	}
	
	private void finished() {
		synchronized (InternalContainerInit.class) {
			IS_CONTAINER_INITED  = true;
		}
	}
	
	/**
	 * 判断框架容器是否已初使化完成(完成bean的依赖、DB实例化、MVC路由等)
	 * @return
	 */
	public boolean isFinished() {
		return IS_CONTAINER_INITED;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public UrlMappingRoute getUrlMappingRoute() {
		return urlMappingRoute;
	}

	public void setUrlMappingRoute(UrlMappingRoute urlMappingRoute) {
		this.urlMappingRoute = urlMappingRoute;
	}
}

