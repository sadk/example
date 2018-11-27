package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.UserJobRecord;
import org.lsqt.rst.model.UserJobRecordQuery;

public interface UserJobRecordService {
	
	UserJobRecord getById(Long id);
	
	List<UserJobRecord> queryForList(UserJobRecordQuery query);
	
	Page<UserJobRecord> queryForPage(UserJobRecordQuery query);

	UserJobRecord saveOrUpdate(UserJobRecord model);

	int deleteById(Long... ids);
	
	Collection<UserJobRecord> getAll();
}
