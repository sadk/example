package org.lsqt.act.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.act.model.Definition;
import org.lsqt.act.model.DefinitionQuery;
import org.lsqt.components.db.Page;

/**
 * 流程定义相关,见表：act_re_procdef 和 act_re_deployment
 * @author mmyuan
 *
 */
public interface DefinitionService {
	
	Definition getById(String id);
	
	Page<Definition> queryForPage(DefinitionQuery query);
	
	/**
	 * 获取所有的流程定义，包括所有发布的版本
	 * @return
	 */
	Collection<Definition> getAll();
	
	/**
	 * 获取的所有的流程定义，只包括最新版本
	 * @param newest 如果为true只返回最新版本，否则返回所有
	 * @return
	 */
	Collection<Definition> getAll(boolean newest);
	
	
	List<Definition> queryForList(DefinitionQuery query);
	
	
	// ----------------------  操作 -------------------------
	/**
	 * 删除部署的流程(不删除级联的实例)
	 * @param id 流程定义ID
	 */
	void delete(String id);
	
	/**
	 * 删除部署的流程(级联删除流程实例)
	 * @param id 流程定义ID
	 * @param cascade 等于true联删除流程实例
	 */
	void delete(String id,boolean cascade);
	
	/**
	 * 批量删除流程定义(不删除级联的实例)
	 * @param id 流程定义ID
	 */
	void delete(List<String> ids);
	
	/**
	 * 批量删除流程定义(级联删除流程实例)
	 * @param ids
	 * @param cascade
	 */
	void delete(List<String> ids,boolean cascade);
}
