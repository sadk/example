package org.lsqt.uum.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.lsqt.uum.model.User;
import org.lsqt.uum.model.UserQuery;
import org.lsqt.uum.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Inject private Db db;
	
	public User getById(Long id) {
		return db.getById(User.class, id);
	}
	
	public Page<User>  queryForPage(UserQuery query) {
		
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), User.class, query);
	}

	public List<User> getAll(){
		  return db.queryForList("queryForPage", User.class);
	}
	
	public User saveOrUpdate(User model) {
		if(StringUtil.isBlank(model.getAppCode())){
			model.setAppCode(Application.APP_CODE_DEFAULT);
		}
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(User.class, Arrays.asList(ids).toArray());
	}
	
}