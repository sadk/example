package org.lsqt.act.model;

/**
 * 报销管理表
 */
public class FormBaoXiao {

	/***/
	private Long id;

	/** 申请标题 */
	private String titile;

	/** 单据流水号 */
	private String flowNo;

	/** 填制人部门ID */
	private String createDeptId;

	/** 填制人部门 */
	private String createDeptName;

	/** 报销金额 */
	private Double money;

	/** 审批的业务状态 */
	private String status;

	private String statusDesc;

	/**流程实例**/
	private String instanceId;
	
	/**当前审批人**/
	private String currApproveUser;
	
	/** 租户编码 */
	private String appCode;

	/** 排序 */

	private Integer sn;

	/** 备注 */
	private String remark;

	/***/
	private String gid;

	/** 填制人 */

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

	public void setTitile(String titile) {
		this.titile = titile;
	}

	public String getTitile() {
		return this.titile;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public String getFlowNo() {
		return this.flowNo;
	}

	public void setCreateDeptId(String createDeptId) {
		this.createDeptId = createDeptId;
	}

	public String getCreateDeptId() {
		return this.createDeptId;
	}

	public void setCreateDeptName(String createDeptName) {
		this.createDeptName = createDeptName;
	}

	public String getCreateDeptName() {
		return this.createDeptName;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getMoney() {
		return this.money;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getStatusDesc() {
		return this.statusDesc;
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

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getCurrApproveUser() {
		return currApproveUser;
	}

	public void setCurrApproveUser(String currApproveUser) {
		this.currApproveUser = currApproveUser;
	}

}
