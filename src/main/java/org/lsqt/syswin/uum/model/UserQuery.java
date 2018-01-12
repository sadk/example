package org.lsqt.syswin.uum.model;

import java.util.List;

import org.lsqt.components.db.Page;

/**
 * 用户_员工信息表，住户跟多个组织交换名片，在用户表就存在多个userid；员工名片是独立的userid
 */
public class UserQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符

	/** 用户名称（客户名称） */
	private String userName;

	/** 性别(1男2女) */
	private Integer sex;

	/** 民族 */
	private String nation;

	/** 出生日期 */
	private java.util.Date birthday;

	/** 用户名（默认手机号） */
	private String workNo;

	/** 账号 */
	private String loginNo;

	/** 密码 */
	private String password;

	/** 加密盐值：8位随机数 */
	private String pwdSalt;

	/** 手机号(注册手机号) */
	private String mobile;

	/** 座机 */
	private String telephone;

	/** 邮箱 */
	private String email;

	/** 状态(1在职2试用期3离职) */
	private Integer status;

	/** employeeid */
	private String employeeId;

	/** 是否有效 */
	private Integer delFlag;

	/** 创建者id */
	private Long createUserId;

	/** 创建者名称 */
	private String createUserName;

	/** 创建时间 */
	private java.util.Date createTime;

	/** 修改者id */
	private Long updateUserId;

	/** 修改者名称 */
	private String updateUserName;

	/** 修改时间 */
	private java.util.Date updateDate;

	// ----------------------------- 辅助属性 --------------------
	private String orgIds; //用于获取多个组织下的用户（单层)
	private String positionIds; // 用于获取多个岗位下的用户
	
	private String roleId; // 用于查询某个角色下的用户
	
	private List<String> loginNoList;
	
	// getter、setter

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getSex() {
		return this.sex;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getNation() {
		return this.nation;
	}

	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}

	public java.util.Date getBirthday() {
		return this.birthday;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}

	public String getWorkNo() {
		return this.workNo;
	}

	public void setLoginNo(String loginNo) {
		this.loginNo = loginNo;
	}

	public String getLoginNo() {
		return this.loginNo;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPwdSalt(String pwdSalt) {
		this.pwdSalt = pwdSalt;
	}

	public String getPwdSalt() {
		return this.pwdSalt;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeId() {
		return this.employeeId;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public Integer getDelFlag() {
		return this.delFlag;
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

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
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

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	public java.util.Date getUpdateDate() {
		return this.updateDate;
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

	public String getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(String orgIds) {
		this.orgIds = orgIds;
	}

	public String getPositionIds() {
		return positionIds;
	}

	public void setPositionIds(String positionIds) {
		this.positionIds = positionIds;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public List<String> getLoginNoList() {
		return loginNoList;
	}

	public void setLoginNoList(List<String> loginNoList) {
		this.loginNoList = loginNoList;
	}

}
