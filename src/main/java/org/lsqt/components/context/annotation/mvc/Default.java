package org.lsqt.components.context.annotation.mvc;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({PARAMETER})
@Retention(RUNTIME)
public @interface Default {
	
	/**
	 * 引用的beanId
	 * @return
	 */
	String value() default "";
	
	/**
	 * 引用的bean名称和版本号
	 * @return
	 */
	String name() default "";

	/**
	 * 描述
	 * @return
	 */
	String text() default "";
}
