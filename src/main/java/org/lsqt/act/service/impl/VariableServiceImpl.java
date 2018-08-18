package org.lsqt.act.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.act.model.Variable;
import org.lsqt.act.model.VariableQuery;
import org.lsqt.act.service.VariableService;

@Service
public class VariableServiceImpl implements VariableService{
	
	@Inject private Db db;
	
	public Page<Variable>  queryForPage(VariableQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Variable.class, query);
	}

	public List<Variable> getAll(){
		  return db.queryForList("getAll", Variable.class);
	}
	
	public List<Variable> queryForList(VariableQuery query){
		  return db.queryForList("queryForPage", Variable.class,query);
	}
	
	public Variable saveOrUpdate(Variable model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(Variable.class, Arrays.asList(ids).toArray());
	}
}
