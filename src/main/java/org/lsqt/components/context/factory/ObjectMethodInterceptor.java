package org.lsqt.components.context.factory;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.lsqt.components.context.GenerateKey;
import org.lsqt.components.context.annotation.Cache;
import org.lsqt.components.context.annotation.Component;
import org.lsqt.components.context.annotation.Dao;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.plugin.cache.EhcachePlugin;
import org.lsqt.components.util.lang.Md5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 容器对象方法调用拦截器。暂时把业务缓存实现写在此类，待封装
 * @author mm
 *
 */
public class ObjectMethodInterceptor implements MethodInterceptor {
	private static final Logger log = LoggerFactory.getLogger(ObjectMethodInterceptor.class);
    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
	//	System.out.println("调用前, " + method.getDeclaringClass());
		
		Class<?> serviceClazz = method.getDeclaringClass();
		
		boolean isCachable = false;
		String nameSpace = null;
		String key = null;
		EhcachePlugin plugin = EhcachePlugin.getInstance();
		
		if (isAssignComponent(serviceClazz)) {
			

			
			Cache cacheClazz = serviceClazz.getAnnotation(Cache.class); //注解在Service、Dao、Component的实现类上
			Cache cacheMethod = method.getAnnotation(Cache.class); //注解在方法上

			if(cacheClazz != null || cacheMethod != null) {
				isCachable = true;
			}

			if (isCachable) {

				GenerateKey.Key keyInfo = GenerateKey.generate(serviceClazz, method, Arrays.asList(args));
				nameSpace = keyInfo.nameSpace;
				key = keyInfo.key;

				if (cacheMethod != null && cacheMethod.evict()) {
					plugin.clear(nameSpace);
				}

				Object data = plugin.get(nameSpace, key);
				if (data != null) {// 有缓存，并且获取到数据,记得执行后置处理
					log.info("命中缓存 nameSpace={}, key={} ", nameSpace, key);
					return data;
				}
			}
		}

		Object rs = proxy.invokeSuper(target, args);
		
		if (isCachable) {
			plugin.put(nameSpace, key, rs);
		}
		
		//System.out.println("调用后, " + method);
		return rs;
    }
    
    /**
     * 判断是否框架的业务组件
     * @param clazz
     * @return
     */
    private boolean isAssignComponent(Class<?> clazz) {
    	if(clazz.isAnnotationPresent(Component.class)) {
    		return true;
    	}
    	if(clazz.isAnnotationPresent(Service.class)) {
    		return true;
    	}
    	if(clazz.isAnnotationPresent(Dao.class)) {
    		return true;
    	}
    	/*if(clazz.isAnnotationPresent(Controller.class)) { // controller 在WEB层单独启用
    		return true;
    	}*/
    	return false;
    }
}
