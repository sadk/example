package org.lsqt.act.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.act.model.Condition;
import org.lsqt.act.model.ConditionQuery;
import org.lsqt.act.service.ConditionService;

@Service
public class ConditionServiceImpl implements ConditionService{
	
	@Inject private Db db;
	
	public Page<Condition>  queryForPage(ConditionQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Condition.class, query);
	}

	public List<Condition> getAll(){
		  return db.queryForList("getAll", Condition.class);
	}
	
	public Condition saveOrUpdate(Condition model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(Condition.class, Arrays.asList(ids).toArray());
	}

	public List<Condition> queryForList(ConditionQuery query) {
		return db.queryForList("queryForPage", Condition.class, query);
	}
}
