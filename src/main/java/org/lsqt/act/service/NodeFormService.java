package org.lsqt.act.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.act.model.NodeForm;
import org.lsqt.act.model.NodeFormQuery;
import org.lsqt.components.db.Page;

public interface NodeFormService {
	
	Page<NodeForm> queryForPage(NodeFormQuery query);
	
	List<NodeForm> queryForList(NodeFormQuery query);

	NodeForm saveOrUpdate(NodeForm model);

	int deleteById(Long... ids);
	
	Collection<NodeForm> getAll();
	
	/**
	 * 表单设置，导入节点
	 * @param query
	 * @return
	 */
	Collection<NodeForm> importNode(String definitionId);
}
