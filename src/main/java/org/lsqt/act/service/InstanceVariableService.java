package org.lsqt.act.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.act.model.InstanceVariable;
import org.lsqt.act.model.InstanceVariableQuery;

public interface InstanceVariableService {
	
	Page<InstanceVariable> queryForPage(InstanceVariableQuery query);

	InstanceVariable saveOrUpdate(InstanceVariable model);

	int deleteById(Long... ids);
	
	Collection<InstanceVariable> getAll();
}
