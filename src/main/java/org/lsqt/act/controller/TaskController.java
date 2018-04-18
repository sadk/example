package org.lsqt.act.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lsqt.act.ActUtil;
import org.lsqt.act.model.ApproveObject;
import org.lsqt.act.model.Node;
import org.lsqt.act.model.NodeQuery;
import org.lsqt.act.model.ProcessInstanceHis;
import org.lsqt.act.model.ProcessInstanceHisQuery;
import org.lsqt.act.model.RunInstance;
import org.lsqt.act.model.RunInstanceQuery;
import org.lsqt.act.model.Task;
import org.lsqt.act.model.TaskQuery;
import org.lsqt.act.service.NodeUserService;
import org.lsqt.act.service.RuntimeService;
import org.lsqt.act.service.TaskService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.uum.model.User;
import org.lsqt.syswin.uum.model.UserQuery;
import org.lsqt.syswin.uum.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 流程任务相关
 * @author mmyuan
 *
 */
@Controller(mapping={"/act/task","/nv2/act/task"})
public class TaskController {
	private static final Logger log = LoggerFactory.getLogger(TaskController.class);
	@Inject private TaskService taskService;
	@Inject private NodeUserService nodeUserService; 
	@Inject private RuntimeService runtimeService;
	
	@Inject private Db db;
	@Inject private PlatformDb db2;
	
	
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public void setNodeUserService(NodeUserService nodeUserService) {
		this.nodeUserService = nodeUserService;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public void setDb(Db db) {
		this.db = db;
	}

	public void setDb2(PlatformDb db2) {
		this.db2 = db2;
	}
	
	
	@Deprecated
	@RequestMapping(mapping = { "/list", "/m/list" })
	public List<Task> queryForList(TaskQuery query) {
		return taskService.queryForList(query);
	}
	
	@Deprecated
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Task> queryForPage(TaskQuery query) {
		return taskService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/query_page_for_mycopy_send", "/m/query_page_for_mycopy_send" },text="抄送我的")
	public Page<Task> queryMyCopySend(TaskQuery query) {
		prepareQuery(query);
		if(StringUtil.isNotBlank(query.getUserIds())) {
			query.setUserIdList(StringUtil.split(query.getUserIds(), ","));
			query.setUserIds(null);
		}
		
		if((query.getUserIdList() == null || query.getUserIdList().isEmpty()) && query.getIsQueryTaskUser()) {
			return new Page.PageModel<>();
		}
		return db.queryForPage("queryMyCopySend",query.getPageIndex(),query.getPageSize(), Task.class, query);
	}
	
	
	@RequestMapping(mapping = { "/list_detail", "/m/list_detail" },text="获取我的待办(含流程标题、发起人信息)")
	public List<Task> queryForListDetail(TaskQuery query) {
		return taskService.queryForListDetail(query);
	}
	
	@RequestMapping(mapping = { "/page_detail", "/m/page_detail" },text="获取我的待办(含流程标题、发起人信息)")
	public Page<Task> queryForPageDetail(TaskQuery query) {
		prepareQuery(query);
		
		if(StringUtil.isBlank(query.getUserIds()) && query.getIsQueryTaskUser()) {
			return new Page.PageModel<>();
		}
		
		Page<Task> page = taskService.queryForPageDetail(query);
		
		 
		
		loadApproveUser(page);
		return page;
	}
	
	@RequestMapping(mapping = { "/page_fast", "/m/page_fast" },text="获取我的待办(含流程标题、发起人信息)")
	public Page<Task> queryForPageFast(TaskQuery query) {
		prepareQuery(query);
		
		if(StringUtil.isBlank(query.getUserIds()) && query.getIsQueryTaskUser()) {
			return new Page.PageModel<>();
		}
		
		Page<Task> page = taskService.queryMyToDoTaskPage(query);
		
		loadApproveUser(page);
		return page;
	}

	void loadApproveUser(Page<Task> page) {
		List<String> instanceIds =new ArrayList<>();
		for (Task t: page.getData()) {
			if (StringUtil.isNotBlank(t.getProcessInstanceId())) {
				instanceIds.add(t.getProcessInstanceId());
			}
		}
		
		if(!instanceIds.isEmpty()) {
			RunInstanceQuery riq = new RunInstanceQuery();
			riq.setInstanceIdList(instanceIds);
			List<RunInstance> data = db.queryForList("queryForPageRunningDetail", RunInstance.class, riq);
			
			Map<String,RunInstance> mapData = new HashMap<>();
			for(RunInstance e: data) {
				
				Map<String, Object> map = new HashMap<>();
				
				if(StringUtil.isNotBlank(e.getCreateDeptId())) {
					map.put(ActUtil.VARIABLES_CREATE_DEPT_ID, Long.valueOf(e.getCreateDeptId()));
				}else {
					log.error("流程实例ID="+e.getInstanceId()+"的流程找不到填制人部门");
				}
				map.put(ActUtil.VARIABLES_START_USER_ID, e.getStartUserId());
				//map.put(ActUtil.VARIABLES_LOGIN_USER,null);
				List<ApproveObject> approveUsers =null;
				try{
					approveUsers = nodeUserService.getNodeUsers(null, e.getProcessDefinitionId(), e.getTaskKey(), map);
				}catch(Exception ex) {
					ex.printStackTrace();
				}
				StringBuilder approveUserText = new StringBuilder();
	
				
				
				if (approveUsers != null) {
					for (ApproveObject t : approveUsers) {
						User user = db2.getById(User.class, t.getId());
						if (user != null) {
							approveUserText.append(t.getName() + "(" + user.getLoginNo()+"/"+user.getUserId() + ")");
						}
					}
					e.setApproveUserText(approveUserText.toString());
				}
				
				
				// 拟稿节点审批人就是发起人自己
				NodeQuery nq =new NodeQuery();
				nq.setTaskBizType(Node.TASK_BIZ_TYPE_DRAFTNODE);
				nq.setDefinitionId(e.getProcessDefinitionId());
				Node draftNode = db.queryForObject("queryForPage", Node.class, nq);
				if(draftNode!=null && e.getTaskKey() !=null && e.getTaskKey().equals(draftNode.getTaskKey())) {
					User user = db2.getById(User.class, e.getStartUserId());
					if (user!=null) {
						e.setApproveUserText(e.getStartUserName()+"("+user.getLoginNo()+"/"+user.getUserId()+")");
					}
				}
				
				mapData.put(e.getInstanceId(), e);
			}
			
			
			for(Task t: page.getData()) {
				RunInstance ri = mapData.get(t.getProcessInstanceId());
				if (ri!=null) {
					t.setCandidateUserNames(mapData.get(t.getProcessInstanceId()).getApproveUserText());
				}
			} 
		}
	}

	/**
	 * 填充多个用户
	 * @param query
	 */
	private void prepareQuery(TaskQuery query) {
		if (StringUtil.isNotBlank(query.getUserName()) || StringUtil.isNotBlank(query.getLoginNo()) || StringUtil.isNotBlank(query.getUserId())) {
			query.setIsQueryTaskUser(true);
			
			UserQuery q = new UserQuery();
			q.setIds(query.getUserId());
			q.setUserName(query.getUserName());
			q.setLoginNo(query.getLoginNo());
			List<User> ulist = db2.queryForList("queryForPage", User.class, q);
			if(ulist.isEmpty()) {
				query.setUserId(null);
				query.setUserIds(null);
				return ;
			}
			
			List<String> uids = new ArrayList<>();
			for (User u : ulist) {
				uids.add(u.getUserId()+"");
			}
			query.setUserIds(StringUtil.join(uids, ","));
			query.setUserId(null);
		}
	}
	
	@RequestMapping(mapping = { "/page_my_started_and_done", "/m/page_my_started_and_done" },text="获取我的已办、我的发起")
	public Page<Task> queryForPageMyStartAndDone(TaskQuery query) {
		return db.queryForPage("queryTask", query.getPageIndex(),query.getPageSize(),Task.class, query);
	}
	
	
	@RequestMapping(mapping = { "/list_my_started_and_done", "/m/list_my_started_and_done" },text="获取我的已办、我的发起")
	public Page<Task> queryForPageMyStartedAndDone(TaskQuery query) {
		
		prepareQuery(query);
		if(StringUtil.isBlank(query.getUserIds()) && query.getIsQueryTaskUser()) {
			return new Page.PageModel<Task>();
		}
		
		Page<Task> page = db.queryForPage("queryTask", query.getPageIndex(),query.getPageSize(),Task.class, query);
		Collection<Task> taskData = page.getData();
		
		/* 加载流程是否已结束状态
		List<String> instanceIds = new ArrayList<>();
		for(Task t: taskData) {
			instanceIds.add(t.getProcessInstanceId());
		}
		ProcessInstanceHisQuery hisQuery = new ProcessInstanceHisQuery();
		hisQuery.setInstanceIdList(instanceIds);
		List<ProcessInstanceHis> data = db.queryForList("queryForList", ProcessInstanceHis.class, hisQuery);
		*/
		
		for (Task t: taskData) {
			if(StringUtil.isNotBlank(t.getProcessInstanceId())) {
				boolean isEnded = runtimeService.isInstanceEnded(t.getProcessInstanceId());
				t.setCloseStatus( isEnded ? ActUtil.END_STATUS_已结束:ActUtil.END_STATUS_未结束);
			}
		}
		return page;
	}
	
	
	@RequestMapping(mapping={"/delete","/m/delete"})
	public void delete(String ids) {
		if(StringUtil.isNotBlank(ids)) {
			List<String> idList = StringUtil.split(ids, ",");
			taskService.deleteTask(idList.toArray(new String[idList.size()]));
		}
	}

}
