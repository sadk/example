package org.lsqt.uum.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Cache;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.lsqt.uum.model.User;
import org.lsqt.uum.model.UserQuery;
import org.lsqt.uum.service.OrgService;
import org.lsqt.uum.service.UserService;

@Service
@Cache(User.class)
public class UserServiceImpl implements UserService{
	
	@Inject private Db db;

	public User getById(Long id) {
		return db.getById(User.class, id);
	}
	
	@Cache(ignore = true)
	public Page<User>  queryForPage(UserQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), User.class, query);
	}

	@Cache(User.class)
	public List<User> getAll(){
		  return db.queryForList("queryForPage", User.class);
	}
	
	@Cache(evict = true)
	public User saveOrUpdate(User model) {
		if(StringUtil.isBlank(model.getAppCode())){
			model.setAppCode(Application.APP_CODE_DEFAULT);
		}
		return db.saveOrUpdate(model);
	}

	@Cache(evict = true)
	public int deleteById(Long ... ids) {
		return db.deleteById(User.class, Arrays.asList(ids).toArray());
	}

 

}