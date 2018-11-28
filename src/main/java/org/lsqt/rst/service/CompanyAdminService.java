package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.CompanyAdmin;
import org.lsqt.rst.model.CompanyAdminQuery;

public interface CompanyAdminService {
	
	CompanyAdmin getById(Long id);
	
	List<CompanyAdmin> queryForList(CompanyAdminQuery query);
	
	Page<CompanyAdmin> queryForPage(CompanyAdminQuery query);

	CompanyAdmin saveOrUpdate(CompanyAdmin model);

	int deleteById(Long... ids);
	
	Collection<CompanyAdmin> getAll();
}
