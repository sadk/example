package org.lsqt.components.cache.mapdb;
import java.util.concurrent.ConcurrentMap;

import org.lsqt.components.cache.Cache;
import org.lsqt.uum.model.User;
import org.mapdb.DBMaker;

public class CacheMapDB<K,V> implements Cache<K, V> {
	@SuppressWarnings("rawtypes")
	private static CacheMapDB instance;
	private static org.mapdb.DB db;

	@SuppressWarnings("unchecked")
	public static <K,V> Cache<K, V> getInstance() {
		if (instance == null) {
			synchronized (CacheMapDB.class) {
				instance = new CacheMapDB<>();
				db = DBMaker.memoryDB().make();
			}
		}
		return instance;
	}
	
	public static void main(String[] args) {
		
		ConcurrentMap<String,User> map = (ConcurrentMap<String, User>) db.hashMap("map").createOrOpen();
		User user = new User();
		user.setAddressHome("xxxxxxxxxxxxxxxx");
		
		map.put("something", user);
		for (int i=0;i<33333;i++)
		System.out.println(map.get("something").getAddressHome());
	}

	@Override
	public V get(String nameSpace, K key) {
		
		return null;
	}

	@Override
	public V put(String nameSpace, K key, V value, Boolean... isBroadcastSynchronize) {
		
		return null;
	}

	@Override
	public V remove(String nameSpace, String key, Boolean... isBroadcastSynchronize) {
		
		return null;
	}

	@Override
	public void clear(String nameSpace, Boolean... isBroadcastSynchronize) {
		
		
	}

}

