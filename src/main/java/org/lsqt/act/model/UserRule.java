package org.lsqt.act.model;

/**
 * <pre>
 * 用户规则定义
 * 注意：用户规则内容解析，最终返回用户ID集合，或用户ID字符串（多个以逗号分割)！！！！！！！！！！！！！！！
 * </pre>
 */
public class UserRule {

	private Long id;

	/** 工作流分类表ID */
	private Long categoryId;

	/** 规则名称 */
	private String name;

	/** 规则编码 */
	private String code;

	/** 规则内容 */
	private String content;

	/**规则内容类型：静态文本=0 SQL语句=1  JavaScript代码=2  Groovy代码=3*/
	private String contentType;
	public static final String CONTENT_TYPE_STATIC_TEXT="0";
	public static final String CONTENT_TYPE_SQL="1";
	public static final String CONTENT_TYPE_JAVASCRIPT="2";
	public static final String CONTENT_TYPE_GROOVY="3";
	
	/** 规则解析类型: 1=freemark 2=velocity 3=javascript 4=groovy */
	private Integer resolveType;
	public static final int RESOLVE_TYPE_FREEMARK=1;
	public static final int RESOLVE_TYPE_VELOCITY=2;
	public static final int RESOLVE_TYPE_JAVASCRIPT=3;
	public static final int RESOLVE_TYPE_GROOVY=4;
	
	
	public String getResolveTypeDesc() {
		if(resolveType == null) return "";
		if(1== resolveType) {
			return "Freemark";
		}
		if(2== resolveType) {
			return "Velocity";
		}
		if(3== resolveType) {
			return "JavaScript";
		}
		if(4== resolveType) {
			return "Groovy";
		}
		return "";
	}

	/** 排序 */
	private Integer sn;

	 
	private String remark;
	 
	private String appCode;
	private String gid;
	private java.util.Date createTime;
	private java.util.Date updateTime;

	// --------------------辅助字段
	private String categoryName ;
	
	
	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getCategoryId() {
		return this.categoryId;
	}

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

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public void setResolveType(Integer resolveType) {
		this.resolveType = resolveType;
	}

	public Integer getResolveType() {
		return this.resolveType;
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

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getGid() {
		return this.gid;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
