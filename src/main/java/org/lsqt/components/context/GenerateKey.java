package org.lsqt.components.context;

import java.lang.reflect.Method;
import java.util.List;

import org.lsqt.components.context.annotation.Cache;
import org.lsqt.components.util.lang.Md5Util;

import com.alibaba.fastjson.JSON;

/**
 * 缓存key统一生成工具类(用于框架)
 * @author mm
 *
 */
public class GenerateKey {
	
	/**
	 * 缓存键信息
	 * 
	 * @author mm
	 *
	 */
	public static class Key {
		public String nameSpace = null;
		public String key = null;
	}

	public static Key generate(Class<?> targetClass, Method targetMethod, List<Object> methodInputParamValues) {
	
		String nameSpace = null;
		String key = null;

		Cache cacheClazz = targetClass.getAnnotation(Cache.class); // 注解在控制器上，所有的方法都有缓存
		Cache cacheMethod = targetMethod.getAnnotation(Cache.class);

		if (cacheMethod != null && cacheMethod.value() != Object.class) { // 注解在方法上
			nameSpace = cacheMethod.value().getName();
		} else if (cacheClazz != null && cacheClazz.value() != Object.class) { // 注解在类上
			nameSpace = cacheClazz.value().getName();
		} else {
			nameSpace = targetClass.getName(); // 默认以控制器类名为缓存命名空间
		}
		key = targetMethod + " ==>" + JSON.toJSONString(methodInputParamValues);
		key = Md5Util.MD5Encode(key, "utf-8", false);

		Key keyInfo = new Key();
		keyInfo.key = key;
		keyInfo.nameSpace = nameSpace;
		return keyInfo;
	}
}

