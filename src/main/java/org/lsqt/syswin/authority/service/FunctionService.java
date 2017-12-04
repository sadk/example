package org.lsqt.syswin.authority.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.syswin.authority.model.Function;
import org.lsqt.syswin.authority.model.FunctionQuery;

public interface FunctionService {
	
	Page<Function> queryForPage(FunctionQuery query);

	Function saveOrUpdate(Function model);

	int deleteById(Long... ids);
	
	Collection<Function> getAll();
	
	/**
	 * 获取角色下的权限功能点
	 * @param roleId
	 * @return
	 */
	List<Function> getFunctionListByRoleID(Long roleId);
	
	/**
	 *获取用户的权限功能点
	 * @param userId
	 * @return
	 */
	List<Function> getFunctionListByUserID(Long userId) ;
	
}
