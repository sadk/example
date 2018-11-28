package org.lsqt.act.model;

import java.util.ArrayList;
import java.util.List;

import org.lsqt.components.db.Page;

/**
 * 节点表单设置
 */
public class NodeFormQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符

	/** 流程定义ID */
	private String definitionId;
	
	/** 多个流程定义列表 **/
	private List<String> definitionIdList = new ArrayList<>();

	/** 节点定义Key */
	private String taskKey;

	/** 表单名称 */
	private String formName;

	/** 表单编码 */
	private String formCode;

	/**
	 * 按钮类型:2=全局表单 0=在线表单
	 * 1=URL表单,注：在线表单,为系统自定义表单;url表单,是外部表单。地址写法规则为：如果表单页面平台在同一个应用中，路径从根开始写，不需要上下文路径，例如
	 * ：/form/addUser.do。 如果需要使用的表单不再同一个应用下，则需要写完整路径如:http://xxx/crm/addUser.do
	 */
	private Integer formType;

	/** 当表单类型=1时：表单URL值 */
	private String formUrl;

	/** 当表单类型=1时: 明细URL值 */
	private String formDetailUrl;

	/** 1=节点表单   2=全局表单 */
	private Integer dataType;
	
	/** 租户编码 */
	private String appCode;

	/** 排序 */

	private Integer sn;

	/** 备注 */
	private String remark;

	// getter、setter

	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}

	public String getDefinitionId() {
		return this.definitionId;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getTaskKey() {
		return this.taskKey;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getFormName() {
		return this.formName;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public String getFormCode() {
		return this.formCode;
	}

	public void setFormType(Integer formType) {
		this.formType = formType;
	}

	public Integer getFormType() {
		return this.formType;
	}

	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}

	public String getFormUrl() {
		return this.formUrl;
	}

	public void setFormDetailUrl(String formDetailUrl) {
		this.formDetailUrl = formDetailUrl;
	}

	public String getFormDetailUrl() {
		return this.formDetailUrl;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAppCode() {
		return this.appCode;
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

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public List<String> getDefinitionIdList() {
		return definitionIdList;
	}

	public void setDefinitionIdList(List<String> definitionIdList) {
		this.definitionIdList = definitionIdList;
	}

}
