package org.lsqt.sys.model;

import java.util.Date;

/**
 * 工程定义
 * @author yuanmm
 *
 */
public class Project {
	private Long id;
	private String name;
	private String code;
	private String fileName;
	private String filePath;
	
	private String structure;// 结构：maven、传统javaee
	private String eclipse;
	
	private String levelDb;
	private String levelWeb;
	private String levelView;
	
	private String plugin;
	private String remark;
	private String appCode;
	
	private String gid;
	private Date createTime;
	private Date updateTime;
	private Integer sn;
	
	
	public static final String PROJECT_CODE_EXAMPLE="example_1";
	public static final String PROJECT_CODE_SPRING3_MYBATIS3="spring_1";
	public static final String PROJECT_CODE_SPRING3_MYBATIS3_SYSWIN="spring_9";
	
	public Integer getSn() {
		return sn;
	}
	public void setSn(Integer sn) {
		this.sn = sn;
	}
	
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getStructure() {
		return structure;
	}
	public void setStructure(String structure) {
		this.structure = structure;
	}
	public String getEclipse() {
		return eclipse;
	}
	public void setEclipse(String eclipse) {
		this.eclipse = eclipse;
	}
	public String getLevelDb() {
		return levelDb;
	}
	public void setLevelDb(String levelDb) {
		this.levelDb = levelDb;
	}
	public String getLevelWeb() {
		return levelWeb;
	}
	public void setLevelWeb(String levelWeb) {
		this.levelWeb = levelWeb;
	}
	public String getLevelView() {
		return levelView;
	}
	public void setLevelView(String levelView) {
		this.levelView = levelView;
	}
	public String getPlugin() {
		return plugin;
	}
	public void setPlugin(String plugin) {
		this.plugin = plugin;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
