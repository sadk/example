package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.ContractTemplate;
import org.lsqt.rst.model.ContractTemplateQuery;

public interface ContractTemplateService {
	
	ContractTemplate getById(Long id);
	
	List<ContractTemplate> queryForList(ContractTemplateQuery query);
	
	Page<ContractTemplate> queryForPage(ContractTemplateQuery query);

	ContractTemplate saveOrUpdate(ContractTemplate model);

	int deleteById(Long... ids);
	
	Collection<ContractTemplate> getAll();
}
