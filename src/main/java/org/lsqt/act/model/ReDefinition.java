package org.lsqt.act.model;

/**
 * 流程定义扩展信息表
 */
public class ReDefinition {

	/***/
	private Long id;

	/** 流程定义ID */
	private String definitionId;

	/** 节点定义全称 */
	private String definitionName;

	/** 流程定义简称 */
	private String definitionShortName;

	/** 节点定义Key */
	private String definitionKey;

	/** 前置角本 */
	private String beforeScript;

	/** 1=url 2=javascript_code 3=javacode */
	private String beforeScriptType;

	/** 后置角本 */
	private String afterScript;

	/** 1=url 2=javascript_code 3=javacode */
	private String afterScriptType;

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

	public void setDefinitionShortName(String definitionShortName) {
		this.definitionShortName = definitionShortName;
	}

	public String getDefinitionShortName() {
		return this.definitionShortName;
	}

	public void setDefinitionKey(String definitionKey) {
		this.definitionKey = definitionKey;
	}

	public String getDefinitionKey() {
		return this.definitionKey;
	}

	public void setBeforeScript(String beforeScript) {
		this.beforeScript = beforeScript;
	}

	public String getBeforeScript() {
		return this.beforeScript;
	}

	public void setBeforeScriptType(String beforeScriptType) {
		this.beforeScriptType = beforeScriptType;
	}

	public String getBeforeScriptType() {
		return this.beforeScriptType;
	}

	public void setAfterScript(String afterScript) {
		this.afterScript = afterScript;
	}

	public String getAfterScript() {
		return this.afterScript;
	}

	public void setAfterScriptType(String afterScriptType) {
		this.afterScriptType = afterScriptType;
	}

	public String getAfterScriptType() {
		return this.afterScriptType;
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

}
