package org.lsqt.components.cache;


/**
 * 缓存同步器,标识接口
 * @author mm
 *
 */
public interface CacheSynchronizer<K,V> {
	
	/**
	 * 
	 * @param nameSpace
	 * @param key
	 * @param value 值对象:注意实现序列化接口!!!
	 * @return
	 */
	V put(String nameSpace, K key, V value) throws Exception;

	V remove(String nameSpace, String key) throws Exception;

	void clear(String nameSpace) throws Exception;
}

