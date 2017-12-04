package org.lsqt.components.db.execute.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.lsqt.components.util.bean.BeanUtil;

/**
 * java bean的元信息缓存工具类.供反射赋值调用
 * 没有使用javaBean的自省，是因为性能太差!
 * @author Sky
 *
 */
public class CacheReflectUtil{
	public static final Map<Class<?>,List<Method>> BEAN_METHODS = new ConcurrentHashMap<Class<?>,List<Method>>();
	public static final Map<Class<?>,List<Method>> BEAN_METHODS_SETTER = new ConcurrentHashMap<Class<?>,List<Method>>();
	public static final Map<Class<?>,List<Method>> BEAN_METHODS_GETTER = new ConcurrentHashMap<Class<?>,List<Method>>();
	
	public static final Map<Class<?>,List<Field>> BEAN_FIELDS = new ConcurrentHashMap<Class<?>,List<Field>>();
	public static final Map<Class<?>,Map<Field,Method>> BEAN_FIELD_METHOD_MAPPING = new ConcurrentHashMap<Class<?>,Map<Field,Method>>();
	
	public static List<Method> getBeanSetterGetterMethod(Class<?> clazz) {
		if (BEAN_METHODS.containsKey(clazz)) {
			return BEAN_METHODS.get(clazz);
		}

		List<Method> list = new ArrayList<Method>();
		Map<Field, Method> mapSetter = BeanUtil.getSetterGetterMap(clazz, false);
		Map<Field, Method> mapGetter = BeanUtil.getSetterGetterMap(clazz, true);

		list.addAll(mapSetter.values());
		list.addAll(mapGetter.values());

		BEAN_METHODS.put(clazz, list);

		return list;
	}
	
	public static List<Method> getBeanSetterMethod(Class<?> clazz) {
		if (BEAN_METHODS_SETTER.containsKey(clazz)) {
			return BEAN_METHODS_SETTER.get(clazz);
		}

		List<Method> list = new ArrayList<Method>();
		Map<Field, Method> mapSetter = BeanUtil.getSetterGetterMap(clazz, false);
		list.addAll(mapSetter.values());
		BEAN_METHODS_SETTER.put(clazz, list);

		return list;

	}
	
	public static List<Method> getBeanGetterMethod(Class<?> clazz) {
		if (BEAN_METHODS_GETTER.containsKey(clazz)) {
			return BEAN_METHODS_GETTER.get(clazz);
		}

		List<Method> list = new ArrayList<Method>();
		Map<Field, Method> mapGetter = BeanUtil.getSetterGetterMap(clazz, true);
		list.addAll(mapGetter.values());

		BEAN_METHODS_GETTER.put(clazz, list);

		return list;
	}
	
	
	/**
	 * 获取一个javaBean的常规域
	 * !!!! 注意:只获取public/protected/private修饰的域
	 * @param clazz
	 * @return
	 */
	public static List<Field> getBeanField(Class<?> clazz) {
		if (BEAN_FIELDS.containsKey(clazz)) {
			return BEAN_FIELDS.get(clazz);
		}
		
		List<Field> list = new ArrayList<Field>();
		
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			if(superClass == null) break; //fix bug: 如果是接口类型，getSuperclass将为null
			
			Field[] fields = superClass.getDeclaredFields();
			List<Field> temp = new ArrayList<Field> ();
			for(Field t: fields) {
				
				if(Modifier.isStatic(t.getModifiers())  //优先排除静态域，因为可以修饰成 private static final int age=20;
						|| Modifier.isFinal(t.getModifiers())) continue;
				
				if(Modifier.isTransient(t.getModifiers()) || Modifier.isVolatile(t.getModifiers())) continue;
					
				if(Modifier.isPrivate(t.getModifiers()) 
						|| Modifier.isProtected(t.getModifiers())
						|| Modifier.isPublic(t.getModifiers())){
					temp.add(t);
				}
			}
			if (temp.size() > 0) {
				list.addAll(Arrays.asList(fields));
			}
		}
		
		BEAN_FIELDS.put(clazz, list);
		
		return list;
	}
	
	public static List<Field> getBeanField(Class<?> clazz,String [] prop) {
		List<Field> rs = new ArrayList<>();
		
		List<Field> fs = getBeanField(clazz);
		if(fs==null || fs.size()==0) return rs;
		for(Field f:fs){
			for(String e: prop){
				if(f.getName().equals(e)){
					rs.add(f);
					break;
				}
			}
		}
		return rs;
	}
	
	public static Map<Field,Method> getBeanFieldMethodMapping(Class<?> clazz){
		if (BEAN_FIELD_METHOD_MAPPING.containsKey(clazz)) {
			return BEAN_FIELD_METHOD_MAPPING.get(clazz);
		}

		Map<Field, Method> rs = new HashMap<Field, Method>();
		Map<Field, Method> mapSetter = BeanUtil.getSetterGetterMap(clazz, false);
		Map<Field, Method> mapGetter = BeanUtil.getSetterGetterMap(clazz, true);

		rs.putAll(mapGetter);
		rs.putAll(mapSetter);

		BEAN_FIELD_METHOD_MAPPING.put(clazz, rs);

		return rs;
	}
	
	/**
	 * @param args
	 */
	public static void main(String args[]){
		class A {
			protected String name;
			public String getName(){return name;}
			private String age;
			
			private static final String test="aaaaa";
		}
		class B extends A{
			protected String name;
		}
		class C extends B{
			
		}
		
		List<Field> fields = getBeanField(C.class);
		for(Field e: fields){
			System.out.println(e.getName());
		}
	}
}