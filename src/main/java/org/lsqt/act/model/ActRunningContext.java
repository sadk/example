package org.lsqt.act.model;

import java.util.List;
import java.util.Map;

 
import org.lsqt.act.model.ApproveObject;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.Node;
import org.lsqt.act.model.ProcessInstance;
import org.lsqt.act.model.Task;
import org.lsqt.components.db.Db;
import org.lsqt.syswin.uum.model.User;

/**
 * 流程在流转的时候，各种使用中的对象
 * @author mmyuan
 *
 */
public class ActRunningContext {
	private org.activiti.bpmn.model.Task currActTask; 
	private org.activiti.engine.runtime.ProcessInstance  currActProcessInstance;
	
	
	private Task currTask;
	private ProcessInstance currProcessInstance;
	
	private org.activiti.bpmn.model.Task prevActTask ; 
	private org.lsqt.act.model.Task prevTask;
	
	private Map<String, List<ApproveObject>> nodeUserMap ; // 瞬时解析的审批用户
	
	private Node draftNode; // 拟稿节点
	private ApproveOpinion approveOpinion; // 提交过来的审批意见（或含附件）
	
	private String prevTaskCandidateUserIds ; // 上一步的审批用户（用于判断相邻节点是同一个人就自动跳过）
	private String nextTaskCandidateUserIds ; // 下一节点的审批用户
	
	private boolean isMutilTaskConcurrent; // 是否是并发的（会签）任务 
	private List<Task> mutilTaskConcurrentList;
	
	
	private User loginUser;
	
	private RunningForm form = new RunningForm();
	
	
	private Db db;
	
	public boolean getIsMutilTaskConcurrent() {
		org.lsqt.act.model.TaskQuery filter = new org.lsqt.act.model.TaskQuery();
		filter.setProcessInstanceId(form.getProcessInstanceId());
		List<Task> taskList = db.queryForList("querySimple", Task.class, filter);
		if (taskList != null && taskList.size() > 1) {
			mutilTaskConcurrentList = taskList;
			isMutilTaskConcurrent = true;
		}
		return isMutilTaskConcurrent;
	}
	
	private long runningStartTime = System.currentTimeMillis();

	public long getRunningCost() {
		long rs = (System.currentTimeMillis() - this.runningStartTime);
		runningStartTime = System.currentTimeMillis();
		return rs;
	}

	public ActRunningContext (Db db) {
		this.db = db;
	}
	
	/**
	 * 主要是用来存放流程相关的数据，而不是业务表单的数据
	 * @author mmyuan
	 *
	 */
	public static class RunningForm {
		private String startUserId;  // 发起人
		private String bussinessKey; 
		private String createDeptId; // 填制人部门
		private String flowNo; // 单据流程号
		
		private String processInstanceId;
		private String processDefinitionId;
		private String action; // 审批动作
		private String actionTarget; // 审批动作的目标（例如，驳回到拟稿节点，那么target就是拟稿节点的key)
		
		
		public String getStartUserId() {
			return startUserId;
		}
		public void setStartUserId(String startUserId) {
			this.startUserId = startUserId;
		}
		public String getBussinessKey() {
			return bussinessKey;
		}
		public void setBussinessKey(String bussinessKey) {
			this.bussinessKey = bussinessKey;
		}
		public String getCreateDeptId() {
			return createDeptId;
		}
		public void setCreateDeptId(String createDeptId) {
			this.createDeptId = createDeptId;
		}
		public String getFlowNo() {
			return flowNo;
		}
		public void setFlowNo(String flowNo) {
			this.flowNo = flowNo;
		}
		public String getProcessInstanceId() {
			return processInstanceId;
		}
		public void setProcessInstanceId(String processInstanceId) {
			this.processInstanceId = processInstanceId;
		}
		public String getProcessDefinitionId() {
			return processDefinitionId;
		}
		public void setProcessDefinitionId(String processDefinitionId) {
			this.processDefinitionId = processDefinitionId;
		}
		public String getAction() {
			return action;
		}
		public void setAction(String action) {
			this.action = action;
		}
		public String getActionTarget() {
			return actionTarget;
		}
		public void setActionTarget(String actionTarget) {
			this.actionTarget = actionTarget;
		}
		
		
	}
	
	public org.activiti.bpmn.model.Task getCurrActTask() {
		return currActTask;
	}

	public void setCurrActTask(org.activiti.bpmn.model.Task currActTask) {
		this.currActTask = currActTask;
	}

	public org.activiti.engine.runtime.ProcessInstance getCurrActProcessInstance() {
		return currActProcessInstance;
	}

	public void setCurrActProcessInstance(org.activiti.engine.runtime.ProcessInstance currActProcessInstance) {
		this.currActProcessInstance = currActProcessInstance;
	}

	public Task getCurrTask() {
		return currTask;
	}

	public void setCurrTask(Task currTask) {
		this.currTask = currTask;
	}

	public ProcessInstance getCurrProcessInstance() {
		return currProcessInstance;
	}

	public void setCurrProcessInstance(ProcessInstance currProcessInstance) {
		this.currProcessInstance = currProcessInstance;
	}

	public org.activiti.bpmn.model.Task getPrevActTask() {
		return prevActTask;
	}

	public void setPrevActTask(org.activiti.bpmn.model.Task prevActTask) {
		this.prevActTask = prevActTask;
	}

	public org.lsqt.act.model.Task getPrevTask() {
		return prevTask;
	}

	public void setPrevTask(org.lsqt.act.model.Task prevTask) {
		this.prevTask = prevTask;
	}

	public Map<String, List<ApproveObject>> getNodeUserMap() {
		return nodeUserMap;
	}

	public void setNodeUserMap(Map<String, List<ApproveObject>> nodeUserMap) {
		this.nodeUserMap = nodeUserMap;
	}

	public Node getDraftNode() {
		return draftNode;
	}

	public void setDraftNode(Node draftNode) {
		this.draftNode = draftNode;
	}

	public ApproveOpinion getApproveOpinion() {
		return approveOpinion;
	}

	public void setApproveOpinion(ApproveOpinion approveOpinion) {
		this.approveOpinion = approveOpinion;
	}

	public String getNextTaskCandidateUserIds() {
		return nextTaskCandidateUserIds;
	}

	public void setNextTaskCandidateUserIds(String nextTaskCandidateUserIds) {
		this.nextTaskCandidateUserIds = nextTaskCandidateUserIds;
	}

	public List<Task> getMutilTaskConcurrentList() {
		return mutilTaskConcurrentList;
	}

	public void setMutilTaskConcurrentList(List<Task> mutilTaskConcurrentList) {
		this.mutilTaskConcurrentList = mutilTaskConcurrentList;
	}

	public Db getDb() {
		return db;
	}

	public void setDb(Db db) {
		this.db = db;
	}


	public RunningForm getForm() {
		return form;
	}


	public void setForm(RunningForm form) {
		this.form = form;
	}

	public String getPrevTaskCandidateUserIds() {
		return prevTaskCandidateUserIds;
	}

	public void setPrevTaskCandidateUserIds(String prevTaskCandidateUserIds) {
		this.prevTaskCandidateUserIds = prevTaskCandidateUserIds;
	}

	public User getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}
}
