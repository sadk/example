package org.lsqt.sys.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.sys.model.CodeContent;
import org.lsqt.sys.model.CodeTemplate;
import org.lsqt.sys.model.CodeTemplateQuery;

public interface CodeTemplateService {
	/** 一键生成单表代码 **/
	String CODEGEN_TYPE_ONECE_GEN_FOR_SINGLE = "1";
	
	/** 一键生成主子表代码 **/
	String CODEGEN_TYPE_ONECE_GEN_FOR_MAIN_SUB = "2";
	
	
	Page<CodeTemplate> queryForPage(CodeTemplateQuery query);

	CodeTemplate saveOrUpdate(CodeTemplate model);

	int deleteById(Long... ids);
	
	Collection<CodeTemplate> getAll();
	
	/**
	 * @param codeGenType 1=一键生成单表代码  2=一键生成主子表代码
	 * @param tableId
	 * @param groupId 类似于MAVEN的groupId,如：com.baidu,org.apache等
	 * @param modules 程序模块名
	 * @param entityName 实体名(不含前com.xxx前缀的名称)
	 * @return 返回代码生成的root目录
	 */
	String codegenForSingle(String codeGenType,Long tableId,String groupId,String modules,String entityName);
	
	/**
	 * 一键生成主子表代码
	 * @param tableId
	 */
	void codegenForMainSubTable(Long tableId);
	
	/**
	 * 一键生成自定义代码
	 * @param tableId
	 */
	void codegenForCustomer(Long tableId);
	
	/**
	 * 生成某个模板的代码
	 * @param templateId 模板ID
	 * @param tableId 表ID
	 * @return
	 */
	CodeContent codegenForTemplate(Long templateId,Long tableId);
}
