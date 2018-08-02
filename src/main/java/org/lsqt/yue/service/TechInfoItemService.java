package org.lsqt.yue.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.yue.model.TechInfoItem;
import org.lsqt.yue.model.TechInfoItemQuery;

public interface TechInfoItemService {
	
	Page<TechInfoItem> queryForPage(TechInfoItemQuery query);

	TechInfoItem saveOrUpdate(TechInfoItem model);

	int deleteById(Long... ids);
	
	Collection<TechInfoItem> getAll();
}
