package org.lsqt.sys.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.sys.model.Tenant;
import org.lsqt.sys.model.TenantQuery;

public interface TenantService {
	
	Page<Tenant> queryForPage(TenantQuery query);

	Tenant saveOrUpdate(Tenant model);

	int deleteById(Long... ids);
	
	Collection<Tenant> getAll();
}
