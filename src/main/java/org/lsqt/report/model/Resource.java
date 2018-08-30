package org.lsqt.report.model;

/**
 * 报表页面元素定义
 */
public class Resource {

	/***/

	private Long id;

	/** 报表分类ID */

	private Long definitionId;

	/** 报表分类名 */
	private String name;

	/** 定义编码 */
	private String code;

	/** 报表页面元素类型: 1=按钮 2=下拉框（单选）3=下拉框（多选）4=文本框 5=文本域  6=File 7=日历 8=数字框 */
	private String type;

	private String eventName;
	
	/** 按钮点击前事件触发的js函数 */
	private String btnBeforeScript;

	/** 按钮点击后事件触发js函数 */
	private String btnAfterScript;

	/**点击按钮时触发的角本**/
	private String btnScript;
	
	/** 租户编码 */
	private String appCode;

	/** 排序 */

	private Integer sn;

	/** 备注 */
	private String remark;

	/***/
	private String gid;

	/** 创建日期 */

	private java.util.Date createTime;

	/***/

	private java.util.Date updateTime;

	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setDefinitionId(Long definitionId) {
		this.definitionId = definitionId;
	}

	public Long getDefinitionId() {
		return this.definitionId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public void setBtnBeforeScript(String btnBeforeScript) {
		this.btnBeforeScript = btnBeforeScript;
	}

	public String getBtnBeforeScript() {
		return this.btnBeforeScript;
	}

	public void setBtnAfterScript(String btnAfterScript) {
		this.btnAfterScript = btnAfterScript;
	}

	public String getBtnAfterScript() {
		return this.btnAfterScript;
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

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getBtnScript() {
		return btnScript;
	}

	public void setBtnScript(String btnScript) {
		this.btnScript = btnScript;
	}

}
