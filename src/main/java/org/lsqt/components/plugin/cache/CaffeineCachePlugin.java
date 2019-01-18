package org.lsqt.components.plugin.cache;

import java.util.concurrent.TimeUnit;

import org.lsqt.components.context.Order;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class CaffeineCachePlugin implements org.lsqt.components.cache.Cache<String, Object>,Order{
	private static CaffeineCachePlugin instance;
	
	private CaffeineCachePlugin () {
		init();
	}
	
	private Cache<String, Object> cache ;
	public static CaffeineCachePlugin getInstance() {
		if (instance == null) {
			synchronized (EhcachePlugin.class) {
				instance = new CaffeineCachePlugin();
			}
		}
		return instance;
	}
	
	
	private void init() {
		cache = Caffeine.newBuilder().maximumSize(1000).softValues().build();
	}
	
	@Override
	public Object get(String nameSpace, String key) {
		Caffeine.newBuilder().maximumSize(1000).softValues().build().getIfPresent(key);
		return null;
	}

	@Override
	public Object put(String nameSpace, String key, Object value) {
		
		return null;
	}

	@Override
	public Object remove(String nameSpace, String key) {
		
		return null;
	}

	@Override
	public void clear(String nameSpace) {
		
		
	}

	@Override
	public int getOrder() {
		return 0;
	}

}

