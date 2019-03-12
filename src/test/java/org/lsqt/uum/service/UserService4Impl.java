package org.lsqt.uum.service;


import java.util.List;

import org.lsqt.components.context.annotation.Service;
import org.lsqt.test.IOtherService;
import org.lsqt.test.UserService;
import org.lsqt.uum.model.UserTest;

//直接引用name(唯一) 
@Service(name = "userService4Unique")
public class UserService4Impl implements UserService ,IOtherService{
	
	//@Inject
	//private UserDao userDao;
	

	public void say() {
		System.out.println("UserServiceImpl...");
	}

	public void hello() {
		System.out.println("IOtherService#hello()");
	}
	
	public static void main(String ...args){
		UserService4Impl u = new UserService4Impl();
		System.out.println(u.getClass().getInterfaces().length);
	}
	
}

