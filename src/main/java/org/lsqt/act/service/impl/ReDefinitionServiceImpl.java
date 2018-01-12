package org.lsqt.act.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.lsqt.act.ActUtil;
import org.lsqt.act.controller.DefinitionController.NodeObject;
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
	
	/**
	 * 获取流程图里的用户任务节点
	 * @param definitionId
	 * @return
	 */
	private List<UserTask> getDiagramTaskNodeList(String definitionId) {
		List<UserTask> diagramNodeList = new ArrayList<>(); // 流程图里的任务节点
		RepositoryService repositoryService = ActUtil.getRepositoryService();
		BpmnModel model = repositoryService.getBpmnModel(definitionId);
		if (model != null) {
			Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
			for (FlowElement e : flowElements) {
				if (UserTask.class.isAssignableFrom(e.getClass())) {
					UserTask task = (UserTask) e;
					diagramNodeList.add(task);
				}
			}
		}
		return diagramNodeList;
	}
	
	public ReDefinition copySettings(String sourceDefinitionId,String targetDefinitionId) {
		if(StringUtil.isBlank(sourceDefinitionId)){
			return null;
		}
		
		List<UserTask> diagramNodeList = getDiagramTaskNodeList(targetDefinitionId);
		
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
			
			for(UserTask t: diagramNodeList) {
				if(t.getId().equals(e.getTaskKey())) {
					e.setTaskName(t.getName());
					break;
				}
			}
			db.save(e);
		}
		
		// 拷贝流程节点用户
		NodeUserQuery nodeUserQuery = new NodeUserQuery();
		nodeUserQuery.setDefinitionId(sourceDefinitionId);
		List<NodeUser> nodeUserList = db.queryForList("queryForPage", NodeUser.class, nodeUserQuery);
		for (NodeUser n : nodeUserList) {
			n.setId(null);
			n.setDefinitionId(targetDefinitionId);
			
			for(UserTask t: diagramNodeList) {
				if(t.getId().equals(n.getTaskKey())) {
					n.setName(t.getName());
					break;
				}
			}
			db.save(n);
		}
		
		// 拷贝流程表单按钮
		NodeButtonQuery nodeButtonQuery = new NodeButtonQuery();
		nodeButtonQuery.setDefinitionId(sourceDefinitionId);
		List<NodeButton> nodeButtonList = db.queryForList("queryForPage", NodeButton.class, nodeButtonQuery);
		for (NodeButton n: nodeButtonList) {
			n.setId(null);
			n.setDefinitionId(targetDefinitionId);
			
			for(UserTask t: diagramNodeList) {
				if(t.getId().equals(n.getTaskKey())) {
					n.setTaskName(t.getName());
					break;
				}
			}
			
			db.save(n);
		}
		
		// 拷贝流程流程表单
		NodeFormQuery nodeFormQuery = new NodeFormQuery();
		nodeFormQuery.setDefinitionId(sourceDefinitionId);
		List<NodeForm> formList = db.queryForList("queryForPage", NodeForm.class, nodeFormQuery);
		for(NodeForm n: formList){
			n.setId(null);
			n.setDefinitionId(targetDefinitionId);
			
			for(UserTask t: diagramNodeList) {
				if(t.getId().equals(n.getTaskKey())) {
					n.setTaskName(t.getName());
					break;
				}
			}
			db.save(n);
		}
		
		return reDef;
	}
}
