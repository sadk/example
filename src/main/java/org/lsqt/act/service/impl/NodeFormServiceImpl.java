package org.lsqt.act.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.lsqt.act.ActUtil;
import org.lsqt.act.model.NodeForm;
import org.lsqt.act.model.NodeFormQuery;
import org.lsqt.act.service.NodeFormService;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;

@Service
public class NodeFormServiceImpl implements NodeFormService{
	
	@Inject private Db db;
	
	public Page<NodeForm>  queryForPage(NodeFormQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), NodeForm.class, query);
	}

	public List<NodeForm> getAll(){
		  return db.queryForList("getAll", NodeForm.class);
	}
	
	public NodeForm saveOrUpdate(NodeForm model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(NodeForm.class, Arrays.asList(ids).toArray());
	}

	public Collection<NodeForm> importNode(String definitionId) {
		List<NodeForm> list = new ArrayList<> ();
		
		if (StringUtil.isBlank(definitionId)) {
			return list;
		}
		
		db.executeUpdate(String.format("delete from %s where definition_id=? and data_type=?",db.getFullTable(NodeForm.class)),definitionId,NodeForm.DATA_TYPE_TASK_FORM);
		
		RepositoryService repositoryService = ActUtil.getRepositoryService();
		BpmnModel model = repositoryService.getBpmnModel(definitionId);
		
		org.activiti.engine.repository.ProcessDefinition def = repositoryService.createProcessDefinitionQuery().processDefinitionId(definitionId).singleResult();
		
		if (model != null) {
			Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
			for (FlowElement e : flowElements) {

				if (UserTask.class.isAssignableFrom(e.getClass())) {// 只导入用户节点
					UserTask task = (UserTask) e;
					NodeForm st = new NodeForm();
					st.setDefinitionId(definitionId);
					st.setTaskKey(task.getId());
					st.setTaskName(task.getName());
					if(def!=null) {
						st.setFormName(def.getName()+"表单");
					}
					
					st.setDataType(NodeForm.DATA_TYPE_TASK_FORM); // 节点表单
					st.setFormType(NodeForm.FORM_TYPE_URL); // url表单
					
					st.setCreateTime(new Date());
					st.setUpdateTime(new Date());
					db.save(st);
					
					list.add(st);
				}
			}
		}
		
		return list;
	}
}
