package org.lsqt.components.context.bean;

/**
 * 
 * @author Sky
 *
 */
public interface BeanFactory {
	
	<T> T getBean(Class<T> requiredType)  ;
 	
	<T> T getBean(String id) ;

}
