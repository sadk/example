package org.lsqt.uum.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.uum.model.Res;
import org.lsqt.uum.model.ResQuery;
import org.lsqt.uum.model.User;
import org.lsqt.uum.model.UserQuery;

public interface UserService {
	User getById(Long id);
	
	/**
	 * 获取用户关联数据：用户的权限资源、角色、部门、用户组、岗位（职称）
	 * @param id
	 * @param cascade
	 * @return
	 */
	User getById(Long id,boolean cascade);
	
	Page<User> queryForPage(UserQuery query);

	User saveOrUpdate(User model);

	int deleteById(Long... ids);
	
	Collection<User> getAll();
	
	/**
	 * 获取用户的权限数据.userId不能为空
	 * @param query
	 * @return
	 */
	List<Res> getResList(ResQuery query);
	
}
