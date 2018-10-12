package org.lsqt.components.util.bean;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @author Sky
 *
 */
public class BeanUtil  {

	
	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * @param object 目标对象
	 * @param propertyName 目标对象属性名称
	 * @throws NoSuchFieldException 如果没有该Field时抛出.
	 * @return 返回对象的"域"对象
	 */
	private static Field getDeclaredField(Object object, String propertyName) throws NoSuchFieldException {
		 
		return getDeclaredField(object.getClass(), propertyName);
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * @param clazz 目标对象类
	 * @param propertyName 目标对象属性名称
	 * @throws NoSuchFieldException 如果没有该Field时抛出.
	 * @return 返回对象的"域"对象
	 */
	private static Field getDeclaredField(Class<?> clazz, String propertyName) throws NoSuchFieldException {
	
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(propertyName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + propertyName);
	}
	
	/**
	 * 暴力获取对象变量值,忽略private,protected修饰符的限制.
	 * @param object 目标对象
	 * @param propertyName 目标对象属性名称
	 * @throws NoSuchFieldException 如果没有该Field时抛出.
	 * @return 返回对象变量值
	 * @throws IllegalAccessException 
	 */
	public static Object forceGetProperty(Object object, String propertyName) throws NoSuchFieldException, IllegalAccessException {
		Field field = getDeclaredField(object, propertyName);

		boolean accessible = field.isAccessible();
		field.setAccessible(true);

		Object result = null;

		result = field.get(object);
		field.setAccessible(accessible);
		return result;
	}

	/**    
	 * 暴力设置对象变量值,忽略private,protected修饰符的限制.
	 * @param object 目标对象
	 * @param propertyName 对象属性名称
	 * @param newValue 设置对应的对象属性新值
	 * @throws NoSuchFieldException 如果没有该Field时抛出.
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void forceSetProperty(Object object, String propertyName, Object newValue) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		
		Field field = getDeclaredField(object, propertyName);
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		
		field.set(object, newValue);
		field.setAccessible(accessible);
	}
	
	
	
	
	

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static  Map<Field,Method> getSetterGetterMap(Class<?> clazz,boolean isGetter){
		Map<Field,Method> setterGetter=new LinkedHashMap<Field,Method>();

		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			for(Field f:superClass.getDeclaredFields()){
				Method m=getMethodByField(superClass,isGetter,f);
				if(m!=null){
					setterGetter.put(f, m);
				}
			}
		}
		
		return setterGetter;
	}
	
	/**
	 * 跟据域，找到对应的getter/setter方法
	 * @param clazz
	 * @param getterSetterType
	 * @param field
	 * @return
	 */
	private static Method getMethodByField(Class<?> clazz,boolean isGetter,Field field){
		for(Field e:clazz.getDeclaredFields()){
			if(field.getName().equals(e.getName())){
				if(isGetter==false){
					if(! e.getGenericType().equals(boolean.class)){ //处理非布尔值的域
						for(Method m: clazz.getDeclaredMethods()){
							if(isCanSetter(m) && ("set".concat(e.getName())).equalsIgnoreCase(m.getName())){
								return m;
							}
						}
					}else{
						for(Method m: clazz.getDeclaredMethods()){
							if(isCanSetter(m)){
								if(e.getName().startsWith("is") 
										&& ("set".concat(e.getName().substring(2,e.getName().length()))).equalsIgnoreCase(m.getName()) ){
									return m;
								}else{
									if(("set".concat(e.getName())).equalsIgnoreCase(m.getName())){
										return m;
									}
								}
							}
						}
					}
					
				}else if(isGetter){
					if(! e.getGenericType().equals(boolean.class)){ //处理非布尔值的域
						for(Method m: clazz.getDeclaredMethods()){
							if(isCanGetter(m) && "get".concat(e.getName()).equalsIgnoreCase(m.getName())){
								return m;
							}
						}
					}else{
						for(Method m: clazz.getDeclaredMethods()){
							if(isCanGetter(m)){
								if(e.getName().startsWith("is") && e.getName().equalsIgnoreCase(m.getName())){
									return m;
								}else{
									if(("is".concat(e.getName()).equalsIgnoreCase(m.getName()))){
										return m;
									}
								}
							}
						}
					}
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 粗略判断此方法是否是setter方法
	 * @param m
	 * @return
	 */
	public static boolean isCanSetter(Method m){
		// 判断setter:只有一个入参，方法名以set开头,无返回值，
		return (m!=null 
				&& m.getName().startsWith("set")
				&& m.getReturnType().equals(void.class)
				&& m.getParameterTypes().length == 1) ;
	}

	/**
	 * 粗略判断此方法是否是getter方法
	 * @param m
	 * @return
	 */
	public static boolean isCanGetter(Method m){
		// 判断getter方法：无入参，有返回值
		return (m!=null
				&& m.getParameterTypes().length == 0
				&& (!m.getReturnType().equals(void.class)));
	}

	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * 
	 * @param field
	 * @param instance
	 * @param fieldValue
	 * @throws Exception
	 */
	public static void forceSetProperty(Field field,Object instance,Object fieldValue) throws Exception {
		//boolean isAccess = field.isAccessible();
		//try {
			field.setAccessible(true);
			field.set(instance, fieldValue);
		/*} catch (Exception ex) {
			throw ex;
		} finally { 
			field.setAccessible(isAccess);
		}*/
	}
	
	/**
	 * 
	 * @param method
	 * @param instance
	 * @param fieldValue
	 * @throws Exception
	 */
	public static void forceSetProperty(Method setterMethod,Object instance,Object fieldValue) throws Exception {
		//boolean isAcess = setterMethod.isAccessible();
		//try{
			setterMethod.setAccessible(true);
			setterMethod.invoke(instance,fieldValue);
		/*} catch(Exception ex) {
			throw ex;
		} finally{
			setterMethod.setAccessible(isAcess);
		}*/
	}
}
