package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.JobCategoryAttr;
import org.lsqt.rst.model.JobCategoryAttrQuery;

public interface JobCategoryAttrService {
	
	JobCategoryAttr getById(Long id);
	
	List<JobCategoryAttr> queryForList(JobCategoryAttrQuery query);
	
	Page<JobCategoryAttr> queryForPage(JobCategoryAttrQuery query);

	JobCategoryAttr saveOrUpdate(JobCategoryAttr model);

	int deleteById(Long... ids);
	
	Collection<JobCategoryAttr> getAll();
}
