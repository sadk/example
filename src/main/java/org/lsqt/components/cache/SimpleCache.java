package org.lsqt.components.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleCache implements Cache {
	private Map<Object,Object> cache = new ConcurrentHashMap<>();
	
	@Override
	public void put(Object key, Object value) {
		cache.put(key, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Object key) {
		return (T)cache.get(key);
	}

	@Override
	public void put(Object key, Object value, CacheExpirePolicy policy) {
		cache.put(key, value);
	}

	@Override
	public void clear() {
		cache.clear();
	}

	
}

/**
 * 缓存
 * @author mm
 *
 */
class CacheKeyWraper {
	private Object  originalKey;
	private long putTime;
	
}
