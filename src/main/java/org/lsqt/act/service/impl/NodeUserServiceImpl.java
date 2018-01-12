package org.lsqt.act.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
/**
import com.qitoon.framework.organ.param.EmployeeListResult;
import com.qitoon.framework.organ.param.EmployeeResult;
import com.qitoon.framework.organ.pojo.Employee;
import com.qitoon.framework.organ.pojo.OrganUnit;
import com.qitoon.framework.organ.pojo.Position;
import com.qitoon.framework.organ.service.IEmployeeService;
import com.qitoon.framework.organ.service.IOrganUnitService;
import com.qitoon.framework.organ.service.IOrganizationService;
import com.qitoon.framework.organ.service.IPositionService;
*/
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.lsqt.act.ActUtil;
import org.lsqt.act.model.ApproveObject;
import org.lsqt.act.model.NodeUser;
import org.lsqt.act.model.NodeUserQuery;
import org.lsqt.act.model.UserRule;
import org.lsqt.act.model.UserRuleMatrixDeptUser;
import org.lsqt.act.model.UserRuleMatrixDeptUserQuery;
import org.lsqt.act.service.NodeUserService;
import org.lsqt.act.service.UserRuleService;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.uum.model.User;
import org.lsqt.syswin.uum.model.UserQuery;
import org.lsqt.syswin.uum.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

@Service
public class NodeUserServiceImpl implements NodeUserService{
	private static final Logger log = LoggerFactory.getLogger(NodeUserServiceImpl.class);
	
