package org.lsqt.sys.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.sys.model.CacheCategory;
import org.lsqt.sys.model.CacheCategoryQuery;

public interface CacheCategoryService {
	
	Page<CacheCategory> queryForPage(CacheCategoryQuery query);

	CacheCategory saveOrUpdate(CacheCategory model);

	int deleteById(Long... ids);
	
	Collection<CacheCategory> getAll();
	
	void repairNodePath();
}
