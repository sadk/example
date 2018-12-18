package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.UserEntryInfo;
import org.lsqt.rst.model.UserEntryInfoQuery;

public interface UserEntryInfoService {
	
	UserEntryInfo getById(Long id);
	
	List<UserEntryInfo> queryForList(UserEntryInfoQuery query);
	
	Page<UserEntryInfo> queryForPage(UserEntryInfoQuery query);

	UserEntryInfo saveOrUpdate(UserEntryInfo model);

	int deleteById(Long... ids);
	
	Collection<UserEntryInfo> getAll();
}
