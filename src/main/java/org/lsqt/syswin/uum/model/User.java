package org.lsqt.syswin.uum.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lsqt.syswin.authority.model.Function;
import org.lsqt.syswin.authority.model.Menu;
import org.lsqt.syswin.authority.model.RangeValue;
import org.lsqt.syswin.authority.model.Role;


/**
 * <pre>
 * 
-- 获取用户的岗位
select * from t_org_duties_info where duties_id in(select duties_id from  t_user_duties where user_id =1)

-- 获取用户的角色
select * from T_POWER_ROLE_INFO where role_id in (select role_id from  T_POWER_DUTIES_ROLE where duties_id in (select duties_id from  t_user_duties where user_id =1))


-- 获取用户的部门 
select * from T_ORG_UNIT_INFO where org_unit_id in (select org_unit_id from t_org_duties_info where duties_id in(select duties_id from  t_user_duties where user_id =1))


-- 用户授权的菜单
select * from T_POWER_RES_MENU_INFO where menu_id in (
select menu_id from T_POWER_ROLE_MENU_OPERATION where role_id in(
select role_id from T_POWER_DUTIES_ROLE where duties_id in (
select duties_id from  t_user_duties where user_id =1))) 

-- 用户授权的功能按钮
select * from T_POWER_RES_FUNC_INFO where func_id in (
select func_id from T_POWER_MENU_FUNC where menu_func_id in (
select menu_func_id from T_POWER_ROLE_MENU_FUNC_OPERATION where role_id in (
select role_id from T_POWER_DUTIES_ROLE where duties_id in ( select duties_id from  t_user_duties where user_id =1))))

-- 用户的数据值权限
select * from T_POWER_ROLE_DATA_RES where role_id in (
select role_id from  T_POWER_DUTIES_ROLE where duties_id in (select duties_id from t_org_duties_info where duties_id in(select duties_id from  t_user_duties where user_id =1)))

 * </pre>
 */
public class User {

	/** 用户id */
	private Long userId;

	/** 用户名称（客户名称） */
	private String userName;

	/** 性别(1男2女) */
	private Integer sex;
	public static final int sex_man=1;
	public static final int sex_woman=2;
	
	public String getSexDesc() {
		if (sex == null) return "";
		if (sex_man == sex) {
			return "男";
		}
		if (sex_woman == sex) {
			return "女";
		}
		return "";
	}
	
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
	public static final int status_on_working=1;
	public static final int status_on_test_working=2;
	public static final int sttaus_off_working=3;
	
	public String getStatusDesc() {
		if(status ==null)return "";
		if(status_on_working==status) {
			return "在职";
		}
		if(status_on_test_working==status) {
			return "试用期";
		}
		if(sttaus_off_working==status) {
			return "离职";
		}
		return "";
	}

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
	private java.util.Date updateTime;

	// ------------------------------------------ 用户的其它属性 ------------------------
	
	/**手机端:用户默认的岗位**/
	private Position userDefaultPosition;
	
	/**手机端:用户默认的项目**/
	private Org userDefaultProject;
	
	/**用户的主部门**/
	private Org userMainOrg;
	/**用户的主岗位**/
	private Position userMainPosition;
	
	// ------------------ 以下是用户的关联的关键信息，比如：银行账号、岗位、组织、菜单、功能、角色等  Begin:---------------------------
	/**用户的岗位信息**/
	private List<Position> userPositionList = new ArrayList<>();
	
	/**用户的组织**/
	private List<Org> userOrgList=new ArrayList<>();
	
	/**用户授权的菜单**/
	private List<Menu> userMenuList = new ArrayList<>();

	/**用户授权的功能(按钮)**/
	private List<Function> userFunctionList = new ArrayList<>();
	
	/**用户的角色*/
	private List<Role> userRoleList = new ArrayList<>();
	
	/**用户的数据查询权限**/
	private List<RangeValue> userRangeValueList = new ArrayList<>();
	
	
	/**用户的其它扩展信息**/
	private Map<String,Object> extProperty = new HashMap<>();
	
	// ------------------ 以上是用户的关联的关键信息，比如：银行账号、岗位、组织、菜单、功能、角色等  End!!!---------------------------
	
	
	
	// getter、setter
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return this.userId;
	}

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

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public Position getUserDefaultPosition() {
		return userDefaultPosition;
	}

	public void setUserDefaultPosition(Position userDefaultPosition) {
		this.userDefaultPosition = userDefaultPosition;
	}

	public Org getUserDefaultProject() {
		return userDefaultProject;
	}

	public void setUserDefaultProject(Org userDefaultProject) {
		this.userDefaultProject = userDefaultProject;
	}

	public List<Position> getUserPositionList() {
		return userPositionList;
	}

	public void setUserPositionList(List<Position> userPositionList) {
		this.userPositionList = userPositionList;
	}

	public List<Org> getUserOrgList() {
		return userOrgList;
	}

	public void setUserOrgList(List<Org> userOrgList) {
		this.userOrgList = userOrgList;
	}

	public List<Menu> getUserMenuList() {
		return userMenuList;
	}

	public void setUserMenuList(List<Menu> userMenuList) {
		this.userMenuList = userMenuList;
	}

	public List<Function> getUserFunctionList() {
		return userFunctionList;
	}

	public void setUserFunctionList(List<Function> userFunctionList) {
		this.userFunctionList = userFunctionList;
	}

	public List<Role> getUserRoleList() {
		return userRoleList;
	}

	public void setUserRoleList(List<Role> userRoleList) {
		this.userRoleList = userRoleList;
	}

	public List<RangeValue> getUserRangeValueList() {
		return userRangeValueList;
	}

	public void setUserRangeValueList(List<RangeValue> userRangeValueList) {
		this.userRangeValueList = userRangeValueList;
	}

	public Org getUserMainOrg() {
		return userMainOrg;
	}

	public void setUserMainOrg(Org userMainOrg) {
		this.userMainOrg = userMainOrg;
	}

	public Position getUserMainPosition() {
		return userMainPosition;
	}

	public void setUserMainPosition(Position userMainPosition) {
		this.userMainPosition = userMainPosition;
	}

	public Map<String, Object> getExtProperty() {
		return extProperty;
	}

	public void setExtProperty(Map<String, Object> extProperty) {
		this.extProperty = extProperty;
	}

}
