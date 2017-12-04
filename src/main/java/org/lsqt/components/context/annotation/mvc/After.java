package org.lsqt.components.context.annotation.mvc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface After {
	Class<?> clazz() default Object.class;
	String method() default "";
	Class<?>[] args() default {};
}
