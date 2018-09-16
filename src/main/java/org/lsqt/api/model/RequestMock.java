package org.lsqt.api.model;

/**
 * HttpAPI模拟报文的内容
 * @author mingmin.yuan
 *
 */
public class RequestMock {
	private Long id;
	private Long definitionId;
	private String content;
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
	public Long getDefinitionId() {
		return definitionId;
	}
	public void setDefinitionId(Long definitionId) {
		this.definitionId = definitionId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
}
