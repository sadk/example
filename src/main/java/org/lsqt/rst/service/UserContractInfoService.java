package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.UserContractInfo;
import org.lsqt.rst.model.UserContractInfoQuery;

public interface UserContractInfoService {
	
	UserContractInfo getById(Long id);
	
	List<UserContractInfo> queryForList(UserContractInfoQuery query);
	
	Page<UserContractInfo> queryForPage(UserContractInfoQuery query);

	UserContractInfo saveOrUpdate(UserContractInfo model);

	int deleteById(Long... ids);
	
	Collection<UserContractInfo> getAll();
}
