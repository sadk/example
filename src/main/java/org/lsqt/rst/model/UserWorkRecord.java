package org.lsqt.rst.model;

/**
 * 工时记录
 */
public class UserWorkRecord {

	/** ID */
	private Long id;

	/** 用户编码 */
	private String userCode;
	/** 用户名称 */
	private String userName;
	
	private String companyCode; //用户所在企业的打卡
	private String companyName; 

	
	
	private Integer recordDate; //考勤记录的日期yyyyMMdd

	private String workingHours; //正常工时(时长精确到小时，保留一个小数) 
	private String leaveHours; //请假如工时
	private String extraHours; //加班工时
	

	/**加班 班次类型:1=白班、2=中班、3=晚班、4=休息(已作废) */
	private String shiftType;
	
	/**是否有请假: 1=有 0=没有**/
	private String leaveHas;
 
	
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
	
	/**1=星期一， 2=星期二， 7=星期天**/
	private Integer weekday;
	
	// -- 辅助字段
	private String attendanceMothCut;//考勤月切天数: 比如等于5， 表示5天后不能再修改上月的考勤数据
	
	
	
	public String getWeekDayDesc() {
		if(weekday == null){
			return "";
		}
		if (weekday ==1) {
			return "星期一";
		}
		if (weekday ==2) {
			return "星期二";
		}
		if (weekday ==3) {
			return "星期三";
		}
		if (weekday ==4) {
			return "星期四";
		}
		if (weekday ==5) {
			return "星期五";
		}
		if (weekday ==6) {
			return "星期六";
		}
		if (weekday ==7) {
			return "星期天";
		}
		return "";
	}

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

	public void setWorkingHours(String workingHours) {
		this.workingHours = workingHours;
	}

	public String getWorkingHours() {
		return this.workingHours;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getWeekday() {
		return weekday;
	}

	public void setWeekday(Integer weekday) {
		this.weekday = weekday;
	}

	public String getLeaveHours() {
		return leaveHours;
	}

	public void setLeaveHours(String leaveHours) {
		this.leaveHours = leaveHours;
	}

	public String getExtraHours() {
		return extraHours;
	}

	public void setExtraHours(String extraHours) {
		this.extraHours = extraHours;
	}

	public Integer getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Integer recordDate) {
		this.recordDate = recordDate;
	}

	public String getShiftType() {
		return shiftType;
	}

	public void setShiftType(String shiftType) {
		this.shiftType = shiftType;
	}

	public String getLeaveHas() {
		return leaveHas;
	}

	public void setLeaveHas(String leaveHas) {
		this.leaveHas = leaveHas;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAttendanceMothCut() {
		return attendanceMothCut;
	}

	public void setAttendanceMothCut(String attendanceMothCut) {
		this.attendanceMothCut = attendanceMothCut;
	}

}
