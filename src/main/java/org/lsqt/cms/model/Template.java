package org.lsqt.cms.model;

/**
 * CMS系统，专用来存储不同的模板内容，模板的文本内容存在Content对象里
 */
public class Template {

	/***/
	private Long id;

	/** 模板名 */
	private String name;
	
	private String title;

	/** 模板编码 */
	private String code;

	/** 所属的栏目 */
	private Long channelId;
	
	/**是否启用 1=启用 0=不启用**/
	private Integer enable;
	

	/** 模板缩略图路径(原始图） */
	private String pathThumbnail;
	private String thumbnailName;
	
	/** 模板缩略图路径大、中、小 */
	private String pathThumbnailLarge; 
	private String pathThumbnailMiddle;	 
	private String pathThumbnailSmall;
	
	
	/** 排序号 */
	private Integer sn;

	/** 用户备注 */
	private String remark;

	/** 所属应用 */
	private String appCode;

	/***/
	private String gid;

	/** 创建日期 */
	private java.util.Date createTime;

	/***/
	private java.util.Date updateTime;

	// ----------------------------
	private String content;
	private String channelName;
	
	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
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

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAppCode() {
		return this.appCode;
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

	public String getPathThumbnail() {
		return pathThumbnail;
	}

	public void setPathThumbnail(String pathThumbnail) {
		this.pathThumbnail = pathThumbnail;
	}

	public String getPathThumbnailLarge() {
		return pathThumbnailLarge;
	}

	public void setPathThumbnailLarge(String pathThumbnailLarge) {
		this.pathThumbnailLarge = pathThumbnailLarge;
	}

	public String getPathThumbnailMiddle() {
		return pathThumbnailMiddle;
	}

	public void setPathThumbnailMiddle(String pathThumbnailMiddle) {
		this.pathThumbnailMiddle = pathThumbnailMiddle;
	}

	public String getPathThumbnailSmall() {
		return pathThumbnailSmall;
	}

	public void setPathThumbnailSmall(String pathThumbnailSmall) {
		this.pathThumbnailSmall = pathThumbnailSmall;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getThumbnailName() {
		return thumbnailName;
	}

	public void setThumbnailName(String thumbnailName) {
		this.thumbnailName = thumbnailName;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

}
