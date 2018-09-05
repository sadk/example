package org.lsqt.report.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.report.model.ExportTemplate;
import org.lsqt.report.model.ExportTemplateQuery;
import org.lsqt.report.service.ExportTemplateService;

import com.alibaba.fastjson.JSON;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;


@Controller(mapping={"/report/export_template"})
public class ExportTemplateController {
	
	@Inject private ExportTemplateService exportTemplateService; 
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public ExportTemplate getById(Long id)  {
		return exportTemplateService.getById(id) ;
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<ExportTemplate> queryForPage(ExportTemplateQuery query)  {
		return exportTemplateService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<ExportTemplate> getAll() {
		return exportTemplateService.getAll();
	}
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public Collection<ExportTemplate> queryForList(ExportTemplateQuery query) {
		return exportTemplateService.queryForList(query);
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public ExportTemplate saveOrUpdate(ExportTemplate form) {
		return exportTemplateService.saveOrUpdate(form);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(mapping = { "/save_or_update_json", "/m/save_or_update_json" })
	public List<ExportTemplate> saveOrUpdate(String data) {
		if (StringUtil.isNotBlank(data)) {
			List<ExportTemplate> models = JSON.parseArray(data, ExportTemplate.class);
			exportTemplateService.saveOrUpdate(models);
			return models;
		}
		return ArrayUtil.EMPTY_LIST;
	}

	@RequestMapping(mapping = { "/delete_by_ids", "/m/delete_by_ids" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return exportTemplateService.deleteById(list.toArray(new Long[list.size()]));
	}
	 
	@SuppressWarnings("rawtypes")
	@RequestMapping(mapping = { "/upload", "/m/upload" },view = View.JSON)
	public String uplodad() throws Exception{
		String serverPath = "";
		String uploadDir = "/upload";
		HttpServletRequest request = ContextUtil.getRequest();

		String filePath = request.getServletContext().getRealPath("/") + uploadDir;
        System.out.println(filePath);//输出存放上传文件所到的路径  
        File uploadPath = new File(filePath);  
       
		if (!uploadPath.exists()) {
			uploadPath.mkdir();
		}
		
		int fileSize = 100;//文件最大允许100M,注意，有时候Nginx需要同步配置!!!

		MultipartRequest mulit = new MultipartRequest(request, filePath, fileSize * 1024 * 1024, "UTF-8",fileNamePolicy);

		
		String type = mulit.getParameter("type");  
        System.out.println("导入还是导出模板:"+type);  
  
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
	
	static final String MULTIPART = "multipart/";
	static final String METHOD_POST = "post";
	
	/** 文件上传改名策略 **/
	FileRenamePolicy fileNamePolicy = new FileRenamePolicy() {
		public File rename(File file) {
			String body = "";
			String ext = "";
			int pot = file.getName().lastIndexOf(".");

			if (pot != -1) {
				ext = file.getName().substring(pot);
			} else {
				ext = "";
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			body = "rpt_"+sdf.format(date) + "";

			String newName = body + ext;
			file = new File(file.getParent(), newName);
			return file;
		}
	};

	@RequestMapping(mapping = { "/download", "/m/download" })
	public void download(String path,String name) throws Exception {
		//HttpServletRequest request = ContextUtil.getRequest();
		//HttpServletResponse response = ContextUtil.getResponse();
		
		Util4Download.download(path, name);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(mapping = { "/save_or_update_json_4import_or_export", "/m/save_or_update_json_4import_or_export" },text="同时保存导入和导出模板信息数据")
	public List<ExportTemplate> saveOrUpdate4ImportExport(String data) {
		if (StringUtil.isNotBlank(data)) {
			List<ExportTemplate> models = JSON.parseArray(data, ExportTemplate.class);
			exportTemplateService.saveOrUpdate(models);
			return models;
		}
		return ArrayUtil.EMPTY_LIST;
	}
	
}
