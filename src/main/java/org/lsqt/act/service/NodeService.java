package org.lsqt.act.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.act.model.Node;
import org.lsqt.act.model.NodeQuery;

public interface NodeService {
	
	Page<Node> queryForPage(NodeQuery query);

	Node saveOrUpdate(Node model);

	int deleteById(Long... ids);
	
	Collection<Node> getAll();
	
	/**
	 * 导入节点
	 * 注：如果节点信息已存在，则删除再添加
	 * @param definitionId 流程定义ID
	 * @return
	 */
	List<Node> importNode(String definitionId);
}
