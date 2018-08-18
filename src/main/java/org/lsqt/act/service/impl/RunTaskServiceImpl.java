package org.lsqt.act.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.act.model.RunTask;
import org.lsqt.act.model.RunTaskQuery;
import org.lsqt.act.service.RunTaskService;

@Service
public class RunTaskServiceImpl implements RunTaskService{
	
	@Inject private Db db;
	
	public List<RunTask>  queryForList(RunTaskQuery query) {
		return db.queryForList("queryForPage", RunTask.class, query);
	}
	
	public Page<RunTask>  queryForPage(RunTaskQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), RunTask.class, query);
	}

	public List<RunTask> getAll(){
		  return db.queryForList("getAll", RunTask.class);
	}
	
	public RunTask saveOrUpdate(RunTask model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(RunTask.class, Arrays.asList(ids).toArray());
	}
}
