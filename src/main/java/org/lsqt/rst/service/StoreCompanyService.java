package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.StoreCompany;
import org.lsqt.rst.model.StoreCompanyQuery;

public interface StoreCompanyService {
	
	StoreCompany getById(Long id);
	
	List<StoreCompany> queryForList(StoreCompanyQuery query);
	
	Page<StoreCompany> queryForPage(StoreCompanyQuery query);

	StoreCompany saveOrUpdate(StoreCompany model);

	int deleteById(Long... ids);
	
	Collection<StoreCompany> getAll();
}
