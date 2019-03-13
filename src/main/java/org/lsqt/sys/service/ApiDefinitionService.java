package org.lsqt.sys.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.sys.model.ApiDefinition;
import org.lsqt.sys.model.ApiDefinitionQuery;
import org.lsqt.components.db.Page;

public interface ApiDefinitionService {
	
	ApiDefinition getById(Long id);
	
	Page<ApiDefinition> queryForPage(ApiDefinitionQuery query);
	
	List<ApiDefinition> queryForList(ApiDefinitionQuery query);

	ApiDefinition saveOrUpdate(ApiDefinition model);

	int deleteById(Long... ids);
	
	Collection<ApiDefinition> getAll();
}
