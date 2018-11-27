package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.rst.model.UserJobRecord;
import org.lsqt.rst.model.UserJobRecordQuery;
import org.lsqt.rst.service.UserJobRecordService;

@Service
public class UserJobRecordServiceImpl implements UserJobRecordService{
	
	@Inject private Db db;
	
	public UserJobRecord getById(Long id) {
		return db.getById(UserJobRecord.class, id) ;
	}
	
	public List<UserJobRecord> queryForList(UserJobRecordQuery query) {
		return db.queryForList("queryForPage", UserJobRecord.class, query);
	}
	
	public Page<UserJobRecord> queryForPage(UserJobRecordQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), UserJobRecord.class, query);
	}

	public List<UserJobRecord> getAll(){
		  return db.queryForList("getAll", UserJobRecord.class);
	}
	
	public UserJobRecord saveOrUpdate(UserJobRecord model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(UserJobRecord.class, Arrays.asList(ids).toArray());
	}
}
