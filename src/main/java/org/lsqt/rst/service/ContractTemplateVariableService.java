package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.ContractTemplateVariable;
import org.lsqt.rst.model.ContractTemplateVariableQuery;

public interface ContractTemplateVariableService {
	
	ContractTemplateVariable getById(Long id);
	
	List<ContractTemplateVariable> queryForList(ContractTemplateVariableQuery query);
	
	Page<ContractTemplateVariable> queryForPage(ContractTemplateVariableQuery query);

	ContractTemplateVariable saveOrUpdate(ContractTemplateVariable model);

	int deleteById(Long... ids);
	
	Collection<ContractTemplateVariable> getAll();
}
