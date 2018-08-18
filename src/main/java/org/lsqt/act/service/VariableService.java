package org.lsqt.act.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.act.model.Variable;
import org.lsqt.act.model.VariableQuery;

public interface VariableService {
	
	Page<Variable> queryForPage(VariableQuery query);
	
	List<Variable> queryForList(VariableQuery query);

	Variable saveOrUpdate(Variable model);

	int deleteById(Long... ids);
	
	Collection<Variable> getAll();
}
