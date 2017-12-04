package org.lsqt.act.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.act.model.RunTaskAssignForwardCc;
import org.lsqt.act.model.RunTaskAssignForwardCcQuery;

public interface RunTaskAssignForwardCcService {
	
	Page<RunTaskAssignForwardCc> queryForPage(RunTaskAssignForwardCcQuery query);

	RunTaskAssignForwardCc saveOrUpdate(RunTaskAssignForwardCc model);

	int deleteById(Long... ids);
	
	Collection<RunTaskAssignForwardCc> getAll();
}
