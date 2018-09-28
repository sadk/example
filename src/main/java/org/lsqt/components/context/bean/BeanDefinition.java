package org.lsqt.components.context.bean;

/**
 * <pre>
 * 配置在XML或注解的类，元信息定义 
 * </pre>
 * @author Sky
 *
 */
public interface BeanDefinition {
	/*
	 * 注入的类型是用来干啥的？A、B类同时实现接口1和接口2，那么C类里面注入接口1时，到底是注入A类的实例还是B类的实现呢？
	 * 我们通过注入类型来设置优先级 id> name+version > name(容器唯一) > 接口名 > 注入实现类
	 */
	String INJECT_TYPE_ID = "id_inject";
	String INJECT_TYPE_NAME_AND_VERSION = "name_and_version_inject";
	String INJECT_TYPE_NAME = "name_inject";
	String INJECT_TYPE_INTERFACE = "interface_inject";
	String INJECT_TYPE_CLASS_IMPL = "class_impl_inject";
	
	String TYPE_STATIC="static";
	String TYPE_RUNTIME="runtime";
	
	String getId();
	void setId(String id);
	
	String getName();
	void setName(String name);
	
	String getVersion();
	void setVersion(String version);
	
	String getFullClassName();
	void setFullClassName(String className);
	
	String getShortClassName();
	void setShortClassName(String shortName);
	
	String getScope();
	void setScope(String scope);
	
	boolean getLazyInit();
	void setLazyInit(boolean lazyInit);
	
	String getDestroyMethod();
	void setDestroyMethod(String method);
	
	Class<?> getBeanClass();
	void setBeanClass(Class<?> defClass);
	
	String getInjectType();
	void setInjectType(String injectType);
	
	/**
	 *  注：执行顺序是构造函数-〉setter方法注入-〉init-method->调用的方法-〉destroy-method
	 * @return
	 */
	String getInitMethod();
	void setInitMethod(String initMethod);

	/**
	 * bean元信息类型，分为静态类型和运行时类型
	 * @return
	 */
	String getType();
	void setType(String type);
	
}
