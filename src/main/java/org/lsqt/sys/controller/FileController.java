package org.lsqt.sys.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.sys.model.File;
import org.lsqt.sys.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;



/**
 * 文件
 */
@Controller(mapping={"/sys/file"})
public class FileController {
	private static final Logger  log = LoggerFactory.getLogger(FileController.class);
	@Inject private FileService fileService;


	@RequestMapping(mapping={"/download","/m/download"})
	public void download(Long id,HttpServletResponse response) {
		try{
			File file = fileService.getById(id);
			if(file!=null) {
				response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getOriginalName(), "UTF-8"));
				response.setContentType("application/octet-stream; charset=UTF8");
			    
			    OutputStream os = response.getOutputStream();
			    os.write(fileService.download(id));
			    os.flush();
			    os.close();
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
 
	@SuppressWarnings("rawtypes")
	@RequestMapping(mapping = { "/upload", "/m/upload" })
	public List<Map<String,Object>> upload() throws Exception{
		HttpServletRequest request = ContextUtil.getRequest();

		String filePath = request.getServletContext().getRealPath("/") + "/upload";
		java.io.File uploadPath = new java.io.File(filePath);
		
        // 检查文件夹是否存在 不存在 创建一个  
        if (!uploadPath.exists()) {  
            uploadPath.mkdir();  
        } 
		
		int fileSize = 10;//文件最大允许10M
		//String savePath = "xxx";//文件的保存目录
		
		FileRenamePolicy fileNamePolicy = new FileRenamePolicy() {
			public java.io.File rename(java.io.File file) {
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
				file = new java.io.File(file.getParent(), newName);
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
			row.put("contentType", contentType);
			row.put("originalFileName", originalFileName);
			row.put("destFileName", fileName);
			rs.add(row);
		}
		log.info("共上传" + cnt + "个文件！"+rs);
		return rs;
	}
}
