package org.lsqt.syswin.authority.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.syswin.authority.model.RoleCategory;
import org.lsqt.syswin.authority.model.RoleCategoryQuery;

public interface RoleCategoryService {
	
	Page<RoleCategory> queryForPage(RoleCategoryQuery query);

	List<RoleCategory>  queryForList(RoleCategoryQuery query) ;
	
	RoleCategory saveOrUpdate(RoleCategory model);

	int deleteById(Long... ids);
	
	Collection<RoleCategory> getAll();
	
	void repairNodePath();
}
