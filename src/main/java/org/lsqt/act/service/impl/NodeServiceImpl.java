package org.lsqt.act.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.lsqt.act.ActUtil;
import org.lsqt.act.model.Node;
import org.lsqt.act.model.NodeQuery;
import org.lsqt.act.service.NodeService;

@Service
public class NodeServiceImpl implements NodeService{
	
	@Inject private Db db;
	
	public Page<Node>  queryForPage(NodeQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Node.class, query);
	}

	public List<Node> getAll(){
		  return db.queryForList("getAll", Node.class);
	}
	
	public Node saveOrUpdate(Node model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(Node.class, Arrays.asList(ids).toArray());
	}

	public List<Node> importNode(String definitionId) {
		List<Node> rs = new ArrayList<>();
		if (StringUtil.isBlank(definitionId)) {
			return rs;
		}
		db.executeUpdate("delete from "+db.getFullTable(Node.class)+" where definition_id=?", definitionId);
		
		RepositoryService repositoryService = ActUtil.getRepositoryService();
		BpmnModel model = repositoryService.getBpmnModel(definitionId);
		if (model != null) {
			Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
			for (FlowElement e : flowElements) {

				if (UserTask.class.isAssignableFrom(e.getClass())) {// 只导入用户节点
					UserTask task = (UserTask) e;
					Node st = new Node();
					st.setDefinitionId(definitionId);
					st.setCandidateGroups(task.getCandidateGroups());
					st.setCandidateUsers(task.getCandidateUsers());
					st.setFormKey(task.getFormKey());
					st.setFormProperties(task.getFormProperties());
					st.setTaskKey(task.getId());
					st.setTaskName(task.getName());
					st.setTaskBizType(Node.TASK_BIZ_TYPE_UNDRAFTNODE); // 普通任务节点
					st.setTaskDefType(Node.TASK_DEF_TYPE_USERTASK);
					st.setNodeVariableCopy(Node.NODE_VARIABLE_COPY_NONE);
					st.setNodeJumpType(Node.NODE_JUMP_TYPE_NORMAL);
					st.setCreateTime(new Date());
					st.setUpdateTime(new Date());
					st.setRemark(task.getDocumentation());
					//db.save(st);
					rs.add(st);
				}
			}
			
			resolveStartEndNodeType(definitionId,rs); // 解析出拟稿节和结束结点
			
			if(!rs.isEmpty()) {
				db.batchSave(rs);
			}
		}
		
		return rs;
	}
	
	/**
	 * 解析拟稿结点和最后结点类型
	 * @param definitionId 流程定义ID
	 * @param list 流程图文件里的节点（UserTask)
	 */
	public void resolveStartEndNodeType(String definitionId ,List<Node> list) {

		if (list != null && list.size() > 0) {
			RepositoryService repositoryService = ActUtil.getRepositoryService();

			ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(definitionId);
			List<ActivityImpl> activitiList = def.getActivities();

			String draftNodeId = null;
			List<String> endNodeIdList = new ArrayList<>();
			for (ActivityImpl e : activitiList) {
				
				List<PvmTransition> pvmList = e.getOutgoingTransitions(); // 当前节点的出口,如果当前节点的出口是“结束”元素，则为“结束结点”类型
				for (PvmTransition p : pvmList) {
					TransitionImpl t = (TransitionImpl) p;
					
					ActivityImpl dest = t.getDestination();
					if (dest!=null && dest.getProperty("type")!=null) { // 判断是否是结束结点
						String type = dest.getProperty("type").toString();
						if("endEvent".equals(type)) {
							endNodeIdList.add(e.getId());
						}
					 
					}
				}
				
				List<PvmTransition> inList = e.getIncomingTransitions(); // 当前节点的入口,如果当前节点的出口是“开始”元素，则为“拟稿结点”类型
				for (PvmTransition p : inList) {
					TransitionImpl t = (TransitionImpl) p;
					ActivityImpl source =  t.getSource();
					if (source!=null && source.getProperty("type")!=null) { // 判断是否是开始结点
						String type = source.getProperty("type").toString();
						if("startEvent".equals(type)) {
							draftNodeId = e.getId();
							break;
						}
					 
					}
				}
			}
			
			
			
			// 自动填充节点类型
			for(Node node: list) {
				if(node.getTaskKey().equals(draftNodeId)) {
					node.setTaskBizType(Node.TASK_BIZ_TYPE_DRAFTNODE);
					node.setNodeJumpType(Node.NODE_JUMP_TYPE_AUTO_JUMP);
				}
				for(String e: endNodeIdList) {
					if(node.getTaskKey().equals(e)) {
						node.setTaskBizType(Node.TASK_BIZ_TYPE_LASTNODE);
						break;
					}
				}
			}

		}
	}
	
	/**
	 * 设置 会签结点类型
	 * @param definitionId 流程定义ID
	 * @param list 数据库定义的流程结点
	 */
	public void setMeetingNodeType(String definitionId ,List<Node> list) {
		if (list == null || StringUtil.isBlank(definitionId)) {
			return ;
		}
		
		NodeQuery query = new NodeQuery();
		query.setDefinitionId(definitionId);
		query.setTaskBizType(Node.TASK_BIZ_TYPE_MEETINGNODE);
		List<Node> meetingList = db.queryForList("queryForPage", Node.class, query);
		if (meetingList == null || meetingList.isEmpty()) {
			return;
		}
		for (Node e : meetingList) {
			for (Node t: list) {
				if(e.getTaskKey().equals(t.getTaskKey())) {
					t.setTaskBizType(Node.TASK_BIZ_TYPE_MEETINGNODE);
					break;
				}
			}
		}
	}
}
