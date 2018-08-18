package org.lsqt.act.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.lsqt.act.ActUtil;
import org.lsqt.act.controller.DefinitionController.NodeObject;
import org.lsqt.act.model.ApproveObject;
import org.lsqt.act.model.NodeUser;
import org.lsqt.act.model.NodeUserQuery;
import org.lsqt.act.service.DefinitionService;
import org.lsqt.act.service.NodeUserService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.uum.model.User;
import org.lsqt.syswin.uum.model.UserQuery;
import org.lsqt.syswin.uum.service.UserService;

import com.alibaba.fastjson.JSON;




@Controller(mapping={"/act/node_user","/nv2/act/node_user"})
public class NodeUserController {
	
	@Inject private NodeUserService nodeUserService; 
	@Inject private UserService userService;
	@Inject private DefinitionService definitionService;
	@Inject private PlatformDb db2;
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/view_user", "/m/view_user" },text="流程设置预览审批人")
	public List<ApproveObject> viewUser(NodeUserQuery query,Long loginUserId,Long createDeptId) throws IOException {
		Map<String,Object> nodeUserVariable = new HashMap<>();
		nodeUserVariable.put(ActUtil.VARIABLES_CREATE_DEPT_ID, createDeptId); 
		List<ApproveObject> userIds = nodeUserService.getNodeUsers(loginUserId,query.getDefinitionId(), query.getTaskKey(),nodeUserVariable);
		return userIds;
	}
	
	@RequestMapping(mapping = { "/get_node_user", "/m/get_node_user" },text="待办列表，快速查看当前的审批人")
	public Page<User> getNodeUser(NodeUserQuery query,Long loginUserId) {
		Page<User> page = new Page.PageModel<>();
		
		List<ApproveObject> list = nodeUserService.getNodeUsers(loginUserId, query.getDefinitionId(), query.getTaskKey(),new HashMap<>());
		if(list!=null && list.size()>0) {
			List<String> uids = ApproveObject.toIdList(list);
			
			UserQuery filter = new UserQuery();
			filter.setIds(StringUtil.join(uids, ","));
			page = userService.queryForPage(filter);
		}
		
		return page;
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<NodeUser> queryForPage(NodeUserQuery query) throws IOException {
		return nodeUserService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<NodeUser> getAll() {
		return nodeUserService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public NodeUser saveOrUpdate(NodeUser form) {
		return nodeUserService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return nodeUserService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(mapping = { "/save_or_update_json", "/m/save_or_update_json" })
	public int saveOrUpdate(String definitionId, String taskKey,String data) {
		if (StringUtil.isNotBlank(data)) {
			List<NodeUser> list = JSON.parseArray(data, NodeUser.class);
			
			for(NodeUser u: list) {
				Map<String,Object> temp = JSON.parseObject(u.getApproveObjectJson(),Map.class);
				if(u.getUserType()!= null && NodeUser.USER_TYPE_ORG == u.getUserType()){ // 组织机构是一个递归树，字段太大！！！
					u.setApproveObjectJson(null);
				}else {
					u.setApproveObjectJson(JSON.toJSONString(temp,true));
				}
			}
			nodeUserService.saveOrUpdate(definitionId,taskKey,list);
			
		}
		return 0;
	}
	
	
	
	@RequestMapping(mapping = { "/init_node_user", "/m/init_node_user" },text="初使化整个流程节点的审批人，初使化的是具体用户")
	public int initNodeUser(String definitionId, String userIds) {
		if (StringUtil.isNotBlank(definitionId,userIds)) {
			
			org.lsqt.act.model.Definition  def = definitionService.getById(definitionId);
			UserQuery query = new UserQuery();
			query.setIds(userIds);
			List<User> userList = db2.queryForList("queryForPage", User.class, query);
			
			db.executeUpdate("delete from ext_node_user where definition_id=? ", definitionId);
			
			List<NodeUser> nodeUserData = new ArrayList<>();
			
			RepositoryService repositoryService = ActUtil.getRepositoryService();
			BpmnModel model = repositoryService.getBpmnModel(definitionId);
			if (model != null) {
				Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
				for (FlowElement e : flowElements) {
					
					if (UserTask.class.isAssignableFrom(e.getClass())) {
						UserTask task = (UserTask) e;
						NodeObject st = new NodeObject();
						st.setCandidateGroups(task.getCandidateGroups());
						st.setCandidateUsers(task.getCandidateUsers());
						st.setFormKey(task.getFormKey());
						st.setFormProperties(task.getFormProperties());
						st.setTaskKey(task.getId());
						st.setTaskName(task.getName());
						
						 for(User user: userList) {
							 NodeUser nodeUser = new NodeUser();
							 nodeUser.setAppCode(Application.APP_CODE_DEFAULT);
							 nodeUser.setApproveObjectId(user.getUserId().toString());
							
							 nodeUser.setDefinitionId(definitionId);
							 nodeUser.setDefinitionName(def.getName());
							 nodeUser.setTaskKey(task.getId());
							 nodeUser.setName(user.getUserName());
							 nodeUser.setUserType(NodeUser.USER_TYPE_USER);
							 nodeUser.setUserFromType(NodeUser.USER_FROM_TYPE_INTERNAL);
							 nodeUserData.add(nodeUser);
						 }
					}
				}
			}
			
			if(!nodeUserData.isEmpty()) {
				db.batchSave(nodeUserData);
			}
		}
		return 0;
	}
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public List<NodeUser> queryForList(NodeUserQuery query)  {
		return nodeUserService.queryForList(query); 
	}
	

}
