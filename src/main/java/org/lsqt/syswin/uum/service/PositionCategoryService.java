package org.lsqt.syswin.uum.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.syswin.uum.model.PositionCategory;
import org.lsqt.syswin.uum.model.PositionCategoryQuery;

public interface PositionCategoryService {
	
	List<PositionCategory> queryForList(PositionCategoryQuery query) ;
	 
	Page<PositionCategory> queryForPage(PositionCategoryQuery query);

	PositionCategory saveOrUpdate(PositionCategory model);

	int deleteById(Long... ids);
	
	Collection<PositionCategory> getAll();
	
	void repairNodePath();
}
