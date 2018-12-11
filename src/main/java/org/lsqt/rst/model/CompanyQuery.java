package org.lsqt.rst.model;

import org.lsqt.components.db.Page;

/**
 * 公司信息
 */
public class CompanyQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符
	private Long id;

	/** 企业编号 */
	private String code;

	/** 企业名称 */
	private String fullName;

	/** 企业简称 */
	private String shortName;

	/** 实际上班地点 */
	private String actualWorkAddress;

	/** 企业介绍 */
	private String introduction;

	/** 状态(0:有效，1:无效) */
	private Integer status;

	/** 创建人 */
	private String createUser;

	/**企业的考勤月切预留天数。例如：5天后不能修改考勤数据**/
	private Integer attendanceDay ;
	
	/** 更新人 */
	private String updateUser;
	
	private String tenantCode;

	// getter、setter

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setActualWorkAddress(String actualWorkAddress) {
		this.actualWorkAddress = actualWorkAddress;
	}

	public String getActualWorkAddress() {
		return this.actualWorkAddress;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getIntroduction() {
		return this.introduction;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTenantCode() {
		return tenantCode;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	public Integer getAttendanceDay() {
		return attendanceDay;
	}

	public void setAttendanceDay(Integer attendanceDay) {
		this.attendanceDay = attendanceDay;
	}

}
