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
	
	/**
	 * 流程批量转发
	 * @param loginUserId 登陆用户
	 * @param processInstanceIds 转发的流程实例
	 * @param userIds  转发给哪些用户
	 */
	void forward(String loginUserId,String processInstanceIds, String userIds) ;
}
