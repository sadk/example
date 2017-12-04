package org.lsqt.act.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.lsqt.act.ActUtil;
import org.lsqt.act.model.InstanceVariable;
import org.lsqt.act.model.InstanceVariableQuery;
import org.lsqt.act.model.ProcessInstanceHis;
import org.lsqt.act.model.RunInstance;
import org.lsqt.act.model.RunInstanceQuery;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.uum.model.Org;
import org.lsqt.syswin.uum.model.OrgQuery;

import com.alibaba.fastjson.JSON;

/**
 * 自定义的流程实例查询
 *
 */
@Controller(mapping = { "/act/runinstance","/nv2/act/runinstance" })
public class RunInstanceController {
	@Inject private Db db;
	@Inject private PlatformDb db2;
	 
	@RequestMapping(mapping = { "/page_running", "/m/page_running" }, text = "获取运行中的流程实例（含流程标题、发起人、业务主键等关键信息)")
	public Page<RunInstance> queryForPageRunningDetail(RunInstanceQuery query) {
		Page<RunInstance> page = db.queryForPage("queryForPageRunningDetail", query.getPageIndex(), query.getPageSize(), RunInstance.class, query);
		
		// 加载流程任务名称
		if(StringUtil.isNotBlank(query.getProcessDefinitionId())) {
			 
			// 获取流程的所有节点
			BpmnModel model =  ActUtil.getRepositoryService().getBpmnModel(query.getProcessDefinitionId());
			if (model != null) {
				Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
				for(RunInstance p : page.getData()) {
					for(FlowElement e: flowElements) {
						if(p.getTaskKey()!=null  && p.getTaskKey().equals(e.getId())) {
							p.setTaskName(e.getName());
							break;
						}
					}
				}
			}
		}
		
		// 关键字查询时，单条记录加载taskName
		for(RunInstance p : page.getData()) {
			if(StringUtil.isBlank(p.getTaskName()) && StringUtil.isNotBlank(p.getProcessDefinitionId())) {
				BpmnModel model = ActUtil.getRepositoryService().getBpmnModel(p.getProcessDefinitionId());
				if (model != null) {
					Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
					 
					for(FlowElement e: flowElements) {
						if(p.getTaskKey()!=null  && p.getTaskKey().equals(e.getId())) {
							p.setTaskName(e.getName());
							break;
						}
					}
				}
			}
		}
		
		// 加载填制人部门名称
		if(page.getData()!=null && page.getData().size()>0) {
			List<String> createDeptIds =new ArrayList<>();
			List<RunInstance> data = (List<RunInstance>)page.getData();
			for(RunInstance e: data) {
				if(StringUtil.isNotBlank(e.getCreateDeptId())) {
					createDeptIds.add(e.getCreateDeptId());
				}
			}
			OrgQuery orgQuery = new OrgQuery();
			String sql = StringUtil.join(createDeptIds,",");
			
			if(StringUtil.isNotBlank(sql)) {
				orgQuery.setIds(sql);
				List<Org> list = db2.queryForList("queryForPage", Org.class, orgQuery);
				
				for(RunInstance e: data) {
					for(Org n: list) {
						if(StringUtil.isNotBlank(e.getCreateDeptId())  
								&& e.getCreateDeptId().equals(n.getId().toString())) {
							e.setCreateDeptName(n.getName());
							break;
						}
					}
				}
			}
		}
		
		// 加载流程发起时的初使变量、聚合变量
		if(page.getData()!=null && page.getData().size()>0) {
			
			List<String> instanceIds =new ArrayList<>();
			List<RunInstance> data = (List<RunInstance>)page.getData();
			for(RunInstance e: data) {
				if(StringUtil.isNotBlank(e.getInstanceId())) {
					instanceIds.add(e.getInstanceId());
				}
			}
			InstanceVariableQuery riQuery = new InstanceVariableQuery();
			riQuery.setInstanceIdList(instanceIds);
			List<InstanceVariable> list = db.queryForList("queryForPage", InstanceVariable.class, riQuery);
			
 
			for(RunInstance e: data) {
				for(InstanceVariable v : list) {
					if(StringUtil.isNotBlank(v.getInstanceId()) 
							&& e.getInstanceId().equals(v.getInstanceId())) {
						e.setVariableJSONInit(format(v.getVariableJSONStart()));
						e.setVariableJSONStarting(format(v.getVariableJson()));
						break;
					}
				}
			}
		}
		return page;
	}
	
	@RequestMapping(mapping = { "/page_his", "/m/page_his" }, text = "获取已结束的流程实例（含流程标题、发起人、业务主键等关键信息)")
	public Page<ProcessInstanceHis> queryForPageHistoryDetail(RunInstanceQuery query) {
		Page<ProcessInstanceHis> page = db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), ProcessInstanceHis.class, query);
		
		return page;
	}
	
	@SuppressWarnings("rawtypes")
	private static String format(String json){
		if(StringUtil.isBlank(json)){
			return "";
		}
		
		if(StringUtil.isNotBlank(json)){
			Map m= JSON.parseObject(json, Map.class);
			return JSON.toJSONString(m, true);
		}
		return "";
	}
}
 