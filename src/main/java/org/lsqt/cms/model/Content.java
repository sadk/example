package org.lsqt.cms.model;

import java.util.Date;

/**
 * 大文本对象,存储新闻、博客、贴子、ftl\vm内容
 * @author yuanmm
 *
 */
public class Content {
	
	public Long id ;
	
	public Long objectId; // 对象ID，如果内容为新闻，则是新闻表的ID,
	
	private String type; // 类型:300=新闻 301=博客 302=贴子(3开头的都是FreeMark解析内容) 303=Html页面内容    见： 3xx=ftl内容模板 4xx=vm内容模板
	public static final String TYPE_NEWS = "300";
	public static final String TYPE_BLOG = "301";
	public static final String TYPE_FORUM = "302";
	public static final String TYPE_HTML_TEMPLATE_FREEMARK = "303";
	
	public String getTypeDesc() {
		if(TYPE_HTML_TEMPLATE_FREEMARK.equals(type)) {
			return "HTML模板（Freemark引擎）";
		}
		return "";
	}
	
    private String  code ; //整表唯一编码
	
    private String  title ;//内容标题
	
  
	 
    private Long createDate  ;//创建日期
	
    private String content  ;//内容
	
    private String  appCode ;// 所属应用
	
    private String  remark  ; 
    
    private Integer sn;//排序号，当内容分割存储时有效！
    
    private Integer enable; // 是否启用：1=启用 0=不启用
    
	
	public String gid; //整系统局唯一码
	
    public Date updateTime;
    
	public Date createTime;
	 
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
	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
	}
}
