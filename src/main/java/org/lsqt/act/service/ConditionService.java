package org.lsqt.act.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.act.model.Condition;
import org.lsqt.act.model.ConditionQuery;

public interface ConditionService {
	
	Page<Condition> queryForPage(ConditionQuery query);
	
	List<Condition> queryForList(ConditionQuery query);

	Condition saveOrUpdate(Condition model);

	int deleteById(Long... ids);
	
	Collection<Condition> getAll();
}
