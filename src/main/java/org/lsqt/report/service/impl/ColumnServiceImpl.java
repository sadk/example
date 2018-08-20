package org.lsqt.report.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.report.model.Column;
import org.lsqt.report.model.ColumnQuery;
import org.lsqt.report.service.ColumnService;

@Service
public class ColumnServiceImpl implements ColumnService{
	
	@Inject private Db db;
	
	public Page<Column>  queryForPage(ColumnQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Column.class, query);
	}

	public List<Column> getAll(){
		  return db.queryForList("getAll", Column.class);
	}
	
	public Column saveOrUpdate(Column model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(Column.class, Arrays.asList(ids).toArray());
	}
}
