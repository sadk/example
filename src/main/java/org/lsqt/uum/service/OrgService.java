package org.lsqt.uum.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.uum.model.Org;
import org.lsqt.uum.model.OrgQuery;

public interface OrgService {
	
	Page<Org> queryForPage(OrgQuery query);

	Org saveOrUpdate(Org model);

	int deleteById(Long... ids);
	
	Collection<Org> getAll();
	
	List<Org> getAllChildNodes(Long id); //根据当前id获取子部门(多层,含当前部门) 
}
