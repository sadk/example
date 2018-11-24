package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.rst.model.User;
import org.lsqt.rst.model.UserQuery;
import org.lsqt.rst.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Inject private Db db;
	
	public User getById(Long id) {
		return db.getById(User.class, id) ;
	}
	
	public List<User> queryForList(UserQuery query) {
		return db.queryForList("queryForPage", User.class, query);
	}
	
	public Page<User> queryForPage(UserQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), User.class, query);
	}

	public List<User> getAll(){
		  return db.queryForList("getAll", User.class);
	}
	
	public User saveOrUpdate(User model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(User.class, Arrays.asList(ids).toArray());
	}
}
