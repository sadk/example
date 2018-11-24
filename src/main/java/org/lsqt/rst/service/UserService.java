package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.User;
import org.lsqt.rst.model.UserQuery;

public interface UserService {
	
	User getById(Long id);
	
	List<User> queryForList(UserQuery query);
	
	Page<User> queryForPage(UserQuery query);

	User saveOrUpdate(User model);

	int deleteById(Long... ids);
	
	Collection<User> getAll();
}
