package org.lsqt.components.mvc.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.impl.bean.resolve.AnnotationBeanMetaResolveImpl;
import org.lsqt.components.context.bean.BeanDefinition;
import org.lsqt.components.context.bean.BeanMetaResolve;
import org.lsqt.components.mvc.ApplicationFilter;
import org.lsqt.components.mvc.impl.UrlMappingDefinition;
import org.lsqt.components.mvc.impl.UrlMappingRoute;
import org.lsqt.components.util.collection.ArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 注解路由实现
 * @author Sky
 *
 */
public class AnnotationUrlMappingRoute implements UrlMappingRoute{
	private static final Logger log = LoggerFactory.getLogger(AnnotationUrlMappingRoute.class);
	
	private List<BeanDefinition> beanDefinitionList = new ArrayList<BeanDefinition>();
	public List<BeanDefinition> getBeanDefinitionList() {
		return beanDefinitionList;
	}
	public void setBeanDefinitionList(List<BeanDefinition> beanDefinitionList) {
		this.beanDefinitionList = beanDefinitionList;
	}
	
	public AnnotationUrlMappingRoute(){}
	public AnnotationUrlMappingRoute(List<BeanDefinition> beanDefs){
		this.beanDefinitionList = beanDefs;
	}
	
	
	private List<UrlMappingDefinition> urlMappingDefs=new ArrayList<UrlMappingDefinition>();
	
	/**
	 * 构建控制器类的所有URL路由定义
	 */
	public void buildUrlMapping() throws Exception{
		final String controllerNameEndFix="Controller";
		final String actionNameEndFix="Action";
		final String urlDefPrifix="/";
		
		for(BeanDefinition e: beanDefinitionList) {
			Class<?> beanClass = e.getBeanClass();
			if(!beanClass.isAnnotationPresent(Controller.class))  continue;
			
			//1.如果控制器类名没有标注URL映射信息，则取类名作为URL映射（去除Controller或Action后缀)
			Controller controller = beanClass.getAnnotation(Controller.class);
			String[] urlForClass = controller.mapping();
			if (urlForClass == null || urlForClass.length == 0) {

				String tempForClass = e.getShortClassName();
				if (tempForClass.endsWith(controllerNameEndFix)) {
					tempForClass = tempForClass.substring(0, tempForClass.lastIndexOf(controllerNameEndFix));
				} else if (tempForClass.endsWith(actionNameEndFix)) {
					tempForClass = tempForClass.substring(0, tempForClass.lastIndexOf(actionNameEndFix));
				}
				urlForClass = new String[] { urlDefPrifix.concat(tempForClass) };
			}
			
			//2.如果控制器内的方法没有标注URL映射信息，则取方法名作为URL映射
			Map<String,Method> urlForMethodMap = new HashMap<String,Method>();
			
			List<Method> list = getUrlMappingMethod(beanClass);
			for(Method m : list) {
				RequestMapping requestMapping = m.getAnnotation(RequestMapping.class);
				String [] urlForMethod = requestMapping.mapping();
				if(urlForMethod==null || urlForMethod.length==0){
					urlForMethod = new String[]{ urlDefPrifix.concat(m.getName())}; //如果没有定义，默认以方法名作映射路径
				}
				
				for(String u: urlForMethod){
					urlForMethodMap.put(u, m);
				}
				
			}
			
			//3.构建映射元数据对象
			List<UrlMappingDefinition> rs = process(e.getBeanClass(),urlForClass,urlForMethodMap);
			
			this.urlMappingDefs.addAll(rs);
		}
	}
	
	/**
	 * 处理一个控制器类的URLMapping元数据信息
	 * @param controller 控制器类
	 * @param urlForClass 类上的URL注解信息
	 * @param urlForMethod 方法上的URL注解信息
	 * @return
	 * @throws UrlMappingException
	 */
	@SuppressWarnings("unchecked")
	private List<UrlMappingDefinition> process(Class<?> controller,String [] urlForClass,Map<String,Method> urlForMethod) throws Exception{
		if(urlForClass==null ||urlForClass.length==0)throw new Exception("have not found url mapping for Controller or Action!");
		//if(urlForMethod == null || urlForMethod.isEmpty())throw new UrlMappingException("none method maping!");
		if(urlForMethod == null || urlForMethod.isEmpty()) return ArrayUtil.EMPTY_LIST;
		
		List<UrlMappingDefinition> rs = new ArrayList<UrlMappingDefinition>();
		for(String uc:urlForClass){
			for(String um : urlForMethod.keySet()) {
				UrlMappingMeta meta = new UrlMappingMeta();
				meta.setControllerClass(controller);
				meta.setMethod(urlForMethod.get(um));
				meta.setUrl(uc.concat(um));
				log.debug("URL mapping : {} , METHOD : {}",meta.getUrl(),meta.getMethod());
				rs.add(meta);
			}
		}
		
		return rs;
	}
	
	//一个类里的所有方法(含父类方法)缓存容器
	private static final  Map<Class<?>,List<Method>> REQUEST_MAPPING_METHOD_CACHE=new ConcurrentHashMap<Class<?>,List<Method>>();
	
	/**
	 * 获取映射的方法URL
	 * @param beanClass
	 * @return
	 */
	private List<Method> getUrlMappingMethod(Class<?> beanClass) {

		if (REQUEST_MAPPING_METHOD_CACHE.containsKey(beanClass)) {
			return REQUEST_MAPPING_METHOD_CACHE.get(beanClass);
		}

		List<Method> list = new ArrayList<Method>();

		for (Class<?> superClass = beanClass; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Method[] ms = superClass.getDeclaredMethods();
			if (ms != null && ms.length > 0) {
				for (Method m : ms) {
					if (m.isAnnotationPresent(RequestMapping.class)) {
						list.add(m);
					}
				}
			}

		}
		
		REQUEST_MAPPING_METHOD_CACHE.put(beanClass, list);
		return list;
	}
	
	@Override
	public UrlMappingDefinition find(String url) {
		for (UrlMappingDefinition e : urlMappingDefs) {
			if (url.equalsIgnoreCase(e.getUrl())) {
				return e;
			}
		}
		return null;
	}
	
	public static void main(String args[]) throws Exception{
		BeanMetaResolve beanMetaResolver = new AnnotationBeanMetaResolveImpl();
		List<BeanDefinition> defs = beanMetaResolver.resolve();
		
		AnnotationUrlMappingRoute route= new AnnotationUrlMappingRoute(defs);
		route.buildUrlMapping();
		
		UrlMappingDefinition m = route.find("/user/list");
		
		m = route.find("/user/m_list");
		
		m = route.find("/m_user/list");
		
		m = route.find("/m_user/m_list");
		
		m = route.find("/m_user/create");
		System.out.println(m);
		
		
		
	
	}
	
}
