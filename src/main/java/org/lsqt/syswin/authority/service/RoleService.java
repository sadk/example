package org.lsqt.syswin.authority.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.syswin.authority.model.Role;
import org.lsqt.syswin.authority.model.RoleQuery;

public interface RoleService {
	
	Page<Role> queryForPage(RoleQuery query);

	Role saveOrUpdate(Role model);

	int deleteById(Long... ids);
	
	Collection<Role> getAll();
}
