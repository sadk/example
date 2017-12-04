package org.lsqt.components.util.reflect;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ClassLoaderUtil {
    private static ClassLoader appClassLoader;
    private static Map<String,Class<?>> cacheClazz = new ConcurrentHashMap<>();
    
	public static Class<?> classForName(String className) throws ClassNotFoundException {
		if(cacheClazz.containsKey(className)) {
			return cacheClazz.get(className);
		}
		
		Class<?> clazz = null;
		try {
			clazz = getClassLoader().loadClass(className);
		} catch (Exception e) {
			// None. do nothing.
		}

		if (clazz == null) {
			ClassLoader loader = getClassLoader().getParent();
			if (loader != null) {
				clazz = getClassLoader().getParent().loadClass(className);
			}
		}

		if (clazz == null) {
			try {
				clazz = Class.forName(className);
			} catch (Exception e) {

			}
		}
		
		cacheClazz.put(className, clazz);
		return clazz;
	}

    private static ClassLoader getClassLoader() {
        if (appClassLoader != null) {
            return appClassLoader;
        } else {
            return Thread.currentThread().getContextClassLoader();
        }
    }
	
}
