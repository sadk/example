package org.lsqt.act.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.act.model.NodeButton;
import org.lsqt.act.model.NodeButtonQuery;
import org.lsqt.act.service.NodeButtonService;

@Service
public class NodeButtonServiceImpl implements NodeButtonService{
	
	@Inject private Db db;
	
	public Page<NodeButton>  queryForPage(NodeButtonQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), NodeButton.class, query);
	}
	
	public List<NodeButton>  queryForList(NodeButtonQuery query) {
		return db.queryForList("queryForPage",NodeButton.class,query);
	}


	public List<NodeButton> getAll(){
		  return db.queryForList("getAll", NodeButton.class);
	}
	
	public NodeButton saveOrUpdate(NodeButton model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(NodeButton.class, Arrays.asList(ids).toArray());
	}
}
