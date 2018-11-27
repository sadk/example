package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.PersonalVideoInfo;
import org.lsqt.rst.model.PersonalVideoInfoQuery;

public interface PersonalVideoInfoService {
	
	PersonalVideoInfo getById(Long id);
	
	List<PersonalVideoInfo> queryForList(PersonalVideoInfoQuery query);
	
	Page<PersonalVideoInfo> queryForPage(PersonalVideoInfoQuery query);

	PersonalVideoInfo saveOrUpdate(PersonalVideoInfo model);

	int deleteById(Long... ids);
	
	Collection<PersonalVideoInfo> getAll();
}
