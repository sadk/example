package cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.uum.model.Res;

public class EhcacheTest {
	public static void main(String[] args) {
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				.withCache("preConfigured", CacheConfigurationBuilder
						.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(100)).build())
				.build(true);

        
		Cache<String, Serializable> myCache = cacheManager.createCache("myCache", CacheConfigurationBuilder
				.newCacheConfigurationBuilder(String.class,Serializable.class, ResourcePoolsBuilder.heap(100)).build());

		List<Dictionary> data = new ArrayList<>();
		Dictionary res = new Dictionary();
		res.setAppCode("1");
		res.setName("格三");
		data.add(res);
		
		myCache.put("1L",(Serializable)data);
		Serializable value = myCache.get("1L");
		System.out.println(((List<Dictionary>)value).get(0).getName());
		cacheManager.close();

	}
}
