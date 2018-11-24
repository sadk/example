package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.rst.model.Company;
import org.lsqt.rst.model.CompanyQuery;
import org.lsqt.rst.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService{
	
	@Inject private Db db;
	
	public Company getById(Long id) {
		return db.getById(Company.class, id) ;
	}
	
	public List<Company> queryForList(CompanyQuery query) {
		return db.queryForList("queryForPage", Company.class, query);
	}
	
	public Page<Company> queryForPage(CompanyQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Company.class, query);
	}

	public List<Company> getAll(){
		  return db.queryForList("getAll", Company.class);
	}
	
	public Company saveOrUpdate(Company model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(Company.class, Arrays.asList(ids).toArray());
	}
}
