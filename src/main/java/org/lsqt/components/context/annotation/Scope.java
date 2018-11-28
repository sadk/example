package org.lsqt.components.context.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {
	String value() default "";
	
	public static String SINGLETON = "singleton";
	public static String PROTOTYPE = "prototype";
	public static String SESSION = "session";
	public static String APPLICATION = "application";
	
}
