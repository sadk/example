package org.lsqt.act.model;

import java.util.List;

import org.lsqt.components.db.Page;

public class NodeQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize  = Page.DEFAULT_PAGE_SIZE;
	
	private Long id;
	private String definitionId;
	private List<String> definitionIdList ;
	
	private String taskKey;
	private String taskName;

	private Integer printNode; //是否是用印节点
	private String printNodes; // 用于查询哪几类的用印节点：用印=1 用印归档=2;档案管理员=3;现保管员=4;
	
	private Integer taskDefType; // 节点定义类型: 1=usertask
	private Integer taskBizType; // 节点类型: 0=拟稿节点

	private String nodeJumpType; // 1=正常跳转 2=选择路径跳转 3=自由跳转  4=发起时自动跳过 5=审批用户为空自动跳过(节点用户设置为空）   6=解析用户为空自动跳过（节点用户设置不为空，但程序解析不到用户）7=强制跳过
	private List<String> nodeJumpTypeList;
	
	/** 是否从全局变量里copy流程变量值到当前节点 0=否  1=是 2=第三方接口设置 **/
	private Integer nodeVariableCopy;

	private String appCode;// 租户编码
	private Integer sn;
	private String remark;
	private String gid;
	private java.util.Date createTime;
	private java.util.Date updateTime;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDefinitionId() {
		return definitionId;
	}
	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}
	public String getTaskKey() {
		return taskKey;
	}
	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Integer getTaskDefType() {
		return taskDefType;
	}
	public void setTaskDefType(Integer taskDefType) {
		this.taskDefType = taskDefType;
	}
	public Integer getTaskBizType() {
		return taskBizType;
	}
	public void setTaskBizType(Integer taskBizType) {
		this.taskBizType = taskBizType;
	}
	public Integer getSn() {
		return sn;
	}
	public void setSn(Integer sn) {
		this.sn = sn;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public java.util.Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
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
	public Integer getNodeVariableCopy() {
		return nodeVariableCopy;
	}
	public void setNodeVariableCopy(Integer nodeVariableCopy) {
		this.nodeVariableCopy = nodeVariableCopy;
	}
	public String getNodeJumpType() {
		return nodeJumpType;
	}
	public void setNodeJumpType(String nodeJumpType) {
		this.nodeJumpType = nodeJumpType;
	}
	public List<String> getNodeJumpTypeList() {
		return nodeJumpTypeList;
	}
	public void setNodeJumpTypeList(List<String> nodeJumpTypeList) {
		this.nodeJumpTypeList = nodeJumpTypeList;
	}
	public Integer getPrintNode() {
		return printNode;
	}
	public void setPrintNode(Integer printNode) {
		this.printNode = printNode;
	}
	public String getPrintNodes() {
		return printNodes;
	}
	public void setPrintNodes(String printNodes) {
		this.printNodes = printNodes;
	}
	public List<String> getDefinitionIdList() {
		return definitionIdList;
	}
	public void setDefinitionIdList(List<String> definitionIdList) {
		this.definitionIdList = definitionIdList;
	}
}
