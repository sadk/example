package org.lsqt.uum.service;

import org.lsqt.components.context.annotation.Service;
import org.lsqt.test.framework.UserService;

//按name不唯一,指定impl=xxxServiceImpl.Class引用
@Service(name="userService3")
public class UserService3AImpl implements UserService {

	public void say() {
		System.out.println("UserService3AImpl....");
	}

}
