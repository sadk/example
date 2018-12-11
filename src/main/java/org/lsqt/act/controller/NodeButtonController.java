package org.lsqt.act.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.lsqt.act.ActUtil;
import org.lsqt.act.model.Definition;
import org.lsqt.act.model.NodeButton;
import org.lsqt.act.model.NodeButtonQuery;
import org.lsqt.act.model.NodeForm;
import org.lsqt.act.model.NodeFormQuery;
import org.lsqt.act.model.NodeQuery;
import org.lsqt.act.service.NodeButtonService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.sys.service.DictionaryService;

import com.alibaba.fastjson.JSON;




@Controller(mapping={"/act/node_button","/nv2/act/node_button"})
public class NodeButtonController {
	
	@Inject private NodeButtonService nodeButtonService; 
	@Inject private DictionaryService dictionaryService;
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public NodeButton getById(Long id) {
		return db.getById(NodeButton.class, id);
	}
	
	@RequestMapping(mapping = { "/delete_by_ids", "/m/delete_by_ids" })
	public void deleteByIds(String ids) {
		if(StringUtil.isNotBlank(ids)) {
			List<Long> idArray = StringUtil.split(Long.class,ids, ",");
			db.deleteById(NodeButton.class, idArray.toArray());
		}
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<NodeButton> queryForPage(NodeButtonQuery query) throws IOException {
		return nodeButtonService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<NodeButton> getAll() {
		return nodeButtonService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public NodeButton saveOrUpdate(NodeButton form) {
		return nodeButtonService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/save_or_update_json", "/m/save_or_update_json" })
	public List<NodeButton> saveOrUpdateJSON(String data) {
		List<NodeButton> list = new ArrayList<>();
		if (StringUtil.isNotBlank(data)) {
			list = JSON.parseArray(data, NodeButton.class);
			if(list!=null && list.size()>0) {
				for(NodeButton e: list) {
					db.saveOrUpdate(e);
				}
			}
		}
		return list;
	}
	
	@RequestMapping(mapping = { "/update_simple", "/m/update_simple" })
	public NodeButton updateSimple(NodeButton form) {
		form.setUpdateTime(new Date());
		if(StringUtil.isBlank(form.getAfterScriptType())){
			form.setAfterScriptType(NodeButton.SCRIPT_TYPE_URL);
		}
		if(StringUtil.isBlank(form.getBeforeScriptType())) {
			form.setBeforeScriptType(NodeButton.SCRIPT_TYPE_URL);
		}
		return db.update(form, "beforeScript","beforeScriptType","afterScript","afterScriptType","updateTime","btnName","remark","btnCode");
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return nodeButtonService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public Collection<NodeButton> list(NodeButtonQuery query) {

		List<NodeButton> dbList = db.queryForList("queryForPage", NodeButton.class, query);
		for(NodeButton n: dbList) {
			n.setTaskName(query.getTaskName());
		}
		return dbList;
	}
	
	@RequestMapping(mapping = { "/init", "/m/init" },text="初使化表单里的审批按钮")
	public Collection<NodeButton> init(NodeButtonQuery query) {
		List<NodeButton> rs = new ArrayList<>();

		List<NodeButton> dbList = db.queryForList("queryForPage", NodeButton.class, query);

		db.executeUpdate("delete from ext_node_button where definition_id=? and task_key=?", query.getDefinitionId(),query.getTaskKey());
		if (dbList == null || dbList.size() == 0) { // 如果没有，加载系统字典里定义的
			List<Dictionary> list = dictionaryService.getOptionByCode("form_node_button_type", Application.APP_CODE_DEFAULT);
			if (list != null) {
				for (Dictionary d : list) {
					NodeButton nb = new NodeButton();
					nb.setDefinitionId(query.getDefinitionId());
					nb.setTaskKey(query.getTaskKey());
					nb.setDataType(query.getDataType());
					
					nb.setBtnName(d.getName());
					nb.setBtnCode(d.getCode());
					nb.setBtnType(Integer.valueOf(d.getValue()));
					nb.setTaskName(query.getTaskName());

					nb.setRemark(d.getRemark());
					
					db.save(nb);
					rs.add(nb);
				}
			}

			return rs;
		}

		for (NodeButton n : dbList) {
			n.setTaskName(query.getTaskName());
		}
		return dbList;
	}
	
	/**
	 * 初始化所有按钮
	 * @param query
	 * @return
	 */
	@RequestMapping(mapping = { "/init_all"},text="初使化表单里所有的审批按钮")
	public Result initAll(NodeFormQuery query) {
		System.out.println(query.getDefinitionId());
		
		List<Dictionary> list = dictionaryService.getOptionByCode("form_node_button_type", Application.APP_CODE_DEFAULT);

		List<NodeForm> dbList = db.queryForList("queryForPage", NodeForm.class, query);

		db.executeUpdate("delete from ext_node_button where definition_id=? and data_type=?", query.getDefinitionId(),NodeButton.DATA_TYPE_TASK_BUTTON);
		
		List<NodeButton> models = new ArrayList<>();
		for (NodeForm nodeForm : dbList) {
			
			if (list != null) {
				for (Dictionary d : list) {
					NodeButton nb = new NodeButton();
					nb.setDefinitionId(nodeForm.getDefinitionId());
					nb.setTaskKey(nodeForm.getTaskKey());
					nb.setDataType(nodeForm.getDataType());
					nb.setBtnName(d.getName());
					nb.setBtnCode(d.getCode());
					nb.setBtnType(Integer.parseInt(d.getValue()));
					nb.setTaskName(nodeForm.getTaskName());
					nb.setRemark(d.getRemark());
					//db.save(nb);
					models.add(nb);
				}
			}
			
		}
		if(!models.isEmpty()){
			db.batchSave(models);
		}

		return Result.ok();
	}
	
	@RequestMapping(mapping = { "/get_approve_action_list", "/m/get_approve_action_list" },text="")
	public Collection<NodeButton> getApproveActionList() {
		List<NodeButton> rs = new ArrayList<>();
	 
		List<Dictionary> list = dictionaryService.getOptionByCode("form_node_button_type", Application.APP_CODE_DEFAULT);
		if (list != null) {
			for (Dictionary d : list) {
				//System.out.println(d.getCode());
				
				//去除"保存表单、保存草稿、发起"审批动作
				if(("button_type_"+NodeButton.BTN_TYPE_START).equals(d.getCode())){
					continue;
				}
				if(("button_type_"+NodeButton.BTN_TYPE_SAVE_DRAFT).equals(d.getCode())){
					continue;
				}
				if(("button_type_"+NodeButton.BTN_TYPE_SAVE_FORM).equals(d.getCode())){
					continue;
				}
				
				NodeButton nb = new NodeButton();
				nb.setBtnName(d.getName());
				nb.setBtnCode(d.getCode());
				nb.setBtnType(Integer.valueOf(d.getValue()));
				nb.setRemark(d.getRemark());
				
				rs.add(nb);
			}
		}
		return rs;
	}
	
	// ----------------------------------- 批量设置节点的后置角本 -------------------------------------------------
	/**
	 * 
	 * @param definitionId 流程定义
	 * @param taskType 节点类别:0=拟稿节点(退回到拟稿人时有用) 1=普通任务节点 2=结束节点
	 * @return
	 */
	@RequestMapping(mapping = { "/get_tree_node", "/m/get_tree_node" },text="批量设置按钮页所用的树")
	public Collection<Node> getTreeNode(String definitionId,String taskType) {
		
		List<Node> diagramNodeList = new ArrayList<>(); // 流程图里的任务节点
		RepositoryService repositoryService = ActUtil.getRepositoryService();
		BpmnModel model = repositoryService.getBpmnModel(definitionId);
		if (model != null) {
			
			Definition def = db.getById(Definition.class, definitionId);
			Node root = new Node();
			root.name = def.getName().endsWith("流程") ? def.getName() : def.getName() + "流程";
			root.id = def.getId();
			root.pid = "-1";
			diagramNodeList.add(root);
		 	
			List<org.lsqt.act.model.Node> nodeList = null;
			if (StringUtil.isNotBlank(taskType)) {
				NodeQuery nquery = new NodeQuery();
				nquery.setDefinitionId(definitionId);
				nquery.setTaskBizType(Integer.valueOf(taskType));
				nodeList = db.queryForList("queryForPage", org.lsqt.act.model.Node.class, nquery);
			}
			
			Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
			for (FlowElement e : flowElements) {
				 
				if (UserTask.class.isAssignableFrom(e.getClass())) {
					UserTask task = (UserTask) e;
					Node nd = new Node();
					nd.id = task.getId();
					nd.pid = root.id;
					nd.name = task.getName()+"("+task.getId()+")";
					
					if(StringUtil.isBlank(taskType)) {
						diagramNodeList.add(nd);
					} else {
						if (nodeList!=null) {
							for(org.lsqt.act.model.Node t:nodeList) {
								if (task.getId().equals(t.getTaskKey())) {
									diagramNodeList.add(nd);
								}
							}
						}
					}
				}
			}
		}
		
		NodeButtonQuery query = new NodeButtonQuery();
		query.setDefinitionId(definitionId);
		query.setDataType(NodeButton.DATA_TYPE_TASK_BUTTON);
		List<NodeButton> list = db.queryForList("queryForPage", NodeButton.class, query);
		List<Node> subNodeList = new ArrayList<>();
		if(list!=null) {
			for (Node d: diagramNodeList) {
				for(NodeButton e: list) {
					if(d.id.equals(e.getTaskKey())) {
						Node nd = new Node();
						nd.id = "btn_"+e.getId();
						nd.pid = d.id;
						nd.name = e.getBtnName()+"("+e.getBtnCode().replace("button_type_", "")+")";
						subNodeList.add(nd);
						//break;
					}
				}
			}
		}
		diagramNodeList.addAll(subNodeList);
		return diagramNodeList;
	
	}
	
	static class Node {
		public String id;
		public String pid;
		public String name;
	}
	
	
	@RequestMapping(mapping = { "/batch_set_script", "/m/batch_set_script" },text="批量设置按钮脚本")
	public void batchSetScript(String ids,String beforeScript,String afterScript) {
		if (StringUtil.isNotBlank(ids,beforeScript,afterScript)) {
			List<String> idList = new ArrayList<>();
			
			List<String> btnIdList = StringUtil.split(ids, ",");
			for (String e: btnIdList) {
				idList.add(e.replace("btn_", ""));
			}
			
			NodeButtonQuery query = new NodeButtonQuery();
			query.setIds(StringUtil.join(idList));
			List<NodeButton> data = nodeButtonService.queryForList(query);
			for(NodeButton m: data) {
				m.setBeforeScript(beforeScript);
				m.setAfterScript(afterScript);
				
				if(StringUtil.isBlank(m.getBeforeScriptType())) {
					m.setBeforeScriptType(NodeButton.SCRIPT_TYPE_URL);
				}
				
				if(StringUtil.isBlank(m.getAfterScriptType())) {
					m.setAfterScriptType(NodeButton.SCRIPT_TYPE_URL);
				}
				db.update(m, "beforeScript","afterScript","beforeScriptType","afterScriptType");
			}
		}
	}
}
