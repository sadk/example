package org.lsqt.sys.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.sys.model.DataSource;
import org.lsqt.sys.model.DataSourceQuery;

public interface DataSourceService {
	
	Page<DataSource> queryForPage(DataSourceQuery query);

	DataSource saveOrUpdate(DataSource model);

	DataSource getById(Long id) ;
		
	int deleteById(Long... ids);
	
	Collection<DataSource> getAll();
	
	Collection<DataSource> queryForList(DataSourceQuery query);
	
	void testConnection(DataSource model) throws Exception;
	
	void testConnectionById(Long id) throws Exception;
	
	/**
	 * 获取系统数据源的数据库
	 * @return
	 */
	List<DataSource> getDatabaseList() ;
}
