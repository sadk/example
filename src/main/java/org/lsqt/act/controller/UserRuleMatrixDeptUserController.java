package org.lsqt.act.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.lsqt.act.model.UserRule;
import org.lsqt.act.model.UserRuleMatrixDeptUser;
import org.lsqt.act.model.UserRuleMatrixDeptUserQuery;
import org.lsqt.act.model.UserRuleQuery;
import org.lsqt.act.service.UserRuleMatrixDeptUserService;
import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.collection.MapUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.uum.model.Org;
import org.lsqt.syswin.uum.model.OrgQuery;
import org.lsqt.syswin.uum.model.User;
import org.lsqt.syswin.uum.model.UserQuery;
import org.lsqt.syswin.uum.service.OrgService;
import org.lsqt.syswin.uum.service.UserService;

import com.alibaba.fastjson.JSON;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;


@Controller(mapping={"/act/user_rule_matrix_dept_user"})
public class UserRuleMatrixDeptUserController {
	
	@Inject private UserRuleMatrixDeptUserService userRuleMatrixDeptUserService; 
	@Inject private UserService userService;
	@Inject private OrgService orgService; 
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<UserRuleMatrixDeptUser> queryForPage(UserRuleMatrixDeptUserQuery query) throws IOException {
		return userRuleMatrixDeptUserService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<UserRuleMatrixDeptUser> getAll() {
		return userRuleMatrixDeptUserService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public UserRuleMatrixDeptUser saveOrUpdate(UserRuleMatrixDeptUser form) {
		return userRuleMatrixDeptUserService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return userRuleMatrixDeptUserService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	// ------------------------------------------------------ 用户规则矩阵 -------------------------------------
	@RequestMapping(mapping = { "/index_ui_view", "/m/index_ui_view" },text="跳转到‘用户规则二维表格配置页’",view=View.JSP,path="/apps/default/admin/act/user_rule")
	public String indeUIView() throws Exception {
		HttpServletRequest request = ContextUtil.getRequest();
		
		UserRuleQuery query = new UserRuleQuery();
		query.setEnable(1);
		query.setSortFieldGbk("A.name");
		query.setSortOrder("asc");
		List<UserRule> data = db.queryForList("queryForPage", UserRule.class, query);
		
		request.setAttribute("columns",data);
		return "index_ui_view";
	}
	
	public static class Column {
		public String field; //单元格值字段
		public String name;
		
		public Integer width; //列宽
		public String headerAlign;
		public String align;
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(mapping = { "/save_rule_dept_ids", "/m/save_rule_dept_ids" })
	public void saveRuleDeptIds(String data) throws Exception {
		if (StringUtil.isNotBlank(data)) {
			List<Map> dataMapList = JSON.parseArray(data, Map.class);
			for (Map m : dataMapList) {
				List<String> keyList = MapUtil.toKeyList(m);
				for (String ky : keyList) {
					String[] temp = ky.split("_");

					if (ky.startsWith("userRule") && temp.length == 2) {
						UserRuleMatrixDeptUser model = new UserRuleMatrixDeptUser();
						model.setCreateDeptId(Long.valueOf(m.get("id") + ""));
						model.setUserRuleId(Long.valueOf(temp[1]));
						model.setUserIds(resovleUserIds(m.get(ky) + ""));
						//System.out.println(model.getUserIds());
						db.executeUpdate("delete from ext_user_rule_matrix_dept_user where user_rule_id=? and create_dept_id=?", model.getUserRuleId(),model.getCreateDeptId());
						db.save(model);
					}
				}
			}
			
			
		}
	}
	
	private String resovleUserIds(String text) {
		if(StringUtil.isNotBlank(text)) {
			List<String> loginNoList = new ArrayList<>();
			
			List<String> eleList = StringUtil.split(text, ",");
			for(String e: eleList) {
				int beginIndex = e.indexOf("(");
				int endIndex = e.lastIndexOf(")");
				if(beginIndex!=-1 && endIndex!=-1){
					String loginNo = e.substring(beginIndex+1, endIndex);
					if(StringUtil.isNotBlank(loginNo)){
						loginNoList.add(loginNo);
					}
				}
			}
			if(loginNoList!=null && loginNoList.size()>0) {
				UserQuery query = new UserQuery();
				query.setLoginNoList(loginNoList);
				
				List<User> userList = userService.queryForList(query);
				
				return StringUtil.join( User.toIdList(userList),",");
			}
		}
		return null;
	}
	
	@RequestMapping(mapping = { "/org/page", "/m/org/page" })
	public Page<Map<String,Object>> queryForPage(OrgQuery query) throws IOException {
		
		// 查找已启用的矩阵用户规则
		UserRuleQuery rQuery = new UserRuleQuery();
		rQuery.setEnable(UserRule.ENABLE_ON);
		List<UserRule> enableList = db.queryForList("queryForPage", UserRule.class, rQuery);
		
		
		Page<Org> page = orgService.queryForPage(query); 
		
		Page<Map<String,Object>> dataPage =new Page.PageModel<Map<String,Object>>();
		dataPage.setTotal(page.getTotal());
		dataPage.setHasNext(page.getHasNext());
		dataPage.setHasPrevious(page.getHasPrevious());
		dataPage.setPageCount(page.getPageCount());
		dataPage.setPageIndex(page.getPageIndex());
		dataPage.setPageSize(page.getPageSize());
		
		List<Map<String,Object>> dataList = new ArrayList<>();
		for (Org e:page.getData()) {
			
			Map<String,Object> map = new HashMap<>();
			map.put("id", e.getId());
			map.put("pid", e.getPid());
			map.put("name", e.getName());
			map.put("typeDesc", e.getTypeDesc());
			map.put("nodePathText", e.getNodePathText());
			
			for(UserRule r: enableList) {
				UserRuleMatrixDeptUserQuery umQuery= new UserRuleMatrixDeptUserQuery();
				umQuery.setCreateDeptId(e.getId());
				umQuery.setUserRuleId(r.getId());
				map.put("userRule_"+r.getId(), null);
				
				UserRuleMatrixDeptUser deptUser = db.queryForObject("queryForPage", UserRuleMatrixDeptUser.class, umQuery);
				if(deptUser!=null && StringUtil.isNotBlank(deptUser.getUserIds())) {
					UserQuery tempQuery = new UserQuery();
					tempQuery.setIds(deptUser.getUserIds());
					List<User> userData = userService.queryForList(tempQuery);
					if(userData!=null && !userData.isEmpty()) {
						List<String> text=new ArrayList<>();
						for (User u: userData) {
							text.add(u.getUserName()+"("+u.getLoginNo()+")");
						}
						map.put("userRule_"+r.getId(), StringUtil.join(text, ","));
					}
					
				}
				 
			}
			dataList.add(map);
		}
		dataPage.setData(dataList);
		
		return dataPage;
	}
	
	/**
	 * 生成用户规则数据.xlsx
	 * @param data
	 * @throws Exception
	 */
	private void excelGenerate(String excelPath,List<List<Object>> data) throws Exception {
		final int maxFlush = 2000; //每当行数达到设置的值就刷新数据到硬盘,以清理内存
		
		SXSSFWorkbook wb = new SXSSFWorkbook();
		Sheet sh = wb.createSheet();
		for (int i = 0; i < data.size(); i++) {
			Row row = sh.createRow(i);
			for (int idx = 0; idx < data.get(i).size(); idx++) {
				Cell cell = row.createCell(idx);
				Object value = data.get(i).get(idx);
				cell.setCellValue(value == null ? "" : value.toString());
			}
			
            
			if (i % maxFlush == 0) { 
				((SXSSFSheet) sh).flushRows();
			}
		}
		
		File outFile = new File(excelPath);
		FileOutputStream os = new FileOutputStream(outFile);
		wb.write(os);
		os.close();
		
		//return outFile;
	}
	
	@RequestMapping(mapping = { "/export_all", "/m/export_all" }, text = "导出用户规则视图Excel")
	public void exportAll() throws Exception {
		//Map<String, Object> root = new HashMap<String, Object>();
		
		List<Map<String,Object>> data = new ArrayList<>();
		
		// 查找已启用的矩阵用户规则
		UserRuleQuery rQuery = new UserRuleQuery();
		rQuery.setEnable(UserRule.ENABLE_ON);
		rQuery.setSortFieldGbk("A.name");
		rQuery.setSortOrder("asc");
		List<UserRule> enableList = db.queryForList("queryForPage", UserRule.class, rQuery);
		
		
		
		
		Collection<Org> orgList = orgService.getAll();		
		for(Org e: orgList) {
			Map<String,Object> row = new LinkedHashMap<>();
			row.put("id", e.getId());
			row.put("pid", e.getPid());
			row.put("name", e.getName());
			row.put("nodePathText", e.getNodePathText());
			
			for(UserRule r: enableList) {
				UserRuleMatrixDeptUserQuery umQuery= new UserRuleMatrixDeptUserQuery();
				umQuery.setCreateDeptId(e.getId());
				umQuery.setUserRuleId(r.getId());
				row.put("userRule_"+r.getId(), null);
				
				UserRuleMatrixDeptUser deptUser = db.queryForObject("queryForPage", UserRuleMatrixDeptUser.class, umQuery);
				if(deptUser!=null && StringUtil.isNotBlank(deptUser.getUserIds())) {
					UserQuery tempQuery = new UserQuery();
					tempQuery.setIds(deptUser.getUserIds());
					List<User> userData = userService.queryForList(tempQuery);
					if(userData!=null && !userData.isEmpty()) {
						List<String> text=new ArrayList<>();
						for (User u: userData) {
							text.add(u.getUserName()+"("+u.getLoginNo()+")");
						}
						row.put("userRule_"+r.getId(), StringUtil.join(text, ","));
					}
					
				}
				 
			}
			data.add(row);
		}
		

		List<List<Object>> rs = new ArrayList<>();
		List<Object> head = new ArrayList<>();
		head.add("ID");
		head.add("父ID");
		head.add("组织名称");
		head.add("组织节点路径");
		for(UserRule r: enableList) {
			head.add(r.getName());
		}
		rs.add(head);
		
		for (Map<String,Object> map : data) {
			rs.add(MapUtil.toValueList(map));
		}
		
		HttpServletRequest request = ContextUtil.getRequest();
		String path = request.getServletContext().getRealPath("/upload")+"/"+System.currentTimeMillis()+".xlsx";
		excelGenerate(path,rs);
		download(path);
		
		//root.put("data", data);
		//root.put("column", enableList);
		
		//String srcFilePath = "D:\\template_test.xlsx";  
		//String destFilePath = "D:\\template_instance.xlsx"; 
		
		// XLSTransformer transformer = new XLSTransformer();  
		// transformer.transformXLS(srcFilePath, root, destFilePath); 
	}
	
	public static void main(String[] args) throws Exception {
		String srcFilePath = "D:\\template.xlsx";  
		String destFilePath = "D:\\template_instance.xlsx";  
		
		Map<String, Object> root = new HashMap<String, Object>();
		List<Map<String,Object>> data = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			Map<String,Object> row = new HashMap<>();
			row.put("id", i);
			row.put("name", "name"+i);
			row.put("card", i*10);
			
			data.add(row);
		}
		root.put("data", data);
		
		XLSTransformer transformer = new XLSTransformer();  
		transformer.transformXLS(srcFilePath, root, destFilePath); 
	}

	/**
	 * 
	 * @param path 服务器上的文件路径
	 */
    private void download(String path) {  
		try {
			HttpServletResponse response = ContextUtil.getResponse();

			File file = new File(path);
			String filename = file.getName();

			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();

			response.addHeader("Content-Disposition", "attachment;filename=" + filename);
			response.addHeader("Content-Length", "" + file.length());
			response.setContentType("application/vnd.ms-excel;charset=gbk2312");
			
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			toClient.write(buffer);
			toClient.flush();
			// toClient.close(); // MVC框架处理关闭
		} catch (IOException ex) {
			ex.printStackTrace();
		}
    }  
}
