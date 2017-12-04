package org.lsqt.components.db.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {
	  public String name() default "id";
	  public String text() default "";
	  public Type type() default Type.AUTO;
	  
	  public enum Type{
		  AUTO, UUID64, UUID58, NANOTIME;
		}
}