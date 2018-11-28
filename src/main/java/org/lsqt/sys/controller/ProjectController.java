package org.lsqt.sys.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Project;
import org.lsqt.sys.model.ProjectQuery;
import org.lsqt.sys.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

@Controller(mapping={"/project"})
public class ProjectController {
	static final Logger log = LoggerFactory.getLogger(ProjectController.class);
	
	@Inject private ProjectService projectService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" }, view = View.JSON)
	public Page<Project> queryForPage(ProjectQuery query) throws IOException {
		return projectService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" },view = View.JSON)
	public Collection<Project> getAll() {
		return projectService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" }, view = View.JSON)
	public Project saveOrUpdate(Project form) {
		 
		return projectService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" },view = View.JSON)
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return projectService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(mapping = { "/upload", "/m/upload" },view = View.JSON)
	public List<Map<String,Object>> uplodad() throws Exception{
		HttpServletRequest request = ContextUtil.getRequest();

		String filePath = request.getServletContext().getRealPath("/") + "/upload";
        log.info(filePath);//输出存放上传文件所到的路径  
        File uploadPath = new File(filePath);  
        // 检查文件夹是否存在 不存在 创建一个  
        if (!uploadPath.exists()) {  
            uploadPath.mkdir();  
        } 
		
		int fileSize = 10;//文件最大允许10M
		//String savePath = "xxx";//文件的保存目录
		
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
				body = sdf.format(date) + "";
				
				String newName = body + ext;
				file = new File(file.getParent(), newName);
				return file;
			}
		};
		
		MultipartRequest mulit = new MultipartRequest(ContextUtil.getRequest(), filePath, fileSize * 1024 * 1024, "UTF-8",fileNamePolicy);

  
        int cnt = 0;
        List<Map<String,Object>> rs = new ArrayList<>();
		Enumeration filesname = mulit.getFileNames();// 取得上传的所有文件(相当于标识)
		while (filesname.hasMoreElements()) {
			String id = (String) filesname.nextElement();// 标识
			String fileName = mulit.getFilesystemName(id); // 取得文件名
			String contentType = mulit.getContentType(id);// 文件类型
			String originalFileName = mulit.getOriginalFileName(id); //原始文件名
			if (fileName != null) {
				cnt++;
			}
			log.info("文件名：" + fileName + " 文件类型： " + contentType+" 原始文件名："+originalFileName);
			Map<String,Object> row = new HashMap<>();
			row.put("fileName",fileName);
			row.put("filePath", "/upload/"+fileName);
			//row.put("contentType", contentType);
			row.put("originalFileName", originalFileName);
			row.put("destFileName", fileName);
			rs.add(row);
		}
		log.info("共上传" + cnt + "个文件！"+rs);
		return rs;
	}
	  
}
