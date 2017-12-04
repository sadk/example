package org.lsqt.sys.model;

import java.util.Date;

/**
 * 文件
 */
public class File {

	private Long id;
	private Long pid;
	
	/**对象ID,如：一个流程表单里，上传几个文件**/
	private String objId; 
	
	/** 显示的名称 */
	private String name;
	
	
	/** 原始文件名称 */
	private String originalName;
	
	/**文件或目录类型编码，引用字典值**/
	private String typeCode;
	
	/** 文件或目录： 1=文件 2=目录*/
	private String fileOrDir;
	public static final String FILE_OR_DIR_FILE="1";
	public static final String FILE_OR_DIR_DIR="2";
	
	
	private Integer sn;
	
	/**
	 * 文件(上传后的)状态
	 */
	private Integer status ;
	public static final int STATUS_OK = 1;
	public static final int STATUS_FAIL = 0;

	/** FastDFS存储后的原路径 */
	private String path;
	
	private String pathSmall; // 缩略图(小)
	private String pathLarge; // 大
	private String pathMedium; // 中
	
	// ---- 美好家专用!!! fuck
	public String breviaryName ;
	public String tfsFileName ;
	
	/** 备注 */
	private String remark;

	/**目录的路径**/
	private String nodePath;
	
	private String gid;
	private Date createTime;
	private Date updateTime;

	private String appCode;
	
	// --------------  辅助属性   ---------------
	
	/** ：用户上传文件后的本地路径 **/
	private String localFilePath;
	

	
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


	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return this.path;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLocalFilePath() {
		return localFilePath;
	}

	public void setLocalFilePath(String localFilePath) {
		this.localFilePath = localFilePath;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
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

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getFileOrDir() {
		return fileOrDir;
	}

	public void setFileOrDir(String fileOrDir) {
		this.fileOrDir = fileOrDir;
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
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

	public String getPathSmall() {
		return pathSmall;
	}

	public void setPathSmall(String pathSmall) {
		this.pathSmall = pathSmall;
	}

	public String getPathLarge() {
		return pathLarge;
	}

	public void setPathLarge(String pathLarge) {
		this.pathLarge = pathLarge;
	}

	public String getPathMedium() {
		return pathMedium;
	}

	public void setPathMedium(String pathMedium) {
		this.pathMedium = pathMedium;
	}

	public String getBreviaryName() {
		return breviaryName;
	}

	public void setBreviaryName(String breviaryName) {
		this.breviaryName = breviaryName;
	}

	public String getTfsFileName() {
		return tfsFileName;
	}

	public void setTfsFileName(String tfsFileName) {
		this.tfsFileName = tfsFileName;
	}

}
