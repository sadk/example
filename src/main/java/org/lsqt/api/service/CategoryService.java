package org.lsqt.api.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.api.model.Category;
import org.lsqt.api.model.CategoryQuery;
import org.lsqt.components.db.Page;

public interface CategoryService {
	
	Category getById(Long id);
	
	Page<Category> queryForPage(CategoryQuery query);

	Category saveOrUpdate(Category model);

	int deleteById(Long... ids);
	
	Collection<Category> getAll();
	
	List<Category> queryForList(CategoryQuery query);
	
	void repairNodePath();
}
