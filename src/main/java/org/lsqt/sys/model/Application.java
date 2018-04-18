package org.lsqt.sys.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.lsqt.components.context.annotation.model.Pattern;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 应用系统模型
 * @author yuanmm
 */
public class Application {
	public static final String ENABLE_YES ="1";
	public static final String ENABLE_NO  ="0";
	
	public static final String APP_CODE_DEFAULT="1000";
	
	private Long id;
	private String name;
	private String value;
	private String code; 
	private Integer sn;
	
	private String remark;
	private String enable;
	private String gid;
	
	private String tenantCode;
	private String tenantName;
	
	//@JSONField (format="yyyy-MM-dd")  
	private Date createTime;
	private String createTimeDesc;
	private String updateTimeDesc;
	//@JSONField (format="yyyy-MM-dd") 
	private Date updateTime;
	
	public String getEnableDesc(){
		if(ENABLE_YES.equals(this.enable)){
			return "启用";
		}
		else if(ENABLE_NO.equals(this.enable)){
			return "禁用";
		}
		return "";
	}
	
	private String formatDate(Date date){
		if(date == null) return null;
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
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
	public Integer getSn() {
		return sn;
	}
	public void setSn(Integer sn) {
		this.sn = sn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
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
	public String getCreateTimeDesc() {
		return formatDate(createTime);
	}
	public void setCreateTimeDesc(String createTimeDesc) {
		this.createTimeDesc = createTimeDesc;
	}
	public String getUpdateTimeDesc() {
		return formatDate(updateTime);
	}
	public void setUpdateTimeDesc(String updateTimeDesc) {
		this.updateTimeDesc = updateTimeDesc;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTenantCode() {
		return tenantCode;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	
}
