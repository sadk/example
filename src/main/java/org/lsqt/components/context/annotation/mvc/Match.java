package org.lsqt.components.context.annotation.mvc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Match {
	String[] mapping() default {}; //URI
	String text() default "";
	
	
	/**
	 * 脱离事务管理，不绑定当前数据库的连接, (默认不脱离)
	 * 无数据库连接、无事务
	 * @return 如果为true则脱离DB连接绑定
	 */
	boolean excludeTransaction() default false;
	
	/**
	 * 是否开启DB事务  (默认开启)
	 * 有数据库连接、事务自动提交
	 * @return
	 */
	boolean isTransaction() default true;
}
