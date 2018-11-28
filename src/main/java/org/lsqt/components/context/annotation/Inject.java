package org.lsqt.components.context.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({FIELD, METHOD})
@Retention(RUNTIME)
public @interface Inject {
	
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
	String version() default "";
	
	/**
	 * 两个不同的bean实现同一个接口，如果用接口注入，需显示指定实现类的class类型
	 * @return
	 */
	Class<?> impl() default Object.class;  
	
	/**
	 * 描述
	 * @return
	 */
	String remark() default "";
}
