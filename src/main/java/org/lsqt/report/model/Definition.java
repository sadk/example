package org.lsqt.report.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 报表定义
 */
public class Definition {

	/***/
	private Long id;

	/** 报表分类ID */
	private Long categoryId;
	private String categoryName;
	
	/** 报表所属的数据源 */
	private Long datasourceId;
	private String datasourceName;
	
	/** 报表导入数据的数据源 */
	private Long importDatasourceId;
	private String importDatasourceName;
	
	/**报表导入时的数据表**/
	private String importTable;
	
	/**是否存储数据副本:1=是 0=否**/
	private Integer storeReplicaData;
	
	/** 数据副本用的数据源,导入数据时，本地会存量一分副本用于留证  **/
	private Long dataReplicaDataSourceId;
	private String dataReplicaDataSourceName;
	
	/** 报表数据导入数据副本存储精度模式 1=全字段按字符存储  2=按导入定义字段类型存储 **/
	private Integer dataReplicaStoragePrecision;
	public static final int DATA_REPLICA_STROE_PRECISION_ALL_STRING=1;
	public static final int  DATA_REPLICA_STROE_PRECISION_BY_CONFIG=2;

	/** 定义全称 */
	private String name;

	/** 定义简称 */
	private String shortName;

	/** 定义编码 */
	private String code;

	/** 报表内容类型： 1=SQL 2=Table 3=View 4=http_json 5=存储过程 */
	private String type;

	/** 报表url */
	private String url;

	/** 报表数据库类型： 1=MySQL 2=oracle 3=sqlserver 4=PostgreSQL */
	private String dbType;
	
	/** 数据SQL用于导入字段用，可以直接执行 */
	private String reportSql;
	
	/** 报表SQL，带参数的真实报表SQL **/
	private String columnSql;
	
	/**是否开启防SQL注入**/
	private String preventSqlInjection ;

	/** 报表自定义脚本 */
	private String reportScript;
	
	/**分页是否统计总记录数**/
	private Integer countRequired;
	
	/** 启用状态: 0=禁用 1=启用   **/
	private String status ;
	
	
	/**报表页面布局:1=左右布局 2=上下布局 3=左上下（用于子报表，左是高级查询区） 4=上中下（用于子报表，上是高级查询区） 5=左中右（用于子报表，左是高级查询区）*/
	private String layout ;
	private String showPager; //报表数据是否分页:true=分页 false=不分页
	private Integer searchAreaWidth=270; //查询区域宽度
	private Integer pageSize=20; //表格分页大小
	private String pageSizeList; //逗号分割
	
	private Integer canExport;  //是否可以导出excel、PDF等, (1=是，0=否)
	private Integer exportMode; //数据导出的模式, 1=默认全部字段数据导出 2=用户选择字段导出
	public static final Integer EXPORT_OR_IMPORT_MODE_ALL_COLUMN=1;
	public static final Integer EXPORT_OR_IMPORT_MODE_SELECTED_COLUMN=2;
	
	private Integer canImport;
	private Integer importMode; // 1=默认全部字段数据导出 2=用户选择字段导出
	
	
	private Integer sortMode ; //排序模式 1=客户浏览器端排  2=服务器端排序
	private Integer searchAreaControlNumPerRow; //高级查询区每行显示几个查询控件
	
	private String pageIndexParam ; //分页的参数名,默认是:pageIndex
	private String pageSizeParam; //分页的大小参数名，默认是 :pageSize

	
	public static final String LAYOUT_LEFT_RIGHT="1";
	public static final String LAYOUT_UP_DOWN="2";
	public static final String LAYOUT_LEFT_UP_DOWN_SUB_REPORT="3";
	public static final String LAYOUT_UP_MID_DOWN_SUB_REPORT="4";
	public static final String LAYOUT_LEFT_MID_RIGHT="5";


	
	private Integer exportCurrPage; //导出查询当前页=1 、导出查询所有页 =0 (注：如果报表定义不分页，则这个忽略)
	private Integer exportDataRender; // 按导出模板渲染=0 ， 大数据自动渲染 = 1
	
	public static int EXPORT_CURR_PAGE_导出查询当前页=1;
	public static int EXPORT_CURR_PAGE_导出查询所有页=0;
	
	public static int EXPORT_DATA_RENDER_按导出模板渲染=0;
	public static int EXPORT_DATA_RENDER_数据自动渲染=1;
	
	
	private String version;
	
	/** 租户编码 */
	private String appCode;

	/** 排序 */
	private Integer sn;

	/** 备注 */
	private String remark;

	/***/
	private String gid;

	/** 创建日期 */
	private java.util.Date createTime;

	/***/
	private java.util.Date updateTime;
	

	//-----------------------------
	private List<Column> columnList = new ArrayList<>();
	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getCategoryId() {
		return this.categoryId;
	}

