package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.StoreManager;
import org.lsqt.rst.model.StoreManagerQuery;

public interface StoreManagerService {
	
	StoreManager getById(Long id);
	
	List<StoreManager> queryForList(StoreManagerQuery query);
	
	Page<StoreManager> queryForPage(StoreManagerQuery query);

	StoreManager saveOrUpdate(StoreManager model);

	int deleteById(Long... ids);
	
	Collection<StoreManager> getAll();
}
