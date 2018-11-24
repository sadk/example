package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.Company;
import org.lsqt.rst.model.CompanyQuery;

public interface CompanyService {
	
	Company getById(Long id);
	
	List<Company> queryForList(CompanyQuery query);
	
	Page<Company> queryForPage(CompanyQuery query);

	Company saveOrUpdate(Company model);

	int deleteById(Long... ids);
	
	Collection<Company> getAll();
}
