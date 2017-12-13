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
	String timeout() default ""; //Web缓存过期时间:30s, 0.5h, 30d, 1y
	String timeoutPoit() default ""; // web缓存过期时间点
	
	public enum Timeout {
		
	}
}
