package org.lsqt.components.context;

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
 * java bean的元信息缓存工具类.供反射赋值调用,主要是缓存Getter和Setter方法
 * 没有使用javaBean的自省，是因为性能太差!
 * @author Sky
 *
 */
public class CacheReflectUtil{
	/**Bean的getter和setter方法**/
	private static final Map<Class<?>,List<Method>> BEAN_METHODS_GETTER_AND_SETTER = new ConcurrentHashMap<Class<?>,List<Method>>();
	private static final Map<Class<?>,List<Method>> BEAN_METHODS_SETTER = new ConcurrentHashMap<Class<?>,List<Method>>();
	private static final Map<Class<?>,List<Method>> BEAN_METHODS_GETTER = new ConcurrentHashMap<Class<?>,List<Method>>();
	
	private static final Map<Class<?>,List<Field>> BEAN_FIELDS = new ConcurrentHashMap<Class<?>,List<Field>>();
	private static final Map<String,Map<Field,Method>> BEAN_FIELD_METHOD_MAPPING = new ConcurrentHashMap<String,Map<Field,Method>>();
	
	/** 整个类的所有方法，包含继承的、接口、私有的、保护的等等，所有方法 **/
	public static final Map<Class<?>, List<Method>> CLASS_METHODS = new ConcurrentHashMap<Class<?>, List<Method>>();
	
	
	public static List<Method> getBeanSetterGetterMethod(Class<?> clazz) {
		if (BEAN_METHODS_GETTER_AND_SETTER.containsKey(clazz)) {
			return BEAN_METHODS_GETTER_AND_SETTER.get(clazz);
		}

		List<Method> list = new ArrayList<Method>();
		Map<Field, Method> mapSetter = BeanUtil.getSetterGetterMap(clazz, false);
		Map<Field, Method> mapGetter = BeanUtil.getSetterGetterMap(clazz, true);

		list.addAll(mapSetter.values());
		list.addAll(mapGetter.values());

		BEAN_METHODS_GETTER_AND_SETTER.put(clazz, list);

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
		
		//过滤静态域
		List<Field> temp = new ArrayList<>();
		for(Field e : list) {
			//System.out.println(e);
			if(e.toString().indexOf("static ") == -1){
				temp.add(e);
			}
		}
		BEAN_FIELDS.put(clazz, temp);
		
		 
		return temp;
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
	
	public static Map<Field, Method> getBeanFieldMethodMapping(Class<?> clazz, boolean isGetter) {
		String key = clazz.toString() + isGetter;
		if (BEAN_FIELD_METHOD_MAPPING.containsKey(key)) {
			return BEAN_FIELD_METHOD_MAPPING.get(key);
		}
		
		Map<Field, Method> mapSetter = BeanUtil.getSetterGetterMap(clazz, isGetter);
		BEAN_FIELD_METHOD_MAPPING.put(key, mapSetter);

		return mapSetter;
	}
	
	/**
	 * 获取一个类的所有方法（包含继承的、接口、私有的、保护的等等，所有方法）
	 * 
	 * @param clazz
	 * @return
	 */
	public static List<Method> getMethodList(Class<?> clazz) {
		if (CLASS_METHODS.containsKey(clazz)) {
			return CLASS_METHODS.get(clazz);
		}

		List<Method> methodList = new ArrayList<>();

		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			for (Method f : superClass.getDeclaredMethods()) {
				methodList.add(f);
			}
		}

		return methodList;
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