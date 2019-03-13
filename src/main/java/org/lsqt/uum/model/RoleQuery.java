package org.lsqt.uum.model;

/**
 * 角色表
 */
public class RoleQuery {
	private Integer pageIndex = 0;
	private Integer pageSize = 20;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private Long id;
	private String ids; // 用逗号分割的id字符

	/**用于获取一个(或多个)部门的角色**/
	private Long orgId; // 用户选择的某部门
	private String orgIds; 

	/**用于获取组的角色**/
	private String groupIds;
	
	/**用于获取称谓的角色**/
	private String titleIds;
	
	/** 名称 */
	private String name;

	/** 角色简称 */
	private String nameShort;

	/** 角色编码 */
	private String code;

	/** 排序 */

	private Integer sn;

	/** 备注 */
	private String remark;

	/***/
	private String appCode;
	
	/**用于查找某个用户的角色用**/
	private Long userId;

	// getter、setter

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setNameShort(String nameShort) {
		this.nameShort = nameShort;
	}

	public String getNameShort() {
		return this.nameShort;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
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

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAppCode() {
		return this.appCode;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(String orgIds) {
		this.orgIds = orgIds;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}

	public String getTitleIds() {
		return titleIds;
	}

	public void setTitleIds(String titleIds) {
		this.titleIds = titleIds;
	}

}
