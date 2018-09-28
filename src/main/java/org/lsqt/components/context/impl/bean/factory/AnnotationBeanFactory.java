package org.lsqt.components.context.impl.bean.factory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.lsqt.components.context.annotation.Component;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Dao;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Scope;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.context.impl.bean.meta.AnnotationBeanDefinition;
import org.lsqt.components.context.impl.bean.meta.BeanWrapper;
import org.lsqt.components.context.impl.bean.resolve.AnnotationBeanMetaResolveImpl;
import org.lsqt.components.context.impl.util.CacheReflect;
import org.lsqt.components.context.bean.BeanDefinition;
import org.lsqt.components.context.bean.BeanException;
import org.lsqt.components.context.bean.BeanFactory;
import org.lsqt.components.context.bean.BeanMetaResolve;
import org.lsqt.components.db.Db;
import org.lsqt.components.mvc.impl.UrlMappingDefinition;
import org.lsqt.components.mvc.impl.AnnotationUrlMappingRoute;
import org.lsqt.components.util.bean.BeanUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.components.util.reflect.ClassLoaderUtil;
import org.lsqt.sys.controller.ApplicationController;
import org.lsqt.sys.service.ApplicationService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * <pre>
 * 一、整个容器的实现机理
 * 解析编译后的.class类文件和jar包获取类名信息，（通过class.forName加载到JVM）
 * 解析只含有controller、service、dao、component的注解类纳入到容器，有些类元信息是用spring.xml配置的也可纳入，通过解析器注册的方式进行纳入，
 * 所以有了AnnotationBeanFactory、AnnotationBeanDefinition、AnnotationBeanMetaResolver 
 * 和XmlBeanFacotry、XmlBeanDefinition、XmlBeanMetaResolver.
 * 
 * 其次，BeanFactoryManager用来注册 AnnotationBeanFactory、XmlBeanFactory
 * BeanMetaResolveManager用来注册 AnnotationBeanMetaResolver、XmlBeanMetaResolver
 * 
 * 
 * 二、Bean的注入
 * 域(或setter方法)的注入的方式有两大类，按接口注入和非接口
 * 按接口注入有，
 *     1.直接引用id; 
 *     2.直接name+version引用; 
 *     3.按name不唯一，指定impl=xxxServiceImpl.Class引用
 *     4.直接引用name(唯一) 
 *     5.不写name,直接写实现类
 *     6.注入一个接口，啥也不写，这个接口只有一个实现类
 *     
 * 在类扫描的时候，三个service类，实现同一个接口，没有指定id,也没有指定name+version, 也没有指定impl=xxxxImpl.class,
 * 在某个域注入这个接口时(因为不知道是注入哪个实现)，就会有冲突，则抛出异常，提示需指定一个ID值、或者name+version值、或者name(唯一)、
 * 或者指定impl=xxxxImpl.class、或者指定name=xxx,impl=xxxImpl.class
 * 
 * 按非接口注入，
 *     则直接实例化注入
 * </pre>
 */

public class AnnotationBeanFactory implements BeanFactory{
	
	private final List<BeanWrapper> singletonObjectList = new ArrayList<BeanWrapper>();
	
	private final List<BeanDefinition> beanDefinitionList = new ArrayList<BeanDefinition>();
	
	private final int cycleDependenceDeep = 10; // 循环依赖的深度
	
	public int order() {
		return 0;
	}

	private final List<BeanFactory> register = new ArrayList<>();

	public void regist(BeanFactory beanFactory) {
		register.add(beanFactory);
	}
	

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 容器启动 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	/**
	 * <pre>
	 * 容器实例化的整个流程,方法1.
	 * 构建IOC容器bean的元信息
	 * </pre>
	 * @return
	 */
	public void buildBeanMeta() {
		BeanMetaResolve r = new AnnotationBeanMetaResolveImpl();
		this.beanDefinitionList.addAll(r.resolve());
	}
	
	/**
	 * <pre>
	 * 容器实例化的整个流程,方法2.
	 * 实例化IOC容器管理的单例bean
	 * </pre>
	 */
	public void buildBean(){
		if(beanDefinitionList == null || beanDefinitionList.size()==0) return ;
		
		for(BeanDefinition d:beanDefinitionList) {
			try{
				BeanWrapper object = new BeanWrapper(d,d.getBeanClass().newInstance(),true);
				
				singletonObjectList.add(object);
			}catch(Exception ex){
				throw new BeanException(ex);
			}
		}
	}
	
