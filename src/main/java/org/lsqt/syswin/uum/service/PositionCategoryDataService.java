package org.lsqt.syswin.uum.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.syswin.uum.model.PositionCategoryData;
import org.lsqt.syswin.uum.model.PositionCategoryDataQuery;

public interface PositionCategoryDataService {
	
	Page<PositionCategoryData> queryForPage(PositionCategoryDataQuery query);

	PositionCategoryData saveOrUpdate(PositionCategoryData model);

	int deleteById(Long... ids);
	
	Collection<PositionCategoryData> getAll();
}
