package org.lsqt.uum.service;

import org.lsqt.components.context.annotation.Service;
import org.lsqt.test.framework.UserService;

//直接name+version引用; 
@Service(name="userService2",version="2.0")
public class UserService2Impl implements UserService {

	
	public void say() {
		System.out.println("UserService2Impl");
	}

}
