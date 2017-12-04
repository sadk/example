package org.lsqt.sys.model;

import java.util.Arrays;
import java.util.List;

/**
 * 变量管理
 */
public class Variable {

	/***/

	private Long id;

	/***/

	private Long objId;

	/** 变量名 */
	private String name;

	/** 变量编码 */
	private String code;
	
	// 代码生成器 常用变量(内置值)
	public static final List<String> COMMON_VARIABLES_内置值 = Arrays
			.asList(new String[] { "tableColumnList", "tableName", "tableComment" });
	
	// 代码生成器 常用变量(固定值)
	public static final List<String> COMMON_VARIABLES_固定值 = Arrays
			.asList(new String[] { "Model", "model" });
	

	/** 变量使用类型 1=流程变量 2=代码生成器变量 3=预置常用变量 */
	private String useType;
	public static final String USE_TYPE_流程变量="1";
	public static final String USE_TYPE_代码生成器变量="2";
	public static final String USE_TYPE_预置常用变量="3";
	
	public String getUseTypeDesc(){
		if(USE_TYPE_流程变量.equals(useType)) {
			return "流程变量";
		}
		else if(USE_TYPE_代码生成器变量.equals(useType)) {
			return "代码生成器变量";
		}
		else if(USE_TYPE_预置常用变量.equals(useType)) {
			return "预置常用变量";
		}
		
		 return "";
	}

	/** 变量值 */
	private String value;
	private String valueType; // 变量值类型  1=固定值 2=运行时 3=内置值
	public static final String VALUE_TYPE_固定值="1";
	public static final String VALUE_TYPE_运行时="2";
	public static final String VALUE_TYPE_内置值="3";
	public String getValueTypeDesc(){
		if(VALUE_TYPE_内置值.equals(valueType)) {
			return "内置值";
		}
		else if(VALUE_TYPE_固定值.equals(valueType)) {
			return "固定值";
		}
		else if(VALUE_TYPE_运行时.equals(valueType)) {
			return "运行时";
		}
		return "";
	}

	/** 变量值解析类型:1=freemark 2=velocity 3=groovy 4=javascript */
	private String valueResolveType;
	public static String VALUE_RESOLVE_TYPE_FREEMARK="1";
	public static String VALUE_RESOLVE_TYPE_VELOCITY="2";
	public static String VALUE_RESOLVE_TYPE_GROOVY="3";
	public static String VALUE_RESOLVE_TYPE_JAVASCRIPT="4";
	
	public String getValueResolveTypeDesc(){
		if(VALUE_RESOLVE_TYPE_FREEMARK.equals(valueResolveType)) {
			return "freemark";
		}
		else if(VALUE_RESOLVE_TYPE_VELOCITY.equals(valueResolveType)) {
			return "velocity";
		}
		else if(VALUE_RESOLVE_TYPE_GROOVY.equals(valueResolveType)) {
			return "groovy";
		}
		else if(VALUE_RESOLVE_TYPE_JAVASCRIPT.equals(valueResolveType)) {
			return "javascript";
		}
		return "";
	}
	/***/

	private Integer sn;

	/** 用户备注 */
	private String remark;

	/***/
	private String appCode;

	/***/
	private String gid;

	/** 创建日期 */

	private java.util.Date createTime;

	/***/

	private java.util.Date updateTime;

	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setObjId(Long objId) {
		this.objId = objId;
	}

	public Long getObjId() {
		return this.objId;
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

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public String getUseType() {
		return this.useType;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public void setValueResolveType(String valueResolveType) {
		this.valueResolveType = valueResolveType;
	}

	public String getValueResolveType() {
		return this.valueResolveType;
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

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

}
