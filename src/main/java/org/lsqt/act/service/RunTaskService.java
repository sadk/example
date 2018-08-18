package org.lsqt.act.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.act.model.RunTask;
import org.lsqt.act.model.RunTaskQuery;

public interface RunTaskService {
	
	List<RunTask>  queryForList(RunTaskQuery query) ;

	Page<RunTask> queryForPage(RunTaskQuery query);

	RunTask saveOrUpdate(RunTask model);

	int deleteById(Long... ids);
	
	Collection<RunTask> getAll();
}
