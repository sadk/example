package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.rst.model.ContractTemplateVariable;
import org.lsqt.rst.model.ContractTemplateVariableQuery;
import org.lsqt.rst.service.ContractTemplateVariableService;

@Service
public class ContractTemplateVariableServiceImpl implements ContractTemplateVariableService{
	
	@Inject private Db db;
	
	public ContractTemplateVariable getById(Long id) {
		return db.getById(ContractTemplateVariable.class, id) ;
	}
	
	public List<ContractTemplateVariable> queryForList(ContractTemplateVariableQuery query) {
		return db.queryForList("queryForPage", ContractTemplateVariable.class, query);
	}
	
	public Page<ContractTemplateVariable> queryForPage(ContractTemplateVariableQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), ContractTemplateVariable.class, query);
	}

	public List<ContractTemplateVariable> getAll(){
		  return db.queryForList("getAll", ContractTemplateVariable.class);
	}
	
	public ContractTemplateVariable saveOrUpdate(ContractTemplateVariable model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(ContractTemplateVariable.class, Arrays.asList(ids).toArray());
	}
}
