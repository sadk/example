package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.rst.model.UserWorkRecord;
import org.lsqt.rst.model.UserWorkRecordQuery;
import org.lsqt.rst.service.UserWorkRecordService;

@Service
public class UserWorkRecordServiceImpl implements UserWorkRecordService{
	
	@Inject private Db db;
	
	public UserWorkRecord getById(Long id) {
		return db.getById(UserWorkRecord.class, id) ;
	}
	
	public List<UserWorkRecord> queryForList(UserWorkRecordQuery query) {
		return db.queryForList("queryForPage", UserWorkRecord.class, query);
	}
	
	public Page<UserWorkRecord> queryForPage(UserWorkRecordQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), UserWorkRecord.class, query);
	}

	public List<UserWorkRecord> getAll(){
		  return db.queryForList("getAll", UserWorkRecord.class);
	}
	
	public UserWorkRecord saveOrUpdate(UserWorkRecord model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(UserWorkRecord.class, Arrays.asList(ids).toArray());
	}
}