	/**
	 * 容器实例化的整个流程,方法3.
	 * 构建IOC容器管理的单例bean属性
	 */
	public void buildBeanDependency() {
		final Map<Field, Integer> fieldInfo = new LinkedHashMap<Field, Integer>();
		final Set<BeanWrapper> singletonCompleted = new LinkedHashSet<BeanWrapper>(); // 用于标记容器内已经依赖关系成立的单例bean
		for(BeanWrapper obj:singletonObjectList) {
			
			//处理域注解
			List<Field> fields = CacheReflect.getBeanField(obj.getBeanDefinition().getBeanClass());
			for(Field e: fields){
				processInjectForField(obj,e,fieldInfo,singletonCompleted);
			}
			
			/*处理方法注解
			List<Method> methods = CacheReflect.getBeanSetterMethod(obj.getBeanDefinition().getBeanClass());
			for(Method m: methods){
				//processInjectForMethod(m,obj);
			}
			*/
			
			obj.setIsWeakBean(false);
			
			
			//处理生命周期操作
		}
		
		for (BeanWrapper e: singletonCompleted) {
			if(e!=null)
			e.setIsWeakBean(false);
		}
		
		List<BeanWrapper> rstList = new ArrayList<BeanWrapper>();
		for (BeanWrapper e: singletonObjectList) {
			//只处理容器单例级联（循环)引用
			if(e.getBeanDefinition()!=null 
					&& (Scope.SINGLETON.equals(e.getBeanDefinition().getScope()) || Scope.APPLICATION.equals(e.getBeanDefinition().getScope()))) {
				Object bean = getBean(e.getBeanDefinition().getBeanClass());
				rstList.add(new BeanWrapper(e.getBeanDefinition(), bean, false));
			}
		}
		singletonObjectList.clear();
		singletonObjectList.addAll(rstList);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> requiredType) throws BeanException {
		if(requiredType.isArray()) return null;
		if(requiredType.isAnnotation()) return null;
		if(requiredType.isEnum()) return null;
		
		T bean = null;
		BeanDefinition def = null;
		
		if(requiredType.isInterface()) {
			def = getBeanDefinitionByInterface(requiredType);
			
		} else {
			def = getBeanDefinitionByImpl(requiredType, true);
			
		}
		
		if (def == null) { // 容器内没有找到bean的定义，支持它容器找，比如：spring
			if (!this.register.isEmpty()) {
				for (BeanFactory app : this.register) {
					T bn = app.getBean(requiredType);
					if (bn != null) {
						return bn;
					}
				}
			}
		}
		
		if(def == null) return null;
		try {
			requiredType = (Class<T>) def.getBeanClass();
			bean =  (T) requiredType.newInstance();
		} catch ( Exception e) {
			e.printStackTrace();
			return null;
		} 
		
		BeanWrapper beanWapper = new BeanWrapper(def, bean,false) ;
		final Map<Field, Integer> fieldInfo = new ConcurrentHashMap<Field, Integer>();
		List<Field> fields = CacheReflect.getBeanField(requiredType);
		for(Field e: fields){
			
			processInjectForField(beanWapper,e,fieldInfo,new LinkedHashSet<BeanWrapper>(singletonObjectList));
		}
		return bean;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getBean(String beanId) throws BeanException {
		if(StringUtil.isBlank(beanId)) return null;
		
		T bean = null;
		BeanDefinition def = null;
		
		def = getBeanDefinitionById(beanId);
		if (def == null) { // 容器内没有找到bean的定义，支持它容器找，比如：spring
			if (!this.register.isEmpty()) {
				for (BeanFactory app : this.register) {
					T bn = app.getBean(beanId);
					if (bn != null) {
						return bn;
					}
				}
			}
		}

		 
		try{
			Class<T> requiredType = (Class<T>) ClassLoaderUtil.classForName(def.getFullClassName());
			bean = requiredType.newInstance();
		
			BeanWrapper beanWapper = new BeanWrapper(def, bean,true) ;
			final Map<Field, Integer> fieldInfo = new ConcurrentHashMap<Field, Integer>();
			List<Field> fields = CacheReflect.getBeanField(requiredType);
			for(Field e: fields){
				processInjectForField(beanWapper,e,fieldInfo,new LinkedHashSet<BeanWrapper>(singletonObjectList));
			}
			
		}catch(Exception ex) {
			throw new BeanException(ex);
		}
		return bean;
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 容器内的bean实例化 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private Object prepareValueFromOtherIOC(Field field){
		Object value = null;
		if(!this.register.isEmpty()) {
			//Collections.sort(this.register);
			for(BeanFactory f: this.register) {
				if( f instanceof SpringBeanFactoryAdapter) {
					SpringBeanFactoryAdapter adapt = (SpringBeanFactoryAdapter) f;
					Map<String, ?>  map = adapt.getApplication().getBeansOfType(field.getType());
					if(!map.values().isEmpty()) {
						value = f.getBean(field.getType());
						if(value!=null) return value;
					}
				}
			}
		}
		return value;
	}
	
	/**
	 * 不管是单例的还是非单例的bean，都要递归处理域的值
	 * @param currObject
	 * @param field
	 * @param fieldInfo 
	 * @param completeBean
	 */
	private void processInjectForField(BeanWrapper currObject,Field field,final Map<Field, Integer> fieldInfo,final Set<BeanWrapper> completeBean) {
		if (fieldInfo.get(field) == null) {
			fieldInfo.put(field, 0);
		}
		
		if (fieldInfo.get(field) > cycleDependenceDeep) return ;		
		
		if (field == null) return;
		
		if (!field.isAnnotationPresent(Inject.class)) return ;
		
		if (currObject == currObject.getOriginalBean()) return ;

		
		List<Field> fds = CacheReflect.getBeanField(currObject.getOriginalBean().getClass());
		
		if(fds == null || fds.size() == 0) return ;
		
		Object fieldValue = null;
		BeanDefinition rootDef = null;
		try{
			// 处理当前域的注入
			rootDef = getBeanDefinitionByField(field);
			BeanWrapper value = prepareValue(rootDef,completeBean);
			if(value != null) {
				fieldValue = value.getOriginalBean();
			}else {
				fieldValue = prepareValueFromOtherIOC(field);
			}
			
			if(fieldValue!=null) {
				BeanUtil.forceSetProperty(field,currObject.getOriginalBean(), fieldValue);
			}
			
			completeBean.add(value);
		}catch(Exception ex){
			throw new BeanException(ex);
		}
		fieldInfo.put(field, fieldInfo.get(field)+1);
		if (fieldValue == null) return ;
		
		
		/**/
		//级联处理子对象(注意处理循环依赖!!!)
		if (fieldValue != null) {
			if (rootDef!=null) { // 定义在spring的xml里的bean在自己的IOC容器框架中是没有bean的元信息定义的
				List<Field> list = CacheReflect.getBeanField(rootDef.getBeanClass());
				if (list == null || list.size() == 0) return;
				
				for (Field e : list) {
					rootDef = getBeanDefinitionByField(e); 
					if(rootDef == null) {
						Object tmp = prepareValueFromOtherIOC(e);
						try {
							if(tmp!=null) {
								BeanUtil.forceSetProperty(e,fieldValue, tmp);
							}
						} catch (Exception ex) {
							throw new BeanException(ex);
						}
					}
					
					if(fieldValue ==null) continue;
					
					processInjectForField(new BeanWrapper(rootDef, fieldValue,false),e,fieldInfo,completeBean);
				}
			}
		}
		
	}
	
	
	private BeanWrapper prepareValue(BeanDefinition def,final Set<BeanWrapper> completeBean) {
		if(def == null) return null;
		
		BeanWrapper value = null;
		
		if (isScopeFor(def.getBeanClass(), Scope.APPLICATION) || isScopeFor(def.getBeanClass(), Scope.SINGLETON)) {
			//优先从已经完成依赖关系的单例bean里查找bean
			if(completeBean!=null && completeBean.size()>0) {
				for (BeanWrapper t: completeBean) {
					if(t.getBeanDefinition().getBeanClass() == def.getBeanClass()) {
						value = t ;
						break;
					}
				}
			} 
			
			if(value == null) {
				for (BeanWrapper t: singletonObjectList){
					if (t.getBeanDefinition().getBeanClass() == def.getBeanClass()) {
						value = t;
						break;
					}
				}
			}
			
			
			
		} else if(isScopeFor(def.getBeanClass(), Scope.PROTOTYPE)) {
			try {
				BeanWrapper object = new BeanWrapper(def, def.getBeanClass().newInstance(),true);
				value = object ;
			} catch (Exception ex){
				ex.printStackTrace();
				throw new BeanException(ex);
			}
			
		} else if(isScopeFor(def.getBeanClass(), Scope.SESSION)) {
			
		}
		
		return value;
	}
	
	private BeanDefinition getBeanDefinitionByField(Field field) {
		Class<?> type = field.getType();
		Inject inject = field.getAnnotation(Inject.class);
		if(inject == null) return null;
		
		 /* 
		  * 按接口注入：
		  * 1.直接引用id; 
		  * 2.直接name+version引用; 
		  * 3.按name不唯一，指定impl=xxxServiceImpl.Class引用 
		  * 4.直接引用name(唯一)  
		  * 5.不写name,直接写实现类impl=zzzServiceImpl.class
		  * 6.注入一个接口，啥也不写，这个接口只有一个实现类
		  */
		 String id		= inject.value();
		 String name	= inject.name();
		 String version = inject.version();
		 Class<?> impl 	= inject.impl();
		 
		if(StringUtil.isNotBlank(id) && StringUtil.isBlank(name,version) && impl == Object.class){ // 1.直接引用id; 
			return getBeanDefinitionById(id);
			
		}else if(StringUtil.isBlank(id) && StringUtil.isNotBlank(name,version) && impl == Object.class){ // 2.直接name+version引用; 
			return getBeanDefinitionByNameAndVersion(name, version);
			
		}else if(StringUtil.isBlank(id) && StringUtil.isNotBlank(name) && impl != Object.class){ // 3.按name不唯一，指定impl=xxxServiceImpl.Class引用 
			return getBeanDefinitionByNameAndImpl(name,impl);
			
		}else if(StringUtil.isBlank(id,version) && StringUtil.isNotBlank(name) && impl == Object.class){ // 4.直接引用name(唯一)  
			return getBeanDefinitionByUniqueName(name);
			
		}else if(StringUtil.isBlank(id,name,version) && impl != Object.class){ // 5.不写name,直接写实现类
			return getBeanDefinitionByImpl(impl,true);
			
		} if(StringUtil.isBlank(id,name,version) && impl == Object.class && type.isInterface()){ // 6.注入一个接口，啥也不写，这个接口只有一个实现类
			return getBeanDefinitionByInterface(type); // 返回运行时的bean“定义”
			
		}else {
			return getBeanDefinitionByImpl(type,false);
		}
	}
	

	
	
	
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 辅助方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	private boolean isScopeFor(Class<?> beanClass,String scope){
		for (BeanDefinition e : this.beanDefinitionList) {
			if (e.getBeanClass().getName().equals(beanClass.getName())) {
				return scope.equals(e.getScope());
			}
		}
		return false;
	}
	
	private BeanDefinition getBeanDefinitionById(String beanId){
		for(BeanDefinition e : this.beanDefinitionList) {
			if(BeanDefinition.INJECT_TYPE_ID.equals(e.getInjectType()) && e.getId().equals(beanId)) {
				return e;
			}
		}
		return null;
	}

	private BeanDefinition getBeanDefinitionByNameAndVersion(String name,String version) {
		for(BeanDefinition e : this.beanDefinitionList) {
			if(BeanDefinition.INJECT_TYPE_NAME_AND_VERSION.equals(e.getInjectType())
					&& (e.getName().equals(name) && e.getVersion().equals(version))){
				return e;
			}
		}
		return null;
	}
	
	private BeanDefinition getBeanDefinitionByNameAndImpl(String name,Class<?> implClass){
		for(BeanDefinition e : this.beanDefinitionList) {
			if(BeanDefinition.INJECT_TYPE_NAME.equals(e.getInjectType()) 
					&& e.getName().equals(name) && e.getBeanClass() == implClass) {
				return e;
			}
		}
		return null;
	}
	
	
	private BeanDefinition getBeanDefinitionByUniqueName(String name){
		int cntNames=0;
		BeanDefinition rs = null;
		for(BeanDefinition e : this.beanDefinitionList) {
			if(BeanDefinition.INJECT_TYPE_NAME.equals(e.getInjectType()) && e.getName().equals(name)){
				cntNames ++ ;
				rs = e;
			}
		}
		if (cntNames != 1) {
			throw new BeanException("have not found bean definition of name '"+name+"'");
		}
		
		return rs;
	}
	
	private BeanDefinition getBeanDefinitionByImpl(Class<?> implClass,boolean isIgnoreInjectType) {
		if(implClass.isInterface()) return null;
		
		for(BeanDefinition e : this.beanDefinitionList) {
			if(isIgnoreInjectType == false 
					&&  BeanDefinition.INJECT_TYPE_CLASS_IMPL.equals(e.getInjectType()) 
					&& e.getBeanClass() == implClass) {
				return e;
				
			}else if(isIgnoreInjectType && e.getBeanClass() == implClass){
				return e;
			}
		}
		return null;
	}
	
	/**
	 * 获取接口的实现类定义
	 * @param interfaceType 给定的接口
	 * @return
	 */
	private BeanDefinition getBeanDefinitionByInterface(Class<?> interfaceType) {
		if(!interfaceType.isInterface()) return null;
		/*
		 * 如果引用或定义的是接口，找接口的实现类，（接口的实现类可能有多个）
		 * 如果只找到一个实现类，直接用该实现类inject进去或实例化;
		 * 否则超出的两个实现类注入有冲突，抛出异常!
		 */
		BeanDefinition def = null;
		List<BeanDefinition> interfaceImpls = new ArrayList<BeanDefinition>();
		for(BeanDefinition e : this.beanDefinitionList){
			if(interfaceType.isAssignableFrom(e.getBeanClass())){
				interfaceImpls.add(e);
			}
		}
		
		/*
		 * 一个接口的多个实现类异常的抛出，需要过滤这几种情况的接口实现类
		 * 1.直接声明id; 
		 * 2.直接name+version引用;
		 * 3.按name不唯一，指定impl=xxxServiceImpl.Class引用 
		 * 4.直接声明name(唯一)
		 * 5.不写name,直接写实现类
		 */
		List<BeanDefinition> multilImpl = new ArrayList<BeanDefinition>();
		for (BeanDefinition t : interfaceImpls) {
			if (isNotAssignIdNameVersion(t)) {
				multilImpl.add(t);
			}
		}
		if (multilImpl.size() == 1) { //一个接口只有一个实现类
			def = multilImpl.get(0);
		} else if (multilImpl.size() > 1) {
			throw new BeanException("Interface to achieve the type of bean is greater than 1, there is conflict: ["+interfaceType+"], please check it~!");
		} else if (multilImpl.size() == 0 && interfaceImpls.size() > 0) { //一个接口有多个实现类，这些实现类都有标注id或name+version
			//粗略探测一个接口的实现类（因为这个接口有多个实现类),探测方法为：如果给定的接口名字和bean定义进的id或name名相等，则返回beanDefinition
			for (BeanDefinition e: interfaceImpls) {
				if (interfaceType.getSimpleName().equalsIgnoreCase(e.getId())
						|| interfaceType.getSimpleName().equalsIgnoreCase(e.getName())) {
					def = e;
					break;
				}
			}
		}
		
		if(def == null) return null;
		
		//不改变原始元信息定义对象，手工克隆
		BeanDefinition rs = new AnnotationBeanDefinition();
		rs.setBeanClass(def.getBeanClass());
		rs.setDestroyMethod(def.getDestroyMethod());
		rs.setFullClassName(def.getFullClassName());
		rs.setId(def.getId());
		rs.setInitMethod(def.getInitMethod());
		rs.setInjectType(BeanDefinition.INJECT_TYPE_INTERFACE);
		rs.setLazyInit(def.getLazyInit());
		rs.setName(def.getName());
		rs.setScope(def.getScope());
		rs.setShortClassName(def.getShortClassName());
		rs.setVersion(def.getVersion());
		rs.setType(BeanDefinition.TYPE_RUNTIME);
		return rs;
	}
	
	private boolean isNotAssignIdNameVersion(BeanDefinition def) { // 判断一个接口是否有多个实现类，只针对一个域的注入时
		boolean isNotAssign = true;
		
		Class<?> target = def.getBeanClass();
		Service service = target.getAnnotation(Service.class);
		Dao dao = target.getAnnotation(Dao.class);
		Controller controller = target.getAnnotation(Controller.class);
		Component component = target.getAnnotation(Component.class);
		
		String id = "" , name = "" , version = "";
		if (dao != null) {
			id = dao.value();
			name = dao.name();
			version = dao.version();
			
		} else if (service != null) {
			id = service.value();
			name = service.name();
			version = service.version();
			
		} else if (component != null) {
			id = component.value();
			name = component.name();
			version = component.version();
			
		} else if (controller != null) {
			id = controller.value();
			name = controller.name();
			version = controller.version();
			
		} 
		
		/*
		 * 1.直接声明id; 
		 * 2.直接name+version引用;
		 * 3.按name不唯一，指定impl=xxxServiceImpl.Class引用 
		 * 4.按name唯一
		 */
		if (BeanDefinition.INJECT_TYPE_ID.equals(def.getInjectType())) {
			if (StringUtil.isNotBlank(id)) return false;
			
		} else if (BeanDefinition.INJECT_TYPE_NAME_AND_VERSION.equals(def.getInjectType())) {
			if(StringUtil.isNotBlank(name,version)) return false;
			
		} else if (BeanDefinition.INJECT_TYPE_NAME.equals(def.getInjectType())) {
			if(StringUtil.isNotBlank(name)) return false;
			
		}
		
		return isNotAssign;
	}
	
	/**
	 * 获取容器内所有bean元信息
	 * @return
	 */
	public List<BeanDefinition> getBeanDefinitions(){
		return this.beanDefinitionList;
	}
	
	 



}
