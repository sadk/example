package org.lsqt.sys.model;

import java.util.Date;

/**
 * 代码生成的内容管理
 * @author yuanmm
 *
 */
public class CodeContent {
	private Long id;
	private Long templateId;
	private Long tableId;
	private String content;
	private String log;
	
	private String status ; // 1=生成成功 0=未生成代码
	public static final String STATUS_生成成功="1";
	public static final String STATUS_生成失败="0";
	
	public String getStatusDesc(){
		if(STATUS_生成失败.equals(status)) {
			return "生成失败";
		}
		else if(STATUS_生成成功.equals(status)) {
			return "生成成功";
		}
		return "";
	}
	
	private Long optUserId; // 操作用户ID
	private Integer sn;
	private String remark;
	private String appCode;
	
	private String gid;
	private Date createTime; 
	private Date updateTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	public Long getTableId() {
		return tableId;
	}
	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getOptUserId() {
		return optUserId;
	}
	public void setOptUserId(Long optUserId) {
		this.optUserId = optUserId;
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
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	
}
