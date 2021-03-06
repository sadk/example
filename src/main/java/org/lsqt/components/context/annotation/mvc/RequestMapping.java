package org.lsqt.components.context.annotation.mvc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
	String rid() default ""; //资源id(唯一）
	String[] mapping() default {}; //URI
	Method method() default Method.POST; //表单请求的方法
	View view() default View.JSON; //视图类型
	String path() default ""; //视图模板，默认为FREEMARK模板


	String text() default "";
	public enum Method {
		GET, HEAD, POST, PUT, DELETE, OPTIONS, TRACE
	}

	public enum View {
		VM,FTL,JSP,JSON,XML,FILE,PDF,EXCEL,WORD,TXT;
		/*,CUSTOM_VIEW;
		
		public interface CustomView {
			
		}*/
		
	}
	
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
