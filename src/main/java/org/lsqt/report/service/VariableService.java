package org.lsqt.report.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.report.model.Variable;
import org.lsqt.report.model.VariableQuery;

public interface VariableService {
	
	Variable getById(Long id);
	
	List<Variable> queryForList(VariableQuery query);
	
	Page<Variable> queryForPage(VariableQuery query);

	Variable saveOrUpdate(Variable model);

	int deleteById(Long... ids);
	
	Collection<Variable> getAll();
}
