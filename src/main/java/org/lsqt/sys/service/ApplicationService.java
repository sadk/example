package org.lsqt.sys.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.sys.model.Application;
import org.lsqt.sys.model.ApplicationQuery;

public interface ApplicationService {
	
	Page<Application> queryForPage(ApplicationQuery query);

	Application saveOrUpdate(Application model);

	int deleteById(Long... ids);
	
	Collection<Application> getAll();
}
