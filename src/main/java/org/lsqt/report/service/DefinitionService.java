package org.lsqt.report.service;

import java.util.Collection;
import java.util.Map;

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
	
}
