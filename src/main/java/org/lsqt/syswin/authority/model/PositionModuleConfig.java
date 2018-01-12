package org.lsqt.syswin.authority.model;

/**
 * 岗位模块范围细分表
 */
public class PositionModuleConfig {

	/** pk */
	private Long id;

	/** 岗位id */

	private Long positionId;

	/** 功能模块编码 */
	private String moduleCode;

	private String moduleName;
	
	/**2=直属下级 3=所有下级**/
	private Integer level;
	
	
	
	
	
	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public Long getPositionId() {
		return this.positionId;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getModuleCode() {
		return this.moduleCode;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

}
