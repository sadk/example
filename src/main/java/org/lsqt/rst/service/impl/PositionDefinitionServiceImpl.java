package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.rst.model.PositionDefinition;
import org.lsqt.rst.model.PositionDefinitionQuery;
import org.lsqt.rst.service.PositionDefinitionService;

@Service
public class PositionDefinitionServiceImpl implements PositionDefinitionService{
	
	@Inject private Db db;
	
	public PositionDefinition getById(Long id) {
		return db.getById(PositionDefinition.class, id) ;
	}
	
	public List<PositionDefinition> queryForList(PositionDefinitionQuery query) {
		return db.queryForList("queryForPage", PositionDefinition.class, query);
	}
	
	public Page<PositionDefinition> queryForPage(PositionDefinitionQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), PositionDefinition.class, query);
	}

	public List<PositionDefinition> getAll(){
		  return db.queryForList("getAll", PositionDefinition.class);
	}
	
	public PositionDefinition saveOrUpdate(PositionDefinition model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(PositionDefinition.class, Arrays.asList(ids).toArray());
	}
}
