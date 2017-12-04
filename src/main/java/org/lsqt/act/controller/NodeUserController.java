package org.lsqt.act.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lsqt.act.ActUtil;
import org.lsqt.act.model.ApproveObject;
import org.lsqt.act.model.NodeUser;
import org.lsqt.act.model.NodeUserQuery;
import org.lsqt.act.service.NodeUserService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.uum.model.User;
import org.lsqt.syswin.uum.model.UserQuery;
import org.lsqt.syswin.uum.service.UserService;

import com.alibaba.fastjson.JSON;




@Controller(mapping={"/act/node_user","/nv2/act/node_user"})
public class NodeUserController {
	
	@Inject private NodeUserService nodeUserService; 
	@Inject private UserService userService;
	
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
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public List<NodeUser> queryForList(NodeUserQuery query)  {
		return nodeUserService.queryForList(query); 
	}
	

}
