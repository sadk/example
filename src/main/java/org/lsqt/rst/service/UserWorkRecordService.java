package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.UserWorkRecord;
import org.lsqt.rst.model.UserWorkRecordQuery;

public interface UserWorkRecordService {
	
	UserWorkRecord getById(Long id);
	
	List<UserWorkRecord> queryForList(UserWorkRecordQuery query);
	
	Page<UserWorkRecord> queryForPage(UserWorkRecordQuery query);

	UserWorkRecord saveOrUpdate(UserWorkRecord model);

	int deleteById(Long... ids);
	
	Collection<UserWorkRecord> getAll();
}
