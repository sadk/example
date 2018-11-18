package org.lsqt.chk.model;

import org.lsqt.components.db.Page;

/**
 * 用户表刑事案底核查表(北京优分数据科技接口)
 */
public class UserCrimeQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符
	private Long id;
	
	/** 引用系统用户id */
	private Long userId;

	/** 用户姓名 */
	private String name;
	/** 身份证号 */
	private String idcard;
	
	
	private String batchNo ; // 导入时生成的批次号
	private String matchResCode ; // 用户名和身份证匹配系统响应码
	private String matchResDesc ; // 用户名和身份证匹配系统响应说明
	private String matchBizCode ; // 用户名和身份证匹配业务响应码
	private String matchBizDesc ; // 用户名和身份证匹配业务响应说明
	
	private String resCode; // 系统响应码 
	private String resMsg; // 响应码描述
	private String statusCode;// 业务状态码 
	private String statusMsg; // 状态码描述 

	/***/

	private Long sn;

	/***/
	private String remark;

	/***/
	private String appCode;

	/***/
	private String code;

	// getter、setter

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getIdcard() {
		return this.idcard;
	}
	
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getResCode() {
		return this.resCode;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

	public String getResMsg() {
		return this.resMsg;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusCode() {
		return this.statusCode;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public String getStatusMsg() {
		return this.statusMsg;
	}

	public void setSn(Long sn) {
		this.sn = sn;
	}

	public Long getSn() {
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

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

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

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getMatchResCode() {
		return matchResCode;
	}

	public void setMatchResCode(String matchResCode) {
		this.matchResCode = matchResCode;
	}

	public String getMatchResDesc() {
		return matchResDesc;
	}

	public void setMatchResDesc(String matchResDesc) {
		this.matchResDesc = matchResDesc;
	}

	public String getMatchBizCode() {
		return matchBizCode;
	}

	public void setMatchBizCode(String matchBizCode) {
		this.matchBizCode = matchBizCode;
	}

	public String getMatchBizDesc() {
		return matchBizDesc;
	}

	public void setMatchBizDesc(String matchBizDesc) {
		this.matchBizDesc = matchBizDesc;
	}

}
