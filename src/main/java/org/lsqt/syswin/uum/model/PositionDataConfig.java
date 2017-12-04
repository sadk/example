package org.lsqt.syswin.uum.model;

/**
 * 岗位数据查询和使用权限配置
 * @author admin
 *
 */
public class PositionDataConfig {
	private Long id;
	private Long positionId;
	private Long orgId;
	private Long orgPid;
	private Integer orgType;
	private String orgName;
	
	//授权类型:mySelf=本人 underMe=直属下级 all=全部
	private String colType;
	public static final String COL_TYPE_MYSELF="mySelf";
	public static final String COL_TYPE_UNDERME="underMe";
	public static final String COL_TYPE_ALL="all";
	public static final String COL_TYPE_USENESS="useness";
	
	
	
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
	public String getColType() {
		return colType;
	}
	public void setColType(String colType) {
		this.colType = colType;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getOrgPid() {
		return orgPid;
	}
	public void setOrgPid(Long orgPid) {
		this.orgPid = orgPid;
	}
	public Integer getOrgType() {
		return orgType;
	}
	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
}
