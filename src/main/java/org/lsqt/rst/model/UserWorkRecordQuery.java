package org.lsqt.rst.model;

import org.lsqt.components.db.Page;

/**
 * 工时记录
 */
public class UserWorkRecordQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符
	private Long id;

	/** 用户编码 */
	private String userCode;
	private String userName;

	private String companyCode; //用户所在企业的打卡
	private String companyName; 
	
	private Integer recordDate; //考勤记录的日期yyyyMMdd

	private Integer recordDateYearMonth; // 用于查询 recordDate like '201811%'记录

	/** 时长精确到小时，保留一个小数 */
	private String workingHours;

	/** 班次类型:1=白班、2=中班、3=晚班*/
	private String shiftType;

	/** 请假类型:1=事假、2=病假、3=其他 */
	private String leaveType;
	
	/**是否有请假: 1=有 0=没有**/
	private String leaveHas;
	
	/** （请假原因）备注 */
	private String remark;

	/** 租户编码 */
	private String tenantCode;

	/** 系统编码 */
	private String appCode;

	/** 排序 */
	private Integer sn;

	/**1=星期一， 2=星期二， 7=星期天**/
	private Integer weekday;
	
	
	private String recordDateBegin;
	
	private String recordDateEnd;
	
	// getter、setter

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

	public Integer getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Integer recordDate) {
		this.recordDate = recordDate;
	}

	public Integer getRecordDateYearMonth() {
		return recordDateYearMonth;
	}

	public void setRecordDateYearMonth(Integer recordDateYearMonth) {
		this.recordDateYearMonth = recordDateYearMonth;
	}

	public String getRecordDateBegin() {
		return recordDateBegin;
	}

	public void setRecordDateBegin(String recordDateBegin) {
		this.recordDateBegin = recordDateBegin;
	}

	public String getRecordDateEnd() {
		return recordDateEnd;
	}

	public void setRecordDateEnd(String recordDateEnd) {
		this.recordDateEnd = recordDateEnd;
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
}
