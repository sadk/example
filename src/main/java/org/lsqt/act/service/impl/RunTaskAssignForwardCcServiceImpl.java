package org.lsqt.act.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.act.model.RunTaskAssignForwardCc;
import org.lsqt.act.model.RunTaskAssignForwardCcQuery;
import org.lsqt.act.service.RunTaskAssignForwardCcService;

@Service
public class RunTaskAssignForwardCcServiceImpl implements RunTaskAssignForwardCcService{
	
	@Inject private Db db;
	
	public Page<RunTaskAssignForwardCc>  queryForPage(RunTaskAssignForwardCcQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), RunTaskAssignForwardCc.class, query);
	}

	public List<RunTaskAssignForwardCc> getAll(){
		  return db.queryForList("getAll", RunTaskAssignForwardCc.class);
	}
	
	public RunTaskAssignForwardCc saveOrUpdate(RunTaskAssignForwardCc model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(RunTaskAssignForwardCc.class, Arrays.asList(ids).toArray());
	}
}
