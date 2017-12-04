package org.lsqt.components.context.impl.bean.meta;

import java.util.ArrayList;
import java.util.List;

import org.lsqt.components.context.spi.bean.BeanDefinition;
import org.lsqt.components.context.spi.bean.XmlConfig.AttrFactoryConfig;
import org.lsqt.components.context.spi.bean.XmlConfig.Propery;
import org.lsqt.components.context.spi.bean.XmlConfig.PropertyConfig;

/**
 * 用以描述spirng.xml配置bean的元信息
 * @author yuanmm
 *
 */
public class XmlBeanDefinition implements BeanDefinition, AttrFactoryConfig, PropertyConfig {
	// 基本元信息
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
	private String initMethod="";  
	private String type; //bean的定义类型，分静态类型(系统启动的时候，类扫描)和运行时类型

	//依赖元信息
	private String factoryBeanId ; //引用的是Bean id
	private String facotryMethodName; 
	
	//属性元信息
	private List<Propery> propertyConfigs = new ArrayList<>();
	
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

	public String getFactoryBeanId() {
		return this.factoryBeanId;
	}

	public void setFacotryBeanId(String id) {
		this.factoryBeanId = id;
	}

	public String getFacotryMethodName() {
		return this.facotryMethodName;
	}

	public void setFactoryMethodName(String name) {
		this.facotryMethodName = name;
	}

	public List<Propery> getPropertyList() {

		return propertyConfigs;
	}

	public void setProperty(List<Propery> items) {
		this.propertyConfigs = items;
	}
 
	public static class ProperyItem implements org.lsqt.components.context.spi.bean.XmlConfig.Propery {
		public String name;
		public String ref;
		public String value;
		public List<String> list;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getRef() {
			return ref;
		}
		public void setRef(String ref) {
			this.ref = ref;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public List<String> getList() {
			return list;
		}
		public void setList(List<String> list) {
			this.list = list;
		}
	}
}

