package org.lsqt.report.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.Result;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.DbException;
import org.lsqt.components.db.Page;
import org.lsqt.components.mvc.util.FileUploadUtil;
import org.lsqt.components.util.ExceptionUtil;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.collection.MapUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.report.model.Column;
import org.lsqt.report.model.ColumnQuery;
import org.lsqt.report.model.Definition;
import org.lsqt.report.model.DefinitionQuery;
import org.lsqt.report.model.ExportTemplate;
import org.lsqt.report.model.ExportTemplateQuery;
import org.lsqt.report.model.Variable;
import org.lsqt.report.service.ColumnService;
import org.lsqt.report.service.DefinitionService;
import org.lsqt.report.service.ExportTemplateService;
import org.lsqt.report.service.impl.support.SelectorData;
import org.lsqt.report.service.impl.support.SelectorDataFromJSArray;
import org.lsqt.report.service.impl.support.SelectorDataFromSQL;
import org.lsqt.report.service.impl.support.SelectorDataFromUrlHtml;
import org.lsqt.report.service.impl.support.SelectorDataFromUrlJson;
import org.lsqt.report.service.impl.support.SelectorDataFromUrlXml;
import org.lsqt.sys.model.DataSource;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.sys.model.DictionaryQuery;
import org.lsqt.sys.service.DataSourceService;
import org.lsqt.sys.service.impl.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.oreilly.servlet.MultipartRequest;

import net.sf.jxls.transformer.XLSTransformer;


@Controller(mapping={"/report/definition"})
public class DefinitionController {
	private static final Logger log = LoggerFactory.getLogger(DefinitionController.class);
	
