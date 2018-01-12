package org.lsqt.act.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.act.model.UserRuleMatrixDeptUser;
import org.lsqt.act.model.UserRuleMatrixDeptUserQuery;
import org.lsqt.act.service.UserRuleMatrixDeptUserService;

@Service
public class UserRuleMatrixDeptUserServiceImpl implements UserRuleMatrixDeptUserService{
	
	@Inject private Db db;
	
	public Page<UserRuleMatrixDeptUser>  queryForPage(UserRuleMatrixDeptUserQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), UserRuleMatrixDeptUser.class, query);
	}

	public List<UserRuleMatrixDeptUser> getAll(){
		  return db.queryForList("getAll", UserRuleMatrixDeptUser.class);
	}
	
	public UserRuleMatrixDeptUser saveOrUpdate(UserRuleMatrixDeptUser model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(UserRuleMatrixDeptUser.class, Arrays.asList(ids).toArray());
	}
}
