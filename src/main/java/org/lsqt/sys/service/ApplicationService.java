package org.lsqt.sys.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.sys.model.Application;
import org.lsqt.sys.model.ApplicationQuery;

public interface ApplicationService {
	List<Application> queryForList(ApplicationQuery query);
	
	Page<Application> queryForPage(ApplicationQuery query);

	Application saveOrUpdate(Application model);

	int deleteById(Long... ids);
	
	Collection<Application> getAll();
}
