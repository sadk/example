package org.lsqt.rst.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;

import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.db.orm.ORMappingIdGenerator;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.rst.model.ContractTemplate;
import org.lsqt.rst.model.ContractTemplateQuery;
import org.lsqt.rst.service.ContractTemplateService;




@Controller(mapping={"/rst/contract_template"})
public class ContractTemplateController {
	
	@Inject private ContractTemplateService contractTemplateService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public ContractTemplate getById(Long id) throws IOException {
		return contractTemplateService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<ContractTemplate> queryForPage(ContractTemplateQuery query) throws IOException {
		query.setTenantCode(ContextUtil.getLoginTenantCode());
		return contractTemplateService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<ContractTemplate> getAll() {
		return contractTemplateService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public ContractTemplate saveOrUpdate(ContractTemplate form) {
		form.setTenantCode(ContextUtil.getLoginTenantCode());
		if(form.getId() == null) {
			form.setCode(new ORMappingIdGenerator().getUUID58()+"");
		}
		return contractTemplateService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return contractTemplateService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
