package org.lsqt.components.cache;

/**
 * 缓存相关
 * @author yuanmm
 *
 */
public interface Cache<K, V> {
 
	V get(String nameSpace,K key);

	V put(String nameSpace,K key, V value);

	V remove(String nameSpace,K value);
	
	void clear();
	
}
