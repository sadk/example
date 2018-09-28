package org.lsqt.components.context.impl.bean.meta;

import org.lsqt.components.context.bean.BeanDefinition;



public class AnnotationBeanDefinition implements BeanDefinition{
	private String id;
	private String name;
	private String version;
	private String fullClassName;
	private String shortClassName;
	private String scope;
	private boolean lazyInit;
	private String destroyMethod;
	private Class<?> beanClass;
	private String injectType;
	private String initMethod=""; //在注解的方式下定义bean，是没有这种注解的，请统一用生命周期接口
	private String type; //bean的定义类型，分静态类型(系统启动的时候，类扫描)和运行时类型
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getFullClassName() {
		return fullClassName;
	}
	public void setFullClassName(String fullClassName) {
		this.fullClassName = fullClassName;
	}
	public String getShortClassName() {
		return shortClassName;
	}
	public void setShortClassName(String shortClassName) {
		this.shortClassName = shortClassName;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public void setLazyInit(boolean lazyInit) {
		this.lazyInit = lazyInit;
	}
	public boolean getLazyInit() {
		return lazyInit;
	}
	public String getDestroyMethod() {
		return destroyMethod;
	}
	public void setDestroyMethod(String destroyMethod) {
		this.destroyMethod = destroyMethod;
	}
	public Class<?> getBeanClass() {
		return this.beanClass;
	}
	public void setBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
	}
	public String getInjectType() {
		return injectType;
	}
	public void setInjectType(String injectType) {
		this.injectType = injectType;
	}
	public String getInitMethod() {
		return initMethod;
	}
	public void setInitMethod(String initMethod) {
		this.initMethod = initMethod;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
