package org.lsqt.rst.model;

import org.lsqt.components.db.Page;

/**
 * 职位表
 */
public class PositionDefinitionQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符
	private Long id;

	/**薪水范围：最小值**/
	private Integer salaryMin;
	
	/**薪水范围：最大值**/
	private Integer salaryMax;
	
	
	/** 职位ID */
	private String code;

	/** 职位名称 */
	private String name;
	
	/**招聘人数**/
	private String headCount; 
	
	/** 用工方式：1=正式工、2=派遣工、3=小时工 **/
	private String employmentMode; 
	


	/** 公司ID */
	private String companyCode;

	/** 公司简称 */
	private String companyShortName;

	/** 中介姓名 */
	private String intermediaryName;

	/** 中介手机号 */
	private String intermediaryPhone;

	/** 发布者编码 */
	private String publishUserCode;

	/** 发布时间 */

	private java.util.Date publishTime;

	/** 综合工资 */
	private String comprehensiveSalary;

	/** 作息时间 */
	private String workTime;

	/** 性别要求 */
	private String requiredSex;

	/** 年龄要求 */
	private String requiredAge;

	/** 学历要求 */
	private String requiredEducation;

	/** 工作年限 */
	private String requiredWorkYears;

	/** 岗位福利 */
	private String welfare;

	/** 创建用户 */
	private String createUser;

	/** 更新用户 */
	private String updateUser;

	/** 状态(0:未发布，1：已发布) */
	private Integer status;
	
	/** 是否急招  1=急招 0=不急招**/
	private Integer urgent;

	/** 工作地址ID */
	private String workAddressCode;

	/** 企业logo图片路径 */
	private String urlCompanyLogo;

	/** 职位类型(暂时没用) */
	private String type;

	/** 岗位封面图片路径 */
	private String urlPositionCover;

	/** 是否置顶（0：不置顶，1：置顶） */
	private String top;

	/** 下线时间 */
	private java.util.Date offlineTime;

	/** 发布平台(0:全平台，1：公众号，2：小程序) */

	private Integer publishPlatfrom;

	/** 是否需要简历 */
	private String requiredResume;

	/** 面试地址 */
	private String interviewAddress;

	private String tenantCode;
	
	// ------------- 辅助字段
	private String imgType; //logo=企业图片，  cover=职位封面
	
	public String getTenantCode() {
		return tenantCode;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyShortName(String companyShortName) {
		this.companyShortName = companyShortName;
	}

	public String getCompanyShortName() {
		return this.companyShortName;
	}

	public void setIntermediaryName(String intermediaryName) {
		this.intermediaryName = intermediaryName;
	}

	public String getIntermediaryName() {
		return this.intermediaryName;
	}

	public void setIntermediaryPhone(String intermediaryPhone) {
		this.intermediaryPhone = intermediaryPhone;
	}

	public String getIntermediaryPhone() {
		return this.intermediaryPhone;
	}

	public void setPublishUserCode(String publishUserCode) {
		this.publishUserCode = publishUserCode;
	}

	public String getPublishUserCode() {
		return this.publishUserCode;
	}

	public void setPublishTime(java.util.Date publishTime) {
		this.publishTime = publishTime;
	}

	public java.util.Date getPublishTime() {
		return this.publishTime;
	}

	public void setComprehensiveSalary(String comprehensiveSalary) {
		this.comprehensiveSalary = comprehensiveSalary;
	}

	public String getComprehensiveSalary() {
		return this.comprehensiveSalary;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public String getWorkTime() {
		return this.workTime;
	}

	public void setRequiredSex(String requiredSex) {
		this.requiredSex = requiredSex;
	}

	public String getRequiredSex() {
		return this.requiredSex;
	}

	public void setRequiredAge(String requiredAge) {
		this.requiredAge = requiredAge;
	}

	public String getRequiredAge() {
		return this.requiredAge;
	}

	public void setRequiredEducation(String requiredEducation) {
		this.requiredEducation = requiredEducation;
	}

	public String getRequiredEducation() {
		return this.requiredEducation;
	}

	public void setRequiredWorkYears(String requiredWorkYears) {
		this.requiredWorkYears = requiredWorkYears;
	}

	public String getRequiredWorkYears() {
		return this.requiredWorkYears;
	}

	public void setWelfare(String welfare) {
		this.welfare = welfare;
	}

	public String getWelfare() {
		return this.welfare;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setWorkAddressCode(String workAddressCode) {
		this.workAddressCode = workAddressCode;
	}

	public String getWorkAddressCode() {
		return this.workAddressCode;
	}

	public void setUrlCompanyLogo(String urlCompanyLogo) {
		this.urlCompanyLogo = urlCompanyLogo;
	}

	public String getUrlCompanyLogo() {
		return this.urlCompanyLogo;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public void setUrlPositionCover(String urlPositionCover) {
		this.urlPositionCover = urlPositionCover;
	}

	public String getUrlPositionCover() {
		return this.urlPositionCover;
	}

	public void setTop(String top) {
		this.top = top;
	}

	public String getTop() {
		return this.top;
	}

	public void setOfflineTime(java.util.Date offlineTime) {
		this.offlineTime = offlineTime;
	}

	public java.util.Date getOfflineTime() {
		return this.offlineTime;
	}

	public void setPublishPlatfrom(Integer publishPlatfrom) {
		this.publishPlatfrom = publishPlatfrom;
	}

	public Integer getPublishPlatfrom() {
		return this.publishPlatfrom;
	}

	public void setRequiredResume(String requiredResume) {
		this.requiredResume = requiredResume;
	}

	public String getRequiredResume() {
		return this.requiredResume;
	}

	public void setInterviewAddress(String interviewAddress) {
		this.interviewAddress = interviewAddress;
	}

	public String getInterviewAddress() {
		return this.interviewAddress;
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

	public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}

	public Integer getUrgent() {
		return urgent;
	}

	public void setUrgent(Integer urgent) {
		this.urgent = urgent;
	}

	public String getEmploymentMode() {
		return employmentMode;
	}

	public void setEmploymentMode(String employmentMode) {
		this.employmentMode = employmentMode;
	}

	public Integer getSalaryMin() {
		return salaryMin;
	}

	public void setSalaryMin(Integer salaryMin) {
		this.salaryMin = salaryMin;
	}

	public Integer getSalaryMax() {
		return salaryMax;
	}

	public void setSalaryMax(Integer salaryMax) {
		this.salaryMax = salaryMax;
	}

	public void setHeadCount(String headCount) {
		this.headCount = headCount;
	}

}
