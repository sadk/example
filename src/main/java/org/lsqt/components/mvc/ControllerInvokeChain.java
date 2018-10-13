package org.lsqt.components.mvc;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.lsqt.components.context.Result;
import org.lsqt.components.context.annotation.mvc.After;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.bean.BeanFactory;
import org.lsqt.components.context.impl.util.CacheMethodUtil;
import org.lsqt.components.db.Db;
import org.lsqt.components.mvc.impl.UrlMappingDefinition;
import org.lsqt.components.mvc.impl.UrlMappingRoute;
import org.lsqt.components.mvc.util.ArgsValueBindUtil;
import org.lsqt.components.util.collection.ArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller方法调用
 * @author mm
 *
 */
public class ControllerInvokeChain implements Chain{
	private static final Logger log = LoggerFactory.getLogger(ControllerInvokeChain.class);
	
	private boolean enable = true;
	private int order = Integer.MAX_VALUE;
	private int state = STATE_NO_WORK;
	
	private HttpServletRequest request;
	
	private Configuration configuration;
	
	private BeanFactory beanFactory ;
	private Db db;
	
	public ControllerInvokeChain(Configuration configuration, HttpServletRequest request) {
		this.request = request;
		
		this.configuration = configuration;
		
		this.beanFactory = configuration.getBeanFacotry(); //从配置获取IOC容器
		this.db = configuration.getBeanFacotry().getBean(Db.class); //从IOC获取DB组件
		
	}
	
	
	public boolean isEnable() {
		return enable;
	}

	
	public int getOrder() {
		return this.order;
	}

	
	public int getState() {
		return this.state;
	}

	/**
	 * 返回controller返回的值
	 */
	public Object handle() throws Exception {
		UrlMappingRoute route = configuration.getUrlMappingRoute();
		UrlMappingDefinition urlMappingDefinition = route.find(getRequestURI());
		
		if (urlMappingDefinition == null) {
			return null;
		}
		
		Object controller = beanFactory.getBean(urlMappingDefinition.getControllerClass());
		
		List<Object> methodInputParamValues = ArgsValueBindUtil.getMethodArgsValue(urlMappingDefinition.getMethod());
		
		boolean isExcludeTransaction = false; // 是否脱离数据库(事务)环境
		boolean isTransaction = true; //开启事务
		
		Method action = urlMappingDefinition.getMethod();
		RequestMapping rm = action.getAnnotation(RequestMapping.class);
		if (rm != null) {
			isExcludeTransaction = rm.excludeTransaction();
			isTransaction = rm.isTransaction();
		}
		
		final Result<Object> result = Result.fail();
		if (isExcludeTransaction) {
			result.setData(urlMappingDefinition.getMethod().invoke(controller, methodInputParamValues.toArray()));
			invokeAfter(urlMappingDefinition,result);
		} else {
			db.executePlan(isTransaction, () -> {
				try {
					result.setData(urlMappingDefinition.getMethod().invoke(controller, methodInputParamValues.toArray()));
					
					//后置处理，一定纳入到当前事务中
					invokeAfter(urlMappingDefinition,result);
				} catch (Exception e) {
					e.printStackTrace();
					this.state = STATE_EXE_EXCEPTION;
				}
			});
		}
		
		if (this.state != STATE_EXE_EXCEPTION) {
			this.state = STATE_IS_CONTINUE_TO_EXECUTE;
		}
		
		ArgsValueBindUtil.clear();
		return result.getData();
	}
	
	/**
	 * 
	 * 获取请求的URI地址
	 * @return
	 */
	private String getRequestURI() {
		String uri = request.getRequestURI();

		// bugFix: 去掉工程名前缀，如: http://ip:poart/工程名(也就是context)/user/login
		String ctx = request.getContextPath();
		uri = uri.substring(ctx.length(), uri.length());
		return uri;
	}
	
	/**
	 * 请求后置处理
	 * @param urlMappingDefinition 当前URI映射定义
	 * @param controllerResult controller方法返回值
	 * @throws Exception
	 */
	private void invokeAfter(UrlMappingDefinition urlMappingDefinition, Result<Object> controllerResult) throws Exception {
		After after = urlMappingDefinition.getMethod().getAnnotation(After.class);
		if (after != null) {
			Class<?> processClass = after.clazz();

			List<Method> methodList = CacheMethodUtil.getMethodList(processClass);
			if (ArrayUtil.isBlank(methodList)) {
				log.warn("类 {} 没有定义方法", processClass);
				return ;
			}
			
			Method processMethod = null;
			if (after.args() == null || after.args().length == 0) {
				for (Method m : methodList) {
					if (m.getName().equals(after.method())) {
						processMethod = m;
						break;
					}
				}
			} else {
				processMethod = processClass.getMethod(after.method(), after.args());
			}

			if (processMethod != null) {
				processMethod.setAccessible(true);

				Object bean = beanFactory.getBean(processClass);
				if (bean == null) {
					bean = processClass.newInstance();
				}

				Object wrappedObject = processMethod.invoke(bean, controllerResult.getData());
				controllerResult.setData(wrappedObject);
			}
		}
	}
	
}

