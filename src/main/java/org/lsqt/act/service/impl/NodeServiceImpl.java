package org.lsqt.act.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
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
					st.setTaskDefType(Node.TASK_DEF_TYPE_USERTASK);
					st.setNodeVariableCopy(Node.NODE_VARIABLE_COPY_NONE);
					st.setNodeJumpType(Node.NODE_JUMP_TYPE_NORMAL);
					st.setCreateTime(new Date());
					st.setUpdateTime(new Date());
					db.save(st);
					
					
					rs.add(st);
				}
				
			}
		}
		
		return rs;
	}
}
