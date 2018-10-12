package org.lsqt.components.mvc;

import java.util.Set;

import org.lsqt.components.context.bean.BeanFactory;
import org.lsqt.components.mvc.impl.UrlMappingRoute;

/**
 * 框架常用配置组件
 * @author mm
 *
 */
public interface Configuration {
	
	/**
	 * 配置是否初使化完毕 
	 * @return 容器正在启动中返回false
	 */
	boolean isInitialized() ;
	
	/** 是否启用登陆   */
	boolean isEnableLogin();
	
	/** 获取允许静态访问的地址**/
	Set<String> getURIStatic();
	
	/** 获取允许脱离容器访问的地址（代后续Servlet或Filter使用）**/
	Set<String> getURIEscape();
	
	/** 获取匿名访问的地址 **/
	Set<String> getURIAnonymous();
	
	/**
	 * 获取MVC路由器组件
	 * @return
	 */
	UrlMappingRoute getUrlMappingRoute();
	
	/**
	 * 获取框架IOC容器组件
	 * @return
	 */
	BeanFactory getBeanFacotry() ;
	
	
}

