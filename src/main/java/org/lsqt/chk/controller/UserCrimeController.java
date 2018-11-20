package org.lsqt.chk.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.lsqt.chk.ifc.IFCClient;
import org.lsqt.chk.ifc.model.ResultIFC;
import org.lsqt.chk.model.UserCrime;
import org.lsqt.chk.model.UserCrimeQuery;
import org.lsqt.chk.mq.UserCrimeProducer;
import org.lsqt.chk.mq.UserCrimeRequest;
import org.lsqt.chk.service.UserCrimeService;
import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.mvc.util.FileUploadUtil;
import org.lsqt.components.util.ExceptionUtil;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.report.controller.PolicyReportFileRename;
import org.lsqt.report.model.Column;
import org.lsqt.report.model.ColumnQuery;
import org.lsqt.report.model.Definition;
import org.lsqt.report.model.DefinitionQuery;
import org.lsqt.report.service.ColumnService;
import org.lsqt.report.service.DefinitionService;
import org.lsqt.sys.service.DataSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.oreilly.servlet.MultipartRequest;




@Controller(mapping={"/chk/user_crime"})
public class UserCrimeController {
	private static final Logger log = LoggerFactory.getLogger(UserCrimeController.class);
	
	private static ExecutorService  executor = Executors.newSingleThreadExecutor(); 
	@Inject private UserCrimeService userCrimeService; 
	@Inject private ColumnService columnService;
	@Inject private DataSourceService dataSourceService;
	@Inject private DefinitionService definitionService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public UserCrime getById(Long id) throws IOException {
		return userCrimeService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<UserCrime> queryForPage(UserCrimeQuery query) throws IOException {
		return userCrimeService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<UserCrime> getAll() {
		return userCrimeService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public UserCrime saveOrUpdate(UserCrime form) {
		if (form.getId() == null) {
			db.save(form);
		} else {
			db.update(form, "name","idcard");
		}
		
		startCheck(form.getId().toString());
		return form;
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return userCrimeService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/start_check", "/m/start_check" },text="发起核查")
	public int startCheck(String ids) { // 测试用 : 陈秀娟 - 321183199007030038
		int cnt = 0;
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		if (ArrayUtil.isBlank(list)) {
			return cnt;
		}
		
		for (Long id : list) {
			UserCrime model = db.getById(UserCrime.class, id);
			if (model != null) {
				
				try {
					String json = IFCClient.getIdCardInfo(model.getName(), model.getIdcard());
					ResultIFC resultIFC = JSON.parseObject(json, ResultIFC.class);
					
					model.setMatchResCode(resultIFC.code);
					model.setMatchResDesc(resultIFC.desc);
					
					if (!ResultIFC.CODE_OK.equals(resultIFC.code)) { // 异常断路!!
						model.setRemark(resultIFC.desc);
						db.update(model, "matchResCode","matchResDesc","remark");
						continue ;
					}
					
					
					if (resultIFC.result != null) {
						model.setMatchBizCode(resultIFC.result.resultCode);
						model.setMatchBizDesc(resultIFC.result.resultMsg);
						
						if (ResultIFC.Result.RESULT_CODE_MATCH_OK.equals(resultIFC.result.resultCode)) { // 匹配成功,发送消息获取是否有犯罪记录
							//获取性别和头像、签 证机关数据	
							model.setSex(resultIFC.result.sex);
							model.setPhoto(resultIFC.result.photo);
							model.setPoliceAddress(resultIFC.result.policeadd);
							
							UserCrimeRequest request = new UserCrimeRequest();
							request.business_id = id.toString(); // 发送的是主键,便于查询
							request.id_no = model.getIdcard();
							request.mobile_no = "18218148802"; // 风控平台接口，手机号要必填，我固定自己的手机号
							request.name = model.getName();
							
							String message = JSON.toJSONString(request);
							UserCrimeProducer.produce(message);
						} 
					}
					db.update(model, "matchResCode","matchResDesc","matchBizCode","matchBizDesc","sex","photo","policeAddress") ;
					
					
					cnt++;
				} catch (Throwable e) {
					model.setRemark("发起审核失败: "+e.getMessage());
					db.update(model, "remark");
					e.printStackTrace();
				}
			}
		}
		
		return cnt;
	}
	
	
	
	
	// ################################################ 报表数据导入 ##################################################3
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(mapping = { "/upload", "/m/upload" }, view=View.JSP, path="/apps/default/admin/chk/user", text="导入用户信息")
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

		
		String remark = mulit.getParameter("remark");
        System.out.println("导入记要:"+remark);  
  
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

		if(cnt == 0) { // 用户没有上传文件，个数为0
			serverPath = "";
		}
		request.setAttribute("remark", remark);
		request.setAttribute("serverPath", serverPath);
		return "/upload_user_htmlfile.jsp";
	}
	
	
	@RequestMapping(mapping = { "/execute_import", "/m/execute_import" }, text = "执行导入报表数据，存储数据到本地库，作为副本")
	public Object executeImport(Long definitionId,String definitionCode, String serverPath,String remark) throws Exception {
		if ((definitionId==null && StringUtil.isBlank(definitionCode)) || StringUtil.isBlank(serverPath)) {
			return null;
		}
		
		HttpServletRequest request = ContextUtil.getRequest();

		String filePath = request.getServletContext().getRealPath("/") + serverPath ;
        log.debug("预览数据文件全路径: "+filePath); 
        File file = new File(filePath);  
       
		if (file.exists() &&  file.isFile()) {
			final List<List<CellWrap>> data = new ArrayList<>();
			try {
				if (definitionId == null) {
					DefinitionQuery defQuery = new DefinitionQuery();
					defQuery.setCode(definitionCode);
					Definition def = db.queryForObject("queryForPage", Definition.class, defQuery);
					if (def!=null) {
						definitionId = def.getId();
					}
				}
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
			 
			
			//2.导入excel数据到业务数据源
			batchSave(db,def.getImportTable(),data);
			String batchNo = updateBatchNo(db,data,remark); // 更新批次号
			
			//3.异步发起核查
			UserCrimeQuery ucQuery = new UserCrimeQuery();
			ucQuery.setBatchNo(batchNo);
			List<UserCrime> ucList = db.queryForList("queryForPage", UserCrime.class, ucQuery);
			if (ArrayUtil.isNotBlank(ucList)) {
				List<String> ids = new ArrayList<>();
				for (UserCrime u: ucList) {
					ids.add(u.getId().toString());
				}
				
				//executor.execute(()->{
					startCheck(StringUtil.join(ids));
				//});
			}
		}
		
		return true;
	}
	
	/**
	 * 用户上传后，更新批次号为当前时间段值
	 * @param db
	 * @param data
	 * @param 导入备注
	 */
	private String updateBatchNo(Db db,List<List<CellWrap>> data,String remark) {
		String batchNo = new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date());
		if (ArrayUtil.isNotBlank(data)) {
			remark = "用户("+ContextUtil.getLoginAccount()+":"+ContextUtil.getLoginName()+")导入|备注:"+remark;
			String sql = String.format("update chk_user_crime set batch_no = '%s' , remark = ? where idcard = ? and batch_no is null ", batchNo);

			for (List<CellWrap> row : data) {
				try {
					db.executeUpdate(sql, remark, row.get(1).cellValue);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return batchNo;
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

			columText.append(" (");
			valueText.append(" (");
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
				for (CellWrap e : r) {
					paramValueList.add(e.cellValue);
				}
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
				} else { //空值单元格不要遗漏!!!
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
