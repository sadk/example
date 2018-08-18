package org.lsqt.act.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 单据记录是否已阅
 * @author mmyuan
 *
 */
public class TaskReadedQuery {
	private Long id;
	private String sourceGid;
	private Integer readed; //1=已阅 0=未阅
	private List<String> sourceGidList = new ArrayList<>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSourceGid() {
		return sourceGid;
	}
	public void setSourceGid(String sourceGid) {
		this.sourceGid = sourceGid;
	}
	public Integer getReaded() {
		return readed;
	}
	public void setReaded(Integer readed) {
		this.readed = readed;
	}
	public List<String> getSourceGidList() {
		return sourceGidList;
	}
	public void setSourceGidList(List<String> sourceGidList) {
		this.sourceGidList = sourceGidList;
	}
}
