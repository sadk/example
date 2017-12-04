package org.lsqt.uum.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.uum.model.Role;
import org.lsqt.uum.model.RoleQuery;

public interface RoleService {
	
	Page<Role> queryForPage(RoleQuery query);

	Role saveOrUpdate(Role model);

	int deleteById(Long... ids);
	
	Collection<Role> getAll();
}
