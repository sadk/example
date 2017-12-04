package org.lsqt.syswin.uum.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.syswin.uum.model.User;
import org.lsqt.syswin.uum.model.UserQuery;

public interface UserService {
	
	User getById(Long id);
	
	Page<User> queryForPage(UserQuery query);
	
	List<User> queryForList(UserQuery query);

	User saveOrUpdate(User model);

	int deleteById(Long... ids);
	
	Collection<User> getAll();
}
