package org.lsqt.rst.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;

import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.rst.model.User;
import org.lsqt.rst.model.UserQuery;
import org.lsqt.rst.service.UserService;




@Controller(mapping={"/rst/user"})
public class UserController {
	
	@Inject private UserService userService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public User getById(Long id) throws IOException {
		return userService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<User> queryForPage(UserQuery query) throws IOException {
		return userService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<User> getAll() {
		return userService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public User saveOrUpdate(User form) {
		return userService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return userService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
