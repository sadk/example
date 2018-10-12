package org.lsqt.uum.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.uum.model.User;
import org.lsqt.uum.model.UserQuery;

public interface UserService {
	
	User getById(Long id);
	
	Page<User> queryForPage(UserQuery query);

	User saveOrUpdate(User model);

	int deleteById(Long... ids);
	
	Collection<User> getAll();
	
	
	
}
