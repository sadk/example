package org.lsqt.act.model;

/**
 * 节点表单设置
 */
public class NodeForm {

	/***/
	private Long id;

	/** 流程定义ID */
	private String definitionId;

	/** 节点定义Key */
	private String taskKey;
	
	private String taskName;
	
	/** 表单名称 */
	private String formName;

	/** 表单编码 */
	private String formCode;

	/**表单定义类别: 1=节点表单 2=全局表单 **/
	private Integer dataType;
	public static int DATA_TYPE_TASK_FORM=1;
	public static int DATA_TYPE_GLOBAL_FORM=2;
	//public static int DATA_TYPE_GLOBAL_BEFORE_SCRIPT_HTTP = 3;
	//public static int DATA_TYPE_GLOBAL_AFTER_SCRIPT_HTTP = 4;
	
	
	public String getDataTypeDesc() {
		if(dataType==null) return "";
		if(DATA_TYPE_GLOBAL_FORM == dataType) {
			return "全局表单";
		}
		if(DATA_TYPE_TASK_FORM == dataType) {
			return "节点表单";
		}
		return "";
	}
	
	/**
	 * 表单类型: 0=动态表单  1=URL表单
	 * 注：在线表单,为系统自定义表单;url表单,是外部表单。地址写法规则为：如果表单页面平台在同一个应用中，路径从根开始写，不需要上下文路径，
	 * 例如 ：/form/addUser.do。 如果需要使用的表单不再同一个应用下，则需要写完整路径如:http://xxxx/crm/addUser.do
	 */
	private Integer formType;
	public static int FORM_TYPE_SYN=0;
	public static int FORM_TYPE_URL=1;
	
	public String getFormTypeDesc() {
		if(formType ==null) return "";
		if(FORM_TYPE_SYN == formType) {
			return "动态表单";
		}
		if(FORM_TYPE_URL == formType) {
			return "URL表单";
		}
		return "";
	}
	
	/** 当表单类型=1时：表单URL值 */
	private String formUrl;

	/** 当表单类型=1时: 明细URL值 */
	private String formDetailUrl;
	
	/**自定义表单或跳转url**/
	private String customUrl;
	//private String customUrl2; // 新城业务上用于单据审批完成后跳转到首页
	//private String customUrl3;
	

	/** 租户编码 */
	private String appCode;

	/** 排序 */
	private Integer sn;

	/** 备注 */
	private String remark;

	private String gid;

	/** 创建日期 */
	private java.util.Date createTime;

	private java.util.Date updateTime;

	
	// -----------辅助属性 ---------
	private Integer taskType;
	private String taskTypeDesc;
	
	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}

	public String getDefinitionId() {
		return this.definitionId;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getTaskKey() {
		return this.taskKey;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getFormName() {
		return this.formName;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public String getFormCode() {
		return this.formCode;
	}

	public void setFormType(Integer formType) {
		this.formType = formType;
	}

	public Integer getFormType() {
		return this.formType;
	}

	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}

	public String getFormUrl() {
		return this.formUrl;
	}

	public void setFormDetailUrl(String formDetailUrl) {
		this.formDetailUrl = formDetailUrl;
	}

	public String getFormDetailUrl() {
		return this.formDetailUrl;
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

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
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

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public String getCustomUrl() {
		return customUrl;
	}

	public void setCustomUrl(String customUrl) {
		this.customUrl = customUrl;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public String getTaskTypeDesc() {
		return taskTypeDesc;
	}

	public void setTaskTypeDesc(String taskTypeDesc) {
		this.taskTypeDesc = taskTypeDesc;
	}



	/*
	public String getCustomUrl2() {
		return customUrl2;
	}

	public void setCustomUrl2(String customUrl2) {
		this.customUrl2 = customUrl2;
	}

	public String getCustomUrl3() {
		return customUrl3;
	}

	public void setCustomUrl3(String customUrl3) {
		this.customUrl3 = customUrl3;
	}
	*/
}
