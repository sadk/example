package org.lsqt.rst.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.IdGenerator;
import org.lsqt.components.db.Page;
import org.lsqt.components.db.orm.ORMappingIdGenerator;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.report.controller.PolicyReportFileRename;
import org.lsqt.sys.model.Dictionary;

import com.oreilly.servlet.MultipartRequest;

import org.lsqt.rst.model.CompanyPicture;
import org.lsqt.rst.model.PositionDefinition;
import org.lsqt.rst.model.PositionDefinitionQuery;
import org.lsqt.rst.model.Video;
import org.lsqt.rst.service.PositionDefinitionService;
import org.lsqt.rst.util.AliyunOssUtils;




@Controller(mapping={"/rst/position_definition"})
public class PositionDefinitionController {
	private static  final String UPLOAD_DIR = "/upload";
	private IdGenerator idgen = new ORMappingIdGenerator();
	
	@Inject private PositionDefinitionService positionDefinitionService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public PositionDefinition getById(Long id) throws IOException {
		return positionDefinitionService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<PositionDefinition> queryForPage(PositionDefinitionQuery query) throws IOException {
		query.setTenantCode(ContextUtil.getLoginTenantCode());
		return positionDefinitionService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<PositionDefinition> getAll() {
		return positionDefinitionService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public PositionDefinition saveOrUpdate(PositionDefinition form) {
		form.setTenantCode(ContextUtil.getLoginTenantCode());
		if (StringUtil.isBlank(form.getCode())) {
			form.setCode(idgen.getUUID58().toString());
		}
		/*if (form.getSalaryMin() != null && form.getSalaryMax() != null) {
			form.setComprehensiveSalary(form.getSalaryMin() + "-" + form.getSalaryMax());
		}*/
		return positionDefinitionService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return positionDefinitionService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/get_welfare_item_nos", "/m/get_welfare_item_nos" },text="获取职位的福利列表")
	public List<Map<String,Object>> getWelfareItemNos(String positionCode) {
		String sql = "select * from bu_job_welfare_relationship where job_id=?";
		return db.executeQuery(sql, positionCode);
	}
	
	@RequestMapping(mapping = { "/save_position_addresses", "/m/save_position_addresses" },text="保存职位工作地址")
	public int savePositionAddresses(String positionCode,String workAddressCodes) {
		int cnt = 0;
		if (StringUtil.isNotBlank( positionCode,workAddressCodes )) {
			String sql = "delete from bu_job_address_relationship where job_id=?";
			db.executeUpdate(sql, positionCode);
			
			List<String> addr = StringUtil.split(workAddressCodes, ",");
			for(String addrId : addr) {
				String sql2 = "insert into bu_job_address_relationship(job_id,addr_id,tenant_code) values(?,?,?)";
				int ct = db.executeUpdate(sql2, positionCode,addrId,ContextUtil.getLoginTenantCode());
				cnt += ct ;
			}
		}
		return cnt;
	}
	
	@RequestMapping(mapping = { "/delete_position_addresses", "/m/delete_position_addresses" },text="删除职位工作地址")
	public int deletePositionAddresses(String positionCode,String wordAddressCodes) {
		int cnt = 0;
		if (StringUtil.isNotBlank(positionCode, wordAddressCodes)) {

			List<String> addr = StringUtil.split(wordAddressCodes, ",");
			for (String addrId : addr) {
				String sql = "delete from bu_job_address_relationship where job_id=? and addr_id=?";

				int ct = db.executeUpdate(sql, positionCode, addrId);
				cnt += ct;
			}
		}
		return cnt;
	}
	
	
	
	// ------------------------------------- 职位视频上传 -------------------------------------------------
	@SuppressWarnings("rawtypes")
	@RequestMapping(mapping = { "/upload", "/m/upload" }, view=View.JSP, path="/apps/default/admin/rst/position_definition",excludeTransaction=true, text="职位视频上传")
	public String upload() throws Exception{
		String serverPath = "";
		String uploadDir = UPLOAD_DIR;
		HttpServletRequest request = ContextUtil.getRequest();

		String filePath = request.getServletContext().getRealPath("/") + uploadDir;
        System.out.println(filePath);//输出存放上传文件所到的路径  
        File uploadPath = new File(filePath);  
       
		if (!uploadPath.exists()) {
			uploadPath.mkdir();
		}
		
		int fileSize = 100;//文件最大允许100M,注意，有时候Nginx需要同步配置!!!

		MultipartRequest mulit = new MultipartRequest(request, filePath, fileSize * 1024 * 1024, "UTF-8",new PolicyReportFileRename());

		String positionCode = mulit.getParameter("positionCode");  
		request.setAttribute("positionCode", positionCode);
		
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
			
			String httpPath = UploadConfigerOSS.uploadAliyunOSS("qdb/position/video/"+System.currentTimeMillis()+aliFile,fullPath);
			request.setAttribute("serverPath", httpPath);
			System.out.println(httpPath);
		}
		
		return "/upload_video_htmlfile.jsp";
	}
	
	

 
	
	
	@RequestMapping(mapping = { "/save_position_video", "/m/save_position_video" },text="保存职位视频")
	public int savePositionVideo(String positionCode, String serverPath) {
		if (StringUtil.isNotBlank(positionCode,serverPath)) {
			Video v = new Video();
			v.setUrl(serverPath);
			v.setTenantCode(ContextUtil.getLoginTenantCode());
			v.setCoverUrl("");
			v.setCreateDate(new Date());
			v.setUpdateDate(new Date());
			db.saveOrUpdate(v);
			
			String sql = "insert into bu_job_video (video_id,job_id,tenant_code) values(?,?,?) ";
			return db.executeUpdate(sql, v.getId(),positionCode,ContextUtil.getLoginTenantCode());
		}
		return 0;
	}
	
	
	@RequestMapping(mapping = { "/delete_position_video", "/m/delete_position_video" },text="删除职位视频")
	public int deletePositionVideo(String positionCode, String videoIds) {
		if (StringUtil.isNotBlank(positionCode,videoIds)) {
			int cnt = 0;
			for (String videoId: videoIds.split(",")) {
				String sql = "delete from bu_job_video where video_id=? and job_id=?";
				int c = db.executeUpdate(sql,videoId, positionCode);
				cnt += c;
			}
			return cnt;
		}
		return 0;
	}
	
	
	
	// ------------------------------------- 职位企业Logo上传 -------------------------------------------------
	@SuppressWarnings("rawtypes")
	@RequestMapping(mapping = { "/upload_logo", "/m/upload_logo" }, view=View.JSP, path="/apps/default/admin/rst/position_definition",excludeTransaction=true, text="职位logo字段")
	public String uploadLogo() throws Exception{
		String serverPath = "";
		String uploadDir = UPLOAD_DIR;
		HttpServletRequest request = ContextUtil.getRequest();

		String filePath = request.getServletContext().getRealPath("/") + uploadDir;
        System.out.println(filePath);//输出存放上传文件所到的路径  
        File uploadPath = new File(filePath);  
       
		if (!uploadPath.exists()) {
			uploadPath.mkdir();
		}
		
		int fileSize = 100;//文件最大允许100M,注意，有时候Nginx需要同步配置!!!

		MultipartRequest mulit = new MultipartRequest(request, filePath, fileSize * 1024 * 1024, "UTF-8",new PolicyReportFileRename());

		String positionCode = mulit.getParameter("positionCode");  
		String type = mulit.getParameter("type");
		request.setAttribute("positionCode", positionCode);
		request.setAttribute("type", type);
		
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
			
			String httpPath = UploadConfigerOSS.uploadAliyunOSS("qdb/position/logo/"+System.currentTimeMillis()+aliFile,fullPath);
			request.setAttribute("serverPath", httpPath);
			System.out.println(httpPath);
		}
		
		return "/upload_img_htmlfile.jsp";
	}
	
	

	  
	@RequestMapping(mapping = { "/update_position_img", "/m/update_position_img" },text="更新职位封面或logo字段")
	public PositionDefinition update_position_img(String positionCode, String serverPath,String type) {
		if (StringUtil.isNotBlank(positionCode,type)) {
			PositionDefinitionQuery query = new PositionDefinitionQuery();
			query.setCode(positionCode);
			PositionDefinition model = db.queryForObject("queryForPage", PositionDefinition.class, query);
			if (model != null) {
				if ("logo".equals(type)) {
					model.setUrlCompanyLogo(serverPath);
					db.update(model, "urlCompanyLogo");
				} 
				if ("cover".equals(type)) {
					model.setUrlPositionCover(serverPath);
					db.update(model, "urlPositionCover");
				}
			}
			return model;
		}
		return null;
	}
	
	@RequestMapping(mapping = { "/get_position_img", "/m/get_position_img" })
	public Page<PositionDefinition> queryForPage4Img(PositionDefinitionQuery query) throws IOException {
		Page<PositionDefinition> emptyPage = new Page.PageModel<>();
		if (StringUtil.isBlank(query.getCode())) {
			return emptyPage;
		}
		if (StringUtil.isBlank(query.getImgType())) {
			return emptyPage;
		}
		
		query.setTenantCode(ContextUtil.getLoginTenantCode());
		
		
		
		Page<PositionDefinition> page = positionDefinitionService.queryForPage(query);  
		if(page.getData().size() == 1) {
			PositionDefinition p = page.getData().iterator().next();
			if("logo".equals(query.getImgType()) && StringUtil.isBlank(p.getUrlCompanyLogo())) {
				return emptyPage;
			}   
			
			if("cover".equals(query.getImgType()) && StringUtil.isBlank(p.getUrlPositionCover())) {
				return emptyPage;
			}
			
			return page;
		} else {
			return emptyPage;
		}
	}
}
