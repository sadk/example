package org.lsqt.components.context.impl.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 提供java类的方法反射
 * @author mingmin.yuan
 *
 */
public class CacheMethodUtil {
	/** 整个类的所有方法，包含继承的、接口、私有的、保护的等等，所有方法 **/
	public static final Map<Class<?>, List<Method>> CLASS_METHODS = new ConcurrentHashMap<Class<?>, List<Method>>();

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
}
