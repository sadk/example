package org.lsqt.rst.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.mvc.util.FileUploadUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.report.controller.PolicyReportFileRename;
import org.lsqt.rst.model.Company;
import org.lsqt.rst.model.CompanyPicture;
import org.lsqt.rst.model.CompanyQuery;
import org.lsqt.rst.service.CompanyService;
import org.lsqt.rst.util.AliyunOssUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oreilly.servlet.MultipartRequest;




@Controller(mapping={"/rst/company"})
public class CompanyController {
	private static final Logger log = LoggerFactory.getLogger(CompanyController.class);
	
	@Inject private CompanyService companyService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public Company getById(Long id) throws IOException {
		return companyService.getById(id);
	}
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public List<Company> queryForList(CompanyQuery query) throws IOException {
		query.setTenantCode(ContextUtil.getLoginTenantCode());
		return companyService.queryForList(query);
	}
	
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Company> queryForPage(CompanyQuery query) throws IOException {
		query.setTenantCode(ContextUtil.getLoginTenantCode());
		return companyService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Company> getAll() {
		return companyService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Company saveOrUpdate(Company form) {
		form.setTenantCode(ContextUtil.getLoginTenantCode());
		return companyService.saveOrUpdate(form);
		
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return companyService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(mapping = { "/upload", "/m/upload" }, view=View.JSP, path="/apps/default/admin/rst/company",excludeTransaction=true, text="上传公司图片")
	public String upload() throws Exception{
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

		String companyCode = mulit.getParameter("companyCode");  
		request.setAttribute("companyCode", companyCode);
		
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
		
		request.setAttribute("serverPath", serverPath);
		
		
		
		String fullPath = request.getServletContext().getRealPath("/") + serverPath;
		System.out.println("全路径:" + fullPath);
		
		String aliFile = "";
		if (StringUtil.isNotBlank(serverPath)) {
			aliFile = serverPath.substring(serverPath.lastIndexOf("/"),serverPath.length());
			
			String httpPath = UploadConfigerOSS.uploadAliyunOSS("qdb/company/"+System.currentTimeMillis()+aliFile,fullPath);
			request.setAttribute("serverPath", httpPath);
			System.out.println(httpPath);
		}
		
		return "/upload_img_htmlfile.jsp";
	}
	
	

  
	@RequestMapping(mapping = { "/save_company_picture", "/m/save_company_picture" },text="保存公司图片")
	public CompanyPicture saveCompanyPicture(String companyCode, String serverPath) {
		if (StringUtil.isNotBlank(companyCode,serverPath)) {
			CompanyPicture cp = new CompanyPicture();
			cp.setCompanyCode(companyCode);
			cp.setUrl(serverPath);
			cp.setTenantCode(ContextUtil.getLoginTenantCode());
			db.saveOrUpdate(cp);
			return cp;
		}
		return null;
	}
}
