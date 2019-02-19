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
	 * @param defaultBroadcastSynchronize 是否广播同步刷新其他机器的缓存,默认为广播刷新!!!
	 * @return
	 */
	V put(String nameSpace, K key, V value, Boolean ... defaultBroadcastSynchronize);

	V remove(String nameSpace, String key, Boolean ... defaultBroadcastSynchronize);
	 
	void clear(String nameSpace,Boolean ... defaultBroadcastSynchronize);

}
