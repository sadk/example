package org.lsqt.sys.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.sys.model.Project;
import org.lsqt.sys.model.ProjectQuery;

public interface ProjectService {
	
	Page<Project> queryForPage(ProjectQuery query);

	Project saveOrUpdate(Project model);

	int deleteById(Long... ids);
	
	Collection<Project> getAll();
	
}
