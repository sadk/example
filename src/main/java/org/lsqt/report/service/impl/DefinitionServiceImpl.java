package org.lsqt.report.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.report.model.Definition;
import org.lsqt.report.model.DefinitionQuery;
import org.lsqt.report.service.DefinitionService;

@Service
public class DefinitionServiceImpl implements DefinitionService{
	
	@Inject private Db db;
	
	public Page<Definition>  queryForPage(DefinitionQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Definition.class, query);
	}

	public List<Definition> getAll(){
		  return db.queryForList("getAll", Definition.class);
	}
	
	public Definition saveOrUpdate(Definition model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(Definition.class, Arrays.asList(ids).toArray());
	}
}
