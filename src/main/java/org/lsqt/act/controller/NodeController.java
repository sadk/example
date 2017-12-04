package org.lsqt.act.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.act.model.Node;
import org.lsqt.act.model.NodeQuery;
import org.lsqt.act.service.NodeService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;

import com.alibaba.fastjson.JSON;



@Controller(mapping={"/act/node","/nv2/act/node"})
public class NodeController {
	@Inject private NodeService nodeService;
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public Node getById(Long id) {
		return db.getById(Node.class, id);
	}
	
	@RequestMapping(mapping = { "/delete_by_ids", "/m/delete_by_ids" })
	public void deleteByIds(String ids) {
		if(StringUtil.isNotBlank(ids)) {
			List<Long> idArray = StringUtil.split(Long.class,ids, ",");
			db.deleteById(Node.class, idArray.toArray());
		}
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Node> queryForPage(NodeQuery query) throws IOException {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Node.class, query);  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Node> getAll() {
		return db.queryForList("getAll", Node.class);
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Node saveOrUpdate(Node form) {
		return db.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/save_or_update_json", "/m/save_or_update_json" })
	public void saveOrUpdate(String data) {
		if(StringUtil.isNotBlank(data)){
			List<Node> nodeLit = JSON.parseArray(data, Node.class);
			for (Node e : nodeLit) {
				db.saveOrUpdate(e,"nodeJumpType","taskBizType","nodeVariableCopy");
			}
		}
	}
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public Collection<Node> list(NodeQuery query) {
		List<Node> dbList = db.queryForList("queryForPage", Node.class, query);
		return dbList;
	}
	
	@RequestMapping(mapping = { "/import_node", "/m/import_node" })
	public Collection<Node> importNode(NodeQuery query) {
		return  nodeService.importNode(query.getDefinitionId());
		
	}
}
