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
	


	public V get(String nameSpace,K key) {
		db.hashMap(nameSpace).createOrOpen().get(key);
		return null;
		 
	}

	
	public V put(String nameSpace,K key, V value) {
		
		return null;
	}

	
	public V remove(String nameSpace,K value) {
		
		return null;
	}

	
	public void clear() {
		 
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
	public void clear(String nameSpace) {
	 
		
	}



	@Override
	public V remove(String nameSpace, String key) {
		// TODO Auto-generated method stub
		return null;
	}
}

