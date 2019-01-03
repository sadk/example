package org.lsqt.uum.model;

import org.lsqt.components.context.annotation.model.Pattern;

/**
 * <pre>
 * 1.uum_user_object ==> 用户所在(部门、组、岗位/职称)[注意没有角色!该表只保存用户的"职能",而角色不是"职能体"]，多对多表(中间表) 
 * 其中，obj_type 表示是 对象类型: 1=职称 2=岗位 3=部门 4=组 7=角色
 * 
 * 2.uum_object_role ==> 对象(指的是部门、组、岗位/职称、用户[注意用户的角色保存在这张表!])拥有的角色，多对多表(中间表)
 * 其中，obj_type 对象类型: 1=职称 2=岗位 3=部门 4=组 5=用户
 * 
 * 
 * </pre>
 */
public class User implements java.io.Serializable{
	private static final long serialVersionUID = -8219775209409585592L;

	public static final String PWD_SALT = "bqjr@123"; // 暂拟用固定值做密码盐
	
	public static final int status_过期 = 3;
	public static final int status_锁定 = 2;
	public static final int status_激活 = 1;
	public static final int status_禁用 = 0;
	public static final int status_删除 = -1;
	
	public static final String sex_男 = "1";
	public static final String sex_女 = "0";
	public static final String sex_未知 = "2";
	
	
	/***/
	private Long id;

	/** 姓名 */
	private String name;
	
	/**用户编码**/
	private String code;

	/** 帐号 */
	private String loginName;

	/** 密码 */
	private  String loginPwd;

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

	/***/
	private String gid;

	/** 创建日期 */

	private java.util.Date createTime;

	/***/

	private java.util.Date updateTime;
	
	/**用户所属的租户**/
	private String tenantCode;
	private String tenantName;
	
	
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
	
	public String getSexDesc(){
		if(sex_女.equals(sex)) {
			return "女";
		}
		if(sex_男.equals(sex)){
			return "男";
		}
		if(sex_未知.equals(sex)) {
			return "未知";
		}
		return "" ;
	}

	public String getStatusDesc() {
		if (status == null)
			return "";
		if (status == status_删除) {
			return "删除";
		}
		if (status == status_禁用) {
			return "禁用";
		}
		if (status == status_激活) {
			return "激活";
		}
		if (status == status_锁定) {
			return "锁定";
		}
		if (status == status_过期) {
			return "过期";
		}
		return "";
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTenantCode() {
		return tenantCode;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}


}
