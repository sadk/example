package org.lsqt.act.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.act.model.UserDataMapping;
import org.lsqt.act.model.UserDataMappingQuery;
import org.lsqt.components.db.Page;

public interface UserDataMappingService {
	Page<UserDataMapping> queryForPage(UserDataMappingQuery query);

	UserDataMapping saveOrUpdate(UserDataMapping model);

	int deleteById(Long... ids);
	
	Collection<UserDataMapping> getAll();
	
	List<UserDataMapping> queryForList(UserDataMappingQuery query);
}
