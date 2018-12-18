package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.rst.model.Role;
import org.lsqt.rst.model.RoleQuery;
import org.lsqt.rst.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Inject private Db db;
	
	public Role getById(Long id) {
		return db.getById(Role.class, id) ;
	}
	
	public List<Role> queryForList(RoleQuery query) {
		return db.queryForList("queryForPage", Role.class, query);
	}
	
	public Page<Role> queryForPage(RoleQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Role.class, query);
	}

	public List<Role> getAll(){
		  return db.queryForList("getAll", Role.class);
	}
	
	public Role saveOrUpdate(Role model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(Role.class, Arrays.asList(ids).toArray());
	}
}
