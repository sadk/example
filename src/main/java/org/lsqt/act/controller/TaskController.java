package org.lsqt.act.controller;

import java.util.List;

import org.lsqt.act.model.Task;
import org.lsqt.act.model.TaskQuery;
import org.lsqt.act.service.TaskService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;

/**
 * 流程任务相关
 * @author mmyuan
 *
 */
@Controller(mapping={"/act/task","/nv2/act/task"})
public class TaskController {
	@Inject private TaskService taskService;
	@Inject private Db db;
	
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
	
	@RequestMapping(mapping = { "/list_detail", "/m/list_detail" },text="获取我的待办(含流程标题、发起人信息)、抄送我的")
	public List<Task> queryForListDetail(TaskQuery query) {
		return taskService.queryForListDetail(query);
	}
	
	@RequestMapping(mapping = { "/page_detail", "/m/page_detail" },text="获取我的待办(含流程标题、发起人信息)、抄送我的")
	public Page<Task> queryForPageDetail(TaskQuery query) {
		return taskService.queryForPageDetail(query);
	}
	
	@RequestMapping(mapping = { "/page_my_started_and_done", "/m/page_my_started_and_done" },text="获取我的已办、我的发起")
	public Page<Task> queryForPageMyStartAndDone(TaskQuery query) {
		return db.queryForPage("queryTask", query.getPageIndex(),query.getPageSize(),Task.class, query);
	}
	
	
	@RequestMapping(mapping = { "/list_my_started_and_done", "/m/list_my_started_and_done" },text="获取我的已办、我的发起")
	public Page<Task> queryForPageMyStartedAndDone(TaskQuery query) {
		return db.queryForPage("queryTask", query.getPageIndex(),query.getPageSize(),Task.class, query);
	}
	
	
	@RequestMapping(mapping={"/delete","/m/delete"})
	public void delete(String ids) {
		if(StringUtil.isNotBlank(ids)) {
			List<String> idList = StringUtil.split(ids, ",");
			taskService.deleteTask(idList.toArray(new String[idList.size()]));
		}
	}
}
