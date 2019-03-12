package org.lsqt.sys.model;

import java.io.Serializable;

/**
 * 租户表
 */
public class Tenant implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5812012009656830036L;

	/***/
	private Long id;

	/** 租户名称 */
	private String name;
	
	/**租户全称*/
	private String fullName;
	
	/**租户昵称**/
	private String nickName;
	
	/**租户介绍**/
	private String introduction;
	
	/**租户值*/
	private String value;

	/** 租户编码 */
	private String code;

	/** 排序 */
	private Integer sn;

	/***/
	private String remark;

	/** 启用状态 0=未起用，1=启用 */
	private String enable;

	/** gid:全局唯一编号 */
	private String gid;

	/** createTime:创建日期 */

	private java.util.Date createTime;

	/** updateTime:更新时间 */

	private java.util.Date updateTime;

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

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
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

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getEnable() {
		return this.enable;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

}
