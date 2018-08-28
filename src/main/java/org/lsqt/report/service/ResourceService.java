package org.lsqt.report.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.report.model.Resource;
import org.lsqt.report.model.ResourceQuery;

public interface ResourceService {
	
	Page<Resource> queryForPage(ResourceQuery query);

	Resource saveOrUpdate(Resource model);

	int deleteById(Long... ids);
	
	Collection<Resource> getAll();
}
