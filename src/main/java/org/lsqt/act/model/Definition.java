package org.lsqt.act.model;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * 流程定义模型,见表：act_re_procdef 和 act_re_deployment
 * @author mmyuan
 *
 */
public class Definition {
	private String id;
	private String name;
	
	private String modelId;
	
	private String key; // 流程定义key
	private Integer version;
	private String deploymentId ; // 流程布署ID
	
	private String resourceName; // 流程定义文件
	private String dgrmResourceName ; // 流程定义图
	
	private String description; // 流程描述
	
	private Integer suspensionState;  // 1=激活 2=挂起
	private Integer hasStartFormKey;  // 0=没有
	private Integer hasGraphicalNotation;
	private String tenantId; 
	
	private String category ; //流程定义分类
	
	private String shortName; // 流程定义简称（新增字段用于移动端)
	
	private String enableMobile; // 手机端是否启用 0=不启用 1=启用  (新增字段用于移动端)
	public static final String ENABLE_MOBILE_DISABLE="0";
	public static final String ENABLE_MOBILE_ENABLE="1";
	
	private String enableNeighborJump; // 启用相邻节点相同审批人就自动跳过
	public static final String ENABLE_NEIGHBOR_JUMP_DISABLE="0";
	public static final String ENABLE_NEIGHBOR_JUMP_ENABLE="1";
	
	// ---------------- 辅助属性 ------------------------
	private String deployName;
	private Date deployTime;

	/**
	 * 以发布时间作为流程版本显示
	 * @return
	 */
	public String getDeployVersion() {
		if (this.deployTime != null) {
			return new SimpleDateFormat("yyyyMMddHHmmss").format(this.deployTime);
		}
		return "";
	}
	
	public String getVersionDesc(){
		return this.name+"（版本"+version+"）";
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getDeployName() {
		return deployName;
	}

	public void setDeployName(String deployName) {
		this.deployName = deployName;
	}

	public Date getDeployTime() {
		return deployTime;
	}

	public void setDeployTime(Date deployTime) {
		this.deployTime = deployTime;
	}

	public String getDgrmResourceName() {
		return dgrmResourceName;
	}

	public void setDgrmResourceName(String dgrmResourceName) {
		this.dgrmResourceName = dgrmResourceName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getEnableMobile() {
		return enableMobile;
	}

	public void setEnableMobile(String enableMobile) {
		this.enableMobile = enableMobile;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getEnableNeighborJump() {
		return enableNeighborJump;
	}

	public void setEnableNeighborJump(String enableNeighborJump) {
		this.enableNeighborJump = enableNeighborJump;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
}
