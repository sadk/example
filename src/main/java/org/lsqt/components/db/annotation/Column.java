package org.lsqt.components.db.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author yuanmm
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Column{
	
  public String name()  default "";

  public String text() default "";
  
  public boolean isVirtual() default false; // 是否是虚拟的列（只在查询时加载某列数据时，当在join表时，很有用!!）
    
}