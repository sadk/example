package org.lsqt.sys.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.sys.model.Variable;
import org.lsqt.sys.model.VariableQuery;

public interface VariableService {
	
	Page<Variable> queryForPage(VariableQuery query);

	Variable saveOrUpdate(Variable model);

	int deleteById(Long... ids);
	
	Collection<Variable> getAll(VariableQuery query);
}
