package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.rst.model.StoreCompany;
import org.lsqt.rst.model.StoreCompanyQuery;
import org.lsqt.rst.service.StoreCompanyService;

@Service
public class StoreCompanyServiceImpl implements StoreCompanyService{
	
	@Inject private Db db;
	
	public StoreCompany getById(Long id) {
		return db.getById(StoreCompany.class, id) ;
	}
	
	public List<StoreCompany> queryForList(StoreCompanyQuery query) {
		return db.queryForList("queryForPage", StoreCompany.class, query);
	}
	
	public Page<StoreCompany> queryForPage(StoreCompanyQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), StoreCompany.class, query);
	}

	public List<StoreCompany> getAll(){
		  return db.queryForList("getAll", StoreCompany.class);
	}
	
	public StoreCompany saveOrUpdate(StoreCompany model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(StoreCompany.class, Arrays.asList(ids).toArray());
	}
}
