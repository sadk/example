package org.lsqt.components.db.orm;

import java.util.List;
import org.lsqt.components.db.Table;
/**
 * 
 * @author yuanmm
 *
 */
public interface SqlStatementBuilder {

	/**
	 * 构建工厂设施的前置调用(如，从远程数据库配置表里添加语句对象)
	 * @return
	 */
	SqlStatementBuilder buildBefore();

	SqlStatementBuilder build();

	/**
	 * 构建工厂设施的后置调用(如，从远程数据库配置表里添加语句对象后，关闭远程连接)
	 * @return
	 */
	SqlStatementBuilder buildAfter();

	/**
	 * 向工厂添加语句对象
	 * @param stmt
	 */
	void put(SqlStatement stmt);
	
	/**
	 * 获取工厂内的语句对象
	 */
	List<SqlStatement> getAllStatement();
	
	/**
	 * 获取工厂内的数据库表对象
	 * @return
	 */
	List<Table> getAllTable();
}