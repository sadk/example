package org.lsqt.components.mvc;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.lsqt.components.context.Result;
import org.lsqt.components.context.annotation.Component;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Dao;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.context.annotation.mvc.Before;
import org.lsqt.components.context.annotation.mvc.Match;
import org.lsqt.components.context.bean.BeanFactory;
import org.lsqt.components.context.impl.util.CacheMethodUtil;
import org.lsqt.components.db.Db;
import org.lsqt.components.mvc.impl.UrlMappingDefinition;
import org.lsqt.components.mvc.impl.UrlMappingRoute;
import org.lsqt.components.mvc.util.ArgsValueBindUtil;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 前置处理:当前请求对应的controller方法的前置处理
 * @author mm
 *
 */
public class BeforeHandleChain implements Chain{
	private static final Logger log = LoggerFactory.getLogger(BeforeHandleChain.class);
	
	private boolean enable = true;
	private int order = 1000;
	private int state = STATE_DO_NEXT_NOT_ALLOW;
	
	private HttpServletRequest request;
	
	
	private Configuration configuration;
	
	private BeanFactory beanFactory ;
	private Db db;
	
	public BeforeHandleChain(Configuration configuration, HttpServletRequest request) {
		this.request = request;
	
		this.configuration = configuration;
		
		this.beanFactory = configuration.getBeanFacotry();//从配置里取IOC容器
		this.db = configuration.getBeanFacotry().getBean(Db.class);//从配置里面取DB组件
		
	}


	@Override
	public boolean isEnable() {
		return this.enable;
	}


	@Override
	public int getOrder() {
		 
		return this.order;
	}


	@Override
	public int getState() {
		 
		return this.state;
	}


	@Override
	public Object handle() throws Exception {
		/* 
		Before处理异常、--> 选择相应的视图显示异常
		Before处理有异常（但补Result包裹）--> 返回Result不成功，选择相应的视图显示异常

		action异常、选择相应的视图显示异常

		After异常、选择相应的视图显示异常
		After异常（但补Result包裹）--> 返回Result不成功，选择相应的视图显示异常
		*/
		UrlMappingRoute route = configuration.getUrlMappingRoute();
		
		
		UrlMappingDefinition urlMappingDefinition = route.find(getRequestURI());
		
		//Object controller = beanFactory.getBean(urlMappingDefinition.getControllerClass());
		
		List<Object> methodInputParamValues = ArgsValueBindUtil.getMethodArgsValue(urlMappingDefinition.getMethod());
		
		 
		
		Object beforeMethodReturnObject = invokeBefore(urlMappingDefinition, methodInputParamValues.toArray());
		
		this.state = STATE_DO_NEXT_CONTINUE;
		
		
		return beforeMethodReturnObject;
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
	 * controller方法的前置处理
	 * @param urlMappingDefinition 当前请求对应的映射元信息
	 * @param params 当前请求controller方法的入参值
	 * @return
	 * @throws Exception
	 */
	private Object invokeBefore(UrlMappingDefinition urlMappingDefinition,  Object ... params) throws Exception {
		if(urlMappingDefinition == null) return null;
		
		Object beforeMethodReturnObject = null; //前置处理器方法返回值
		
		Before before = urlMappingDefinition.getMethod().getAnnotation(Before.class);
		if (before != null) {
			Class<?> processClass = before.clazz();

			List<Method> processMethodList = CacheMethodUtil.getMethodList(processClass);

			if (ArrayUtil.isNotBlank(processMethodList)) {
				 
				Method processMethod = null; 
				final List<Object> processMethodParamValue = new ArrayList<>();

				if(processMethodList.size() == 1) {
					processMethod = processMethodList.get(0);
					processMethodParamValue.addAll(ArgsValueBindUtil.getMethodArgsValue(processMethod));
					
				} else {
					String controllerMethodName = urlMappingDefinition.getMethod().getName();
					Class<?>[] controllerMethodArgsTypes = urlMappingDefinition.getMethod().getParameterTypes();
					
					if (StringUtil.isBlank(before.method())) {// 如果没有标注哪个处理方法，用controller的源方法名和参数查找处理“方法”
						processMethod = processClass.getMethod(controllerMethodName, controllerMethodArgsTypes);
						processMethodParamValue.addAll(Arrays.asList(params));
	
					} else if (StringUtil.isNotBlank(before.method()) && before.args().length == 0) { // 如果标注了处理方法，没有标注方法参数，用源方法参数查找处理“方法”
						processMethod = processClass.getMethod(before.method(), controllerMethodArgsTypes);
						processMethodParamValue.addAll(ArgsValueBindUtil.getMethodArgsValue(processMethod));
						
					} else if (StringUtil.isNotBlank(before.method()) && before.args().length > 0) {// 即标注了处理方法又指定了方法参数类型，直接查找到处理“方法”
						processMethod = processClass.getMethod(before.method(), before.args());
						processMethodParamValue.addAll(ArgsValueBindUtil.getMethodArgsValue(processMethod));
					}
				}
				
				if (processMethod != null) {
					boolean isMatch = false;
					Match matcher = processMethod.getAnnotation(Match.class);
					
					if (matcher == null) {//不写match，controller的任何url都匹配，处理器执行！
						isMatch = true; 
					} else {
						String[] urls = matcher.mapping();
						if (ArrayUtil.isBlank(Arrays.asList(urls))) {
							return null;
						}
						 
						String currUrl = getRequestURI();
						
						Controller ctl = urlMappingDefinition.getControllerClass().getAnnotation(Controller.class);
						String [] ctlMapping = ctl.mapping();
						if (ArrayUtil.isBlank(Arrays.asList(ctlMapping))) { //控制器定义的url没有标注模块前缀
							for (String path: urls) {
								isMatch = Pattern.matches(path.concat(".*"), currUrl);
								if(isMatch) {
									break;
								}
							}
						} else {
							
							for (String ctlUrl : ctlMapping) {
								boolean isFind = false;
								for (String path : urls) {
									isFind = Pattern.matches(ctlUrl.concat(path).concat(".*"), currUrl);
									if (isFind) {
										break;
									}
								}
								if (isFind) {
									isMatch = isFind;
									break;
								}
							}
						}
					}
					
					
					
					if (isMatch && processMethod != null) {
						processMethod.setAccessible(true);

						Object bean = null;
						if (processClass.getAnnotation(Dao.class) != null
								|| processClass.getAnnotation(Service.class) != null
								|| processClass.getAnnotation(Component.class) != null
								|| processClass.getAnnotation(Controller.class) != null) {
							bean = beanFactory.getBean(processClass);
						}

						if (bean == null) {// 单纯的POJO
							bean = processClass.newInstance();
						}

						
						
						//如果有前置事务处理
						boolean isExcludeTransaction = matcher.excludeTransaction(); // 是否脱离数据库(事务)环境
						boolean isTransaction = true; //开启事务
						
						if (isExcludeTransaction) {
							beforeMethodReturnObject = processMethod.invoke(bean, processMethodParamValue.toArray());
						} else {
							
							Result<Object> result = Result.ok();
							result.setHook(processMethod);
							result.setData(bean);
							
							db.executePlan(isTransaction, ()->{
								try {
									Method m = (Method)result.getHook();
									Object b = result.getData();
									result.setData( m.invoke(b, processMethodParamValue.toArray()));
								} catch (Exception e) {
									e.printStackTrace();
								}
							});
							
							beforeMethodReturnObject = result.getData();
						}
						
					}
				}
				
			} 
		}
		return beforeMethodReturnObject;
	}

}

