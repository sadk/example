package org.lsqt.yue.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.yue.model.Tech;
import org.lsqt.yue.model.TechQuery;

public interface TechService {
	
	Page<Tech> queryForPage(TechQuery query);

	Tech saveOrUpdate(Tech model);

	int deleteById(Long... ids);
	
	Collection<Tech> getAll();
	
	List<Tech> getAllChildNodes(Long id); //根据当前id获取子部门(多层,含当前部门) 
}