	@Inject private DefinitionService definitionService; 
	@Inject private ExportTemplateService exportTemplateService;
	@Inject private ColumnService columnService;
	@Inject private DataSourceService dataSourceService;
	@Inject private Db db;
	
	
	@RequestMapping(mapping = { "/page", "/m/page" ,"/ipad"})
	public Page<Definition> queryForPage(DefinitionQuery query) throws IOException {
		return definitionService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Definition> getAll() {
		return definitionService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Definition saveOrUpdate(Definition form,
			Long variableId,String variableCode, String variableType, String variableValue) {
		
		if (StringUtil.isNotBlank(form.getColumnSql())) {
			form.setColumnSql(form.getColumnSql().trim());
		}
		if (StringUtil.isNotBlank(form.getReportSql())) {
			form.setReportSql(form.getReportSql().trim());
		}
		
		form = definitionService.saveOrUpdate(form);
		
		if (StringUtil.isNotBlank(variableCode, variableType)) {
			Variable var = new Variable();
			var.setId(variableId);
			var.setCode(variableCode);
			var.setType(Integer.valueOf(variableType));
			var.setValue(variableValue);
			var.setDefinitionId(form.getId());
			db.saveOrUpdate(var);
		}
		return form;
	}
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public Definition getById(Long id) {
		return definitionService.getById(id);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return definitionService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	/**
	 * @param id 报表定义Id
	 * @param dataType 数据类型 1=报表展示列 2=数据导入列
	 * @param isIncremental 是否是增量导入
	 */
	@RequestMapping(mapping = { "/import_column", "/m/import_column" }, text = "导入报表的列")
	public void importColumn(Long id, Integer dataType, Boolean isIncremental) {
		definitionService.importColumn(id, dataType, isIncremental);
	}
	
	
	/**
	 * 
	 * @param selectorDataFromType 数据来源类型： 0=URL(页面) 1=URL(返回JSON) 2=URL(返回XML) 3=代码片断(JavaScript)数组  4=SQL
	 * @param dataSourceCode 数据来源对应的具体数据源编码（如果是SQL的话）
	 * @param selectorDataFrom 选择器数据来源 (SQL、HTTPJSON Url、XML等)
	 */
	@RequestMapping(mapping = { "/display_column", "/m/display_column" },text="当字段为选择器数据来源于SQL的时候，定义显示的字段与值字段，如：性别=sex=0或1")
	public Page<Map<String,Object>> displayColumn(String selectorDataFromType,String selectorDataSourceCode,String selectorDataFrom) {
		Map<String, SelectorData<Map<String, Object>>> handlerMap = new HashMap<>();

		SelectorData<Map<String, Object>> sql = new SelectorDataFromSQL(db, selectorDataSourceCode, selectorDataFrom);
		SelectorData<Map<String, Object>> jsArray = new SelectorDataFromJSArray();
		SelectorData<Map<String, Object>> urlHtml = new SelectorDataFromUrlHtml();
		SelectorData<Map<String, Object>> urlJson = new SelectorDataFromUrlJson(selectorDataFrom);
		SelectorData<Map<String, Object>> urlXml = new SelectorDataFromUrlXml();

		handlerMap.put("0", urlHtml);
		handlerMap.put("1", urlJson);
		handlerMap.put("2", urlXml);
		handlerMap.put("3", jsArray);
		handlerMap.put("4", sql);

		return handlerMap.get(selectorDataFromType).getData();
	}
	
	@RequestMapping(mapping = { "/generate_report_file", "/m/generate_report_file" },text="生成报表文件,读取报表配置，利用freemark生成jsp文件")
	public void generateReportFile(Long id) throws Exception {
		definitionService.generateReportFile(id);
	}

	
	@RequestMapping(mapping = { "/search", "/m/search" }, text = "通用报表查询",isTransaction = false) //查询不需要开启事务
	public Object search(Long reportDefinitionId) throws Exception {
		if(reportDefinitionId == null) {
			return new Page.PageModel<>();
		}
		
		try{
			Map<String, Object> formData = ContextUtil.getFormMap();
			return definitionService.search(reportDefinitionId, formData);
		}catch(Exception e) {
			String msg = ExceptionUtil.getStackTrace(e);
			log.error(msg);
			
			Page<?> page = new Page.PageModel<>();
			page.setHook("出错啦："+msg);
			
			return page;
		}
	}
	
	@SuppressWarnings("unchecked")
	private Page<Map<String,Object>> searchHttpJSON(Definition model,Map<String,Object> formMap) throws Exception {
		Page<Map<String,Object>> page = new Page.PageModel<>();
		
		String paramJSON =  JSON.toJSONString(formMap,true);
		log.info("报表请求参数: {}",paramJSON);
		
		String json = org.lsqt.biz.util.HttpClient.post(model.getReportSql(),paramJSON);
		log.info("报表：{} 报表id={}，返回：{}", model.getName(), model.getId(), json);
		
		Map<String, Object> rs = JSON.parseObject(json, Map.class);
		if (rs != null && rs.containsKey("data")) {
			Object data = rs.get("data");
			if(data instanceof List) {
				List<Map<String,Object>> list = (List<Map<String,Object>>)data;
				page.setData(list);
			}
			
			if(String.class.isAssignableFrom(data.getClass())) { // 服务器异常，data有可能返回字符说明(而不是list数据!!!)
				page.setHook(data.toString());
			}
		} else {
			page.setHook(json);
		}
		return page;
	}
	
	/**
	 * <pre>
	 * 导出Excel时，字典数据翻译成中文
	 * 注意：只有导出Excel的时候才需要翻译字典!!!!
	 * </pre>
	 * @param def
	 * @param data
	 */
	private void translateDictionaryExportedExcel(Definition def,List<Map<String,Object>> data) {
		Map<String /*id_java字段名*/,List<Dictionary>> dictColumnMap = new LinkedHashMap<>(); //当前报表所有的字典字典字段
		
		for (Column col : def.getColumnList()) {
			if(col.getColumnCodegenType()!=null 
					&& Column.COLUMN_CODEGEN_TYPE_下拉框_字典 == col.getColumnCodegenType()) {
				
				DictionaryQuery query = new DictionaryQuery();
				query.setParentCode(col.getSelectorDataFrom());
				List<Dictionary> dictData = db.queryForList("queryForPage", Dictionary.class, query);
				dictColumnMap.put(col.getId()+"_"+col.getPropertyName(), dictData);
			}
		}
		

		for (Map<String, Object> row : data) {
			Set<Entry<String, Object>> set = row.entrySet();
			for (Entry<String, Object> e : set) {
				String key = e.getKey();
				Object value = e.getValue();

				for (Column col : def.getColumnList()) {
					if (e.getKey().equals(col.getPropertyName())) { // 确定字段
						List<Dictionary> dictData = dictColumnMap.get(col.getId() + "_" + col.getPropertyName());
						if(dictData!=null) {
							for (Dictionary d : dictData) {
								if (d.getValue().equals(value + "")) {
									row.put(key, d.getName());
								}
							}
						}
						break;
					}
				}
			}
		}
	}
	
	@RequestMapping(mapping = { "/export", "/m/export" },text="导出报表数据")
	public void export(Long reportDefinitionId) throws Exception {
		HttpServletRequest request = ContextUtil.getRequest();
		final String root = request.getServletContext().getRealPath("/");
		
		Definition def = definitionService.getById(reportDefinitionId);
		
		if(def == null) {
			return ;//Result.fail(String.format("没有找到id=%s的报表定义", reportDefinitionId));
		}
		
		if (def.getCanExport() == null || def.getCanExport() == Dictionary.NO) {
			return ;//Result.fail(String.format("没有设置可以导出,报表定义id=%s", reportDefinitionId));
		}

		if (def.getExportCurrPage() == null) {
			return ;//Result.fail(String.format("没有设置导出所有页或当前页,报表定义id=%s", reportDefinitionId));
		}
		
		if (def.getExportCurrPage() == null) {
			return ;//Result.fail(String.format("没有设置数据导出渲染方式,报表定义id=%s", reportDefinitionId));
		}
		
		//加载报表的所有列
		ColumnQuery colQuery = new ColumnQuery();
		colQuery.setDefinitionId(def.getId());
		List<org.lsqt.report.model.Column> list = db.queryForList("queryForPage", org.lsqt.report.model.Column.class, colQuery); 
		def.setColumnList(list);
		
		
		//查询不导出的列，用于将字段数据置为null
		ColumnQuery columnQuery = new ColumnQuery();
		columnQuery.setDefinitionId(reportDefinitionId);
		columnQuery.setDataType(Column.DATA_TYPE_REPORT_SHOW);
		columnQuery.setAllowExport(Dictionary.NO);
		List<Column> notAllowColumn = columnService.queryForList(columnQuery);
		
		
		//获取报表数据源,并实例化
		DataSource dsModel = db.getById(DataSource.class, def.getDatasourceId());
		DataSourceFactory dbfactory = new DataSourceFactory();
		dbfactory.setBaseDb(db);
		javax.sql.DataSource ds = dbfactory.getDataSouce(dsModel.getCode());//报表数据源实例
		
		
		if (def.getExportDataRender() == Definition.EXPORT_DATA_RENDER_按导出模板渲染) {
			
			// 获取导出模板 
			ExportTemplateQuery query = new ExportTemplateQuery();
			query.setDefinitionId(reportDefinitionId);
			query.setType(ExportTemplate.TYPE_EXPORT);
			ExportTemplate model = exportTemplateService.queryForObject(query);
			
			if (model == null) {
				return ;//Result.fail(String.format("没有上传导出模板,报表定义id=%s", reportDefinitionId));
			}
			
			
			
			List<Map<String,Object>> excelData = new ArrayList<>();
			if (Definition.TYPE_HTTP_JSON.equals(def.getType())) {
				Page<Map<String, Object>> page = searchHttpJSON(def, ContextUtil.getFormMap());
				excelData = new ArrayList<>(page.getData());
			}
			else if(Definition.TYPE_SQL.equals(def.getType())) {
				excelData = definitionService.getDataFromDbByLoopPage(def, ds);
				//if (def.getExportMode()!=null && def.getExportMode() == Definition.EXPORT_OR_IMPORT_MODE_SELECTED_COLUMN) { //数据导出模式:默认全部导出、
					
					if (ArrayUtil.isNotBlank(notAllowColumn)) {
						for (Map<String, Object> row : excelData) {
							for (Column c : notAllowColumn) {
								row.put(c.getCode(), null);
							}
						}
					}
				//}
			}
			
			translateDictionaryExportedExcel(def,excelData);
			Map<String,Object> ct = new HashMap<>();
			ct.put("data",excelData); //查询不导出的列，将字段数据置为null
			
			String uploadDir = "/upload/report/data";
			String dir = root +uploadDir ;
			File dirF = new File(dir);
			if(!dirF.exists()) {
				dirF.mkdirs();
			}
			
			String fileName = "/report_"+reportDefinitionId+"_"+ContextUtil.getLoginId()+".xlsx";
			String destFilePath = root +  uploadDir + fileName;
			String srcFilePath =  root + model.getPath(); //源模板路径
			
			XLSTransformer transformer = new XLSTransformer();  
			transformer.transformXLS(srcFilePath,ct,destFilePath); //直接模板渲染导出
		
			Util4Download.download(uploadDir+fileName, def.getName());
			
			File destFile = new File(destFilePath);
			if(destFile.exists()) {
				destFile.delete();
			}
			return ;
		}

		
		if (def.getExportDataRender() == Definition.EXPORT_DATA_RENDER_数据自动渲染) {
			
			String uploadDir = "/upload/report/data";
			String dir = root +uploadDir ;
			File dirF = new File(dir);
			if(!dirF.exists()) {
				dirF.mkdirs();
			}
			
			String fileName = "/rpt_"+reportDefinitionId+"_"+ContextUtil.getLoginId()+"_"+System.currentTimeMillis()+".xlsx";
			String destFilePath = root + uploadDir + fileName;
			
			
			Workbook wb = new SXSSFWorkbook(1000); // keep 100 rows in memory,  exceeding rows will be flushed to disk 
			Sheet sh = wb.createSheet();

			if (Definition.TYPE_HTTP_JSON.equals(def.getType())) {
				Page<Map<String, Object>> page = searchHttpJSON(def, ContextUtil.getFormMap());
				// 在这里渲染excel列与数据列对这套 to be continue ...
			}
			else if(Definition.TYPE_SQL.equals(def.getType())) {
				definitionService.getDataFromDbByLoopPage(def, ds, (currPageIndex, currPageSize, currPageData) -> {
					
					if (ArrayUtil.isNotBlank(notAllowColumn)) { // 不允许导出的列，不导出数据
						for (Map<String, Object> row : currPageData) {
							for (Column c : notAllowColumn) {
								row.remove(c.getCode());
							}
						}
					}
					
					if (currPageIndex == 0 && (currPageData != null && currPageData.size() > 0)) { // 处理表头
						Map<String, Object> dataHead= new  LinkedHashMap<>();
						
						List<String> keyList = MapUtil.toKeyList(currPageData.get(0));
						for (String key: keyList) {
							boolean isExists = false;
							for (Column c: def.getColumnList()) {
								if(key.equals(c.getCode())) {
								 
									dataHead.put(c.getCode(), c.getName());
									 
									isExists = true;
									break;
								}
							}
							
							if(!isExists) {
								dataHead.put(key,null);
							}
						}
						currPageData.add(0,dataHead);
					}
					
					for (int rownum = 0; rownum < currPageData.size(); rownum++) {
						int idx = rownum + (currPageIndex * currPageSize);
						if (currPageIndex>0) {
							idx+=1;
						}
						Row row = sh.createRow(idx); //物理行号
	
						Map<String, Object> rowMap = currPageData.get(rownum);
						List<String> keyList = MapUtil.toKeyList(rowMap);
	
						for (int i = 0; i < keyList.size(); i++) {
							Cell cell = row.createCell(i);
							Object value = rowMap.get(keyList.get(i));
							cell.setCellValue(value == null ? "" : value.toString());
						}
					}
				});
			}
			FileOutputStream out = new FileOutputStream(destFilePath); 
			wb.write(out); 
			out.close();
			
			Util4Download.download(uploadDir+fileName, def.getName());
			return ;
		}
	}


	
	
	
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(mapping = { "/upload", "/m/upload" }, text="报表数据上传")
	public String uplodad() throws Exception{
		String serverPath = "";
		String uploadDir = FileUploadUtil.UPLOAD_DIR;
		HttpServletRequest request = ContextUtil.getRequest();

		String filePath = request.getServletContext().getRealPath("/") + uploadDir;
        System.out.println(filePath);//输出存放上传文件所到的路径  
        File uploadPath = new File(filePath);  
       
		if (!uploadPath.exists()) {
			uploadPath.mkdir();
		}
		
		int fileSize = 100;//文件最大允许100M,注意，有时候Nginx需要同步配置!!!

		MultipartRequest mulit = new MultipartRequest(request, filePath, fileSize * 1024 * 1024, "UTF-8",new PolicyReportFileRename());

		
		String definitionId = mulit.getParameter("definitionId");  
        System.out.println("模板ID:"+definitionId);  
  
        int cnt = 0;
		Enumeration filesname = mulit.getFileNames();// 取得上传的所有文件(相当于标识)
		while (filesname.hasMoreElements()) {
			String id = (String) filesname.nextElement();// 标识
			String fileName = mulit.getFilesystemName(id); // 取得文件名
			String contentType = mulit.getContentType(id);// 文件类型
			String originalFileName = mulit.getOriginalFileName(id); //原始文件名
			if (fileName != null) {
				cnt++;
			}
			System.out.println("文件名：" + fileName + " \n\r文件类型： " + contentType+" \n\r原始文件名："+originalFileName);
			serverPath =uploadDir+"/"+fileName;
		}
		System.out.println("共上传" + cnt + "个文件！");
		
		return JSON.toJSONString(serverPath);
	}
	
	@RequestMapping(mapping = { "/execute_import", "/m/execute_import" }, text = "执行导入报表数据，存储数据到本地库，作为副本")
	public Object executeImport(Long definitionId, String serverPath,String remark) throws Exception {
		if(definitionId==null || StringUtil.isBlank(serverPath)) {
			return null;
		}
		log.info("扫表执行志入,登陆用户为: {},导入记要: {}",ContextUtil.getLoginAccount(),remark);
		
		HttpServletRequest request = ContextUtil.getRequest();

		String filePath = request.getServletContext().getRealPath("/") + serverPath ;
        log.debug("预览数据文件全路径: "+filePath); 
        File file = new File(filePath);  
       
		if (file.exists() &&  file.isFile()) {
			final List<List<CellWrap>> data = new ArrayList<>();
			try {
				List<List<CellWrap>> fileData = resolveImportedFileData(definitionId, file.getAbsolutePath());
				data.addAll(fileData);
				
				// 去除表头
				if (ArrayUtil.isNotBlank(data)) {
					data.remove(0);
				} else {
					return null;
				}
			} catch (Exception ex) {
				throw ex;
			}
			
			
				
			//1.导入到数据副本的数据源，如果没有表则新建表
			Definition def = definitionService.getById(definitionId);
			final String tableName = "rtp_data_"+definitionId;
			
			if (def != null 
					&& (def.getStoreReplicaData() != null && def.getStoreReplicaData() == Dictionary.YES)
					&& def.getDataReplicaDataSourceId() != null) {
				ColumnQuery query = new ColumnQuery();
				query.setDataType(Column.DATA_TYPE_IMPORT);
				query.setDefinitionId(definitionId);
				query.setAllowImport(Dictionary.YES);
				final List<Column> columnList = columnService.queryForList(query);
				
				if (ArrayUtil.isBlank(columnList)) return null;
				
				DataSource model = dataSourceService.getById(def.getDataReplicaDataSourceId());

				//切换数据源!!!
				DataSourceFactory dbfactory = new DataSourceFactory();
				dbfactory.setBaseDb(db);
				javax.sql.DataSource ds = dbfactory.getDataSouce(model.getCode());

				
				
				Connection con = db.getCurrentConnection();
				try {
					Connection switchConn = ds.getConnection();
					db.setCurrentConnection(switchConn);
					db.executePlan(() -> {
						
						String sql = "SELECT count(0) cnt FROM information_schema.TABLES WHERE TABLE_NAME =? and TABLE_SCHEMA=?" ;
						Integer cnt = db.executeQueryForObject(sql, Integer.class, tableName,model.getLoginDefaultDb());
						if (cnt ==null || cnt == 0) {
							StringBuilder createTable = new StringBuilder();
							createTable.append("CREATE TABLE " + tableName + " ( id_ bigint(20) NOT NULL AUTO_INCREMENT primary key,remark_ varchar(200) DEFAULT NULL,");
							
							for (int i=0;i<columnList.size();i++) {
								Column e = columnList.get(i);
								
								createTable.append(e.getCode() + " " + e.getDbType());
								
								if (def.getDataReplicaStoragePrecision()!=null 
										&& Definition.DATA_REPLICA_STROE_PRECISION_ALL_STRING == def.getDataReplicaStoragePrecision()) {
									//createTable.append(" varchar(200) ");
								} else {
									if (StringUtil.isNotBlank(e.getDbTypeLength())) {
										createTable.append(" (" + e.getDbTypeLength() + ") ");
									}
								}
								
								//if (e.getImportRequired()!=null && Column.YES == e.getImportRequired()) {
								//	createTable.append(" NOT NULL ");
								//} else {
									createTable.append(" DEFAULT NULL ");
								//}
								
								createTable.append(" comment '"+e.getComment()+"' ");
								if (i!=columnList.size()-1) {
									createTable.append(" , ");
								}
							}
							createTable.append(" ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='"+ def.getName() +"' ");
							db.executeUpdate(createTable.toString());
						}
						
						//导入数据到副本数据库
						batchSave(db,tableName,data);
					});
				}catch(Exception e) {
					throw e;
				}finally{
					db.setCurrentConnection(con);
				}
			}
			
			
			//2.导入excel数据到业务数据源
			if (def != null && def.getImportDatasourceId() != null) {
				DataSource targetDS = db.getById(DataSource.class, def.getImportDatasourceId());
				DataSourceFactory dbfactory = new DataSourceFactory();
				dbfactory.setBaseDb(db);
				javax.sql.DataSource ds = dbfactory.getDataSouce(targetDS.getCode());
				Connection con = db.getCurrentConnection();
				try {
					Connection switchConn = ds.getConnection();
					db.setCurrentConnection(switchConn);
					db.executePlan(() -> {
						batchSave(db,def.getImportTable(),data);
					});
				} catch (Exception e) {
					throw e;
				} finally {
					db.setCurrentConnection(con);
				}
			}
		}
		
		return true;
	}
	
	/**
	 * 保存数据到给定DB
	 * 
	 * @param db 给定的DB实例
	 * @param data Excel数据
	 */
	private void batchSave(Db db, String tableName, List<List<CellWrap>> data) {
		if (ArrayUtil.isNotBlank(data)) {
			List<CellWrap> row = data.get(0);

			StringBuilder sql = new StringBuilder("insert into " + tableName);

			StringBuilder columText = new StringBuilder();
			StringBuilder valueText = new StringBuilder();

			columText.append(" ( ");
			valueText.append(" ( ");
			for (int i = 0; i < row.size(); i++) {
				columText.append(row.get(i).columnConfig.getCode());
				valueText.append("?");
				if (i != row.size() - 1) {
					columText.append(",");
					valueText.append(",");
				}
			}
			columText.append(" ) ");
			valueText.append(" ) ");

			sql.append(columText).append(" values ").append(valueText);

			log.debug(sql.toString());

			List<Object> paramValueList = new ArrayList<>();
			for (List<CellWrap> r : data) {
				List<Object> args = new ArrayList<>();
				
				for (CellWrap e : r) {
					paramValueList.add(e.cellValue);

					args.add(e.cellValue);
				}
				
				System.out.println(args.toString());
			}
			db.batchUpdate(sql.toString(), paramValueList.toArray());
		}
	}
	
	/**
	 * 按报表定义id或编码预览数据 (优先使用报表定义ID)
	 * @param definitionId 报表定义Id
	 * @param definitionCode 或报表定义编码
	 * @param serverPath
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(mapping = { "/view_data", "/m/view_data" }, text = "报表数据预览")
	public Page<Map<String, Object>> viewData(Long definitionId,String definitionCode,String serverPath) throws Exception{
		Page<Map<String, Object>> page = new Page.PageModel<>();
		
		HttpServletRequest request = ContextUtil.getRequest();

		String filePath = request.getServletContext().getRealPath("/") + serverPath ;
        log.debug("预览数据文件全路径: "+filePath);//输出存放上传文件所到的路径  
        File file = new File(filePath);  
       
		if (file.exists() &&  file.isFile()) {
			try{
				if (definitionId == null) {
					DefinitionQuery defQuery = new DefinitionQuery();
					defQuery.setCode(definitionCode);
					Definition def = db.queryForObject("queryForPage", Definition.class, defQuery);
					if (def!=null) {
						definitionId = def.getId();
					}
				}
				List<List<CellWrap>> data = resolveImportedFileData(definitionId,file.getAbsolutePath());
				
				// 解析头字段
				if (ArrayUtil.isNotBlank(data)) {
					List<CellWrap> head = data.get(0);
					page.setHook(head);
					
					data.remove(0);
				}
				page.setData(convertData(data));
			}catch(Exception ex) {
				page.setHook(ExceptionUtil.getStackTrace(ex));
				throw ex;
			}
		}
		return page;
	}
	
	private List<Map<String, Object>> convertData(List<List<CellWrap>> data) {
		List<Map<String, Object>> rs = new ArrayList<>();
		if (ArrayUtil.isBlank(data)) {
			return rs;
		}

		for (List<CellWrap> row : data) {
			Map<String, Object> rowMap = new LinkedHashMap<>();
			for (CellWrap e : row) {
				rowMap.put(e.columnConfig.getPropertyName(), e.cellValue);
			}
			rs.add(rowMap);
		}
		return rs;
	}
	
	/**
	 * 解析模板文件数据 (注：第一行为表头)
	 * 
	 * @param definitionId  模板文件定义ID
	 * @param fullPath  文件在服务器端的全路径
	 * 
	 */
	private List<List<CellWrap>> resolveImportedFileData(Long definitionId, String fullPath) throws Exception {
		if (fullPath.endsWith(".xls")) {
			throw new UnsupportedOperationException("不支持此格式文件，请用更高级excel版本");
		}
		List<List<CellWrap>> data = new ArrayList<>();
		
		// 检查表头定义是否在同一行，如果是xls文件，使用fastExcel组件解析；如果是xlsx用poi解析!!
		ColumnQuery query = new ColumnQuery();
		query.setDefinitionId(definitionId);
		query.setDataType(Column.DATA_TYPE_IMPORT);
		query.setAllowImport(Dictionary.YES);
		List<Column> list = columnService.queryForList(query);
		if (ArrayUtil.isNotBlank(list)) {
			List<Column> headList = new ArrayList<>();
			for(Column ele: list) {
				if (StringUtil.isNotBlank(ele.getCoordinate())) {
					headList.add(ele);
				}
			}
			list = headList;
			
			Workbook wb = WorkbookFactory.create(new File(fullPath));

			int sn = wb.getNumberOfSheets();
			if (sn == 0) {
				throw new NullPointerException("Excel文件内没有sheet表");
			}

			Sheet sheet = wb.getSheetAt(0);
			if (sheet.getLastRowNum() == 0) {
				throw new NullPointerException("Sheet表内没有数据");
			}

			Set<String> rowIndexs = new HashSet<>();
			for (Column e : list) {
				CellReference cr = new CellReference(e.getCoordinate());
				rowIndexs.add(cr.getRow() + "");
			}
			
			if (rowIndexs.size() > 1) {
				throw new UnsupportedOperationException("检测到表头定义不在同一个数据行");
			} else if (rowIndexs.size() == 1) {
				int headIndex = Integer.valueOf(new ArrayList<>(rowIndexs).get(0)); // 表头所在的行索引
				if (!(headIndex >= 0)) {
					throw new UnsupportedOperationException("没有检测到表头定义");
				}
				List<List<CellWrap>> excelData = getSheetData(sheet, list);
				return excelData;
			} else {
				throw new UnsupportedOperationException("没有检测到表头定义");
			}
			 
		}
		return data;
	}
	
	
	
	/**
	 * 解析报表数据
	 * @param sheet 报表数据所在的sheet
	 * @param columnList 报表头在线的定义
	 * @return 报表结构化的数据
	 */
	private List<List<CellWrap>> getSheetData(Sheet sheet,List<Column> columnList) {
		List<List<CellWrap>> data = new ArrayList<>();
		
		for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) { // getPhysicalNumberOfRows此方法，excel里有空行的时候，
			Row row = sheet.getRow(j);
			
			List<CellWrap> rowData = new ArrayList<>(); // 行数据
			
			for (int k = 0; k < row.getLastCellNum(); k++) { //row.getPhysicalNumberOfCells();==>注意踩坑!!!!,看英文注释：Gets the number of defined cells (NOT number of cells in the actual row!). That is to say if only columns 0,4,5 have values then there would be 3.
				Cell cel = row.getCell(k);
				if (cel != null) {
					if (cel instanceof XSSFCell) {
						XSSFCell cell = (XSSFCell) cel;

						for (Column e : columnList) {
							CellReference cr = new CellReference(e.getCoordinate());
							if (cr.getCol() == cell.getColumnIndex()) {
								CellWrap cellWrap = new CellWrap();
								cellWrap.cellCoordinate = cell.getReference();
								cellWrap.cellRowIndex = cell.getRowIndex();
								cellWrap.cellColumnIndex = cell.getColumnIndex();
								cellWrap.cellValue = getCellValue(cell);
								cellWrap.columnConfig = e;
								rowData.add(cellWrap);
								break;
							}
						}
					} else {
						throw new UnsupportedOperationException("不支持的单元格实例");
					}
				} else { //数据块中间的空值单元格不要遗漏!!!
					for (Column e : columnList) {
						CellReference cr = new CellReference(e.getCoordinate());
						
						if (cr.getCol() == k) {
							CellWrap cellWrap = new CellWrap();
							cellWrap.cellCoordinate = e.getCoordinate();
							cellWrap.cellRowIndex = j;
							cellWrap.cellColumnIndex = k;
							cellWrap.cellValue = null;
							cellWrap.columnConfig = e;
							rowData.add(cellWrap);
							break;
						}
						
					}
				}
			}
			if (ArrayUtil.isNotBlank(rowData)) {
				if (!isBlankExcelRow(rowData)) {
					data.add(rowData);
				}
			}
			
			if (ArrayUtil.isNotBlank(rowData) && rowData.size() < columnList.size()) { // bug Fix: 补全，假有10列数据，前3列有数据，后7列是null的单元格(用于补全SQL参数)
				for (int i = rowData.size(); i < columnList.size(); i++) {
					CellWrap cellWrap = new CellWrap();
					//cellWrap.cellCoordinate = null;
					cellWrap.cellRowIndex = j;
					cellWrap.cellColumnIndex = i;
					cellWrap.cellValue = null;
					//cellWrap.columnConfig = null;
					
					
					for (Column e: columnList) {
						CellReference cr = new CellReference(e.getCoordinate());
						
						if(cr.getCol() == i) {
							cellWrap.columnConfig = e;
							cellWrap.cellCoordinate = e.getCoordinate();
							break;
						}
					}
					if (cellWrap.columnConfig != null) {
						rowData.add(cellWrap);
					}
				}
			}
		}
		return data;
	}
	
	/**
	 * 判断是否是Excel空行数据
	 * @param row
	 * @return
	 */
	private boolean isBlankExcelRow(List<CellWrap> row) {
		for (CellWrap e : row) {
			if (e.cellValue != null && StringUtil.isNotBlank(e.cellValue.toString())) {
				return false;
			}
		}
		return true;
	}
	
	private Object getCellValue(XSSFCell cell) { 
        Object cellValue = null;      
        switch (cell.getCellType()) {
        case XSSFCell.CELL_TYPE_STRING :
        	cellValue = cell.getRichStringCellValue().getString();
            break;
        case XSSFCell.CELL_TYPE_NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
            	cellValue= cell.getDateCellValue();
            } else {
            	cellValue = cell.getNumericCellValue();
            }
            break;
        case XSSFCell.CELL_TYPE_BOOLEAN:
        	cellValue =cell.getBooleanCellValue();
            break;
        case XSSFCell.CELL_TYPE_FORMULA:
        	cellValue = cell.getCellFormula();
            break;
        case XSSFCell.CELL_TYPE_BLANK:
        	cellValue = "";
            break;
        default:
            cellValue = "";
    }
        return cellValue;      
    }     
	
	public static class CellWrap {
		public String cellCoordinate; //单元格坐标
		public int cellRowIndex; //单元格行索引
		public int cellColumnIndex; //单元格列索引
		public Object cellValue; //单元格的值
		
		public Column columnConfig; // 单元格所在的列（字段）配置
 
	}
}
