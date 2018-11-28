package org.lsqt.act.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.act.model.InstanceVariable;
import org.lsqt.act.model.InstanceVariableQuery;
import org.lsqt.act.service.InstanceVariableService;

@Service
public class InstanceVariableServiceImpl implements InstanceVariableService{
	
	@Inject private Db db;
	
	public Page<InstanceVariable>  queryForPage(InstanceVariableQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), InstanceVariable.class, query);
	}

	public List<InstanceVariable> getAll(){
		  return db.queryForList("getAll", InstanceVariable.class);
	}
	
	public InstanceVariable saveOrUpdate(InstanceVariable model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(InstanceVariable.class, Arrays.asList(ids).toArray());
	}
}
