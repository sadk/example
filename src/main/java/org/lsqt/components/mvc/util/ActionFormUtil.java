package org.lsqt.components.mvc.util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.lsqt.components.context.CacheReflectUtil;
import org.lsqt.components.context.annotation.model.Pattern;
import org.lsqt.components.util.bean.BeanUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class ActionFormUtil {
	static final Logger log = LoggerFactory.getLogger(ActionFormUtil.class);
	/**
	 * 填充表单数据到javaBean
	 * @param bean 表单对象
	 * @param formMap 表单数据
	 * @throws Exception 
	 */
	public static void fillBean(Object bean,Map<String,Object> formMap) throws Exception{
		/*
		 * 超过表单解析出来的对象层次数就退出递归，例如表单请求参数名为
		 * (User.)province.user.province.user.province.total=1000对象引用的层次数是5层
		 */
		int maxLevel = 0;
		for(String key : formMap.keySet()) {
			int t = key.split("\\.").length;
			if(maxLevel < t){
				maxLevel = t;
			}
		}
		
		
		List<Field> list = CacheReflectUtil.getBeanField(bean.getClass());
		for(Field e : list) {
			
				if(isCanBeBaseType(e.getType())){
					Object paramValue = formMap.get(e.getName());
					paramValue = "".equals(paramValue) ? null : paramValue ;
					 
					Object value = prepareBaseValue(e.getType(), paramValue);
					
					if(value!=null) {
						BeanUtil.forceSetProperty(e, bean, value);
					}
					
				} else if(isCanBeDate(e)){
					Object value = prepareDateValue(e, formMap.get(e.getName()));
					if (value != null) {
						BeanUtil.forceSetProperty(e, bean, value);
					}
					
				} else if(isCanBeBeanType(e.getType())){
					fillField(e,bean,maxLevel,formMap,e.getName());
				}
			
		}
		
		// log.info(JSON.toJSONString(bean));
	}
	
	private static void fillField(Field beanField,Object parentObject,int maxLevel,Map<String,Object> formMap,String key) throws Exception {
		if (key.split("\\.").length > maxLevel) return;
		
		Class<?> type = beanField.getType();
		List<Field> list = CacheReflectUtil.getBeanField(type);
		
		Object beanFiledValue = null;
		boolean isCreateSub = false;
		for(Field e : list) {
			String temp = "".equals(key) ? beanField.getName()+"."+e.getName(): key+"."+e.getName();
			if(isCreateSub == false) {
				for(String tmp : formMap.keySet()) {
					if(tmp.startsWith(temp)){
						beanFiledValue = type.newInstance() ;
						BeanUtil.forceSetProperty(beanField, parentObject, beanFiledValue);
						isCreateSub = true;
						break;
					}
				}
			}
		
			if(isCanBeBaseType(e.getType())){
				if(isCreateSub) {
					Object paramValue = formMap.get(temp);
					Object value = "".equals(paramValue) ? null : paramValue;
					
					if(value!=null) {
						BeanUtil.forceSetProperty(e, beanFiledValue, prepareBaseValue(e.getType(), value));
					}
				}
				
			} else if(isCanBeDate(e)){
				if(isCreateSub) {
					Object value = prepareDateValue(e, formMap.get(temp));
					if(value!=null) {
						BeanUtil.forceSetProperty(e, beanFiledValue, value);
					}
				}
			}else if(isCanBeBeanType(e.getType())){
				fillField(e,beanFiledValue,maxLevel,formMap,temp);
			}
		 
		}
	}
	
	/*
	public static void main(String ...args) throws Exception {
		
		Map<String,Object> formMap = new LinkedHashMap<String, Object>();
		formMap.put("id", "1");
		formMap.put("name", "2");
	
		
		formMap.put("cityxxxx.cname","14ct_xxx");
		formMap.put("city.cname","13");
		formMap.put("province.name","3");
		formMap.put("province.id","4");
		
		formMap.put("province.city.cname","7");
		formMap.put("province.user.name","5");
		formMap.put("province.user.id","6");
		 
		formMap.put("province.city.no.noCode","8");
		formMap.put("province.user.province.name","9");
		
		formMap.put("province.user.province.user.age","10");
		
		formMap.put("province.user.province.user.address.localStr","12"); 
		formMap.put("province.user.province.user.province.total","11");
		UserTest user = new UserTest();
		
		long s = System.currentTimeMillis();
		System.out.println(s);
		fillBean(user, formMap);
		System.out.println(System.currentTimeMillis()-s);
	}*/
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~辅助方法~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static boolean isCanBeBaseType(Class<?> currType) {
		if (currType.isAnnotation() 
				|| currType.isArray()
				|| currType.isArray()
				|| currType.isEnum()
				|| currType.isInterface()) return false;
		
		
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
				|| Boolean.class.isAssignableFrom(currType) || boolean.class == currType) {
			return true;
		}
		return false;
	}
	
	public static boolean isCanBeBeanType(Class<?> currType) {
		if (currType.isAnnotation() 
				|| currType.isArray()
				|| currType.isAnnotation()
				|| currType.isEnum() 
				|| currType.isInterface()) return false;
		
		if (currType == Object.class) return false;

		// 过滤集合、文件、日期类型，这三种类型填充到formBean里面，单独处理
		if (Collection.class.isAssignableFrom(currType)) return false;
		if (File.class.isAssignableFrom(currType)) return false;
		if (Date.class.isAssignableFrom(currType)) return false;

		// 单独调用此方法判断，请优先判断是否是"基础"类型
		if (isCanBeBaseType(currType)) return false;

		return true;
	}
	
	public static Object prepareBaseValue(Class<?> baseType,Object value){
		 //// 目前这里的值没有克隆!!!!
		if (String.class.isAssignableFrom(baseType)) { //字符串
			return value;
			
		
			
		} else if (Integer.class.isAssignableFrom(baseType)) { //数字
			return value == null ? null : Integer.valueOf(value.toString());
		} else if (baseType == int.class) {
			return value == null ? 0 : Integer.valueOf(value.toString());
		}

		else if (Long.class.isAssignableFrom(baseType)) {
			return (value == null || StringUtil.isBlank(value.toString())) ? null : Long.valueOf(value.toString());
		} else if (baseType == long.class) {
			return value == null ? 0L : Long.valueOf(value.toString());
		}

		else if (Float.class.isAssignableFrom(baseType)) {
			return (value == null || StringUtil.isBlank(value.toString())) ? null : Float.valueOf(value.toString());
		} else if (baseType == float.class) {
			return value == null ? 0F : Float.valueOf(value.toString());
		}

		else if (Double.class.isAssignableFrom(baseType)) {
			return (value == null || StringUtil.isBlank(value.toString())) ? null : Double.valueOf(value.toString());
		} else if (baseType == double.class) {
			return value == null ? 0D : Double.valueOf(value.toString());
		}

		else if (Byte.class.isAssignableFrom(baseType)) {
			return (value == null || StringUtil.isBlank(value.toString())) ? null : Byte.valueOf(value.toString());
		} else if (baseType == byte.class) {
			return value == null ? (byte) 0 : Byte.valueOf(value.toString());
		}
		
		else if(BigInteger.class.isAssignableFrom(baseType)) {
			return value == null ? null:BigInteger.valueOf(Long.valueOf(value.toString()));
		}
		
		else if(BigDecimal.class.isAssignableFrom(baseType)) {
			return value == null ? null:BigDecimal.valueOf(Double.valueOf(value.toString()));
		}
		
		
		
		else if (Boolean.class.isAssignableFrom(baseType)) { // 布尔型
			return  value == null ? null:Boolean.valueOf(value.toString());
		} else if (baseType == boolean.class) {
			return value == null ? false:Boolean.valueOf(value.toString());
		}
		
		/*
		else if (Date.class.isAssignableFrom(baseType)) { // 日期类型
			
		} 
		*/
		return null;
	}
	
	/**
	 * 判断Form对象的某个域是否是日期类型
	 * @param field
	 * @return
	 */
	public static boolean isCanBeDate(Field field){
		if(Date.class.isAssignableFrom(field.getType())) {
			return true;
		}
		return false;
	}
	
	public static Object prepareDateValue(Field field,Object value) throws ParseException{
		if(value == null || "".equals(value)) return null;
		Pattern parttern = field.getAnnotation(Pattern.class);
		if(parttern!=null){
			String apply = parttern.value();
			SimpleDateFormat df = new SimpleDateFormat(apply);
			return df.parse(value+"");
		}
		return "".equals(value) ? null : value;
	}
	
	
	/**
	 * 判断Controller对象的某个方法入参是否是日期类型
	 * @param param
	 * @return
	 */
	public static boolean isCanBeDate(Parameter param) {
		if(Date.class.isAssignableFrom(param.getType())){
			return true;
		}
		return false;
	}
	
	
	public static Object prepareDateValue(Parameter param,Object value) throws ParseException{
		if(value == null) return null; 
		Pattern parttern = param.getAnnotation(Pattern.class);
		String apply = parttern.value();
		SimpleDateFormat df = new SimpleDateFormat(apply);
		return df.parse(value+"");
	}
}
