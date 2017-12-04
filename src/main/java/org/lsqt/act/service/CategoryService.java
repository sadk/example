package org.lsqt.act.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.act.model.Category;
import org.lsqt.act.model.CategoryQuery;
import org.lsqt.components.db.Page;

public interface CategoryService {
	
	Page<Category> queryForPage(CategoryQuery query);

	Category saveOrUpdate(Category model);

	int deleteById(Long... ids);
	
	Collection<Category> getAll();
	
	List<Category> queryForList(CategoryQuery query);
	
	List<Category> getOptionByCode(String code,String appCode) ;
	
	void repairNodePath();
}
