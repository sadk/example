package org.lsqt.act.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.act.model.UserDataConfigParam;
import org.lsqt.act.model.UserDataConfigParamQuery;
import org.lsqt.components.db.Page;

public interface UserDataConfigParamService {
	
	Page<UserDataConfigParam> queryForPage(UserDataConfigParamQuery query);

	UserDataConfigParam saveOrUpdate(UserDataConfigParam model);

	int deleteById(Long... ids);
	
	Collection<UserDataConfigParam> getAll();
	
	List<UserDataConfigParam> queryForList(UserDataConfigParamQuery query);
}
