package org.lsqt.cms.model;

import java.util.Date;

import org.lsqt.components.context.annotation.model.Pattern;

public class News {
	private Long id;
	private String name;
	private String code;
	private String title;
	private String summary;
	private String tags;
	
	private String author; //作者
	
	@Pattern("yyyy-MM-dd")
	private Date publishDate; // 发布日期
	private Integer enable; // 0=未启用 1=启用
	private Integer statusAuth; // 状态：0=未审 1=审核中 2=审核通过
	private Integer topDays; // 置顶天数
	private Integer cntReplay ; // 评论数
	private Integer cntView; // 阅读数
	private Integer sn;
	private String appCode;
	private String remark;
	
	//时间、地点、人物
	@Pattern("yyyy-MM-dd")
	private Date time;
	private String address;
	private String person;
	
	// 来源
	private String source; //如：新浪、百度等字眼
	private String sourceUrl;
	private Integer generateType; // 生成的类型：0=用户后台发布 1=应用程序采集 2=注册用户自己发布
	
	private Integer staticFileFlag; //否生成静态文件 0=未生成 1=已生成
	private String staticFilePath; // 静态文件路径
	
	
	private String gid;
	private Date createTime;
	private Date updateTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	public Integer getTopDays() {
		return topDays;
	}
	public void setTopDays(Integer topDays) {
		this.topDays = topDays;
	}
	public Integer getSn() {
		return sn;
	}
	public void setSn(Integer sn) {
		this.sn = sn;
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
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getGenerateType() {
		return generateType;
	}
	public void setGenerateType(Integer generateType) {
		this.generateType = generateType;
	}
	public String getStaticFilePath() {
		return staticFilePath;
	}
	public void setStaticFilePath(String staticFilePath) {
		this.staticFilePath = staticFilePath;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getStatusAuth() {
		return statusAuth;
	}
	public void setStatusAuth(Integer statusAuth) {
		this.statusAuth = statusAuth;
	}
	public Integer getCntReplay() {
		return cntReplay;
	}
	public void setCntReplay(Integer cntReplay) {
		this.cntReplay = cntReplay;
	}
	public Integer getCntView() {
		return cntView;
	}
	public void setCntView(Integer cntView) {
		this.cntView = cntView;
	}
	public Integer getStaticFileFlag() {
		return staticFileFlag;
	}
	public void setStaticFileFlag(Integer staticFileFlag) {
		this.staticFileFlag = staticFileFlag;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
}
