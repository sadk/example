package org.lsqt.act.model;

/**
 * 用户规则矩阵：流程启用设置
 */
public class UserRuleMatrixFlow {

	/***/
	private Long id;

	/** 用户规则ID */
	private Long userRuleId;
	private String userRuleName;

	/** 流程定义Id */
	private String definitionId;
	private String definitionName; 
	
	/** 流程节点 */
	private String taskKey;

	/** 流程节点名称 */
	private String taskName;

	// getter、setter
	
	
	private String version;
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setUserRuleId(Long userRuleId) {
		this.userRuleId = userRuleId;
	}

	public Long getUserRuleId() {
		return this.userRuleId;
	}

	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}

	public String getDefinitionId() {
		return this.definitionId;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getTaskKey() {
		return this.taskKey;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public String getDefinitionName() {
		return definitionName;
	}

	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUserRuleName() {
		return userRuleName;
	}

	public void setUserRuleName(String userRuleName) {
		this.userRuleName = userRuleName;
	}

}
