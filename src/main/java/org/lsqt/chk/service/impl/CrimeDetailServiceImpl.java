package org.lsqt.chk.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.chk.model.CrimeDetail;
import org.lsqt.chk.model.CrimeDetailQuery;
import org.lsqt.chk.service.CrimeDetailService;

@Service
public class CrimeDetailServiceImpl implements CrimeDetailService{
	
	@Inject private Db db;
	
	public CrimeDetail getById(Long id) {
		return db.getById(CrimeDetail.class, id) ;
	}
	
	public List<CrimeDetail> queryForList(CrimeDetailQuery query) {
		return db.queryForList("queryForPage", CrimeDetail.class, query);
	}
	
	public Page<CrimeDetail> queryForPage(CrimeDetailQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), CrimeDetail.class, query);
	}

	public List<CrimeDetail> getAll(){
		  return db.queryForList("getAll", CrimeDetail.class);
	}
	
	public CrimeDetail saveOrUpdate(CrimeDetail model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(CrimeDetail.class, Arrays.asList(ids).toArray());
	}
}
