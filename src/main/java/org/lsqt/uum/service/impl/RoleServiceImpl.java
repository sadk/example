package org.lsqt.uum.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.uum.model.Role;
import org.lsqt.uum.model.RoleQuery;
import org.lsqt.uum.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Inject private Db db;
	
	public Page<Role>  queryForPage(RoleQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Role.class, query);
	}

	public List<Role> getAll(){
		  return db.queryForList("queryForPage", Role.class);
	}
	
	public Role saveOrUpdate(Role model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(Role.class, Arrays.asList(ids).toArray());
	}
}
