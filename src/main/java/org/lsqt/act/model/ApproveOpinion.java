package org.lsqt.act.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 审批意见
 */
public class ApproveOpinion {
	
	/**当前意见的附件**/
	private List<ApproveOpinionFile> attachmentList = new ArrayList<>();
	
	private Long id;

	/** 表单数据id */
	private String businessKey;

	private String businessType;
	
	private String processInstanceId;

	/** 流程定义ID */
	private String definitionId;

	/** 流程定义Key**/
	private String definitionKey;
	
	/** 流程定义名称 */
	private String definitionName;

	/** 节点定义Key */
	private String approveTaskKey;

	/** 记录审批时的节点ID */
	private String approveTaskId;
	
	/** 记录审批时的节点名称 */
	private String approveTaskName;
	
	/** 记录审批时的节点别名 */
	private String approveTaskNameAlias;

	/** 审批操作 ： 同意、拒绝结果等code，详情见  */
	private String approveAction;
	
	/** 当用户驳回到选择节点时，该属性值就是用户选择的节点key */
	private String rejectToChooseNodeTaskKey;
	
	/**
	 * 多级退回时，是否已“归位”状态：1=已归位
	 */
	private String rejectReRunCompleteStatus; 
	public static final String REJECT_RE_RUN_COMPLETESTATUS_COMPLETE="1";

	/** 审批操作 ： 同意、拒绝结果值等 */
	private String approveResult;

	/** 意见内容 */
	private String approveOpinion;

	/** 审批用户Id */
	private String approveUserId;

	/** 审批用户名 */
	private String approveUserName;
	
	/** 审批用户岗位信息 */
	private String approveUserPositionText;
	
	/**审批用户组织信息**/
	private String approveUserOrgText;
	

	/**记录审批任务时，候选的审批用户（例如，驳回时，需要使用此字段)**/
	private String approveTaskCandidateUserIds;
	
	/**记录审批任务的流程变量Map 的JSON表示**/
	private String variablesJson;
	 
	/** 备注 */
	private String remark;

	private String gid;
	
	private java.util.Date createTime;

	private java.util.Date updateTime;
	
	/** 待办（手机）消息是否已删除，可用于消息是否已阅、已处理等业务**/
	private Integer deleteMsgFlag;
	public static final int DELETE_MSG_FLAG_已删除=1;
	
	// --- 辅助字段，用户加签、转发、抄送的用户ID
	private String assignForwardCcUserIds;
	
	// --- 辅助字段，审批意见对应的节点是否是会签节点
	private boolean isMeetingNode; 
	
	public static List<Long> getIdList(List<ApproveOpinion> list) {
		List<Long> data = new ArrayList<>();
		for(ApproveOpinion e: list) {
			if(e!=null) {
				data.add(e.getId());
			}
		}
		return data;
	}
	
	public String getCreateTimeDesc() {
		if(createTime!=null) {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.createTime);
		}
		return "";
	}
	
	public String getUpdateTimeDesc() {
		if(updateTime!=null) {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.updateTime);
		}
		return "";
	}
	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getBusinessKey() {
		return this.businessKey;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getProcessInstanceId() {
		return this.processInstanceId;
	}

	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}

	public String getDefinitionId() {
		return this.definitionId;
	}

	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}

	public String getDefinitionName() {
		return this.definitionName;
	}
 

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getGid() {
		return this.gid;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
 
	public String getApproveTaskKey() {
		return approveTaskKey;
	}
	public void setApproveTaskKey(String approveTaskKey) {
		this.approveTaskKey = approveTaskKey;
	}
	public String getApproveTaskId() {
		return approveTaskId;
	}
	public void setApproveTaskId(String approveTaskId) {
		this.approveTaskId = approveTaskId;
	}
	public String getApproveAction() {
		return approveAction;
	}
	public void setApproveAction(String approveAction) {
		this.approveAction = approveAction;
	}
	public String getApproveResult() {
		return approveResult;
	}
	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}
	public String getApproveUserId() {
		return approveUserId;
	}
	public void setApproveUserId(String approveUserId) {
		this.approveUserId = approveUserId;
	}
	public String getApproveUserName() {
		return approveUserName;
	}
	public void setApproveUserName(String approveUserName) {
		this.approveUserName = approveUserName;
	}
	public String getRejectToChooseNodeTaskKey() {
		return rejectToChooseNodeTaskKey;
	}
	public void setRejectToChooseNodeTaskKey(String rejectToChooseNodeTaskKey) {
		this.rejectToChooseNodeTaskKey = rejectToChooseNodeTaskKey;
	}
	public List<ApproveOpinionFile> getAttachmentList() {
		return attachmentList;
	}
	public void setAttachmentList(List<ApproveOpinionFile> attachmentList) {
		this.attachmentList = attachmentList;
	}
	public String getDefinitionKey() {
		return definitionKey;
	}
	public void setDefinitionKey(String definitionKey) {
		this.definitionKey = definitionKey;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getApproveOpinion() {
		return approveOpinion;
	}
	public void setApproveOpinion(String approveOpinion) {
		this.approveOpinion = approveOpinion;
	}
	public String getApproveTaskCandidateUserIds() {
		return approveTaskCandidateUserIds;
	}
	public void setApproveTaskCandidateUserIds(String approveTaskCandidateUserIds) {
		this.approveTaskCandidateUserIds = approveTaskCandidateUserIds;
	}
	public String getVariablesJson() {
		return variablesJson;
	}
	public void setVariablesJson(String variablesJson) {
		this.variablesJson = variablesJson;
	}
	public String getAssignForwardCcUserIds() {
		return assignForwardCcUserIds;
	}
	public void setAssignForwardCcUserIds(String assignForwardCcUserIds) {
		this.assignForwardCcUserIds = assignForwardCcUserIds;
	}

	public String getApproveTaskName() {
		return approveTaskName;
	}

	public void setApproveTaskName(String approveTaskName) {
		this.approveTaskName = approveTaskName;
	}

	public String getApproveUserPositionText() {
		return approveUserPositionText;
	}

	public void setApproveUserPositionText(String approveUserPositionText) {
		this.approveUserPositionText = approveUserPositionText;
	}

	public String getApproveUserOrgText() {
		return approveUserOrgText;
	}

	public void setApproveUserOrgText(String approveUserOrgText) {
		this.approveUserOrgText = approveUserOrgText;
	}

	public String getRejectReRunCompleteStatus() {
		return rejectReRunCompleteStatus;
	}

	public void setRejectReRunCompleteStatus(String rejectReRunCompleteStatus) {
		this.rejectReRunCompleteStatus = rejectReRunCompleteStatus;
	}

	public boolean getIsMeetingNode() {
		return isMeetingNode;
	}

	public void setIsMeetingNode(boolean isMeetingNode) {
		this.isMeetingNode = isMeetingNode;
	}

	public String getApproveTaskNameAlias() {
		return approveTaskNameAlias;
	}

	public void setApproveTaskNameAlias(String approveTaskNameAlias) {
		this.approveTaskNameAlias = approveTaskNameAlias;
	}

	public Integer getDeleteMsgFlag() {
		return deleteMsgFlag;
	}

	public void setDeleteMsgFlag(Integer deleteMsgFlag) {
		this.deleteMsgFlag = deleteMsgFlag;
	}


}
