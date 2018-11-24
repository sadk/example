package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.rst.model.JobDefinition;
import org.lsqt.rst.model.JobDefinitionQuery;
import org.lsqt.rst.service.JobDefinitionService;

@Service
public class JobDefinitionServiceImpl implements JobDefinitionService{
	
	@Inject private Db db;
	
	public JobDefinition getById(Long id) {
		return db.getById(JobDefinition.class, id) ;
	}
	
	public List<JobDefinition> queryForList(JobDefinitionQuery query) {
		return db.queryForList("queryForPage", JobDefinition.class, query);
	}
	
	public Page<JobDefinition> queryForPage(JobDefinitionQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), JobDefinition.class, query);
	}

	public List<JobDefinition> getAll(){
		  return db.queryForList("getAll", JobDefinition.class);
	}
	
	public JobDefinition saveOrUpdate(JobDefinition model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(JobDefinition.class, Arrays.asList(ids).toArray());
	}
}
