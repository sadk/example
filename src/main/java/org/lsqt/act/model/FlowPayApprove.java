package org.lsqt.act.model;

/**
 * 付款审批业务表
 */
public class FlowPayApprove {

	/***/
	private Long id;

	/** 发起人ID */
	private String applyUserId;

	/** 发起人名称 */
	private String applyUserName;

	/** 发起时间 */
	private java.util.Date applyTime;

	/** 付款说明 */
	private String payRemark;

	/** 创建者id */
	private String createby;

	/** 创建者名称 */
	private String createbyName;

	/** 修改者id */
	private String updateby;

	/** 修改者名称 */
	private String updatebyName;

	/** 修改时间 */
	private java.util.Date updateDate;

	
	//-----  辅助属性 
	private String taskId; // 流程任务id
	private String approveResult; // "true"=同意 "false"=不同意
	public static String APPROVE_RESULT_同意="true";
	public static String APPROVE_RESULT_不同意="false";
	
	private String fileUrls ; // (多个)文件附件地址
	private String approveOpinion; // 审批意见
	
	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}

	public String getApplyUserId() {
		return this.applyUserId;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public String getApplyUserName() {
		return this.applyUserName;
	}

	public void setApplyTime(java.util.Date applyTime) {
		this.applyTime = applyTime;
	}

	public java.util.Date getApplyTime() {
		return this.applyTime;
	}

	public void setPayRemark(String payRemark) {
		this.payRemark = payRemark;
	}

	public String getPayRemark() {
		return this.payRemark;
	}

	public void setCreateby(String createby) {
		this.createby = createby;
	}

	public String getCreateby() {
		return this.createby;
	}

	public void setCreatebyName(String createbyName) {
		this.createbyName = createbyName;
	}

	public String getCreatebyName() {
		return this.createbyName;
	}

	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}

	public String getUpdateby() {
		return this.updateby;
	}

	public void setUpdatebyName(String updatebyName) {
		this.updatebyName = updatebyName;
	}

	public String getUpdatebyName() {
		return this.updatebyName;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
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

	public String getFileUrls() {
		return fileUrls;
	}

	public void setFileUrls(String fileUrls) {
		this.fileUrls = fileUrls;
	}

}
