package org.lsqt.sys.model;

import java.util.Date;

/**
 * 数据字典相关
 * 
 * @author yuanmm
 *
 */
public class Dictionary implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9161220675039743944L;
	/**导出数据的文件类型**/
	public static final String EXPORT_FILE_TYPE_EXCEL = "excel";
	public static final String EXPORT_FILE_TYPE_PDF = "pdf";
	public static final String EXPORT_FILE_TYPE_TXT = "excel";
	public static final String EXPORT_FILE_TYPE_DOC = "doc";
	
	/**是否导出全部数据**/
	public static final String EXPORT_DATA_TYPE_当前页="0";
	public static final String EXPORT_DATA_TYPE_选中行="1";
	public static final String EXPORT_DATA_TYPE_全部数据="2";
	
	
	public static final int ENABLE_启用=1;
	public static final int ENABLE_禁用=0;
	
	public static final int YES=1;
	public static final int NO=0;
	
	private Long id;
	private Long pid;
	
	
	private String code;
	
	private String categoryCode;
	private String categoryName;
	
	private Integer sn;
	private String name;
	private String value;
	
	private String nodePath;
	private String appCode;
	private String remark;
	
	private String dataType;
	private String enable;
	
	private String gid;
	private Date createTime;
	private Date updateTime;
	
	
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

	public String getNodePath() {
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

}
