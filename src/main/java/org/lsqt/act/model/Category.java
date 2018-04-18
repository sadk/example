package org.lsqt.act.model;

import java.util.Date;

/**
 * 流程相关的分类都存在这个表里
 * 
 * @author yuanmm
 *
 */
public class Category {
	
	private Long id;
	private Long pid;
	
	
	private String code;
	
	private String categoryCode;
	private String categoryName;
	
	private Integer sn;
	private String name;
	private String value;
	
	private String nodePath;
	private String appCode;
	private String remark;
	
	/** 业务类型：1=流程定义分类  2=用户规则分类 3=流程变量分类**/
	private String dataType;
	public static final String DATA_TYPE_FLOW="1";
	public static final String DATA_TYPE_USER_RULE="2";
	public static final String DATA_TYPE_VARIABLE="3";
	
	public String getDataTypeDesc() {
		if(DATA_TYPE_FLOW.equals(dataType)) {
			return "流程定义分类";
		}
		if(DATA_TYPE_USER_RULE.equals(dataType)) {
			return "用户规则分类";
		}
		return "";
	}
	
	private String gid;
	private Date createTime;
	private Date updateTime;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNodePath() {
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

}
