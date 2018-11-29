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
import org.lsqt.components.context.annotation.OnStarted;
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
import org.lsqt.sys.model.Dictionary;
import org.lsqt.sys.model.Machine;
import org.lsqt.sys.model.MachineQuery;
import org.lsqt.sys.model.Property;
import org.lsqt.sys.model.PropertyQuery;
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
			
			String httpPath = uploadAliyunOSS("qdb/company/"+System.currentTimeMillis()+aliFile,fullPath);
			request.setAttribute("serverPath", httpPath);
			System.out.println(httpPath);
		}
		
		return "/upload_img_htmlfile.jsp";
	}
	
	
	
	
	private static String aliUrl = "https://rst-sit-oss.oss-cn-shenzhen.aliyuncs.com/"; // 不知道这个地址是干什么的
    private static String endPoint = "https://oss-cn-shenzhen-internal.aliyuncs.com";// endpoint 访问OSS的域名
    private static String accessKeyId = "LTAIcuauDFcgjLjf"; // accessKeyId和accessKeySecret OSS的访问密钥
    private static String accessKeySecret = "uIAFNo4rDl3iP8Bt8HuU96aRADIXST";
    private static String bucketName = "rst-sit-oss";  // Bucket 用来管理所存储Object的存储空间
    
    @OnStarted
    public void initOSSConfig() throws Exception {
    	db.executePlan(false, ()->{
    		MachineQuery query = new MachineQuery();
    		query.setCode("AliYun_OSS_FILE");
    		Machine model = db.queryForObject("queryForPage", Machine.class, query);
    		if (model == null) {
    			log.warn("机器: AliYun_OSS_FILE 没有配置");
    			return ;
    		}
    		
    		if(model.getStatus() ==null) {
    			log.warn("机器: AliYun_OSS_FILE 启用状态没有设置");
    			return ;
    		}
    		
    		if (Dictionary.ENABLE_启用 != model.getStatus()) {
    			log.warn("机器: AliYun_OSS_FILE 状态没有启用");
    			return ;
    		}
    		
    		PropertyQuery pq = new PropertyQuery();
    		pq.setParentCode(model.getCode());
    		List<Property> list = db.queryForList("queryForPage", Property.class, pq);
    		for (Property p: list) {
    			if("aliUrl".equals(p.getName())) {
    				aliUrl = p.getValue();
    			}
    			if("endPoint".equals(p.getName())) {
    				endPoint = p.getValue();
    			}
    			if("accessKeyId".equals(p.getName())) {
    				accessKeyId = p.getValue();
    			}
    			if("accessKeySecret".equals(p.getName())) {
    				accessKeySecret = p.getValue();
    			}
    			if("bucketName".equals(p.getName())) {
    				bucketName = p.getValue();
    			}
    		}
    		
    		log.info("######################################################################################");
    		log.info("#    阿里云文件上传服务器配置");
    		log.info("#    aliUrl:\t {}",aliUrl);
    		log.info("#    endPoint:\t {}",endPoint);
    		log.info("#    accessKeyId:\t {}",accessKeyId);
    		log.info("#    accessKeySecret:{}",accessKeySecret);
    		log.info("#    bucketName:\t {}",bucketName);
    		log.info("######################################################################################");
    	});
    }
    
	/**
	 * 同步上传到阿里云OSS
	 * @param serverPath
	 */
	private String uploadAliyunOSS(String objectName, String fileFullPath) {
		AliyunOssUtils util = new AliyunOssUtils(endPoint, accessKeyId, accessKeySecret);

		try {
			util.withBucket(bucketName);
			util.createBucketIfExists(bucketName);
			
			StringBuilder result = new StringBuilder(aliUrl);
			result.append(util.uploadInputStream(objectName, new FileInputStream(new File(fileFullPath))));
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.shutdown();
		}

		return null;
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
