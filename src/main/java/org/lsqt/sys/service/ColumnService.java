package org.lsqt.sys.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.sys.model.Column;
import org.lsqt.sys.model.ColumnQuery;

public interface ColumnService {
	
	
	Page<Column> queryForPage(ColumnQuery query);

	Column saveOrUpdate(Column model);

	int deleteById(Long... ids);
	
	Collection<Column> getAll();
	
	/**
	 * 跟据表名，导入表的字段信息
	 * @param tableName
	 */
	void impColumsByTable(Long tableId);
	
}
