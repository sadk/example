package org.lsqt.act.model;

/**
 * 用户规则矩阵：填制人部门审批用户设置
 */
public class UserRuleMatrixDeptUser {

	/***/

	private Long id;

	/** 用户规则ID */

	private Long userRuleId;

	/** 填制人部门 */

	private Long createDeptId;

	/** 审批用户 */
	private String userIds;

	// getter、setter
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

	public void setCreateDeptId(Long createDeptId) {
		this.createDeptId = createDeptId;
	}

	public Long getCreateDeptId() {
		return this.createDeptId;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getUserIds() {
		return this.userIds;
	}

}
