package org.lsqt.act.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.act.model.Definition;
import org.lsqt.act.model.Node;
import org.lsqt.act.model.NodeButton;
import org.lsqt.act.model.NodeButtonQuery;
import org.lsqt.act.model.NodeForm;
import org.lsqt.act.model.NodeFormQuery;
import org.lsqt.act.model.NodeQuery;
import org.lsqt.act.model.NodeUser;
import org.lsqt.act.model.NodeUserQuery;
import org.lsqt.act.model.ReDefinition;
import org.lsqt.act.model.ReDefinitionQuery;
import org.lsqt.act.service.ReDefinitionService;

@Service
public class ReDefinitionServiceImpl implements ReDefinitionService{
	
	@Inject private Db db;
	
	public Page<ReDefinition>  queryForPage(ReDefinitionQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), ReDefinition.class, query);
	}

	public List<ReDefinition> getAll(){
		  return db.queryForList("getAll", ReDefinition.class);
	}
	
	public ReDefinition saveOrUpdate(ReDefinition model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(ReDefinition.class, Arrays.asList(ids).toArray());
	}
	
	
	public ReDefinition copySettings(String sourceDefinitionId,String targetDefinitionId) {
		if(StringUtil.isBlank(sourceDefinitionId)){
			return null;
		}
		
		ReDefinitionQuery query = new ReDefinitionQuery();
		query.setDefinitionId(sourceDefinitionId);
		ReDefinition reDef= db.queryForObject("queryForPage",ReDefinition.class, query);
		if(reDef == null) {
			return null;
		}
		
		// 拷贝流程定义配置
		reDef.setId(null);
		db.save(reDef);
		
		
		// 拷贝流程节点配置
		NodeQuery nodeQuery = new NodeQuery();
		nodeQuery.setDefinitionId(sourceDefinitionId);
		List<Node> nodeList = db.queryForList("queryForPage",Node.class, nodeQuery);
		for(Node e: nodeList) {
			e.setId(null);
			e.setDefinitionId(targetDefinitionId);
			db.save(e);
		}
		
		// 拷贝流程节点用户
		NodeUserQuery nodeUserQuery = new NodeUserQuery();
		nodeUserQuery.setDefinitionId(sourceDefinitionId);
		List<NodeUser> nodeUserList = db.queryForList("queryForPage", NodeUser.class, nodeUserQuery);
		for (NodeUser n : nodeUserList) {
			n.setId(null);
			n.setDefinitionId(targetDefinitionId);
			db.save(n);
		}
		
		// 拷贝流程表单按钮
		NodeButtonQuery nodeButtonQuery = new NodeButtonQuery();
		nodeButtonQuery.setDefinitionId(sourceDefinitionId);
		List<NodeButton> nodeButtonList = db.queryForList("queryForPage", NodeButton.class, nodeButtonQuery);
		for (NodeButton n: nodeButtonList) {
			n.setId(null);
			n.setDefinitionId(targetDefinitionId);
			db.save(n);
		}
		
		// 拷贝流程流程表单
		NodeFormQuery nodeFormQuery = new NodeFormQuery();
		nodeFormQuery.setDefinitionId(sourceDefinitionId);
		List<NodeForm> formList = db.queryForList("queryForPage", NodeForm.class, nodeFormQuery);
		for(NodeForm n: formList){
			n.setId(null);
			n.setDefinitionId(targetDefinitionId);
			db.save(n);
		}
		
		return reDef;
	}
}
