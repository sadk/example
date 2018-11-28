package org.lsqt.sys.model;

/**
 * 国省市区数据整合到一张表，辅助对象
 * @author yuanmm
 *
 */
public class RegionCity {
	private Long id;
	private Long provinceId;
	
	private String name;
	private String zipCode; // 邮编
	
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
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	
}
