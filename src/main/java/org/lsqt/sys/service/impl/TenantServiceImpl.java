package org.lsqt.sys.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.sys.model.Tenant;
import org.lsqt.sys.model.TenantQuery;
import org.lsqt.sys.service.TenantService;

@Service
public class TenantServiceImpl implements TenantService{
	
	@Inject private Db db;
	
	public Page<Tenant>  queryForPage(TenantQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Tenant.class, query);
	}

	public List<Tenant> getAll(){
		  return db.queryForList("getAll", Tenant.class);
	}
	
	public Tenant saveOrUpdate(Tenant model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(Tenant.class, Arrays.asList(ids).toArray());
	}
}
