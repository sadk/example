package org.lsqt.uum.service;


import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.test.IOtherService;
import org.lsqt.test.UserService;
import org.lsqt.uum.dao.UserDao;
import org.lsqt.components.context.annotation.Scope;
//直接引用id;
@Service(value = "userService",scope=Scope.PROTOTYPE)
public class UserServiceImpl implements UserService ,IOtherService{
	@Inject
	private UserDao dao;
	
	public void say() {
		System.out.println("UserServiceImpl...");
	}

	public void hello() {
		System.out.println("IOtherService#hello()");
	}
	
}

