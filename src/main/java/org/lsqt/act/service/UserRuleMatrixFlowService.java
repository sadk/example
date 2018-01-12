package org.lsqt.act.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.act.model.UserRuleMatrixFlow;
import org.lsqt.act.model.UserRuleMatrixFlowQuery;

public interface UserRuleMatrixFlowService {
	
	Page<UserRuleMatrixFlow> queryForPage(UserRuleMatrixFlowQuery query);

	UserRuleMatrixFlow saveOrUpdate(UserRuleMatrixFlow model);

	int deleteById(Long... ids);
	
	Collection<UserRuleMatrixFlow> getAll();
}
