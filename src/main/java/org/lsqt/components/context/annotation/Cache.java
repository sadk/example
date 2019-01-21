package org.lsqt.components.context.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {
	/**
	 * 禁用缓存
	 * @return
	 */
	boolean ignore() default false;
	
	/**
	 * 添加到缓存的实体对象类型,默认为实体类类型
	 * 注：当evict=true时，将同时清除指定的多个实体类型缓存,比如关联对象
	 * @return
	 */
	Class<?>[] value() default { Object.class };
	
	/**
	 * 移除缓存的实体对象类型
	 * 
	 * @return
	 */
	boolean evict() default false;
	
	
	/* to be continue ...
	String timeout() default ""; //Web缓存过期时间:30s,5m, 0.5h, 30d
	String timeoutPoit() default ""; // web缓存过期时间点,每天13点
	
	String scope() default ""; // application,session，request,缓存并不存储session里，而是session失效缓存就失效
	*/
	
	
}
