package org.lsqt.components.db.orm;


import java.util.List;

import org.lsqt.components.db.Db;
import org.lsqt.components.db.DbException;
import org.lsqt.components.db.Page;

/**
 * 结合ORMapping的DB操作定义
 * 
 * @author yuanmm
 *
 */
public interface ORMappingDb extends Db{
	 
	/**
	 * 
	 * @param nameSpace 实体类名(空间)
	 * @param sqlID SQL语句命名ID
	 * @param requiredType 可以是基础类型、Map、POJO
	 * @param args 
	 * @return
	 * @throws DbException
	 */
	<T> T queryForObject(String nameSpace,String sqlID, Class<T> requiredType, Object... args)  throws DbException ;
	
	/**
	 * 
	 * @param nameSpace 实体类名(空间)
	 * @param sqlID SQL语句命名ID
	 * @param requiredType 可以是基础类型、Map、POJO
	 * @param args 
	 * @return
	 * @throws DbException
	 */
	<T> List<T> queryForList(String nameSpace,String sqlID, Class<T> requiredType, Object... args) throws DbException;
	
	/**
	 * @param nameSpace 实体类名(空间)
	 * @param sqlID SQL语句定义ID
	 * @param requiredType 可以是基础类型、Map、POJO
	 * @param args
	 * @return
	 * @throws DbException
	 */
	<T> Page<T> queryForPage(String nameSpace,String sqlID,int pageIndex,int pageSize,Class<T> requiredType,Object ... args) throws DbException;
	
}
