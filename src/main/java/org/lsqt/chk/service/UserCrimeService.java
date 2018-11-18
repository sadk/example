package org.lsqt.chk.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.chk.model.UserCrime;
import org.lsqt.chk.model.UserCrimeQuery;

public interface UserCrimeService {
	
	UserCrime getById(Long id);
	
	List<UserCrime> queryForList(UserCrimeQuery query);
	
	Page<UserCrime> queryForPage(UserCrimeQuery query);

	UserCrime saveOrUpdate(UserCrime model);

	int deleteById(Long... ids);
	
	Collection<UserCrime> getAll();
}
