package org.lsqt.uum.model;

import org.lsqt.components.context.annotation.model.Pattern;

/**
 * 用户表
 */
public class UserQuery {
	private Integer pageIndex = 0;
	private Integer pageSize = 20;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符
	private Long id;
	/** 姓名 */
	private String name;

	private String code;
	
	/** 帐号 */
	private String loginName;

	/** 密码 */
	private String loginPwd;

	/**
	 * 状态 3=过期(长久没有登陆，僵尸用户) 2=锁定（可登陆不能操作） 1=激活（可登陆、可操作） 0=禁用（不可登陆） -1=删除
	 */

	private Integer status;

	/** 邮箱 */
	private String email;

	/** 手机 */
	private String mobile;

	/** 电话 */
	private String tel;

	/** QQ */
	private String numQq;

	/** 微信 */
	private String numWx;

	/** 生日 */
	@Pattern("yyyy-MM-dd")
	private java.util.Date birthday;

	/** 办公地点（多个地址用分号分割） */
	private String addressOffice;

	/** 家庭地址（多个地址用分号分割） */
	private String addressHome;

	/**
	 * 1=男 0=女
	 */
	private String sex;

	/***/

	private Long sn;

	/***/
	private String remark;

	/***/
	private String appCode;

	/**获取多层部门下的用户**/
	private String orgIds;
	
	/**获取部门下用户用**/
	private Long orgId;
	
	/**获取某角色下的用户用**/
	private Long roleId;
	
	/**获取某组下的用户用**/
	private Long groupId;
	
	/**获取称谓下的用户**/
	private Long titleId;
	
	// getter、setter

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getLoginPwd() {
		return this.loginPwd;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTel() {
		return this.tel;
	}

	public void setNumQq(String numQq) {
		this.numQq = numQq;
	}

	public String getNumQq() {
		return this.numQq;
	}

	public void setNumWx(String numWx) {
		this.numWx = numWx;
	}

	public String getNumWx() {
		return this.numWx;
	}

	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}

	public java.util.Date getBirthday() {
		return this.birthday;
	}

	public void setAddressOffice(String addressOffice) {
		this.addressOffice = addressOffice;
	}

	public String getAddressOffice() {
		return this.addressOffice;
	}

	public void setAddressHome(String addressHome) {
		this.addressHome = addressHome;
	}

	public String getAddressHome() {
		return this.addressHome;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSn(Long sn) {
		this.sn = sn;
	}

	public Long getSn() {
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

	public String getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(String orgIds) {
		this.orgIds = orgIds;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getTitleId() {
		return titleId;
	}

	public void setTitleId(Long titleId) {
		this.titleId = titleId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
