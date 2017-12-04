package org.lsqt.act.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.act.model.Node;
import org.lsqt.act.model.NodeForm;
import org.lsqt.act.model.NodeFormQuery;
import org.lsqt.act.model.NodeQuery;

public interface NodeFormService {
	
	Page<NodeForm> queryForPage(NodeFormQuery query);

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
