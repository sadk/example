package org.lsqt.components.mvc.spi;

import java.lang.reflect.Method;

public interface UrlMappingDefinition {

	String getUrl();

	Method getMethod();

	Class<?> getControllerClass();
}
