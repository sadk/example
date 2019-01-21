package org.lsqt.components.context.permission;

/**
* 权限资源节点
* @author mm
*
*/
public  class  AuthenticationNode implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -916797034756117739L;
	
	private Long id;
	private Long pid;
	private String name; // 资源名笱
	private String code; // 资源编码
	
	private String value; // 资源定义值

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

