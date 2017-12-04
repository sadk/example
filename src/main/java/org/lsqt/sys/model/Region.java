package org.lsqt.sys.model;

import java.util.Date;

/**
 * 国、省、市、区
 * 
 * @author yuanmm
 *
 */
public class Region {
	public static final int TYPE_NATIONAL= 1;
	public static final int TYPE_PROVINCE= 2;
	public static final int TYPE_CITY= 3;
	public static final int TYPE_DISTRICT= 4;
	
	
	private Long id;
	private Long pid;
	private String code;
	private String name;
	private String value;
	private String nodePath;
	private String nodePathText;
	private Integer sn;
	private String appCode;
	private String remark;
	private Integer type;
	
	
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

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getNodePathText() {
		return nodePathText;
	}

	public void setNodePathText(String nodePathText) {
		this.nodePathText = nodePathText;
	}

}
