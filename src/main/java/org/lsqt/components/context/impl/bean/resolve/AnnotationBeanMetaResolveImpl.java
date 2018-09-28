package org.lsqt.components.context.impl.bean.resolve;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.lsqt.components.context.annotation.Component;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Dao;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.context.impl.bean.meta.AnnotationBeanDefinition;
import org.lsqt.components.context.bean.BeanDefinition;
import org.lsqt.components.context.bean.BeanException;
import org.lsqt.components.context.bean.BeanMetaResolve;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.components.util.reflect.ClassLoaderUtil;
import org.lsqt.components.util.reflect.ScanerUtil;

/**
 * 注解Bean元信息解析实现
 * @author yuanmm
 *
 */
public class AnnotationBeanMetaResolveImpl implements BeanMetaResolve{
	private String []  basePackage = null;
	
	@Override
	public String[] getScanBase() {
		
		return null;
	}

	@Override
	public void setScanBase(String... base) {
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BeanDefinition> resolve() throws BeanException {
		List<BeanDefinition> rs = new ArrayList<BeanDefinition>();

		try {
			List<String> classNames = ScanerUtil.scan(basePackage);
			if (classNames == null || classNames.size() == 0)
				return ArrayUtil.EMPTY_LIST;

			List<Class<?>> beanClasses = new LinkedList<Class<?>>();

			for (String className : classNames) {
				try {
					Class<?> target = ClassLoaderUtil.classForName(className);
					beanClasses.add(target);
				} catch (Exception e) {
					System.out.println("load class error: " + className);
				}
			}

			for (Class<?> e : beanClasses) {
				if(isCanBeanClass(e)){
					rs.addAll(resolve(e));
				}
			}
		} catch (Exception ex) {
			throw new BeanException(ex);
		}
		return rs;
	}

	/**
	 * <pre>
	 * 解析一个bean所对应的bean元信息
	 * 理论上来说正常情况下，一个bean应该只对应一个beanDefinition信息，
	 * 但用户可在一个类上写两个组件注解,用户可以在UserService类上标注component和controller
	 * </pre>
	 * @param beanClass
	 * @return
	 * @throws BeanMetaResolveException
	 */
	@SuppressWarnings("unchecked")
	public List<BeanDefinition> resolve(Class<?> beanClass) throws BeanException {
		if(isCanBeanClass(beanClass) == false) return ArrayUtil.EMPTY_LIST;
		
		List<BeanDefinition> rs = new LinkedList<BeanDefinition>();
		BeanDefinition defComp = resolveComponent(beanClass);
		BeanDefinition defDao = resolveDao(beanClass);
		BeanDefinition defService = resolveService(beanClass);
		BeanDefinition defController = resolveController(beanClass);
		
		
		if(defComp != null) {
			defComp.setType(BeanDefinition.TYPE_STATIC);
			rs.add(defComp);
		}
		
		if(defDao != null){
			defDao.setType(BeanDefinition.TYPE_STATIC);
			rs.add(defDao);
		}
		
		if(defService != null) {
			defService.setType(BeanDefinition.TYPE_STATIC);
			rs.add(defService);
		}
		
		if(defController !=null) {
			defController.setType(BeanDefinition.TYPE_STATIC);
			rs.add(defController);
		}
		
		return rs;
	}
	
	private boolean isCanBeanClass(Class<?> target){
		//基础类型判断
		if(target == null ) return false;
		if(target.isAnnotation()) return false;
		if(target.isArray()) return false;
		if(target.isEnum()) return false;
		if(target.isInterface()) return false;
		if(target.isPrimitive()) return false;
		//异常类不纳入
		if(Exception.class.isAssignableFrom(target)) return false;
		
		//没有getter,setter域的类不纳入(可以只有方法的类不作为容器的bean是错误的)
		//if(target.getFields()==null || target.getFields().length==0) return false;
		return true;
	}
	
	private boolean isComponent(Class<?> target) {
		return target.getAnnotation(Component.class) != null;
	}

	private boolean isDao(Class<?> target) {
		return target.getAnnotation(Dao.class) != null;
	}

	private boolean isService(Class<?> target) {
		return target.getAnnotation(Service.class) != null;
	}

	private boolean isController(Class<?> target) {
		return target.getAnnotation(Controller.class) != null;
	}
	
	private String resolveBeanId(Class<?> target,String originalID){
		if(StringUtil.isNotBlank(originalID)){ 
			return originalID;
		} else {
			String idPrefix = target.getSimpleName().substring(0, 1).toLowerCase();
			String part = target.getSimpleName().substring(1,target.getSimpleName().length());
			return (idPrefix.concat(part));
		}
	}
	
	private String resolveCanInjectType(Class<?> target){
		String id = null, name = null, version = null;
		boolean isInterface = false;
		
		if(isComponent(target)){
			Component component = target.getAnnotation(Component.class);
			
			id = component.value();
			name = component.name();
			version = component.version();
		}else if(isDao(target)){
			Dao component = target.getAnnotation(Dao.class);
			
			id = component.value();
			name = component.name();
			version = component.version();
		}else if(isService(target)){
			Service component = target.getAnnotation(Service.class);
			
			id = component.value();
			name = component.name();
			version = component.version();
		}else if(isController(target)){
			Controller component = target.getAnnotation(Controller.class);
			
			id = component.value();
			name = component.name();
			version = component.version();
		}
		
		if(target.getInterfaces()!=null && target.getInterfaces().length>0){
			isInterface = true;
		}
		return resolveCanInjectTypeHelp(id,name, version,isInterface);
	}
	
	private String resolveCanInjectTypeHelp(String id,String name,String version,boolean isInterface){
		if (StringUtil.isNotBlank(id) && StringUtil.isBlank(name,version)) {
			return BeanDefinition.INJECT_TYPE_ID;
			
		} else if (StringUtil.isBlank(id) && StringUtil.isNotBlank(name,version)) {
			return BeanDefinition.INJECT_TYPE_NAME_AND_VERSION;
			
		} else if(StringUtil.isBlank(id,version) && StringUtil.isNotBlank(name) ) {
			return BeanDefinition.INJECT_TYPE_NAME;
			
		} else if (StringUtil.isBlank(id,name,version) && isInterface) {
			return BeanDefinition.INJECT_TYPE_INTERFACE;
			
		}else{
			return BeanDefinition.INJECT_TYPE_CLASS_IMPL;
		}
	}
	
	public BeanDefinition resolveComponent(Class<?> target){
		if(isComponent(target) == false) return null;

		Component component = target.getAnnotation(Component.class);
		BeanDefinition definition = new AnnotationBeanDefinition();
		
		definition.setId(resolveBeanId(target, component.value()));//beanId
		
		definition.setName(StringUtil.isBlank(component.name()) ? definition.getId() : component.name()); //bean名称
		definition.setVersion(component.version());
		
		definition.setFullClassName(target.getName()); //bean的类名
		definition.setLazyInit(component.lazy()); //默认懒加载
		
		definition.setScope(component.scope()); 
		definition.setShortClassName(target.getSimpleName());
		
		definition.setBeanClass(target);
		definition.setDestroyMethod(component.destroyMethod());
		
		definition.setInjectType(resolveCanInjectType(target));
		return definition;
	}
	
	public  BeanDefinition resolveDao(Class<?> target){
		if(isDao(target) == false) return null;
		
		Dao dao = target.getAnnotation(Dao.class);
		BeanDefinition definition = new AnnotationBeanDefinition();
		
		definition.setId(resolveBeanId(target, dao.value()));
		definition.setName(StringUtil.isBlank(dao.name()) ? definition.getId() : dao.name());
		definition.setFullClassName(target.getName()); 
		definition.setLazyInit(dao.lazy()); 
		
		definition.setScope(dao.scope()); 
		definition.setShortClassName(target.getSimpleName());
		definition.setVersion(dao.version());
		definition.setBeanClass(target);
		definition.setDestroyMethod(dao.destroyMethod());
		
		definition.setInjectType(resolveCanInjectType(target));
		return definition ;
	}
	
	public  BeanDefinition resolveService(Class<?> target){
		if(isService(target) == false) return null;
		
		Service service = target.getAnnotation(Service.class);
		BeanDefinition definition = new AnnotationBeanDefinition();
		 
		definition.setId(resolveBeanId(target, service.value()));
		definition.setName(StringUtil.isBlank(service.name()) ? definition.getId() : service.name());
		definition.setFullClassName(target.getName());  
		definition.setLazyInit(service.lazy());  
		
		definition.setScope(service.scope()); 
		definition.setShortClassName(target.getSimpleName());
		definition.setVersion(service.version());
		definition.setBeanClass(target);
		definition.setDestroyMethod(service.destroyMethod());
		definition.setInjectType(resolveCanInjectType(target));
		return definition ;
	}
	
	
	public  BeanDefinition resolveController(Class<?> target){
		if(isController(target) == false) return null;
		
		Controller controller =  target.getAnnotation(Controller.class);
		BeanDefinition definition = new AnnotationBeanDefinition();
		 
		definition.setId(resolveBeanId(target, controller.value()));
		definition.setName(StringUtil.isBlank(controller.name()) ? definition.getId() : controller.name());
		definition.setFullClassName(target.getName());  
		definition.setLazyInit(controller.lazy());  
		
		definition.setScope(controller.scope()); 
		definition.setShortClassName(target.getSimpleName());
		definition.setVersion(controller.version());
		definition.setBeanClass(target);
		definition.setDestroyMethod(controller.destroyMethod());
		
		definition.setInjectType(resolveCanInjectType(target));
		return definition ;
	}
	
	
	
	public static void main(String args[]){
		BeanMetaResolve impl = new AnnotationBeanMetaResolveImpl();
		List<BeanDefinition> defs = impl.resolve();
		for(BeanDefinition def : defs) {
			System.out.println("id     :"+def.getId());
			System.out.println("name   :"+def.getName());
			System.out.println("scope  :"+def.getScope());
			System.out.println("version:"+def.getVersion());
			
			System.out.println("fullClassName :"+def.getFullClassName());
			System.out.println("shortClassName:"+def.getShortClassName());
			System.out.println("---------------------------------------");
		}
		
	}


	
}