	public void setDatasourceId(Long datasourceId) {
		this.datasourceId = datasourceId;
	}

	public Long getDatasourceId() {
		return this.datasourceId;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getDbType() {
		return this.dbType;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDatasourceName() {
		return datasourceName;
	}

	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}

	public String getReportSql() {
		return reportSql;
	}

	public void setReportSql(String reportSql) {
		this.reportSql = reportSql;
	}

	public String getColumnSql() {
		return columnSql;
	}

	public void setColumnSql(String columnSql) {
		this.columnSql = columnSql;
	}

	public String getShowPager() {
		return showPager;
	}

	public void setShowPager(String showPager) {
		this.showPager = showPager;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCanExport() {
		return canExport;
	}

	public void setCanExport(Integer canExport) {
		this.canExport = canExport;
	}

	public String getPageSizeList() {
		return pageSizeList;
	}

	public void setPageSizeList(String pageSizeList) {
		this.pageSizeList = pageSizeList;
	}

	public Integer getSortMode() {
		return sortMode;
	}

	public void setSortMode(Integer sortMode) {
		this.sortMode = sortMode;
	}

	public Integer getSearchAreaWidth() {
		return searchAreaWidth;
	}

	public void setSearchAreaWidth(Integer searchAreaWidth) {
		this.searchAreaWidth = searchAreaWidth;
	}

	public Integer getSearchAreaControlNumPerRow() {
		return searchAreaControlNumPerRow;
	}

	public void setSearchAreaControlNumPerRow(Integer searchAreaControlNumPerRow) {
		this.searchAreaControlNumPerRow = searchAreaControlNumPerRow;
	}

	public String getPageIndexParam() {
		return pageIndexParam;
	}

	public void setPageIndexParam(String pageIndexParam) {
		this.pageIndexParam = pageIndexParam;
	}

	public String getPageSizeParam() {
		return pageSizeParam;
	}

	public void setPageSizeParam(String pageSizeParam) {
		this.pageSizeParam = pageSizeParam;
	}

	public String getPreventSqlInjection() {
		return preventSqlInjection;
	}

	public void setPreventSqlInjection(String preventSqlInjection) {
		this.preventSqlInjection = preventSqlInjection;
	}

	public List<Column> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
	}

	public Integer getExportMode() {
		return exportMode;
	}

	public void setExportMode(Integer exportMode) {
		this.exportMode = exportMode;
	}

	public Integer getCanImport() {
		return canImport;
	}

	public void setCanImport(Integer canImport) {
		this.canImport = canImport;
	}

	public Integer getImportMode() {
		return importMode;
	}

	public void setImportMode(Integer importMode) {
		this.importMode = importMode;
	}

	public Long getImportDatasourceId() {
		return importDatasourceId;
	}

	public void setImportDatasourceId(Long importDatasourceId) {
		this.importDatasourceId = importDatasourceId;
	}

	public String getImportDatasourceName() {
		return importDatasourceName;
	}

	public void setImportDatasourceName(String importDatasourceName) {
		this.importDatasourceName = importDatasourceName;
	}

	public Long getDataReplicaDataSourceId() {
		return dataReplicaDataSourceId;
	}

	public void setDataReplicaDataSourceId(Long dataReplicaDataSourceId) {
		this.dataReplicaDataSourceId = dataReplicaDataSourceId;
	}

	public String getDataReplicaDataSourceName() {
		return dataReplicaDataSourceName;
	}

	public void setDataReplicaDataSourceName(String dataReplicaDataSourceName) {
		this.dataReplicaDataSourceName = dataReplicaDataSourceName;
	}

	public String getImportTable() {
		return importTable;
	}

	public void setImportTable(String importTable) {
		this.importTable = importTable;
	}

	public Integer getDataReplicaStoragePrecision() {
		return dataReplicaStoragePrecision;
	}

	public void setDataReplicaStoragePrecision(Integer dataReplicaStoragePrecision) {
		this.dataReplicaStoragePrecision = dataReplicaStoragePrecision;
	}

	public String getReportScript() {
		return reportScript;
	}

	public void setReportScript(String reportScript) {
		this.reportScript = reportScript;
	}

	public Integer getCountRequired() {
		return countRequired;
	}

	public void setCountRequired(Integer countRequired) {
		this.countRequired = countRequired;
	}

	public Integer getStoreReplicaData() {
		return storeReplicaData;
	}

	public void setStoreReplicaData(Integer storeReplicaData) {
		this.storeReplicaData = storeReplicaData;
	}

	public Integer getExportCurrPage() {
		return exportCurrPage;
	}

	public void setExportCurrPage(Integer exportCurrPage) {
		this.exportCurrPage = exportCurrPage;
	}

	public Integer getExportDataRender() {
		return exportDataRender;
	}

	public void setExportDataRender(Integer exportDataRender) {
		this.exportDataRender = exportDataRender;
	}
}
