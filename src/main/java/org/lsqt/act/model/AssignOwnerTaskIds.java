package org.lsqt.act.model;

/**
 * 加签主人的任务ID累计
 */
public class AssignOwnerTaskIds {

	/***/
	private Long id;

	/** 流程实例id */
	private Long processInstanceId;

	/**加签主人用户Id**/
	private Long assignOwnerId;
	
	/** 加签时的节点key */
	private String taskKey;

	/***/
	private String taskIds;

	/** 备注 */
	private String remark;

	/***/
	private String gid;

	/** 创建日期 */
	private java.util.Date createTime;

	/***/
	private java.util.Date updateTime;

	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public Long getProcessInstanceId() {
		return this.processInstanceId;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getTaskKey() {
		return this.taskKey;
	}

	public void setTaskIds(String taskIds) {
		this.taskIds = taskIds;
	}

	public String getTaskIds() {
		return this.taskIds;
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

	public Long getAssignOwnerId() {
		return assignOwnerId;
	}

	public void setAssignOwnerId(Long assignOwnerId) {
		this.assignOwnerId = assignOwnerId;
	}

}
