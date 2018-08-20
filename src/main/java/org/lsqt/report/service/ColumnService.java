package org.lsqt.report.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.report.model.Column;
import org.lsqt.report.model.ColumnQuery;

public interface ColumnService {
	
	Page<Column> queryForPage(ColumnQuery query);

	Column saveOrUpdate(Column model);

	int deleteById(Long... ids);
	
	Collection<Column> getAll();
}
