package org.lsqt.api.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.api.model.Definition;
import org.lsqt.api.model.DefinitionQuery;
import org.lsqt.components.db.Page;

public interface DefinitionService {
	
	Definition getById(Long id);
	
	Page<Definition> queryForPage(DefinitionQuery query);
	
	List<Definition> queryForList(DefinitionQuery query);

	Definition saveOrUpdate(Definition model);

	int deleteById(Long... ids);
	
	Collection<Definition> getAll();
}
