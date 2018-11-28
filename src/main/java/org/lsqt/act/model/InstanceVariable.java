package org.lsqt.act.model;

/**
 * 流程变量数据
 */
public class InstanceVariable {

	/***/
	private Long id;

	/** 流程实例ID */
	private String instanceId;

	/** 流程实例标题 */
	private String title;

	/** 流程实例ID */
	private String definitionId;

	/** 业务数据主键ID */
	private String businessKey;

	/** 流程变量（最初传入变量+流程审批人）*/
	private String variableJson;
	
	/**流程发起时最初传入的变量**/
	private String variableJSONStart;

	/** 租户编码 */
	private String appCode;

	/** 排序 */
	private Integer sn;

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

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getInstanceId() {
		return this.instanceId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}

	public String getDefinitionId() {
		return this.definitionId;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getBusinessKey() {
		return this.businessKey;
	}

	public void setVariableJson(String variableJson) {
		this.variableJson = variableJson;
	}

	public String getVariableJson() {
		return this.variableJson;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAppCode() {
		return this.appCode;
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

	public String getVariableJSONStart() {
		return variableJSONStart;
	}

	public void setVariableJSONStart(String variableJSONStart) {
		this.variableJSONStart = variableJSONStart;
	}

}
