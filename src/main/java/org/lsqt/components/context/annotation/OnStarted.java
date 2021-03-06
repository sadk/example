package org.lsqt.components.context.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 框架启动后执行的(一次性)方法
 * 注：如果有用到DB，请注意手动关闭事务
 * @author mm
 *
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OnStarted {
	/**
	 * 容器启动后，执行的方法
	 * @return
	 */
	String method() default "";
	
	/**
	 * 方法参数类型
	 * @return
	 */
	Class<?>[] args() default {};
	
	/**
	 * 方法的执行顺序
	 * @return
	 */
	int order() default 0;
	
	/**
	 * 注释说明
	 * @return
	 */
	String text() default "";
	
}

