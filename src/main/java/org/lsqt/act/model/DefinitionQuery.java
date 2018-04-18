package org.lsqt.act.model;

import org.lsqt.components.db.Page;

/**
 * 流程定义查询,见表：act_re_procdef 和 act_re_deployment
 * @author mmyuan
 *
 */
public class DefinitionQuery {
	private Integer pageIndex=Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize=Page.DEFAULT_PAGE_SIZE;
	
	private String sortOrder;
	private String sortField;
	
	private String keyWord; // 关键字
	
	private String id;
	private String ids;

	private String name;
	
	private String key; // 流程定义key
	private Integer version;
	private String deploymentId ; // 流程布署ID
	
	private String resourceName; // 流程定义文件
	private String diagramResourceName ; // 流程定义图
	
	private String description; // 流程描述
	
	private Integer suspensionState;  // 1=激活 2=挂起
	private Integer hasStartFormKey;  // 0=没有
	private Integer hasGraphicalNotation;
	private String tenantId; 
	
	private String category ; //流程定义分类
	
	private Boolean isDisplayNewest = false ; // 只显示最新版本流程
	
	// ----------------------------------------
	private String deployCategory; //辅助属性，流程布署分类（一般和流程定义分类保持一致！！！但不是流程定义分类）
	
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


	public String getKeyWord() {
		return keyWord;
	}


	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public String getResourceName() {
		return resourceName;
	}


	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}


	public String getTenantId() {
		return tenantId;
	}


	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}


	public String getDeploymentId() {
		return deploymentId;
	}


	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public Integer getVersion() {
		return version;
	}


	public void setVersion(Integer version) {
		this.version = version;
	}


	public String getIds() {
		return ids;
	}


	public void setIds(String ids) {
		this.ids = ids;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDiagramResourceName() {
		return diagramResourceName;
	}


	public void setDiagramResourceName(String diagramResourceName) {
		this.diagramResourceName = diagramResourceName;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Integer getSuspensionState() {
		return suspensionState;
	}


	public void setSuspensionState(Integer suspensionState) {
		this.suspensionState = suspensionState;
	}


	public Integer getHasStartFormKey() {
		return hasStartFormKey;
	}


	public void setHasStartFormKey(Integer hasStartFormKey) {
		this.hasStartFormKey = hasStartFormKey;
	}


	public Integer getHasGraphicalNotation() {
		return hasGraphicalNotation;
	}


	public void setHasGraphicalNotation(Integer hasGraphicalNotation) {
		this.hasGraphicalNotation = hasGraphicalNotation;
	}


	public Boolean getIsDisplayNewest() {
		return isDisplayNewest;
	}


	public void setIsDisplayNewest(Boolean isDisplayNewest) {
		this.isDisplayNewest = isDisplayNewest;
	}


	public String getDeployCategory() {
		return deployCategory;
	}


	public void setDeployCategory(String deployCategory) {
		this.deployCategory = deployCategory;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}
}
