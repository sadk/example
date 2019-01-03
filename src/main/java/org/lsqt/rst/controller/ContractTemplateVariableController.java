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
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.rst.model.ContractTemplateVariable;
import org.lsqt.rst.model.ContractTemplateVariableQuery;
import org.lsqt.rst.service.ContractTemplateVariableService;




@Controller(mapping={"/rst/contract_template_variable"})
public class ContractTemplateVariableController {
	
	@Inject private ContractTemplateVariableService contractTemplateVariableService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public ContractTemplateVariable getById(Long id) throws IOException {
		return contractTemplateVariableService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<ContractTemplateVariable> queryForPage(ContractTemplateVariableQuery query) throws IOException {
		query.setTenantCode(ContextUtil.getLoginTenantCode());
		return contractTemplateVariableService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<ContractTemplateVariable> getAll() {
		return contractTemplateVariableService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public ContractTemplateVariable saveOrUpdate(ContractTemplateVariable form) {
		form.setTenantCode(ContextUtil.getLoginTenantCode());
		return contractTemplateVariableService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return contractTemplateVariableService.deleteById(list.toArray(new Long[list.size()]));
	}
}
