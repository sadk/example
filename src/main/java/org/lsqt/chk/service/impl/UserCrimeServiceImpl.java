package org.lsqt.chk.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.chk.model.UserCrime;
import org.lsqt.chk.model.UserCrimeQuery;
import org.lsqt.chk.service.UserCrimeService;

@Service
public class UserCrimeServiceImpl implements UserCrimeService{
	
	@Inject private Db db;
	
	public UserCrime getById(Long id) {
		return db.getById(UserCrime.class, id) ;
	}
	
	public List<UserCrime> queryForList(UserCrimeQuery query) {
		return db.queryForList("queryForPage", UserCrime.class, query);
	}
	
	public Page<UserCrime> queryForPage(UserCrimeQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), UserCrime.class, query);
	}

	public List<UserCrime> getAll(){
		  return db.queryForList("getAll", UserCrime.class);
	}
	
	public UserCrime saveOrUpdate(UserCrime model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(UserCrime.class, Arrays.asList(ids).toArray());
	}
}
