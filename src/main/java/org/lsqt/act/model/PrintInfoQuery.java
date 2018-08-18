package org.lsqt.act.model;

import org.lsqt.components.db.Page;

/**
 * 印章管理表
 */
public class PrintInfoQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private Long id;
	private String key; // 关键字
	private String ids; // 用逗号分割的id字符

	/** 公司名称 */
	private String name;

	/** 公司编码 */
	private String code;

	/** 用印人 */
	private String printMan;

	/** 用印归档 */
	private String printArchive;

	/** 档案管理员 */
	private String docAdmin;

	/** 现保管员 */
	private String protectMan;

	/** 计划划分区域 */
	private Integer areaId;

	/** 计划划分区域 */
	private String areaName;

	/** 启停用状态: 0=停用 1=启用 */

	private Integer status;

	/** 租户编码 */
	private String appCode;

	/** 排序 */
	private Integer sn;

	/** 备注 */
	private String remark;

	// getter、setter

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setPrintMan(String printMan) {
		this.printMan = printMan;
	}

	public String getPrintMan() {
		return this.printMan;
	}

	public void setPrintArchive(String printArchive) {
		this.printArchive = printArchive;
	}

	public String getPrintArchive() {
		return this.printArchive;
	}

	public void setDocAdmin(String docAdmin) {
		this.docAdmin = docAdmin;
	}

	public String getDocAdmin() {
		return this.docAdmin;
	}

	public void setProtectMan(String protectMan) {
		this.protectMan = protectMan;
	}

	public String getProtectMan() {
		return this.protectMan;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getAreaId() {
		return this.areaId;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaName() {
		return this.areaName;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
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

}
