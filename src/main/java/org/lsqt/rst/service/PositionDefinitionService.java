package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.PositionDefinition;
import org.lsqt.rst.model.PositionDefinitionQuery;

public interface PositionDefinitionService {
	
	PositionDefinition getById(Long id);
	
	List<PositionDefinition> queryForList(PositionDefinitionQuery query);
	
	Page<PositionDefinition> queryForPage(PositionDefinitionQuery query);

	PositionDefinition saveOrUpdate(PositionDefinition model);

	int deleteById(Long... ids);
	
	Collection<PositionDefinition> getAll();
}
