package org.lsqt.syswin.uum.model;

import org.lsqt.components.db.Page;

/**
 * 组织_组织，组织的根节点：新增第一个节点的时候，需要创建虚拟节点为根节点 组织单元id=集团id，组织单元级
 */
public class OrgQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符

	private Long id;
	/** 父节点 */
	private Long pid;

	/** 组织名称 */
	private String name;

	/** 组织单元全称 */
	private String fullName;

	/** 组织单元级别（1集团2区域公司3公司4管理项目5部门6工作组） */

	private Integer type;

	/** 排序 */
	private Integer sn;

	/** 节点路径 */
	private String nodePath;
	
	/**用于获取节点下的所有节点**/
	private String nodePath2;

	/** ets_id */
	private String etsId;

	/** ets_parentid */
	private String etsParentid;

	/** 创建者id */
	private Long createUserId;

	/** 创建者名称 */
	private String createUserName;

	/** 创建时间 */

	private java.util.Date createTime;

	/** 修改者id */
	private Long updateUserId;

	/** 修改者名称 */
	private String updateUserName;

	/** 修改时间 */
	private java.util.Date updateTime;

	
	// ----------------------------辅助属性 ----------
	private Long userId; //用于查询用户所属的部门用
	private Long idNotIn; 
	// getter、setter

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getPid() {
		return this.pid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return this.type;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public Integer getSn() {
		return this.sn;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	public String getNodePath() {
		return this.nodePath;
	}

	public void setEtsId(String etsId) {
		this.etsId = etsId;
	}

	public String getEtsId() {
		return this.etsId;
	}

	public void setEtsParentid(String etsParentid) {
		this.etsParentid = etsParentid;
	}

	public String getEtsParentid() {
		return this.etsParentid;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Long getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateUserName() {
		return this.createUserName;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Long getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getUpdateUserName() {
		return this.updateUserName;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime() {
		return this.updateTime;
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

	public Long getIdNotIn() {
		return idNotIn;
	}

	public void setIdNotIn(Long idNotIn) {
		this.idNotIn = idNotIn;
	}

	public String getNodePath2() {
		return nodePath2;
	}

	public void setNodePath2(String nodePath2) {
		this.nodePath2 = nodePath2;
	}

}
