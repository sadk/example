package org.lsqt.cms.model;

import java.util.Date;

/**
 * 大文本对象,存储新闻、博客、贴子、ftl\vm内容
 * @author yuanmm
 *
 */
public class Content {
	
	public Long id ;
	
	public Long objectId; // 对象ID，如果内容为新闻，则是新闻表的ID
	public String type; //类型:30=新闻 31=博客 32=贴子(3开头的都是FreeMark解析内容)         3=ftl内容模板 4=vm内容模板
	
	
    public String  code ; //整表唯一编码
	
    public String  title ;//内容标题
	
  
	 
    public Long createDate  ;//创建日期
	
    public String content  ;//内容
	
    public String  appCode ;// 所属应用
	
    public String  remark  ; 
    
    public Integer sn;//排序号，当内容分割存储时有效！
	
	public String gid; //整系统局唯一码
	
    public Date updateTime;
    
	public Date createTime;
	 
	public static final String TYPE_NEWS = "30";
	public static final String TYPE_BLOG = "31";
	public static final String TYPE_FORUM = "32";
	public static final String TYPE_FTL_CONTENT = "3";
	public static final String TYPE_VM_CONTENT = "4";
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
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
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getObjectId() {
		return objectId;
	}
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	public Integer getSn() {
		return sn;
	}
	public void setSn(Integer sn) {
		this.sn = sn;
	}
}
