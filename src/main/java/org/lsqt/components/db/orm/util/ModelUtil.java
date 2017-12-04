package org.lsqt.components.db.orm.util;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;

/**
 * @author yuanmm
 *
 */
public class ModelUtil {
	
	public static Object prepareBaseValue(Class<?> baseType,Object value){
		if(value == null) return null; 
		
		if (String.class.isAssignableFrom(baseType)) { //字符串
			return String.valueOf(value);
			
		
			
		} else if (Integer.class.isAssignableFrom(baseType)) { //数字
			return value == null ? null : Integer.valueOf(value.toString());
		} else if (baseType == int.class) {
			return value == null ? 0 : Integer.valueOf(value.toString());
		}

		else if (Long.class.isAssignableFrom(baseType)) {
			return value == null ? null : Long.valueOf(value.toString());
		} else if (baseType == long.class) {
			return value == null ? 0L : Long.valueOf(value.toString());
		}

		else if (Float.class.isAssignableFrom(baseType)) {
			return value == null ? null : Float.valueOf(value.toString());
		} else if (baseType == float.class) {
			return value == null ? 0F : Float.valueOf(value.toString());
		}

		else if (Double.class.isAssignableFrom(baseType)) {
			return value == null ? null : Double.valueOf(value.toString());
		} else if (baseType == double.class) {
			return value == null ? 0D : Double.valueOf(value.toString());
		}

		else if (Byte.class.isAssignableFrom(baseType)) {
			return value == null ? null : Byte.valueOf(value.toString());
		} else if (baseType == byte.class) {
			return value == null ? (byte) 0 : Byte.valueOf(value.toString());
		}
		/*
		else if(BigInteger.class.isAssignableFrom(baseType)) {
			return value == null ? null:BigInteger.valueOf(Long.valueOf(value.toString()));
		}
		
		else if(BigDecimal.class.isAssignableFrom(baseType)) {
			return value == null ? null:BigDecimal.valueOf(Long.valueOf(value.toString()));
		}
		*/
		
		
		else if (Boolean.class.isAssignableFrom(baseType)) { // 布尔型
			return  value == null ? null:Boolean.valueOf(value.toString());
		} else if (baseType == boolean.class) {
			return value == null ? false:Boolean.valueOf(value.toString());
		}
		
		/**/
		else if (Date.class.isAssignableFrom(baseType)) { // 日期类型
			return (Date) value;
		} 
		
		
		
		
		/*
		else if (Object.class.isAssignableFrom(baseType)) { // 字段声明为Object, 如 : private Object salary = new Double(25000D);
			if(value!=null) {
				if(value instanceof String) {
					return value.toString();
				}else {
					//字面意义
					 String valueStr = value.toString();
					 Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
					 Matcher isNum = pattern.matcher(valueStr);
					
					if (!isNum.matches()) { //数字
						Double.valueOf(valueStr);
					}
				}
			}
		}*/
		return null;
	}
	
	public static boolean isCanBeBaseType(Class<?> currType) {
		if (currType.isAnnotation() 
				|| currType.isArray()
				|| currType.isArray()
				|| currType.isEnum()
				|| currType.isInterface()) return false;
		
		if (currType == Object.class) return false;

		// 过滤集合、文件、单独处理
		if (Collection.class.isAssignableFrom(currType)) return false;
		if (File.class.isAssignableFrom(currType)) return false;
		//if (Date.class.isAssignableFrom(currType)) return false;
		
		
		if (String.class.isAssignableFrom(currType) //字符串
				
				//数字
				|| Integer.class.isAssignableFrom(currType) || int.class == currType
				|| Long.class.isAssignableFrom(currType) 	|| long.class == currType
				|| Float.class.isAssignableFrom(currType) 	|| float.class == currType
				|| Double.class.isAssignableFrom(currType) 	|| double.class == currType
				|| Byte.class.isAssignableFrom(currType) 	|| byte.class == currType
				|| Short.class.isAssignableFrom(currType)	|| short.class == currType
				/*
				|| BigInteger.class.isAssignableFrom(currType)
				|| BigDecimal.class.isAssignableFrom(currType)
				*/
				
				//布尔型
				|| Boolean.class.isAssignableFrom(currType) || boolean.class == currType
				
				
				//日期型
				|| Date.class.isAssignableFrom(currType)) {
			return true;
		}
		return false;
	}
	
	public static void forceSetPropertyValue(Object model, String prop, Object value) {
		try {
			Field field = model.getClass().getDeclaredField(prop);
			Class<?> type = field.getType();
			if (isCanBeBaseType(type)) {
				boolean accessible = field.isAccessible();
				try {
					field.setAccessible(true);
					field.set(model, prepareBaseValue(type, value));
				} finally {
					field.setAccessible(accessible);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
