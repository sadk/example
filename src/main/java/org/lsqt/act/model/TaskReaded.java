package org.lsqt.act.model;

/**
 * 单据记录是否已阅
 * @author mmyuan
 *
 */
public class TaskReaded {
	private Long id;
	private String sourceGid;
	private Integer readed; //1=已阅 0=未阅
	
	public static int READED_YES=1;
	public static int READED_NO=0;
	
	
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
}
