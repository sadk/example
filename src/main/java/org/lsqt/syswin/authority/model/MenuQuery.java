package org.lsqt.syswin.authority.model;

import org.lsqt.components.db.Page;

/**
 * 权限_菜单
 */
public class MenuQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;
	private String sortFieldGbk; //中文字段排序

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符

	private Long id;
	
	/**辅助属性**/
	private String idNotIn;
	
	/** 菜单名称 */
	private String name;

	/** 菜单code */
	private String code;

	/** 菜单顺序 */
	private Integer sn;

	/** 菜单url */
	private String url;

	/** 菜单图标 */
	private String icon;

	/** 菜单描述 */
	private String description;

	/** 父级菜单id（根结节点-1） */
	private Long pid;

	/** 菜单类型(1=pc 2=app 3=pad) */
	private Integer type;

	/** 菜单层级（根节点为0） */
	private Integer level;

	/** 菜单路径 */
	private String nodePath;

	/** 创建者id */
	private Long createUserId;

	/** 创建者名称 */
	private String createUserName;

	/** 修改者id */
	private Long updateUserId;

	/** 修改者名称 */
	private String updateUserName;

	// -----------------------------------------------辅助属性 ------------------
	private Long roleId ; // 用于查询角色的菜单
	private Long userId; // 用于查询用户授权的菜单
	
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

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public Integer getSn() {
		return this.sn;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getPid() {
		return this.pid;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return this.type;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	public String getNodePath() {
		return this.nodePath;
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

	public String getIdNotIn() {
		return idNotIn;
	}

	public void setIdNotIn(String idNotIn) {
		this.idNotIn = idNotIn;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSortFieldGbk() {
		return sortFieldGbk;
	}

	public void setSortFieldGbk(String sortFieldGbk) {
		this.sortFieldGbk = sortFieldGbk;
	}

}
