package org.lsqt.syswin.uum.model;

/**
 * 岗位归类数据表
 */
public class PositionCategoryData {

	/***/
	private Long id;

	/** 岗位分类（维度）ID,引用t_duties表id */
	private Long positCategoryId;

	/** 岗位ID */
	private Long positionId;

	/** 岗位所在的组织(冗余字段) */
	private Long orgId;

	/** (冗余的)组织名称 */
	private String orgName;

	/** (冗余的)组织节点路径 */
	private String orgNodePath;

	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setPositCategoryId(Long positCategoryId) {
		this.positCategoryId = positCategoryId;
	}

	public Long getPositCategoryId() {
		return this.positCategoryId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public Long getPositionId() {
		return this.positionId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgNodePath(String orgNodePath) {
		this.orgNodePath = orgNodePath;
	}

	public String getOrgNodePath() {
		return this.orgNodePath;
	}

}
