package org.lsqt.yue.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.yue.model.TechInfo;
import org.lsqt.yue.model.TechInfoQuery;

public interface TechInfoService {
	
	Page<TechInfo> queryForPage(TechInfoQuery query);

	TechInfo saveOrUpdate(TechInfo model);

	int deleteById(Long... ids);
	
	Collection<TechInfo> getAll();
}
