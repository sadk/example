package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.rst.model.CompanyAdmin;
import org.lsqt.rst.model.CompanyAdminQuery;
import org.lsqt.rst.service.CompanyAdminService;

@Service
public class CompanyAdminServiceImpl implements CompanyAdminService{
	
	@Inject private Db db;
	
	public CompanyAdmin getById(Long id) {
		return db.getById(CompanyAdmin.class, id) ;
	}
	
	public List<CompanyAdmin> queryForList(CompanyAdminQuery query) {
		return db.queryForList("queryForPage", CompanyAdmin.class, query);
	}
	
	public Page<CompanyAdmin> queryForPage(CompanyAdminQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), CompanyAdmin.class, query);
	}

	public List<CompanyAdmin> getAll(){
		  return db.queryForList("getAll", CompanyAdmin.class);
	}
	
	public CompanyAdmin saveOrUpdate(CompanyAdmin model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(CompanyAdmin.class, Arrays.asList(ids).toArray());
	}
}
