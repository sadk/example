package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.rst.model.JobCategoryAttr;
import org.lsqt.rst.model.JobCategoryAttrQuery;
import org.lsqt.rst.service.JobCategoryAttrService;

@Service
public class JobCategoryAttrServiceImpl implements JobCategoryAttrService{
	
	@Inject private Db db;
	
	public JobCategoryAttr getById(Long id) {
		return db.getById(JobCategoryAttr.class, id) ;
	}
	
	public List<JobCategoryAttr> queryForList(JobCategoryAttrQuery query) {
		return db.queryForList("queryForPage", JobCategoryAttr.class, query);
	}
	
	public Page<JobCategoryAttr> queryForPage(JobCategoryAttrQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), JobCategoryAttr.class, query);
	}
	
 

	public List<JobCategoryAttr> getAll(){
		  return db.queryForList("getAll", JobCategoryAttr.class);
	}
	
	public JobCategoryAttr saveOrUpdate(JobCategoryAttr model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(JobCategoryAttr.class, Arrays.asList(ids).toArray());
	}
}
