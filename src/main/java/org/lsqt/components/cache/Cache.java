package org.lsqt.components.cache;

/**
 * 缓存相关
 * 缓存有不同的过期策略
 * 缓存有不同的存储介质
 * 缓存有不同的撞击算法
 * @author yuanmm
 *
 */
public interface Cache {
 
	void put(Object key,Object value);
	
	<T> T get(Object key);
	
	void put(Object obj,Object value,CacheExpirePolicy policy);
	
	void clear();
	
	interface CacheExpirePolicy {
		
	}
	
	interface CacheStorePolicy {
		
	}
	
	
}
