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
import org.lsqt.act.model.UserRuleMatrixFlow;
import org.lsqt.act.model.UserRuleMatrixFlowQuery;
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
		
		final Definition targetDefinition = db.getById(Definition.class, targetDefinitionId);
		
		List<UserTask> diagramNodeList = getDiagramTaskNodeList(targetDefinitionId);
		
		ReDefinitionQuery query = new ReDefinitionQuery();
		query.setDefinitionId(sourceDefinitionId);
		ReDefinition reDef= db.queryForObject("queryForPage",ReDefinition.class, query);
		if(reDef == null) {
			return null;
		}
		
		//1.拷贝流程定义配置
		reDef.setId(null);
		reDef.setDefinitionId(targetDefinitionId);
		db.save(reDef);
		
		
		
		
		
		//2.拷贝流程节点配置
		NodeQuery nodeQuery = new NodeQuery();
		nodeQuery.setDefinitionId(sourceDefinitionId);
		List<Node> nodeList = db.queryForList("queryForPage",Node.class, nodeQuery); //老流程节点
		List<Node> targetNodeList = new ArrayList<>(); // 新流程节点
		for(Node e: nodeList) {
			e.setId(null);
			e.setDefinitionId(targetDefinitionId);
			
			for(UserTask t: diagramNodeList) {
				if(t.getId().equals(e.getTaskKey())) {
					e.setTaskName(t.getName());
					e.setRemark(t.getDocumentation());
					e.setTaskBizType(Node.TASK_BIZ_TYPE_UNDRAFTNODE);
					targetNodeList.add(e);
					break;
				}
			}
		}
		// 如果是新节点，也要导入进去
		for (UserTask u : diagramNodeList) {
			boolean isExists = false;
			for (Node n : nodeList) {
				if (u.getId().equals(n.getTaskKey())) {
					isExists = true;
					break;
				}
			}

			if (!isExists) {
				Node temp = new Node();
				temp.setDefinitionId(targetDefinitionId);
				temp.setTaskKey(u.getId());
				temp.setTaskName(u.getName());
				temp.setTaskBizType(Node.TASK_BIZ_TYPE_UNDRAFTNODE);
				temp.setNodeVariableCopy(Node.NODE_VARIABLE_COPY_NONE);
				temp.setNodeJumpType(Node.NODE_JUMP_TYPE_NORMAL);
				temp.setTaskDefType(Node.TASK_DEF_TYPE_USERTASK);
				temp.setRemark(u.getDocumentation());
				targetNodeList.add(temp);
			}
		}
		
		if (!targetNodeList.isEmpty()) {
			NodeServiceImpl util = new NodeServiceImpl();
			util.resolveStartEndNodeType(targetDefinitionId, targetNodeList);
			db.batchSave(targetNodeList);
		}
		
		
		
		
		
		//3.拷贝流程节点用户
		NodeUserQuery nodeUserQuery = new NodeUserQuery();
		nodeUserQuery.setDefinitionId(sourceDefinitionId);
		List<NodeUser> nodeUserList = db.queryForList("queryForPage", NodeUser.class, nodeUserQuery);
		List<NodeUser> targetNodeUserList = new ArrayList<>();
		for (NodeUser n : nodeUserList) {
			n.setId(null);
			n.setDefinitionId(targetDefinitionId);
			for(UserTask t: diagramNodeList) {
				if(t.getId().equals(n.getTaskKey())) {
					n.setName(n.getName());
					n.setRemark(t.getDocumentation());
					targetNodeUserList.add(n);
					break;
				}
			}
		}
		/*
		// 如果是新节点，也要导入进去
		for (UserTask u: diagramNodeList) {
			boolean isExists = false;
			for (NodeUser n: nodeUserList) {
				if(u.getId().equals(n.getTaskKey())) {
					isExists = true;
					break;
				}
			}
			
			if(!isExists) {
				NodeUser temp = new NodeUser();
				temp.setDefinitionId(targetDefinitionId);
				temp.setDefinitionName(targetDefinition.getName());
				temp.setTaskKey(u.getId());
				temp.setUserFromType(NodeUser.USER_FROM_TYPE_INTERNAL);
				temp.setRemark(u.getDocumentation());
				targetNodeUserList.add(temp);
			}
			
		}*/
		if (!targetNodeUserList.isEmpty()) {
			db.batchSave(targetNodeUserList);
		}
		
		
		
		//4.拷贝流程表单按钮
		NodeButtonQuery nodeButtonQuery = new NodeButtonQuery();
		nodeButtonQuery.setDefinitionId(sourceDefinitionId);
		List<NodeButton> nodeButtonList = db.queryForList("queryForPage", NodeButton.class, nodeButtonQuery);
		List<NodeButton> targetButtonList = new ArrayList<>();
		for (NodeButton n: nodeButtonList) {
			n.setId(null);
			n.setDefinitionId(targetDefinitionId);
			
			for(UserTask t: diagramNodeList) {
				if(t.getId().equals(n.getTaskKey())) {
					n.setTaskName(t.getName());
					n.setRemark(t.getDocumentation());
					targetButtonList.add(n);
					break;
				}
			}
		}
		if(!targetButtonList.isEmpty()) {
			db.batchSave(targetButtonList);
		}
		
		
		
		
		//5.拷贝流程表单
		NodeFormQuery nodeFormQuery = new NodeFormQuery();
		nodeFormQuery.setDefinitionId(sourceDefinitionId);
		List<NodeForm> formList = db.queryForList("queryForPage", NodeForm.class, nodeFormQuery);
		List<NodeForm> targetFormList = new ArrayList<>();
		for(NodeForm n: formList){
			n.setId(null);
			n.setDefinitionId(targetDefinitionId);
			
			for(UserTask t: diagramNodeList) {
				if(t.getId().equals(n.getTaskKey())) {
					n.setTaskName(t.getName());
					n.setRemark(t.getDocumentation());
					
					targetFormList.add(n);
					break;
				}
			}
		}
		
		// 如果是新节点，也要导入进去
		for (UserTask u: diagramNodeList) {
			boolean isExists = false;
			for (NodeForm n: formList) {
				if(u.getId().equals(n.getTaskKey())) {
					isExists = true;
					break;
				}
			}
			if(!isExists) {
				NodeForm temp = new NodeForm();
				temp.setDefinitionId(targetDefinitionId);
				temp.setTaskKey(u.getId());
				temp.setTaskName(u.getName());
				temp.setFormName(targetDefinition.getName()+"表单");
				temp.setDataType(NodeForm.DATA_TYPE_TASK_FORM);
				temp.setFormType(NodeForm.FORM_TYPE_URL);
				temp.setRemark(u.getDocumentation());
				targetFormList.add(temp);
			}
		}
		
		//全局表单记录
		NodeFormQuery nfQuery = new NodeFormQuery();
		nfQuery.setDataType(NodeForm.DATA_TYPE_GLOBAL_FORM);
		nfQuery.setDefinitionId(sourceDefinitionId);
		NodeForm globalForm = db.queryForObject("queryForPage", NodeForm.class, nfQuery);
		if(globalForm!=null) {
			globalForm.setId(null);
			globalForm.setDefinitionId(targetDefinitionId);
			targetFormList.add(globalForm);
		}
		
		if (targetFormList.size() > 0) {
			db.batchSave(targetFormList);
		}
		
		
		
		
		//6.拷贝用户规则矩阵设置（只copy新流程存在的节点）
		List<UserRuleMatrixFlow> newList = new ArrayList<>();

		UserRuleMatrixFlowQuery ruleQuery = new UserRuleMatrixFlowQuery();
		ruleQuery.setDefinitionId(sourceDefinitionId);
		List<UserRuleMatrixFlow> list = db.queryForList("queryForPage", UserRuleMatrixFlow.class, ruleQuery);
		for (UserRuleMatrixFlow e : list) {
			boolean isExists = false;
			for (UserTask d : diagramNodeList) {
				if (StringUtil.isNotBlank(e.getTaskKey()) && e.getTaskKey().equals(d.getId())) {
					e.setTaskName(d.getName());
					isExists = true;
					break;
				}
			}
			if (isExists) {
				e.setId(null);
				e.setDefinitionId(targetDefinitionId);
				e.setVersion(targetDefinition.getVersion() + "");
				newList.add(e);
			}
		}
		
		if (!newList.isEmpty()) {
			db.batchSave(newList);
		}
		return reDef;
	}
}
