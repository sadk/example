package org.lsqt.cms.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.cms.model.Resource;
import org.lsqt.cms.model.ResourceQuery;

public interface ResourceService {
	
	Page<Resource> queryForPage(ResourceQuery query);

	Resource saveOrUpdate(Resource model);

	int deleteById(Long... ids);
	
	Collection<Resource> getAll();
}
