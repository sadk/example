package org.lsqt.rst.controller;

import java.io.File;
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
import org.lsqt.rst.model.VideoYear;
import org.lsqt.rst.model.VideoYearQuery;
import org.lsqt.rst.service.VideoYearService;

import com.oreilly.servlet.MultipartRequest;




@Controller(mapping={"/rst/video_year"})
public class VideoYearController {
	
	@Inject private VideoYearService videoYearService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public VideoYear getById(Long id) throws IOException {
		return videoYearService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<VideoYear> queryForPage(VideoYearQuery query) throws IOException {
		query.setTenantCode(ContextUtil.getLoginTenantCode());
		return videoYearService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public List<VideoYear> queryForList(VideoYearQuery query) throws IOException {
		query.setTenantCode(ContextUtil.getLoginTenantCode());
		return videoYearService.queryForList(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<VideoYear> getAll() {
		return videoYearService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public VideoYear saveOrUpdate(VideoYear form) {
		form.setTenantCode(ContextUtil.getLoginTenantCode());
		if (StringUtil.isBlank(form.getName())) {
			form.setName(ContextUtil.getLoginName());
		}
		return videoYearService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return videoYearService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(mapping = { "/upload", "/m/upload" }, view=View.JSP, path="/apps/default/admin/rst/video_yeared",excludeTransaction=true, text="职位视频上传")
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

		String departmentName = mulit.getParameter("departmentName");  
		request.setAttribute("departmentName", departmentName);
		
		String videoUrl = mulit.getParameter("videoUrl");  
		request.setAttribute("serverPathVideo", videoUrl);
		
		String coverUrl = mulit.getParameter("coverUrl");  
		request.setAttribute("serverPathCover", coverUrl);
		
		String vid = mulit.getParameter("id");  
		request.setAttribute("id", vid);
		
		String serverPathVideo="";
		String serverPathCover="";
		
		
        int cnt = 0;
		Enumeration filesname = mulit.getFileNames();// 取得上传的所有文件(相当于标识)
		while (filesname.hasMoreElements()) {
			String id = (String) filesname.nextElement();// 标识
			String fileName = mulit.getFilesystemName(id); // 取得文件名
			String contentType = mulit.getContentType(id);// 文件类型
			String originalFileName = mulit.getOriginalFileName(id); //原始文件名
			if (fileName == null) {
				continue;
			}
			
			System.out.println("文件名：" + fileName + " \n\r文件类型： " + contentType+" \n\r原始文件名："+originalFileName);
			
			serverPath =uploadDir+"/"+fileName;
			String fullPath = request.getServletContext().getRealPath("/") + serverPath;
			
			if ("videoFile".equals(id)) {
				serverPathVideo = serverPath;
				//视频路径
				String aliFile = serverPath.substring(serverPathVideo.lastIndexOf("/"), serverPathVideo.length());
				String httpPath = UploadConfigerOSS.uploadAliyunOSS("qdb/position/video/" + System.currentTimeMillis() + aliFile, fullPath);
				request.setAttribute("serverPathVideo", httpPath);
				
			} else if ("coverFile".equals(id)) {
				serverPathCover = serverPath;
				//封面路径
				String aliFile = serverPath.substring(serverPathCover.lastIndexOf("/"), serverPathCover.length());
				String httpPath = UploadConfigerOSS.uploadAliyunOSS("qdb/position/video/" + System.currentTimeMillis() + aliFile, fullPath);
				request.setAttribute("serverPathCover", httpPath);
			}
		}
		System.out.println("共上传" + cnt + "个文件！");
		
		
		
		return "/upload_video_htmlfile.jsp";
	}
}
