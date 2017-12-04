package org.lsqt.components.db.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Parent
{
      String fk() default "id";

      boolean lazy() default true;

}