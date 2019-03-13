package org.lsqt.uum.model;


/**
 * 用户组表
 */
public class GroupQuery {
	private Integer pageIndex = 0;
	private Integer pageSize = 20;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	
	private String ids; // 用逗号分割的id字符

	private Long id;
	private String idNotIn; // 树状结构不能选择自己做为父节点，否则会出现死循环 
	
	/** 名称 */
	private String name;

	/** 组简称 */
	private String nameShort;

	/** 组编码 */
	private String code;

	/**
	 * 状态 1=启用 0=禁用 -1=已删除
	 */

	private Integer status;

	/** 排序 */
	private Integer sn;

	/** 备注 */
	private String remark;

	/***/
	private String appCode;

	/**用于查询某用户所属的组用**/
	private Long userId;
	
	/**用于获取某个组下的多层组**/
	private String nodePath;
	
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

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
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

	public String getIdNotIn() {
		return idNotIn;
	}

	public void setIdNotIn(String idNotIn) {
		this.idNotIn = idNotIn;
	}

	public String getNodePath() {
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

}
