package org.lsqt.act.model;

import java.util.ArrayList;
import java.util.List;

import org.lsqt.components.db.Page;

/**
 * 审批意见
 */
public class ApproveOpinionQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;
	private Long limit; //限制记录条数
	
	private String key; // 关键字
	private String ids; // 用逗号分割的id字符

	private Long id;
	
	private String idNotIn;
	
	private String processInstanceId;
	
	private String processInstanceIds;
	
	/**表单数据ID**/
	private String businessKey;
	
	/** 流程定义ID */
	private String definitionId;

	/** 流程定义名称 */
	private String definitionName;

	/** 记录审批时的节点ID */
	private String approveTaskId;

	/** 记录审批时的节点Key */
	private String approveTaskKey;

	/** 审批操作 ： 同意、拒绝等 */
	private String approveResult;

	/** 意见内容 */
	private String approveOpinion;

	/** 审批用户Id */
	private String approveUserId;

	/** 审批用户名 */
	private String approveUserName;
	
	/***多级退回时，是否已“归位”状态：1=已归位*/
	private String rejectReRunCompleteStatus;
	

	/** 备注 */
	private String remark;
	
	// 辅助 -------------------------- 去除加签、转发、抄送意见用
	private List<String> approveActionListNotIn = new ArrayList<>();
	
	
	// getter、setter

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

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getApproveTaskId() {
		return approveTaskId;
	}

	public void setApproveTaskId(String approveTaskId) {
		this.approveTaskId = approveTaskId;
	}

	public String getApproveTaskKey() {
		return approveTaskKey;
	}

	public void setApproveTaskKey(String approveTaskKey) {
		this.approveTaskKey = approveTaskKey;
	}

	public String getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}

	public String getApproveOpinion() {
		return approveOpinion;
	}

	public void setApproveOpinion(String approveOpinion) {
		this.approveOpinion = approveOpinion;
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

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public List<String> getApproveActionListNotIn() {
		return approveActionListNotIn;
	}

	public void setApproveActionListNotIn(List<String> approveActionListNotIn) {
		this.approveActionListNotIn = approveActionListNotIn;
	}

	public Long getLimit() {
		return limit;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdNotIn() {
		return idNotIn;
	}

	public void setIdNotIn(String idNotIn) {
		this.idNotIn = idNotIn;
	}

	public String getRejectReRunCompleteStatus() {
		return rejectReRunCompleteStatus;
	}

	public void setRejectReRunCompleteStatus(String rejectReRunCompleteStatus) {
		this.rejectReRunCompleteStatus = rejectReRunCompleteStatus;
	}

	public String getProcessInstanceIds() {
		return processInstanceIds;
	}

	public void setProcessInstanceIds(String processInstanceIds) {
		this.processInstanceIds = processInstanceIds;
	}

}
