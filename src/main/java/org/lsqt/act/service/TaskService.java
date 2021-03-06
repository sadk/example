package org.lsqt.act.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lsqt.act.model.ActRunningContext;
import org.lsqt.act.model.ApproveObject;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.ProcessInstance;
import org.lsqt.act.model.Task;
import org.lsqt.act.model.TaskQuery;
import org.lsqt.components.db.Page;

public interface TaskService {
	// ------------------------------------------------------ 提交动作  --------------------------------------------------
	/**
	 * 认领任务
	 * @param taskId 
	 * @param userId
	 */
	void claim(String taskId, String userId);
	
	/**
	 * 放回认领的任务
	 * @param taskId
	 */
	void unclaim(String taskId) ;
	
	/**
	 * 流程下一步
	 * @param taskId
	 */
	void complete(String taskId);
	
	/**
	 * 流程下一步
	 * @param taskId
	 * @param variables
	 */
	void complete(String taskId,Map<String,Object> variables);
	
	/**
	 * 流程下一步
	 * @param taskId
	 * @param variable
	 * @param approveOpinion 流程审批意见，其中approveOpinion.approveAction字段很重要
	 * @param isSaveOpinionAttachment 是否保存意见的附件
	 */
	ActRunningContext complete(Long loginUserId,String taskId, Map<String, Object> variable,ApproveOpinion approveOpinion) ;
	
	/**
	 * 流程任意跳转
	 * @param taskId 当前待办ID
	 * @param targetTaskKey 目标任务节点key
	 * @param variables 流程变量
	 */
	void jump(String taskId,String targetTaskKey,Map<String,Object> variables);
	
	/**
	 * 删除(作废)任务
	 * @param ids
	 */
	void deleteTask(String ...ids);
	
	
	/**
	 * 删除(作废)任务
	 * @param reason 废除原因
	 * @param ids
	 */
	void deleteTask(String reason,String ...ids);
	
	
	
	
	
	// --------------------------------------------------------- 查询 ----------------------------------------------
	/**
	 * 判断当前任务是否是加签过来的任务
	 * 注意：当前任务为非并发的任务（也就是流程的Getway为exclusive产生的任务)
	 * @param taskId
	 * @return
	 */
	boolean isFromAssignedTask(String taskId);
	
	/**
	 * 获取(待办)任务对象
	 * @param taskId
	 * @return
	 */
	Task getById(String taskId);
	
	/**
	 * 获取任务分页列表(不推荐使用，没有含流程标题、发起人等信息)
	 * @param query (登陆用户的,注：query#userId 设置值，即可获取用户的任务列表)
	 * @return
	 */
	@Deprecated
	Page<Task> queryForPage(TaskQuery query);
	
	/**
	 *  获取任务分页列表(不推荐使用，没有含流程标题、发起人等信息)
	 * @param query (登陆用户的,注：query#userId 设置值，即可获取用户的任务列表)
	 * @return
	 */
	@Deprecated
	List<Task> queryForList(TaskQuery query);
	
	
	
	/**
	 * 获取待办 （含发起人、流程标题等信息）,生产环境性能慢
	 * @param query
	 * @return
	 */
	@Deprecated
	Page<Task> queryForPageDetail(org.lsqt.act.model.TaskQuery query) ;
	
	/**
	 * 获取待办 （含发起人、流程标题等信息），生产环境性能慢
	 * @param query
	 * @return
	 */
	@Deprecated
	List<Task> queryForListDetail(org.lsqt.act.model.TaskQuery query) ;
	
	
	/**
	 * 获取待办 （含发起人、流程标题等信息），已优化到"极致"！
	 * @param query
	 * @return
	 */
	Page<Task> queryMyToDoTaskPage(org.lsqt.act.model.TaskQuery query);
	
	/**
	 * 获取待办 （含发起人、流程标题等信息），已优化到"极致"！
	 * @param query
	 * @return
	 */
	List<Task> queryMyToDoTaskList(org.lsqt.act.model.TaskQuery query);
	
	
	
	/**
	 * 获取当前实例的活动任务（不支持并发模式）
	 * @param processInstanceId
	 * @return
	 */
	Task getNextNewTask(String processInstanceId) ;
	
	/**
	 * 获取当前实例的活动任务（并发模式）
	 * @param processInstanceId 
	 * @return
	 */
	List<Task> getCurrentMutilTaskList(String processInstanceId);
	
	/**
	 * 填充会签流程变量（供流程complete用）
	 * @param instance 流程实例
	 * @param nodeUserMap 审批用户
	 * @return
	 */
	Map<String,Object> prepareMeetingVariable(ProcessInstance instance,Map<String, List<ApproveObject>> nodeUserMap);
	
	/**
	 * 加载任务的审批用户
	 * @param taskData
	 */
	void loadApproveUser(Collection<Task> taskData) ;
	
	/**
	 * 加载任务是否是历史任务，见标志isHistoryTask
	 * @param taskData
	 
	void loadIsHistoryTask(Collection<Task> taskData) ;
	*/
	
	/**
	 * 我已审批，替换taskKey为最新的待办任务taskKey
	 * @param taskData
	 */
	void replaceTaskKey(Collection<Task> taskData);
}
