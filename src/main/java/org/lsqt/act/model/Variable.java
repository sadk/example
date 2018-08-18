package org.lsqt.act.model;

import java.util.Date;

/**
 * 流程变量管理
 */
public class Variable {

	private Long id;

	/**流程定义ID**/
	private String definitionId;
	
	/** 变量名 */
	private String name;

	/** 变量编码 */
	private String code;

	/** 是否是常用类,1=常用固定类(如流程发起人等） 0=自定义类 */
	private Integer type;
	public static final int TYPE_FIX=1;
	public static final int TYPE_CUSTOM=0;
	

	/** 变量使用类型 2=字符串 1=数字 3=日期 */
	private Integer dataType;
	public static final int DATA_TYPE_数字 =1;
	public static final int DATA_TYPE_字符=2;
	public static final int DATA_TYPE_日期=3;
	
	/** 变量parttern*/
	private String format;

	/** 是否必填 */
	private Integer requried;
	public static final int REQURIED_YES=1;
	public static final int REQURIED_NO=0;
	

	/** 默认值 */
	private String defaultValue;

	/***/
	private Integer sn;

	/** 用户备注 */
	private String remark;

	private String appCode;

	private String gid;

	/** 创建日期 */
	private Date createTime;

	/***/
	private Date updateTime;

	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
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

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return this.type;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Integer getDataType() {
		return this.dataType;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getFormat() {
		return this.format;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDefaultValue() {
		return this.defaultValue;
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

	public String getDefinitionId() {
		return definitionId;
	}

	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}

	public Integer getRequried() {
		return requried;
	}

	public void setRequried(Integer requried) {
		this.requried = requried;
	}
 

}
