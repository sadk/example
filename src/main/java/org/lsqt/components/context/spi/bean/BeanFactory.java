package org.lsqt.components.context.spi.bean;

import java.util.List;



/**
 * 
 * @author Sky
 *
 */
public interface BeanFactory {
	
	<T> T getBean(Class<T> requiredType) throws BeanException;
	
 	
	<T> T getBean(String id) throws BeanException;
	/*
	<T> T getBean(String name,String version) throws BeanException;
	
	<T> List<T> getBeans(String name) throws BeanException ;
	*/
	
}
