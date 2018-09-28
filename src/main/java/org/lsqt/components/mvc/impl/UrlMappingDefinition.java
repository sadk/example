package org.lsqt.components.mvc.impl;

import java.lang.reflect.Method;

public interface UrlMappingDefinition {

	String getUrl();

	Method getMethod();

	Class<?> getControllerClass();
}