	@Inject private Db db;
	@Inject private PlatformDb db2;
	@Inject private UserRuleService userRuleService;
	@Inject private UserService userService;

	
	public Page<NodeUser> queryForPage(NodeUserQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), NodeUser.class, query);
	}

	public List<NodeUser> queryForList(NodeUserQuery query) {
		return db.queryForList("queryForPage", NodeUser.class, query);
	}

	public List<NodeUser> getAll(){
		  return db.queryForList("getAll", NodeUser.class);
	}
	
	public NodeUser saveOrUpdate(NodeUser model) {
		return db.saveOrUpdate(model);
	}

	public void saveOrUpdate(String definitionId,String taskKey,List<NodeUser> models) {
		db.executeUpdate("delete from ext_node_user where definition_id=? and task_key=? ", definitionId,taskKey);
		
		for (NodeUser m : models) {
			db.save(m);
		}
		
		// 添加审批对象JSON详情，（大字段迁移到另一个表）
		
	}
	
	public int deleteById(Long ... ids) {
		return db.deleteById(NodeUser.class, Arrays.asList(ids).toArray());
	}
	
	public List<ApproveObject> getNodeUsers(Long loginUserId,String definitionId,String taskDefKey,Map<String,Object> variables) {
		List<ApproveObject> result = new ArrayList<>();
		if (StringUtil.isBlank(definitionId, taskDefKey)) {
			return result;
		}
		
		NodeUserQuery query = new NodeUserQuery();
		query.setDefinitionId(definitionId);
		query.setTaskKey(taskDefKey);
		
		List<NodeUser> list = db.queryForList("queryForPage", NodeUser.class, query);
		if(list==null || list.isEmpty()) {
			return result;
		}
		return resolveApproveUsers(loginUserId, list,variables);
	}
	
	public Map<String, List<ApproveObject>> getNodeUsers(Long loginUserId, String definitionId,Map<String,Object> variables) {
		Map<String, List<ApproveObject>> result = new HashMap<>();

		NodeUserQuery query = new NodeUserQuery();
		query.setDefinitionId(definitionId);
		List<NodeUser> list = db.queryForList("queryForPage", NodeUser.class, query);
		if (list == null || list.isEmpty()) {
			return result;
		}

		Map<String, List<NodeUser>> map = new HashMap<>();
		for (NodeUser u : list) {
			map.put(u.getTaskKey(), new ArrayList<>());
		}

		Set<Entry<String, List<NodeUser>>> set = map.entrySet();
		for (Entry<String, List<NodeUser>> e : set) {
			String key = e.getKey();
			List<NodeUser> value = e.getValue();

			for (NodeUser n : list) {
				if (key.equals(n.getTaskKey())) {
					value.add(n);
				}
			}

			result.put(key, resolveApproveUsers(loginUserId, value,variables)); // 每个节点的审批用户
		}

		// 补全流程所有节点
		RepositoryService repositoryService = ActUtil.getRepositoryService();
		BpmnModel model = repositoryService.getBpmnModel(definitionId);
		if (model != null) {
			Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
			for (FlowElement e : flowElements) {
				if (UserTask.class.isAssignableFrom(e.getClass())) {
					UserTask task = (UserTask) e;
					String taskKey = task.getId();

					boolean isExists = false;
					for (String t : result.keySet()) {
						if (t.equals(taskKey)) {
							isExists = true;
						}
					}

					if (!isExists) {
						result.put(taskKey, null);
					}
				}
			}
		}
		return result;
	}
	

	


	private List<ApproveObject> resolveApproveUsers(Long loginUserId, List<NodeUser> list,Map<String,Object> variables) {
		List<ApproveObject> result = new ArrayList<>();
		
		List<String> userIdList = new ArrayList<>();
		List<String> orgIdList = new ArrayList<>();
		List<String> positionIdList = new ArrayList<>();
		
		List<String> ruleIdList = new ArrayList<>();
		
		
		List<User> userList = new ArrayList<>();
		
		for (NodeUser e : list) {
			if (e.getUserType() == null) {
				continue;
			}
			if (NodeUser.USER_FROM_TYPE_INTERNAL == e.getUserFromType()) {
				// result.addAll(getNodeUsersForLocal(e));
				
				if (NodeUser.USER_TYPE_USER == e.getUserType()) {
					userIdList.add(e.getApproveObjectId());
				}

				if (NodeUser.USER_TYPE_ORG == e.getUserType()) {
					orgIdList.add(e.getApproveObjectId());
				}

				if (NodeUser.USER_TYPE_POSITION == e.getUserType()) {
					positionIdList.add(e.getApproveObjectId());
				}

				if (NodeUser.USER_TYPE_SCRIPT == e.getUserType()) {
					UserRule rule = db.getById(UserRule.class, e.getApproveObjectId());
					if(rule != null) {
						if (rule.getEnable()!=null && UserRule.ENABLE_ON == rule.getEnable()) { // 部门与用户规则矩阵配置的审批用户

							UserRuleMatrixDeptUserQuery q = new UserRuleMatrixDeptUserQuery();
							q.setUserRuleId(rule.getId());
							q.setCreateDeptId(Long.valueOf(variables.get(ActUtil.VARIABLES_CREATE_DEPT_ID) + ""));
							List<UserRuleMatrixDeptUser> tempData = db.queryForList("queryForPage",UserRuleMatrixDeptUser.class, q); //理论上只有一个值 

							if (tempData != null && !tempData.isEmpty()) {
								for (UserRuleMatrixDeptUser md : tempData) {
									if (StringUtil.isNotBlank(md.getUserIds())) {

										UserQuery userQuery = new UserQuery();
										userQuery.setIds(md.getUserIds());
										List<User> users0 = db2.queryForList("queryForPage", User.class, userQuery);
										userList.addAll(users0);
									}
								}
							}
						} else {
							ruleIdList.add(e.getApproveObjectId()); // freemarkt等代码类型角本
						}
					}
					
				}
				
			} else if (NodeUser.USER_FROM_TYPE_REMOTE_HTTP_JSON == e.getUserFromType()) {

			}
		}
		
		
		
		// 指定的用户
		if(!userIdList.isEmpty()){
			UserQuery userQuery = new UserQuery();
			userQuery.setIds(StringUtil.join(userIdList, ","));
			List<User> users1 = db2.queryForList("queryForPage", User.class, userQuery);
			userList.addAll(users1);
		}
		
		// 组织下的用户
		if(!orgIdList.isEmpty()){
			UserQuery userQuery = new UserQuery();
			userQuery.setOrgIds(StringUtil.join(orgIdList, ","));
			List<User> users2 = db2.queryForList("queryForPage", User.class, userQuery);
			userList.addAll(users2);
		}
		
		// 岗位下的用户
		if(!positionIdList.isEmpty()) {
			UserQuery userQuery = new UserQuery();
			userQuery.setPositionIds(StringUtil.join(positionIdList, ","));
			List<User> users3 = db2.queryForList("queryForPage", User.class, userQuery);
			userList.addAll(users3);
		}
		
		// 角本规则用户(代码引擎解析出用户的一类别)
		if (!ruleIdList.isEmpty()) {
			String rids = ArrayUtil.join(ruleIdList, ",");
			List<Long> idList = StringUtil.split(Long.class, rids, ",");
			Long[] ids = idList.toArray(new Long[idList.size()]);
			
			try {
				log.debug(" --- 准备解析角本规则用户,规则Id : "+rids);
				
				List<String> userIds = userRuleService.resolveUsers(loginUserId,variables, ids);
				UserQuery userQuery = new UserQuery();
				userQuery.setIds(StringUtil.join(userIds, ","));
				if(StringUtil.isNotBlank(userQuery.getIds())){
					List<User> users = db2.queryForList("queryForPage", User.class, userQuery);
					if(users!=null && !users.isEmpty()){
						userList.addAll(users);
					}
				}
			} catch (Exception e1) {
				throw new RuntimeException("角本规则用户解析错误",e1);
			}
		}
		

		
		
		for(User e: userList) {
			if(e!=null){
				ApproveObject obj = new ApproveObject();
				obj.setId(e.getUserId()+"");
				obj.setName(e.getUserName());
				obj.setEmail(e.getEmail());
				obj.setMobile(e.getMobile());
				obj.setCode(e.getLoginNo());
				result.add(obj);
			}
		}
		
		return result;
	}
	
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
 
	
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		String url = "http://t200.admin.qitoon.com/organUnit/queryOrganTreeNodes?nodeId=5586&nodeType=0&isPosition=1&organizationId=1427598448086793";
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		HttpUriRequest request = new HttpGet(url.toString()); // 默认为get请求
		
		System.out.println( " --- 请求远程地址："+url);
		request = new HttpPost(url.toString());
		 

        HttpResponse response = httpclient.execute(request);
		String json = EntityUtils.toString(response.getEntity());
		System.out.println(JSON.toJSONString(JSON.parseObject(json,Map.class),true));
	}
	


}
