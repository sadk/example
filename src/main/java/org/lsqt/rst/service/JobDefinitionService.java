package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.JobDefinition;
import org.lsqt.rst.model.JobDefinitionQuery;

public interface JobDefinitionService {
	
	JobDefinition getById(Long id);
	
	List<JobDefinition> queryForList(JobDefinitionQuery query);
	
	Page<JobDefinition> queryForPage(JobDefinitionQuery query);

	JobDefinition saveOrUpdate(JobDefinition model);

	int deleteById(Long... ids);
	
	Collection<JobDefinition> getAll();
}
