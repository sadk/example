package org.lsqt.act.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.act.model.UserRuleMatrixFlow;
import org.lsqt.act.model.UserRuleMatrixFlowQuery;
import org.lsqt.act.service.UserRuleMatrixFlowService;

@Service
public class UserRuleMatrixFlowServiceImpl implements UserRuleMatrixFlowService{
	
	@Inject private Db db;
	
	public Page<UserRuleMatrixFlow>  queryForPage(UserRuleMatrixFlowQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), UserRuleMatrixFlow.class, query);
	}

	public List<UserRuleMatrixFlow> getAll(){
		  return db.queryForList("getAll", UserRuleMatrixFlow.class);
	}
	
	public UserRuleMatrixFlow saveOrUpdate(UserRuleMatrixFlow model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(UserRuleMatrixFlow.class, Arrays.asList(ids).toArray());
	}
}
