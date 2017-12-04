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
	String path() default "index.ftl"; //视图模板，默认为FREEMARK模板


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
	
	
}
