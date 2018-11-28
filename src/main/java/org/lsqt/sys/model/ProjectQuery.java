package org.lsqt.sys.model;

import org.lsqt.components.db.Page;

/**
 * 
 * @author yuanmm
 *
 */
public class ProjectQuery {
	private Integer pageIndex=Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize=Page.DEFAULT_PAGE_SIZE;
	
	private String sortOrder;
	private String sortField;
	
	private String key; // 关键字
	
	
	private Long id;
	private String name; // 工程名称
	private String code; 
	private String fileName;
	
	private String levelDb;
	private String levelWeb;
	private String levelView;
	
	private String plugin;
	
	private String appCode;
	private String remark;
	
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
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
