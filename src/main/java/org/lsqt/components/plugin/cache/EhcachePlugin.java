package org.lsqt.components.plugin.cache;

import java.io.File;
import java.io.Serializable;
import java.time.Duration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.lsqt.components.context.Order;

/**
 * 缓存插件实现类,承接Ehcache与应用框架的中间层
 * @author mm
 *
 */
public class EhcachePlugin implements org.lsqt.components.cache.Cache<String, Object>, Order {
	private static EhcachePlugin instance;

	private EhcachePlugin () {
		init();
	}
	
	public static EhcachePlugin getInstance() {
		if (instance == null) {
			synchronized (EhcachePlugin.class) {
				instance = new EhcachePlugin();
				
			}
		}
		return instance;
	}
	
	private CacheManager cacheManager;
	private CacheConfigurationBuilder<String, Serializable> cacheConfig;
	
	private void init() {
		cacheConfig = CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Serializable.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                    .heap(1000, EntryUnit.ENTRIES)  //堆
                    .offheap(20, MemoryUnit.MB)    //堆外
                    .disk(1, MemoryUnit.GB)      //磁盘
                );//.withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(20)));
		
		cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
		        .with(CacheManagerBuilder.persistence(System.getProperty("java.io.tmpdir") + File.separator + "webCacheData_"+System.currentTimeMillis()))
		        //.withCache(EhcachePlugin.class.getName(), cacheConfig)
		        .build(true);
		
	}
	
	@Override
	public Object get(String nameSpace, String key) {
		Cache<String,Serializable> cache = cacheManager.getCache(nameSpace, String.class, Serializable.class);
		if(cache == null) {
			cache = cacheManager.createCache(nameSpace,  cacheConfig);
		}
		return cache.get(key);
	}

	@Override
	public Object put(String nameSpace, String key, Object value) {
		if (value == null) {
			return null;
		}

		Cache<String, Serializable> cache = cacheManager.getCache(nameSpace, String.class, Serializable.class);
		if (cache == null) {
			cache = cacheManager.createCache(nameSpace, cacheConfig);
		}
		cache.put(key, (Serializable) value);
		return value;
	}


	@Override
	public void clear(String nameSpace) {
		Cache<String, Serializable> cache = cacheManager.getCache(nameSpace, String.class, Serializable.class);
		if (cache != null) {
			Set<String> keys = new HashSet<>();
			Iterator<org.ehcache.Cache.Entry<String, Serializable>> iter = cache.iterator();
			while (iter.hasNext()) {
				org.ehcache.Cache.Entry<String, Serializable> entry = iter.next();
				keys.add(entry.getKey());
			}

			cache.removeAll(keys);
		}
	}

	@Override
	public Object remove(String nameSpace, String key) {
		Cache<String, Serializable> cache = cacheManager.getCache(nameSpace, String.class, Serializable.class);
		if (cache == null) {
			return null;
		}
		
		Object driftObject = cache.get(key);
		cache.remove(key);
		return driftObject;
	}

	@Override
	public int getOrder() {
		
		return 0;
	}
 
}

