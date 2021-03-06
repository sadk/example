package org.lsqt.components.context.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {
	String value() default "";
	String name() default "";
	boolean lazy() default true;
	String version() default "";
	String scope() default Scope.PROTOTYPE;
	//String initMethod() default ""; 用生命周期接口
	String destroyMethod() default "";
}