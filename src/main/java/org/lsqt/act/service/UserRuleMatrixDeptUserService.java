package org.lsqt.act.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.act.model.UserRuleMatrixDeptUser;
import org.lsqt.act.model.UserRuleMatrixDeptUserQuery;

public interface UserRuleMatrixDeptUserService {
	
	Page<UserRuleMatrixDeptUser> queryForPage(UserRuleMatrixDeptUserQuery query);

	UserRuleMatrixDeptUser saveOrUpdate(UserRuleMatrixDeptUser model);

	int deleteById(Long... ids);
	
	Collection<UserRuleMatrixDeptUser> getAll();
}
