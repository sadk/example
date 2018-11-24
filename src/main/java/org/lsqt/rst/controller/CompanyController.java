package org.lsqt.rst.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;

import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.rst.model.Company;
import org.lsqt.rst.model.CompanyQuery;
import org.lsqt.rst.service.CompanyService;




@Controller(mapping={"/rst/company"})
public class CompanyController {
	
	@Inject private CompanyService companyService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public Company getById(Long id) throws IOException {
		return companyService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Company> queryForPage(CompanyQuery query) throws IOException {
		return companyService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Company> getAll() {
		return companyService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Company saveOrUpdate(Company form) {
		return companyService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return companyService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
