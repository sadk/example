package org.lsqt.components.cache.mapdb;
import java.util.concurrent.ConcurrentMap;

import org.lsqt.uum.model.User;
import org.mapdb.*;
public class Test {

	public static void main(String[] args) {
		DB db = DBMaker.memoryDB().make();
		ConcurrentMap<String,User> map = (ConcurrentMap<String, User>) db.hashMap("map").createOrOpen();
		User user = new User();
		user.setAddressHome("xxxxxxxxxxxxxxxx");
		
		map.put("something", user);
		for (int i=0;i<33333;i++)
		System.out.println(map.get("something").getAddressHome());
	}

}

