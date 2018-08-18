package org.lsqt.act.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.ProcessDefinition;
import org.lsqt.components.db.Db;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.uum.model.User;

/**
 * 流程在流转的时候，各种使用中的对象上下文
 * @author mmyuan
 *
 */
public class ActRunningContext {
	private int nodeCount; // 当前流程节点数
	
	private org.activiti.bpmn.model.Task inputActTask; 

	private org.activiti.engine.runtime.ProcessInstance  currActProcessInstance;
	 
	
	private Task inputTask; // 入参时候的任务（用户在页面上点击待办的那个任务）
	private Map<String,Object> inputVariable = new HashMap<>(); // 入参时的流程变量
	
	private Task runingCurrTask; // 流程在流转中的任务
	private Task runingPrevTask; // 流程在流转中的上一步任务
	
	
	private RunInstance currProcessInstance;  
	private ProcessDefinition currActDefinition; 
	
	private ReDefinition currReDefinion; // 扩展的流程定义
	
	
	private ProcessInstance startedProcessInstance; // 流程发起后的流程实例
	
	private Map<String,Object> completeVariable = new HashMap<>() ;
	
	
	
	private Map<String /*节点key*/, List<ApproveObject>> nodeUserMap ; // 瞬时解析的审批用户
	
	private Node draftNode; // 拟稿节点
	private ApproveOpinion approveOpinion; // 提交过来的审批意见（或含附件）
	
	private String prevTaskCandidateUserIds ; // 入参节点的审批用户（用于判断相邻节点是同一个人就自动跳过）
	private String nextTaskCandidateUserIds ; // 下一节点的审批用户
	
	

	
	
	private User loginUser;
	
	private RunningForm form = new RunningForm();
	
	private Object dataHook ; // 数据钩子（用于传流程中的数据返回给客户端程序）
	
	
	private Db db;
	private PlatformDb db2;
	
	public PlatformDb getPlatformDb(){
		return this.db2;
	}
	public ActRunningContext (Db db) {
		this.db = db;
	}
	
	public ActRunningContext (Db db,PlatformDb db2) {
		this.db = db;
		this.db2 = db2;
	}
	
	/**
	 * 主要是用来存放流程相关的数据，而不是业务表单的数据
	 * @author mmyuan
	 *
	 */
	public static class RunningForm {
		private String title; // 单据标题
		private String startUserId;  // 发起人
		private String businessKey; // 单据主键
		private String createDeptId; // 填制人部门
		private String businessFlowNo; // 单据流程号
		private String approveOpinion; // 单据审批意见
		private String businessType; // 单据数据业务分类
		private String companyNamePrint; //用印公司
		
		private String processInstanceId;
		private String processDefinitionId;

		
		public String getStartUserId() {
			return startUserId;
		}
		public void setStartUserId(String startUserId) {
			this.startUserId = startUserId;
		}
		public String getCreateDeptId() {
			return createDeptId;
		}
		public void setCreateDeptId(String createDeptId) {
			this.createDeptId = createDeptId;
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
		public String getBusinessFlowNo() {
			return businessFlowNo;
		}
		public void setBusinessFlowNo(String businessFlowNo) {
			this.businessFlowNo = businessFlowNo;
		}
		public String getApproveOpinion() {
			return approveOpinion;
		}
		public void setApproveOpinion(String approveOpinion) {
			this.approveOpinion = approveOpinion;
		}
		public String getBusinessType() {
			return businessType;
		}
		public void setBusinessType(String businessType) {
			this.businessType = businessType;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getBusinessKey() {
			return businessKey;
		}
		public void setBusinessKey(String businessKey) {
			this.businessKey = businessKey;
		}
		public String getCompanyNamePrint() {
			return companyNamePrint;
		}
		public void setCompanyNamePrint(String companyNamePrint) {
			this.companyNamePrint = companyNamePrint;
		}
		
		
	}
 

	public org.activiti.engine.runtime.ProcessInstance getCurrActProcessInstance() {
		return currActProcessInstance;
	}

	public void setCurrActProcessInstance(org.activiti.engine.runtime.ProcessInstance currActProcessInstance) {
		this.currActProcessInstance = currActProcessInstance;
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

	public User getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}

	public org.activiti.bpmn.model.Task getInputActTask() {
		return inputActTask;
	}

	public void setInputActTask(org.activiti.bpmn.model.Task inputActTask) {
		this.inputActTask = inputActTask;
	}



	public Task getInputTask() {
		return inputTask;
	}

	public void setInputTask(Task inputTask) {
		this.inputTask = inputTask;
	}

	public Task getRuningCurrTask() {
		return runingCurrTask;
	}

	public void setRuningCurrTask(Task runingCurrTask) {
		this.runingCurrTask = runingCurrTask;
	}

	public Task getRuningPrevTask() {
		return runingPrevTask;
	}

	public void setRuningPrevTask(Task runingPrevTask) {
		this.runingPrevTask = runingPrevTask;
	}

	public Map<String, Object> getCompleteVariable() {
		return completeVariable;
	}

	public void setCompleteVariable(Map<String, Object> completeVariable) {
		this.completeVariable = completeVariable;
	}

	public Map<String, Object> getInputVariable() {
		return inputVariable;
	}

	public void setInputVariable(Map<String, Object> inputVariable) {
		this.inputVariable = inputVariable;
	}

	public RunInstance getCurrProcessInstance() {
		return currProcessInstance;
	}

	public void setCurrProcessInstance(RunInstance currProcessInstance) {
		this.currProcessInstance = currProcessInstance;
	}

	public ProcessDefinition getCurrActDefinition() {
		return currActDefinition;
	}

	public void setCurrActDefinition(ProcessDefinition currActDefinition) {
		this.currActDefinition = currActDefinition;
	}

	public ProcessInstance getStartedProcessInstance() {
		return startedProcessInstance;
	}

	public void setStartedProcessInstance(ProcessInstance startedProcessInstance) {
		this.startedProcessInstance = startedProcessInstance;
	}

	public ReDefinition getCurrReDefinion() {
		return currReDefinion;
	}

	public void setCurrReDefinion(ReDefinition currReDefinion) {
		this.currReDefinion = currReDefinion;
	}

	public int getNodeCount() {
		return nodeCount;
	}

	public void setNodeCount(int nodeCount) {
		this.nodeCount = nodeCount;
	}

	public String getPrevTaskCandidateUserIds() {
		return prevTaskCandidateUserIds;
	}

	public void setPrevTaskCandidateUserIds(String prevTaskCandidateUserIds) {
		this.prevTaskCandidateUserIds = prevTaskCandidateUserIds;
	}
	public Object getDataHook() {
		return dataHook;
	}
	public void setDataHook(Object dataHook) {
		this.dataHook = dataHook;
	}
}
