package org.lsqt.report.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.report.model.Resource;
import org.lsqt.report.model.ResourceQuery;

public interface ResourceService {
	Resource getById(Long id);
	
	List<Resource> queryForList(ResourceQuery query);
	
	Page<Resource> queryForPage(ResourceQuery query);

	Resource saveOrUpdate(Resource model);

	int deleteById(Long... ids);
	
	Collection<Resource> getAll();
}
