package org.lsqt.report.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.report.model.Definition;
import org.lsqt.report.model.DefinitionQuery;

public interface DefinitionService {
	Definition getById(Long id);
	
	Page<Definition> queryForPage(DefinitionQuery query);

	Definition saveOrUpdate(Definition model);

	int deleteById(Long... ids);
	
	Collection<Definition> getAll();
	
	/**
	 * 导入报表字段
	 */
	void importColumn(Long id);
}
