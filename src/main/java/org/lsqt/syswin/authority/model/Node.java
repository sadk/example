package org.lsqt.syswin.authority.model;

/**
 * 整个系统的的资源节点树状结构：（菜单+功能按钮）
 * @author mmyuan
 *
 */
public class Node {
	private String id;
	private String pid;
	private String name;
	private String dataType; 
	private String extProperty; // 扩展属性，用于装配业务
	
	private String code ;
	private String nodePath;
	private String url; 
	
	//节点类型：1=菜单 2=功能
	public static final String DATA_TYPE_MENU="1";
	public static final String DATA_TPYE_FUNCTION="2";
	
	
 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getExtProperty() {
		return extProperty;
	}
	public void setExtProperty(String extProperty) {
		this.extProperty = extProperty;
	}
	public String getNodePath() {
		return nodePath;
	}
	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
