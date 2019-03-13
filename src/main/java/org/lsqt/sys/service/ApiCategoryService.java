package org.lsqt.sys.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.sys.model.ApiCategory;
import org.lsqt.sys.model.ApiCategoryQuery;
import org.lsqt.components.db.Page;

public interface ApiCategoryService {
	
	ApiCategory getById(Long id);
	
	Page<ApiCategory> queryForPage(ApiCategoryQuery query);

	ApiCategory saveOrUpdate(ApiCategory model);

	int deleteById(Long... ids);
	
	Collection<ApiCategory> getAll();
	
	List<ApiCategory> queryForList(ApiCategoryQuery query);
	
	void repairNodePath();
}
