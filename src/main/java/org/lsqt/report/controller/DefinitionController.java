package org.lsqt.report.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.mvc.util.FileUploadUtil;
import org.lsqt.components.util.ExceptionUtil;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.report.model.Column;
import org.lsqt.report.model.ColumnQuery;
import org.lsqt.report.model.Definition;
import org.lsqt.report.model.DefinitionQuery;
import org.lsqt.report.model.ExportTemplate;
import org.lsqt.report.model.ExportTemplateQuery;
import org.lsqt.report.service.ColumnService;
import org.lsqt.report.service.DefinitionService;
import org.lsqt.report.service.ExportTemplateService;
import org.lsqt.report.service.impl.support.SelectorData;
import org.lsqt.report.service.impl.support.SelectorDataFromJSArray;
import org.lsqt.report.service.impl.support.SelectorDataFromSQL;
import org.lsqt.report.service.impl.support.SelectorDataFromUrlHtml;
import org.lsqt.report.service.impl.support.SelectorDataFromUrlJson;
import org.lsqt.report.service.impl.support.SelectorDataFromUrlXml;
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
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Definition> queryForPage(DefinitionQuery query) throws IOException {
		return definitionService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Definition> getAll() {
		return definitionService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Definition saveOrUpdate(Definition form) {
		if (StringUtil.isNotBlank(form.getColumnSql())) {
			form.setColumnSql(form.getColumnSql().trim());
		}
		if (StringUtil.isNotBlank(form.getReportSql())) {
			form.setReportSql(form.getReportSql().trim());
		}
		return definitionService.saveOrUpdate(form);
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
	
	@RequestMapping(mapping = { "/import_column", "/m/import_column" },text="导入报表的列")
	public void importColumn(Long id,Integer dataType) {
		definitionService.importColumn(id,dataType);
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

	
	@RequestMapping(mapping = { "/search", "/m/search" }, text = "通用报表查询")
	public Object search(Long reportDefinitionId) throws Exception {
		if(reportDefinitionId == null) {
			return new Page.PageModel<>();
		}
		
		try{
			Map<String, Object> formData = ContextUtil.getFormMap();
			return definitionService.search(reportDefinitionId, formData);
		}catch(Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	@RequestMapping(mapping = { "/export", "/m/export" },text="导出报表数据")
	public void export(Long reportDefinitionId) throws Exception {
		Definition def = definitionService.getById(reportDefinitionId);
		if(def!=null) {
			ExportTemplateQuery query = new ExportTemplateQuery();
			query.setDefinitionId(reportDefinitionId);
			query.setType(ExportTemplate.TYPE_EXPORT);
			ExportTemplate model = exportTemplateService.queryForObject(query);
			
			if(model!=null) {
				HttpServletRequest request = ContextUtil.getRequest();
				String root = request.getServletContext().getRealPath("/");
				String srcFilePath =  root + model.getPath();
			 
				Page<Map<String, Object>> data = definitionService.search(reportDefinitionId, ContextUtil.getFormMap());
				Map<String,Object> ct = new HashMap<>();
				ct.put("data",data.getData());
				
				XLSTransformer transformer = new XLSTransformer();  
				
				String webFilePath="/upload/rpt_"+reportDefinitionId+".xlsx";
				String destFilePath= root + webFilePath;
				transformer.transformXLS(srcFilePath,ct,destFilePath);
				
				Util4Download.download(webFilePath, def.getName());
				
				File destFile = new File(destFilePath);
				if(destFile.exists()) {
					destFile.delete();
				}
			}
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
	
	@RequestMapping(mapping = { "/view_data", "/m/view_data" }, text = "报表数据预览")
	public Page<Map<String, Object>> viewData(Long definitionId,String serverPath) throws Exception{
		Page<Map<String, Object>> page = new Page.PageModel<>();
		
		HttpServletRequest request = ContextUtil.getRequest();

		String filePath = request.getServletContext().getRealPath("/") + serverPath ;
        log.debug("预览数据文件全路径: "+filePath);//输出存放上传文件所到的路径  
        File file = new File(filePath);  
       
		if (file.exists() &&  file.isFile()) {
			try{
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
		query.setAllowImport(Column.YES);
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
				if (!(headIndex > 0)) {
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
		
	/*	if (startRowIndex >= sheet.getLastRowNum()) { // 表格没有数据情况
			return data;
		}*/
		
		for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {
			Row row = sheet.getRow(j);
			List<CellWrap> rowData = new ArrayList<>(); // 行数据
			
			for (int k = 0; k < row.getPhysicalNumberOfCells(); k++) {
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
				}
			}
			if (ArrayUtil.isNotBlank(rowData)) {
				if (!isBlankExcelRow(rowData)) {
					data.add(rowData);
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
