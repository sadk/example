package org.lsqt.uum.service;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.uum.controller.UserController;
import org.lsqt.uum.dao.UserDao;

//@Service
public class DepartmentServiceImpl {
	//@Inject
	private UserDao userDao;
	
	@Inject
	private UserController userController;
}
