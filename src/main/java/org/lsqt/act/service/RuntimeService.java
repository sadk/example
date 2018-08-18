package org.lsqt.act.service;

import java.util.List;
import java.util.Map;

import org.lsqt.act.model.ActRunningContext;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.ApproveOpinionQuery;
import org.lsqt.act.model.ProcessInstance;
import org.lsqt.act.model.ProcessInstanceHis;
import org.lsqt.act.model.ProcessInstanceQuery;
import org.lsqt.act.model.Task;
import org.lsqt.components.db.Page;

/**
 * 流程流转相关接口
 * @author mmyuan
 *
 */
public interface RuntimeService {
	
	/**
	 * 启动流程(跟据流程定义key)
	 * @param processDefinitionKey 流程定义Key
	 * @return processInstanceId 返回流程实例
	 */
	ActRunningContext startProcessInstanceById(String processDefinitionId);
	
	/**
	 * 启动流程(跟据流程定义Id)
	 * @param processDefinitionId 流程定义ID
	 * @param variables
	 * @return
	 */
	ActRunningContext startProcessInstanceById(String processDefinitionId,Map<String, Object> variables);
	
	/**
	 * 启动流程（重要！！！！）
	 * @param processDefinitionId 流程定义ID
	 * @param businessKey 业务主键 
	 * @param variables 流程变量
	 * @return
	 */
	ActRunningContext startProcessInstanceById(String processDefinitionId,String businessKey,Map<String, Object> variables);
	
	
	/**
	 * 跟据流程定义key启动流程
	 * @param processDefinitionKey 流程定义Key
	 * @return processInstanceId 返回流程实例
	 */
	ActRunningContext startProcessInstanceByKey(String processDefinitionKey);
	
	/**
	 * 
	 * @param processDefinitionKey 流程定义Key
	 * @param variables 流程变量Map
	 * @return processInstanceId 返回流程实例
	 */
	ActRunningContext startProcessInstanceByKey(String processDefinitionKey,Map<String,Object> variables);
	
	/**
	 *
	 * @param processDefinitionKey 流程定义ID
	 * @param businessKey 业务主键 
	 * @param variables 流程变量
	 * @return
	 */
	ActRunningContext startProcessInstanceByKey(String processDefinitionKey,String businessKey,Map<String,Object> variables);
	
	/**
	 *  启动流程（核心方法）
	 * @param processDefinitionKey （必填）
	 * @param businessKey 业务主键（必填）
	 * @param businessType 业务类型（非必填）
	 * @param variables 
	 * @return
	 */
	ActRunningContext startProcessInstanceByKey(String processDefinitionKey,String businessKey,String businessType, Map<String, Object> variables);
	
	/**
	 * 流程挂起
	 * 如果一个流程实例状态暂停，与该实例的流程相关活动将不执行工作（定时器、消息）
	 * @param processInstanceId
	 */
	void suspendProcessInstanceById(String processInstanceId);
	
	/**
	 * 流程废除
	 * @param processInstanceId
	 * @param deleteReason 废除原因
	 */
	void deleteProcessInstance(String processInstanceId,String deleteReason);
	
	/**
	 * 流程废除
	 * @param processInstanceId
	 */
	void deleteProcessInstance(String processInstanceId);
	
	// -------------------------------------- 查询接口 ------------------------------------
	
	ProcessInstance getById(String processInstanceId);
	
	/**
	 * 获取运行中的流程
	 * @param query
	 * @return
	 */
	Page<ProcessInstance> queryForPageRunning(ProcessInstanceQuery query);
	
	/**
	 * 获取已结束的流程
	 * @param query
	 * @return
	 */
	Page<ProcessInstanceHis> queryForPageFinished(ProcessInstanceQuery query);
	
	/**
	 * 流程审批意见综合查询
	 * @param query
	 * @return
	 */
	List<ApproveOpinion> queryForListOpinion(ApproveOpinionQuery query) ;
	

	
	/**
	 * 当前实例调（会签并发）产生的任务
	 * @param processInstanceId 流程实例ID
	 * @return 注：如果不是“会签”或其它多实例分支产生的并发，返回任务数只有一个
	 */
	List<Task> getCurrentTaskList(String processInstanceId) ;
	
}