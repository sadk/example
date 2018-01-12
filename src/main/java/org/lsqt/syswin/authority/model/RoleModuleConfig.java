package org.lsqt.syswin.authority.model;

/**
 * 角色模块下级\所有下级配置表（只用于UI显示）
 * @author admin
 *
 */
public class RoleModuleConfig {
	private Long id;
	private Long  roleId;
	private String moduleCode;
	private Integer level;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
}
