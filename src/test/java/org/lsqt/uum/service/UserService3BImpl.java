package org.lsqt.uum.service;

import org.lsqt.components.context.annotation.Service;
import org.lsqt.spi.uum.UserService;


//按name不唯一,指定impl=xxxServiceImpl.Class引用
@Service(name="userService3")
public class UserService3BImpl implements UserService {


	public void say() {
		System.out.println("userService3BImpl....");
	}

}
