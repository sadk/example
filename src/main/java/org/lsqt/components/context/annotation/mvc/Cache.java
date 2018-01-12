package org.lsqt.components.context.annotation.mvc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {
	
	// ---------- 自动失效策略 ------------
	String timeout() default ""; //Web缓存过期时间:30s,5min, 0.5h, 30d, 1month, 1year
	String timeoutPoit() default ""; // web缓存过期时间点,每天13点
	
	String scope() default ""; // application,session，缓存并不存储session里，而是session失效缓存就失效
	public enum Timeout {
		
	}
	
	// 
}
