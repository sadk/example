package org.lsqt.report.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.report.model.Definition;
import org.lsqt.report.model.DefinitionQuery;

public interface DefinitionService {
	
	Definition getById(Long id);
	
	Page<Definition> queryForPage(DefinitionQuery query);

	Definition saveOrUpdate(Definition model);

	int deleteById(Long... ids);
	
	Collection<Definition> getAll();
	
	/**
	 * 导入报表字段
	 * @param id 报表定义Id
	 * @param dataType 数据类型： 1=报表展示用， 2=报表导入用
	 * @param isIncremental 是否是增量导入
	 */
	void importColumn(Long id,Integer dataType,boolean isIncremental);
	
	/**
	 * 生成报表文件（一般为jsp文件）
	 * @param id 报表ID
	 * @return 返回带上下文件的报表文件http请求路径,如:${contextPath}/apps/default/admin/report/files/report_1.jsp
	 */
	String generateReportFile(Long id) throws Exception;
	
	/**
	 * 报表数据查询
	 * @param id 报表ID
	 * @param formMap 表单查询条件
	 * @return 返回数据集
	 */
	Page<Map<String, Object>> search(Long id,Map<String,Object> formMap) throws Exception ;
	
	/**
	 * 获取报表的一页数据(一般大数量导出excel用到,一页一页查询数据)
	 * @param reportDb 报表数据源
	 * @param def 报表配置对象
	 * @param countRequired 是否统计总记录数（一般加载第一页数据的时候会设为true，后续页加载不需要指定true!!!)
	 * @param formMap 查询条件（一般有pageIndex , pageSize分页参数)
	 * @return 返回一页数据
	 * @throws Exception
	 */
	Page<Map<String, Object>> getPerPage(Db reportDb,Definition def,boolean countRequired, Map<String,Object> formMap) throws Exception ;

	/**
	 * 以一页一页循环的方式加载数据,返回所有数据
	 * @param def 报表定义
	 * @param ds 报表数据源
	 * @return 返回报表所有页的记录
	 * @throws Exception
	 */
	List<Map<String, Object>> getDataFromDbByLoopPage(Definition def, javax.sql.DataSource ds) throws Exception ;
	
	/**
	 * 以一页一页循环的方式加载数据,返回所有数据
	 * @param def 报表定义
	 * @param ds 报表数据源
	 * @return 返回报表所有页的记录
	 * @throws Exception
	 */
	List<Map<String, Object>> getDataFromDbByLoopPage(Definition def, javax.sql.DataSource ds,Page.Action action) throws Exception ;
	
}
