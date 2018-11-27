package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.rst.model.PersonalVideoInfo;
import org.lsqt.rst.model.PersonalVideoInfoQuery;
import org.lsqt.rst.service.PersonalVideoInfoService;

@Service
public class PersonalVideoInfoServiceImpl implements PersonalVideoInfoService{
	
	@Inject private Db db;
	
	public PersonalVideoInfo getById(Long id) {
		return db.getById(PersonalVideoInfo.class, id) ;
	}
	
	public List<PersonalVideoInfo> queryForList(PersonalVideoInfoQuery query) {
		return db.queryForList("queryForPage", PersonalVideoInfo.class, query);
	}
	
	public Page<PersonalVideoInfo> queryForPage(PersonalVideoInfoQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), PersonalVideoInfo.class, query);
	}

	public List<PersonalVideoInfo> getAll(){
		  return db.queryForList("getAll", PersonalVideoInfo.class);
	}
	
	public PersonalVideoInfo saveOrUpdate(PersonalVideoInfo model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(PersonalVideoInfo.class, Arrays.asList(ids).toArray());
	}
}
