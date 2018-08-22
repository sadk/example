package org.lsqt.report.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.report.model.Column;
import org.lsqt.report.model.ColumnQuery;

public interface ColumnService {
	
	Page<Column> queryForPage(ColumnQuery query);
	
	List<Column> queryForList(ColumnQuery query);

	Column saveOrUpdate(Column model);

	Column getById(Long id);
	
	int deleteById(Long... ids);
	
	Collection<Column> getAll();
}
