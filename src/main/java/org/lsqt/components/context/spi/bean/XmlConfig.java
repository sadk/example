package org.lsqt.components.context.spi.bean;

import java.util.List;

public interface XmlConfig {
	
	/**
	 * 一个xmlBean的工厂实例配置
	 * @author yuanmm
	 *
	 */
	interface AttrFactoryConfig {
		/**
		 * 当前bean的实例化，可由另一个工厂里创建而来
		 */
		String getFactoryBeanId(); // 引用的是Bean id

		void setFacotryBeanId(String id);

		String getFacotryMethodName();

		void setFactoryMethodName(String name);
	}
	
	/**
	 * 一个xmlBean的属性配置
	 * @author yuanmm
	 *
	 */
	interface PropertyConfig {
		List<Propery> getPropertyList();

		void setProperty(List<Propery> items);
	}
	 
	/**
	 * 一个xmlBean的某个属性配置
	 * @author yuanmm
	 *
	 */
	interface Propery{
		String getName() ;
		void setName(String name);
		
		String getRef();
		void setRef(String ref) ;
		 
		String getValue() ;
		void setValue(String value) ;
		  
		List<String> getList();
		void setList(List<String> list) ;
	}
	
	interface ConstructorArgConfig {
		List<BeanDefinition>  getBeanArgList() ;
	}
}
