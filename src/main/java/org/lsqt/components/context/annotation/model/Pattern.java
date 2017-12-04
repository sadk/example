package org.lsqt.components.context.annotation.model;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({FIELD,PARAMETER})
@Retention(RUNTIME)
public @interface Pattern {
	
	String value() default ""; //默认没有格式化，日期或数字原样输出
	
	 
}