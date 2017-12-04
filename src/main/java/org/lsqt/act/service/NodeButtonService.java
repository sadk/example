package org.lsqt.act.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.act.model.NodeButton;
import org.lsqt.act.model.NodeButtonQuery;

public interface NodeButtonService {
	
	Page<NodeButton> queryForPage(NodeButtonQuery query);
	
	List<NodeButton> queryForList(NodeButtonQuery query);

	NodeButton saveOrUpdate(NodeButton model);

	int deleteById(Long... ids);
	
	Collection<NodeButton> getAll();
}
