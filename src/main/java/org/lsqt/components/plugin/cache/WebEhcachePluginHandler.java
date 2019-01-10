package org.lsqt.components.plugin.cache;

import java.io.File;
import java.io.Serializable;

import org.ehcache.Cache;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.lsqt.components.context.Order;
import org.lsqt.components.context.annotation.Component;
import org.lsqt.components.context.annotation.OnStarted;
import org.lsqt.components.mvc.WebCacheHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WEB缓存支持
 * @author mm
 *
 */
@Component
public class WebEhcachePluginHandler implements WebCacheHandler , Order{
	private static final Logger log = LoggerFactory.getLogger(WebEhcachePluginHandler.class);
	
	private static PersistentCacheManager persistentCacheManager ; 

	private static volatile boolean isInited = false;
	
	private String webCacheKey ;
	
	@OnStarted
	private synchronized void initConfig() {
        persistentCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
        .with(CacheManagerBuilder.persistence(System.getProperty("java.io.tmpdir") + File.separator + "webCacheData_"+System.currentTimeMillis())) 
        .withCache(WebEhcachePluginHandler.class.getName(),
                CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Serializable.class,
                    ResourcePoolsBuilder.newResourcePoolsBuilder()
                        .heap(500, EntryUnit.ENTRIES)  //堆
                        .offheap(20, MemoryUnit.MB)    //堆外
                        .disk(10, MemoryUnit.GB)      //磁盘
                    )
            ).build(true);
		
        isInited = true;
		log.info("Web缓存管理器实使成功!");
	}
	
	public Object handle(Object context) throws Exception {
		try {
			if (!isInited) {
				initConfig();
			}
			
			String currRequestKey = getWebCacheKey();
			
			Cache<String, Serializable> cache = persistentCacheManager.getCache(WebEhcachePluginHandler.class.getName(),String.class, Serializable.class);
			
			Object rs = cache.get(currRequestKey);
			if (rs != null) {
				return rs;
			}
			
			if (context != null) {
				cache.put(currRequestKey, (Serializable) context);
				Object data = cache.get(currRequestKey);
				return data;
			}
			
		}catch(Exception ex){
			throw ex;
		}/* finally {
			persistentCacheManager.close();
		}*/
		return null;
	}

	/**
	 * 要据URL+请求值的摘要作为缓存Key
	 * @return
	 */
	public String getWebCacheKey() {
		/*Map<String,Object> map = ContextUtil.getFormMap();
		String key = WebEhcachePluginHandler.class.getName() + RequestUtil.getRequestURI(ContextUtil.getRequest()) + map.toString();
		return Md5Util.MD5Encode(key, "utf-8", false);*/
		return webCacheKey;
	}
	
	public void setWebCacheKey(String webCacheKey) {
		this.webCacheKey = webCacheKey;
	}

	public boolean isEnable() {
		return true;
	}

	@Override
	public int getOrder() {
		return 0;
	}
	

	@Override
	public void setKey(String key) {
		this.setWebCacheKey(key);
	}
}

