package org.lsqt.act.model;

/**
 * 数据映射配置
 */
public class UserDataConfigParam {

	/***/
	private Long id;

	/** 数据配置ID(引用ext_user_data_config.id) */
	private String configId;
	
	/** 参数名称 */
	private String paramName;

	/** 参数编码 */
	private String paramCode;

	/** 参数类型: 100=登陆用户上下文 200=静态值 */
	private String paramType;
	
	public static final String PARAM_TYPE_CONTEXT_LOGINER_APP_CODE="100"; // 租户编码
	
	public static final String PARAM_TYPE_CONTEXT_LOGINER_ORG_ID="104"; // 登陆用户的组织ID
	public static final String PARAM_TYPE_CONTEXT_LOGINER_USER_ID="105"; // 登陆用户的用户ID
	public static final String PARAM_TYPE_CONTEXT_LOGINER_POSITION_ID="102"; // 登陆用户的岗位ID

	
	public static final String PARAM_TYPE_CONSTANT="200"; // 静态值参数
	public static final String PARAM_TYPE_DYNAMIC_FROM_APPROVE_OBJECT="300"; // 300=从审批对象里取值
	

	/** 参数值 */
	private String paramValue;
	
	/**当为动态参数时，参数值表达式**/
	private String paramValueExpress;

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

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getConfigId() {
		return this.configId;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamName() {
		return this.paramName;
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

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamValue() {
		return this.paramValue;
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

	public String getParamValueExpress() {
		return paramValueExpress;
	}

	public void setParamValueExpress(String paramValueExpress) {
		this.paramValueExpress = paramValueExpress;
	}

}
