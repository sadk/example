package org.lsqt.act.model;

/**
 * 流程附件表
 */
public class ApproveOpinionFile {

	private Long id;

	/** 业务主键（如付款审批ID) */
	private String businessKey;

	/**审批意见id(ext_approve_opinion.id) */
	private String opinionId;
	
	
	private String approveProcessInstanceId;

	
	private String approveTaskId;

	
	private String approveTaskKey;

	/** 存储文件名 */
	private String storedName;

	/** 原始文件名 */
	private String originalName;

	/** 文件类型编码，引用字典值或自定义 */
	private String typeCode;

	/** 排序 */

	private Integer sn;

	/** 备注 */
	private String remark;

	
	private String appCode;

	/** (FastDFS文件)路径 */
	private String path;

	/** 文件缩略图（大） */
	private String pathLarge;

	/** 文件缩略图（中） */
	private String pathMedium;

	/** 文件缩略图（小） */
	private String pathSmall;

	/** 上传的用户ID */
	private String uploadUserId;

	/** 上传的用户名称 */
	private String uploadUserName;

	private String definitionId;
	private String definitionName;
	private String definitionKey;
	
	
	private String gid;

	/** 创建日期 */

	private java.util.Date createTime;

	private java.util.Date updateTime;

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

	public void setApproveProcessInstanceId(String approveProcessInstanceId) {
		this.approveProcessInstanceId = approveProcessInstanceId;
	}

	public String getApproveProcessInstanceId() {
		return this.approveProcessInstanceId;
	}

	public void setApproveTaskId(String approveTaskId) {
		this.approveTaskId = approveTaskId;
	}

	public String getApproveTaskId() {
		return this.approveTaskId;
	}

	public void setApproveTaskKey(String approveTaskKey) {
		this.approveTaskKey = approveTaskKey;
	}

	public String getApproveTaskKey() {
		return this.approveTaskKey;
	}

	public void setStoredName(String storedName) {
		this.storedName = storedName;
	}

	public String getStoredName() {
		return this.storedName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getOriginalName() {
		return this.originalName;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeCode() {
		return this.typeCode;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public Integer getSn() {
		return this.sn;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAppCode() {
		return this.appCode;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return this.path;
	}

	public void setPathLarge(String pathLarge) {
		this.pathLarge = pathLarge;
	}

	public String getPathLarge() {
		return this.pathLarge;
	}

	public void setPathMedium(String pathMedium) {
		this.pathMedium = pathMedium;
	}

	public String getPathMedium() {
		return this.pathMedium;
	}

	public void setPathSmall(String pathSmall) {
		this.pathSmall = pathSmall;
	}

	public String getPathSmall() {
		return this.pathSmall;
	}

	public void setUploadUserId(String uploadUserId) {
		this.uploadUserId = uploadUserId;
	}

	public String getUploadUserId() {
		return this.uploadUserId;
	}

	public void setUploadUserName(String uploadUserName) {
		this.uploadUserName = uploadUserName;
	}

	public String getUploadUserName() {
		return this.uploadUserName;
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

	public String getOpinionId() {
		return opinionId;
	}

	public void setOpinionId(String opinionId) {
		this.opinionId = opinionId;
	}

	public String getDefinitionId() {
		return definitionId;
	}

	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}

	public String getDefinitionName() {
		return definitionName;
	}

	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}

	public String getDefinitionKey() {
		return definitionKey;
	}

	public void setDefinitionKey(String definitionKey) {
		this.definitionKey = definitionKey;
	}

}
