package cache;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

public class CaffeineTest {
	public static void main(String[] args) {
		Cache<String, DataObject> cache = Caffeine.newBuilder()
				 .expireAfterWrite(1, TimeUnit.MINUTES)
				 .maximumSize(100)
				 .build();
		
		String key = "A";
		DataObject dt = new DataObject("this is data");
		
		cache.put(key, dt);
		cache.invalidate(key);
		
		DataObject dataObject = cache.getIfPresent(key);
		
		System.out.println(dataObject);
		
		
		//dataObject = cache.get(key, k-> DataObject.get("this is data kwg哈哈2"));
		//System.out.println(dataObject);
		LoadingCache<String, DataObject> cache2 = Caffeine.newBuilder()
				 .maximumSize(100)
				 .expireAfterWrite(1, TimeUnit.MINUTES)
				 .build(k -> new DataObject(k));
		 
		 dataObject = cache2.get(key);
		 System.out.println(dataObject);
		 
		 
		 Map<String, DataObject> dataObjectMap = cache2.getAll(Arrays.asList("A", "B", "C","aaa"));
		 System.out.println(dataObjectMap);
	}
	
	public static class DataObject {
		
		private final String data;

		private static int objectCounter = 0;
	 
		public DataObject(String data) {
			this.data = data;
		}
		
		public static DataObject get(String data) {
			objectCounter++;
			return new DataObject(data);
		}
		
		@Override
		public String toString() {
			return this.data.toString();
		}
	}
}


