package org.lsqt.uum.service;


import org.lsqt.components.context.annotation.Service;
import org.lsqt.test.framework.UserService;

//不写name,直接写实现类
@Service("userService5")
public class UserService5Impl implements UserService{
	

	public void say() {
		System.out.println("UserService5Impl...");
	}

	
}

