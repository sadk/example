package org.lsqt.cms.model;

import java.util.List;

import org.lsqt.components.db.Page;

/**
 * 
 * @author yuanmm
 *
 */
public class TagsQuery {
	private Integer pageIndex=Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize=Page.DEFAULT_PAGE_SIZE;
	
	private String sortOrder;
	private String sortField;
	
	private String key; // 关键字
 
	private Long id;
	private String name; // 名称
	private String code; // 编码
	private String value; 
	
	private List<String> nameList; // 用于批量查询
	 
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
	public List<String> getNameList() {
		return nameList;
	}
	public void setNameList(List<String> nameList) {
		this.nameList = nameList;
	}
}
