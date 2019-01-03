package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.ContractTemplateVariable;
import org.lsqt.rst.model.ContractVariableValue;
import org.lsqt.rst.model.ContractVariableValueQuery;

public interface ContractVariableValueService {
	
	ContractVariableValue getById(Long id);
	
	List<ContractVariableValue> queryForList(ContractVariableValueQuery query);
	
	Page<ContractVariableValue> queryForPage(ContractVariableValueQuery query);

	ContractVariableValue saveOrUpdate(ContractVariableValue model);

	int deleteById(Long... ids);
	
	Collection<ContractVariableValue> getAll();
}
