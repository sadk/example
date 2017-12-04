package org.lsqt.uum.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.uum.model.User;
import org.lsqt.uum.model.UserQuery;

public interface UserService {
	
	Page<User> queryForPage(UserQuery query);

	User saveOrUpdate(User model);

	int deleteById(Long... ids);
	
	Collection<User> getAll();
	
	
	/**
	 * 获取用户的访问权限资源
	 * @param userId 用户ID
	 * @return 资源列表IDs
	
	List<Long> getAccessResIds(Long userId);  */
}
