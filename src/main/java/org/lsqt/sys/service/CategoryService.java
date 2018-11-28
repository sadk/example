package org.lsqt.sys.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.sys.model.Category;
import org.lsqt.sys.model.CategoryQuery;

/**
 * 系统分类管理
 * @author yuanmm
 *
 */
public interface CategoryService {
	Page<Category> queryForPage(CategoryQuery query);
	
	Category getByCode(String code);

	Category saveOrUpdate(Category model);

	int deleteById(Long... ids);
	
	Collection<Category> getAll();
	
	List<Category> queryForList(CategoryQuery query);
	
}
