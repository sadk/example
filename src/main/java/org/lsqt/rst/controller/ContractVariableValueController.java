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
import org.lsqt.rst.model.ContractVariableValue;
import org.lsqt.rst.model.ContractVariableValueQuery;
import org.lsqt.rst.service.ContractVariableValueService;



@Controller(mapping={"/rst/contract_variable_value"})
public class ContractVariableValueController {
	
	@Inject private ContractVariableValueService contractVariableValueService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public ContractVariableValue getById(Long id) throws IOException {
		return contractVariableValueService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<ContractVariableValue> queryForPage(ContractVariableValueQuery query) throws IOException {
		query.setTenantCode(ContextUtil.getLoginTenantCode());
		return contractVariableValueService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<ContractVariableValue> getAll() {
		return contractVariableValueService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public ContractVariableValue saveOrUpdate(ContractVariableValue form) {
		form.setTenantCode(ContextUtil.getLoginTenantCode());
		return contractVariableValueService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return contractVariableValueService.deleteById(list.toArray(new Long[list.size()]));
	}
}
