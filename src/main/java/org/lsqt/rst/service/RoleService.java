package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.Role;
import org.lsqt.rst.model.RoleQuery;

public interface RoleService {
	
	Role getById(Long id);
	
	List<Role> queryForList(RoleQuery query);
	
	Page<Role> queryForPage(RoleQuery query);

	Role saveOrUpdate(Role model);

	int deleteById(Long... ids);
	
	Collection<Role> getAll();
}
