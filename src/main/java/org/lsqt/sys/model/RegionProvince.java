package org.lsqt.sys.model;

/**
 * 国省市区数据整合到一张表，辅助对象
 * @author yuanmm
 *
 */
public class RegionProvince {
	private Long id;
	private String name;
	
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
	
}
