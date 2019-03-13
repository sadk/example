package org.lsqt.sms.model;

/**
 *  短信 文案
 */
public class Templ {
	private Long id;
	private String templId;
	private String templName;
	private String templContent;
	private String signName;
	private Integer launchNum;
	private String launchTime;
	private String templStatus;
	private String reply;
	private String createTime;
	private String updateTime;
	private String useStatus;
	private String signId;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTemplId() {
		return templId;
	}
	public void setTemplId(String templId) {
		this.templId = templId;
	}
	public String getTemplName() {
		return templName;
	}
	public void setTemplName(String templName) {
		this.templName = templName;
	}
	public String getTemplContent() {
		return templContent;
	}
	public void setTemplContent(String templContent) {
		this.templContent = templContent;
	}
	public String getSignName() {
		return signName;
	}
	public void setSignName(String signName) {
		this.signName = signName;
	}
	public Integer getLaunchNum() {
		return launchNum;
	}
	public void setLaunchNum(Integer launchNum) {
		this.launchNum = launchNum;
	}
	public String getLaunchTime() {
		return launchTime;
	}
	public void setLaunchTime(String launchTime) {
		this.launchTime = launchTime;
	}
	public String getTemplStatus() {
		return templStatus;
	}
	public void setTemplStatus(String templStatus) {
		this.templStatus = templStatus;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}

	public String getSignId() {
		return signId;
	}

	public void setSignId(String signId) {
		this.signId = signId;
	}
}
