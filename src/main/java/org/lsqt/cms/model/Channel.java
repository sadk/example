package org.lsqt.cms.model;

import java.util.Date;

/**
 * 数据字典相关
 * 
 * @author yuanmm
 *
 */
public class Channel {
	public static final String status_启用="1";
	public static final String status_禁用="0";
	

	private Long id;
	private Long pid;
	
	private String status;
	private String code;
	
	private Integer sn;
	private String name;
	
	private String nodePath;
	private String appCode;
	private String remark;
	
	private String gid;
	private Date createTime;
	private Date updateTime;
	
	// categoryName 虚拟列
	private String categoryCode;
	private String categoryName;
	
	// 辅助字段: 1=应用 2=栏目
	private String nodeType;
	public static final String NODE_TYPE_租户="0";
	public static final String NODE_TYPE_应用="1";
	public static final String NODE_TYPE_栏目="2";
	
	
	public String getStatusDesc(){
		if(status_启用.equals(this.status)){
			return "启用";
		}
		else if(status_禁用.equals(this.status)){
			return "禁用";
		}
		return "";
	}
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

}
