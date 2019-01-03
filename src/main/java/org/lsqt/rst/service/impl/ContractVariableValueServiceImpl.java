package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.rst.model.ContractVariableValue;
import org.lsqt.rst.model.ContractVariableValueQuery;
import org.lsqt.rst.service.ContractVariableValueService;

@Service
public class ContractVariableValueServiceImpl implements ContractVariableValueService{
	
	@Inject private Db db;
	
	public ContractVariableValue getById(Long id) {
		return db.getById(ContractVariableValue.class, id) ;
	}
	
	public List<ContractVariableValue> queryForList(ContractVariableValueQuery query) {
		return db.queryForList("queryForPage", ContractVariableValue.class, query);
	}
	
	public Page<ContractVariableValue> queryForPage(ContractVariableValueQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), ContractVariableValue.class, query);
	}

	public List<ContractVariableValue> getAll(){
		  return db.queryForList("getAll", ContractVariableValue.class);
	}
	
	public ContractVariableValue saveOrUpdate(ContractVariableValue model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(ContractVariableValue.class, Arrays.asList(ids).toArray());
	}
}
