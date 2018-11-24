package org.lsqt.rst.model;

/**
 * 工时记录
 */
public class UserWorkRecord {

	/** ID */
	private Long id;

	/** 用户编码 */
	private String userCode;

	/** 考勤类型 :100=正常上班 200=加班 300=请假 */
	private Integer type;
	public static final int TYPE_正常上班=100;
	public static final int TYPE_加班=200;
	public static final int TYPE_请假=300;
	

	/** 时长精确到小时，保留一个小数 */
	private String workingHours;

	/** 班次类型:1=白班、2=中班、3=晚班、4=休息 */
	private String shiftType;

	/** 请假类型:1=事假、2=病假、3=其他 */
	private String leaveType;

	/** （请假原因）备注 */
	private String remark;

	/** 租户编码 */
	private String tenantCode;

	/** 系统编码 */
	private String appCode;

	/** 排序 */

	private Integer sn;

	/** 全局码 */
	private String gid;

	/** 创建日期 */

	private java.util.Date createTime;

	/** 更新时间 */

	private java.util.Date updateTime;

	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return this.type;
	}

	public void setWorkingHours(String workingHours) {
		this.workingHours = workingHours;
	}

	public String getWorkingHours() {
		return this.workingHours;
	}

	public void setShiftType(String shiftType) {
		this.shiftType = shiftType;
	}

	public String getShiftType() {
		return this.shiftType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getLeaveType() {
		return this.leaveType;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	public String getTenantCode() {
		return this.tenantCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAppCode() {
		return this.appCode;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public Integer getSn() {
		return this.sn;
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

}
