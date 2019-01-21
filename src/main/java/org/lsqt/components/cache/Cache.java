package org.lsqt.components.cache;

/**
 * 缓存相关
 * 
 * @author yuanmm
 *
 */
public interface Cache<K, V> {
	V get(String nameSpace, K key);

	/**
	 * 
	 * @param nameSpace
	 * @param key
	 * @param value 值对象:注意实现序列化接口!!!
	 * @return
	 */
	V put(String nameSpace, K key, V value);

	V remove(String nameSpace, String key);

	void clear(String nameSpace);

}
