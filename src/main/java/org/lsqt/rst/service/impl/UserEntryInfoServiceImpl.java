package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.rst.model.UserEntryInfo;
import org.lsqt.rst.model.UserEntryInfoQuery;
import org.lsqt.rst.service.UserEntryInfoService;

@Service
public class UserEntryInfoServiceImpl implements UserEntryInfoService{
	
	@Inject private Db db;
	
	public UserEntryInfo getById(Long id) {
		return db.getById(UserEntryInfo.class, id) ;
	}
	
	public List<UserEntryInfo> queryForList(UserEntryInfoQuery query) {
		return db.queryForList("queryForPage", UserEntryInfo.class, query);
	}
	
	public Page<UserEntryInfo> queryForPage(UserEntryInfoQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), UserEntryInfo.class, query);
	}

	public List<UserEntryInfo> getAll(){
		  return db.queryForList("getAll", UserEntryInfo.class);
	}
	
	public UserEntryInfo saveOrUpdate(UserEntryInfo model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(UserEntryInfo.class, Arrays.asList(ids).toArray());
	}
}
