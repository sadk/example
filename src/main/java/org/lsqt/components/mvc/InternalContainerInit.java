package org.lsqt.components.mvc;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.lsqt.components.context.CacheReflectUtil;
import org.lsqt.components.context.Order;
import org.lsqt.components.context.annotation.OnStarted;
import org.lsqt.components.context.bean.BeanDefinition;
import org.lsqt.components.context.bean.BeanFactory;
import org.lsqt.components.context.factory.AnnotationBeanFactory;
import org.lsqt.components.context.factory.SpringBeanFactoryAdapter;
import org.lsqt.components.mvc.impl.AnnotationUrlMappingRoute;
import org.lsqt.components.mvc.impl.UrlMappingRoute;
import org.lsqt.components.util.collection.ArrayUtil;
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
			try{
				long start = System.currentTimeMillis();
				
				initAsynchronous();
				onStarted();
				finished();
				
				long end = System.currentTimeMillis();
				
				consoleEnvInfo();
				log.info("Application startup successfully, cost->{}(ms)",(end - start));
				
			}catch(Exception e) {
				Throwable err = e.getCause();
				if (err == null) {
					e.printStackTrace();
				} else {
					err.printStackTrace();
				}
				System.exit(-1);
			}
		});
	}
	
	private void consoleEnvInfo() {
		Runtime run = Runtime.getRuntime();
		Properties env = System.getProperties();
		
		log.info("#################################################################");
		log.info("#\tJVM可以使用的总内存:\t\t{}(m)\t\t\t\t#" , run.totalMemory()/(1024*1024));
		log.info("#\tJVM可以使用的剩余内存:\t\t{}(m)\t\t\t\t#" , run.freeMemory()/(1024*1024));
		log.info("#\tJVM可以使用的处理器个数:\t{}(个)\t\t\t\t#" , run.availableProcessors());
        
		String ip = "#\t本地ip地址获取失败";
		try {
			InetAddress addr = InetAddress.getLocalHost();
			ip = addr.getHostAddress();
			log.info("#\t本地ip地址:\t\t{}\t\t\t#", ip);			
		} catch (Exception e) {
			log.error(ip);
		}
      
        
		log.info("#\t操作系统:{}, 版本:{}, jdk:{}, cpu架构:{}\t#",env.getProperty("os.name"), env.getProperty("os.version"),
				env.getProperty("java.version"), env.getProperty("os.arch"));
		log.info("#################################################################");
	}
	
	private class ClassWrap {
		public Class<?> clazz;
		public int order;
		
		public String text;
	}

	private static class MethodWrap {
		public Class<?> clazz;
		public Method method;
		public int order;
		
		public String text;
		
		/**
		 * 填充指定的方法参数为null，保证调用不报错
		 * @param m
		 * @return
		 */
		public static Object [] fillNullParam(Method m) {
			List<Object> paramValue = new ArrayList<>();
			int paramCount = m.getParameterCount();
			for (int i = 0; i < paramCount; i++) {
				paramValue.add(null);
			}
			return paramValue.toArray();
		}
		
		/**
		 * 批量调用标注了OnStart注解的启动方法
		 * @param beanFactory 容器对象
		 * @param methodList 需要调用的方法
		 * @throws Exception
		 */
		public static void invokeOnStartMethodList(BeanFactory beanFactory, List<Method> methodList) throws Exception {
			List<MethodWrap> toDoList = new ArrayList<>();
			for (Method me: methodList) {
				if (me.isAnnotationPresent(OnStarted.class)) {
					OnStarted start= me.getAnnotation(OnStarted.class);
					MethodWrap m = new MethodWrap();
					m.method = me;
					m.order = start.order();
					m.text = start.text();
					m.clazz = me.getDeclaringClass();
					toDoList.add(m);
				}
			}
			
			if (ArrayUtil.isNotBlank(toDoList)) {
				Collections.sort(toDoList,(o1,o2)-> o1.order-o2.order);
				for (MethodWrap mw : toDoList) {
					Object instance = beanFactory.getBean(mw.clazz);
					mw.method.setAccessible(true);
					mw.method.invoke(instance,MethodWrap.fillNullParam(mw.method));
					log.info("The method \"{}\" has been invoked after application startup, and the signature of the method is: {}", mw.method.getName(),mw.method);
				}
			}
		}
	}
	
	
	/**
	 * 容器启动事件响应
	 * @throws Exception 
	 */
	private synchronized void onStarted() throws Exception {
		AnnotationBeanFactory annotationFactory = (AnnotationBeanFactory)beanFactory;
		List<BeanDefinition> list = annotationFactory.getBeanDefinitions();

		List<ClassWrap> annotationInClassList = new ArrayList<>();
		List<ClassWrap> annotationInMethodList= new ArrayList<>();
		
		for (BeanDefinition e: list) {
			Class<?> clazz = e.getBeanClass();
			ClassWrap clazzWrap = new ClassWrap();
			clazzWrap.clazz = clazz;
			
			OnStarted onStarted = null;
			if (clazz.isAnnotationPresent(OnStarted.class)) {
				onStarted = clazz.getAnnotation(OnStarted.class); // 1.注解在类上
				clazzWrap.order = onStarted.order();
				clazzWrap.text = onStarted.text();
				annotationInClassList.add(clazzWrap);
			} else {
				List<Method> methodList = CacheReflectUtil.getMethodList(clazz);// 2.注解在方法上的类
				for (Method m : methodList) {
					if (m.isAnnotationPresent(OnStarted.class)) {
						onStarted = m.getAnnotation(OnStarted.class);
						clazzWrap.order = onStarted.order();
						clazzWrap.text = onStarted.text();
						annotationInMethodList.add(clazzWrap);
						break;
					}
				}
			}
		}
		
		if (ArrayUtil.isNotBlank(annotationInClassList)) {
			Collections.sort(annotationInClassList,(o1,o2)-> o1.order-o2.order);
			for (ClassWrap c: annotationInClassList) {
				List<Method>  methodList = CacheReflectUtil.getMethodList(c.clazz);
				if (ArrayUtil.isNotBlank(methodList)) {
					if (methodList.size() == 1) {// 类里只有一个方法,不需要注解
						Method currMethod = methodList.get(0); 
						
						Object instance = beanFactory.getBean(c.clazz);
						currMethod.invoke(instance,MethodWrap.fillNullParam(currMethod));
						log.info("The method \"{}\" has been invoked after application startup, and the signature of the method is: {}",currMethod.getName(),currMethod);
					} else {
						MethodWrap.invokeOnStartMethodList(beanFactory, methodList);
					}
				}
			}
		}
		
		if (ArrayUtil.isNotBlank(annotationInMethodList)) {
			List<Method> invokeList = new ArrayList<>();
			for (ClassWrap c: annotationInMethodList) {
				List<Method>  methodList = CacheReflectUtil.getMethodList(c.clazz);
				if (ArrayUtil.isNotBlank(methodList)) {
					for (Method m: methodList) {
						if (m.isAnnotationPresent(OnStarted.class)) {
							invokeList.add(m);
						}
					}
				}
			}
			MethodWrap.invokeOnStartMethodList(beanFactory, invokeList);
		}
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
		
		
		// MVC
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

