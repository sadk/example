package org.lsqt.act.model;

import org.lsqt.components.db.Page;

/**
 * 流程附件表
 */
public class ApproveOpinionFileQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符

	/** 业务主键（如付款审批ID) */
	private String businessKey;

	/***/
	private String approveProcessInstanceId;

	/***/
	private String approveTaskId;

	/***/
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

	/***/
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

	// getter、setter

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

}
