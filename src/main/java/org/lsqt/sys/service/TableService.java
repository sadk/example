package org.lsqt.sys.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.sys.model.Table;
import org.lsqt.sys.model.TableQuery;

public interface TableService {

	/**
	 * 
	 * @param query
	 * @param isSearchDb 如果为true,实时查询数据库的表元信息，否则查询用户管理的表信息
	 * @return
	 */
	Page<Table> queryForPage(TableQuery query);
	
	Table saveOrUpdate(Table model);

	int deleteById(Long... ids);
	
	Collection<Table> getAll();
	
	/**
	 * 从数据库的元信息导入表定义
	 * @param dbName 数据库名称
	 * @param tableName 表名
	 */
	void impTable(String dataSourceCode,String dbName,String tableName);
	
	/**
	 * 同步已有的表定义
	 * @param id
	 */
	void impSyn(Long id);
	
	/**
	 * 
	 * 从数据库里即时查询表信息
	 * @param dbName
	 * @return
	 */
	List<Table> getAllFormDb(String dbName);
	
	/**
	 * 根据表名导出SQL映射文件
	 * @param dbName
	 * @param tableName
	 * @param tmplType 模板类型,具体见CodeTemplate.TMPL_TYPE_XXXX属性
	 * @param tmplResolveType 模板解析方式  0=freemark 1=velocity
	 * @return
	 */
	String toORO(String dbName,String tableName,int tmplType, int tmplResolveType);
}
