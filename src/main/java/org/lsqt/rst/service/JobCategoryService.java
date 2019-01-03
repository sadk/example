package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.JobCategory;
import org.lsqt.rst.model.JobCategoryQuery;

public interface JobCategoryService {
	
	JobCategory getById(Long id);
	
	Page<JobCategory> queryForPage(JobCategoryQuery query);

	JobCategory saveOrUpdate(JobCategory model);

	int deleteById(Long... ids);
	
	Collection<JobCategory> getAll();
	
	List<JobCategory> queryForList(JobCategoryQuery query);
	
	List<JobCategory> getOptionByCode(String code,String appCode) ;
	
	/**
	 * 修复节点路径
	 */
	void repairNodePath();
}
