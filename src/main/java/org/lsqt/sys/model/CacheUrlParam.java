package org.lsqt.sys.model;

/**
 * 缓存刷新参数配置
 */
public class CacheUrlParam {

	/***/

	private Long id;

	/** 系统缓存管理表ID */
	private String cacheId;

	/** 参数名称 */
	private String paramName;

	/** 参数值 */
	private String paramValue;

	/** 参数编码 */
	private String paramCode;

	/**
	 * 参数类型: 100=登陆用户的租户ID 104=登陆用户的主组织ID 105=登陆用户的用户ID 102=登陆用户的主岗位ID
	 * 150=登陆用户的单元ID ; 200=静态值
	 */
	private String paramType;

	/** 排序 */

	private Integer sn;

	/** 备注 */
	private String remark;

	/***/
	private String gid;

	/** 创建日期 */

	private java.util.Date createTime;

	/***/

	private java.util.Date updateTime;

	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setCacheId(String cacheId) {
		this.cacheId = cacheId;
	}

	public String getCacheId() {
		return this.cacheId;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamName() {
		return this.paramName;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamValue() {
		return this.paramValue;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public String getParamCode() {
		return this.paramCode;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getParamType() {
		return this.paramType;
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

}
