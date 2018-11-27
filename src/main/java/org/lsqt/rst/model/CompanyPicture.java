package org.lsqt.rst.model;

/**
 * 企业图片表
 */
public class CompanyPicture {

	/***/
	private Long id;

	/** 企业编号 */
	private String companyCode;

	/** 图片路径 */
	private String url;

	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}

}
