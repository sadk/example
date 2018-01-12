package org.lsqt.syswin.authority.model;

/**
 * 岗位的组织结点数据权限
 * @author mmyuan
 *
 */
public class PositionOrgPermission {
	private Long id;
	private Long positionId;
	private String moduleCode;
	private String orgIds;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPositionId() {
		return positionId;
	}
	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public String getOrgIds() {
		return orgIds;
	}
	public void setOrgIds(String orgIds) {
		this.orgIds = orgIds;
	}
}
