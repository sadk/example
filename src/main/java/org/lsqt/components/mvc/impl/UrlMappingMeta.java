package org.lsqt.components.mvc.impl;

import java.lang.reflect.Method;

import org.lsqt.components.mvc.impl.UrlMappingDefinition;

public class UrlMappingMeta implements UrlMappingDefinition{
	
	private String url;
	private Class<?> controllerClass;
	private Method method;
	
	public void setUrl(String url) {
		this.url = url;
	}
	public void setControllerClass(Class<?> controllerClass) {
		this.controllerClass = controllerClass;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	
	@Override
	public String getUrl() {
		return url;
	}
	@Override
	public Method getMethod() {
		return method;
	}
	
	@Override
	public Class<?> getControllerClass() {
		return controllerClass;
	}
}
