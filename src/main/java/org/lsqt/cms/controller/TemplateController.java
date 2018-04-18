package org.lsqt.cms.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.lsqt.cms.model.Content;
import org.lsqt.cms.model.Template;
import org.lsqt.cms.model.TemplateQuery;
import org.lsqt.cms.service.TemplateService;
import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import net.coobird.thumbnailator.Thumbnails;

@Controller(mapping={"/template"})
public class TemplateController {
	private static final Logger  log = LoggerFactory.getLogger(TemplateController.class);
	@Inject private TemplateService templateService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Template> queryForPage(TemplateQuery query) throws IOException {
		return templateService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Template> getAll() {
		return templateService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" },text="资讯类HTML模板")
	public Template saveOrUpdate(Template form,String content) {
		if(StringUtil.isBlank(form.getAppCode())) {
			form.setAppCode(Application.APP_CODE_DEFAULT);
		}
		form =  templateService.saveOrUpdate(form);
		
		db.executeUpdate(String.format("delete from %s where object_id=? and type=?" , db.getFullTable(Content.class)), form.getId(),Content.TYPE_HTML_TEMPLATE_FREEMARK);
		Content ct = new Content();
		ct.setTitle(form.getTitle());
		ct.setObjectId(form.getId());
		ct.setType(Content.TYPE_HTML_TEMPLATE_FREEMARK); 
		ct.setContent(content);
		ct.setEnable(1);
		db.save(ct);
		return form;
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return templateService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	
	private static final String MULTIPART = "multipart/";
	private static final String METHOD_POST = "post";
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(mapping={"/upload","/m/upload"},text="模板文件上传")
	public Map<String,Object> upload(final File file) throws IOException {
		Map<String,Object> rs = new HashMap<String,Object>();
		
		HttpServletRequest request = ContextUtil.getRequest();
		
		
		if (!METHOD_POST.equalsIgnoreCase(request.getMethod())) {
			
		}
		
		if (!request.getContentType().toLowerCase(Locale.ENGLISH).startsWith(MULTIPART)) {
			
		}
		
		/* 表单example:
		 <form enctype="multipart/form-data" method = "post" action = "UploadServlet">  
	       <input type="text" name="userName" value="这里是一个常规字段!!!!"/>   
	       <p>上传文件1:<input type = "file" name = "File1" size = "20" maxlength = "20"><br></p>  
	       <p>上传文件2:<input type = "file" name = "File2" size = "20" maxlength = "20"><br></p>  
	       <p>上传文件3:<input type = "file" name = "File3" size = "20" maxlength = "20"><br></p>  
	       <p>上传文件4:<input type = "file" name = "File4" size = "20" maxlength = "20"><br></p>  
	       <input type = "submit" value = "上传">  
	    </form>
		 */
		
		String filePath = request.getServletContext().getRealPath("/") + "/upload";
        log.info("//输出存放上传文件所到的路径: "+filePath);
        File uploadPath = new File(filePath);  
        // 检查文件夹是否存在 不存在 创建一个  
        if (!uploadPath.exists()) {  
            uploadPath.mkdir();  
        } 
		
		int fileSize = 10;//文件最大允许10M
		
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
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss_S");
				Date date = new Date();
				body = sdf.format(date) + "";
				
				String newName = body + ext;
				file = new File(file.getParent(), newName);
				return file;
			}
		};
		
		MultipartRequest mulit = new MultipartRequest(ContextUtil.getRequest(), filePath, 
				fileSize * 1024 * 1024, "UTF-8",fileNamePolicy);

		
		String categoryCode = mulit.getParameter("categoryCode");  
        log.debug("上传到流程分类："+categoryCode);
  
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
			log.debug("文件名：" + fileName + " 文件类型： " + contentType+" 原始文件名："+originalFileName);
			
			rs.put("pathThumbnail", "/upload/"+fileName);
			rs.put("pathThumbnailLarge", "/upload/"+"800_600_"+fileName);
			rs.put("pathThumbnailMiddle", "/upload/"+"400_300_"+fileName);
			rs.put("pathThumbnailSmall", "/upload/"+"85_116_"+fileName);
			
			// 对图片进行缩略 (大、中、小)
			Thumbnails.of(filePath+"\\"+fileName).size(800, 600).toFile(filePath+"\\"+"800_600_"+fileName); 
			Thumbnails.of(filePath+"\\"+fileName).size(400, 300).toFile(filePath+"\\"+"400_300_"+fileName); 
			Thumbnails.of(filePath+"\\"+fileName).size(85, 116).toFile(filePath+"\\"+"85_116_"+fileName); 
		}
		log.debug("共上传" + cnt + "个文件！");
		return rs;
	}
	
	@RequestMapping(mapping = { "/test", "/m/test" })
	public void test() {
		//Object [] args= new Object[]{"张三","1",new Date(),"李四","2",new Date(),"王五","3",new Date()};
		
		List<Object> args2 = new ArrayList<>();
		
		List<Template> list = new ArrayList<>();
		
		List<String> sqls = new ArrayList<>();
		for (int i=0;i<2;i++) {
			/*
			Template t=new Template();
			t.setName("name"+i);
			t.setChannelId((long)i);
			t.setCode("code"+i);
			t.setRemark("remark"+i);
			t.setTitle("title"+i);
			t.setPathThumbnail("setPathThumbnail"+i);
			t.setPathThumbnailLarge("setPathThumbnailLarge"+i);
			t.setPathThumbnailMiddle("setPathThumbnailMiddle"+i);
			t.setPathThumbnailSmall("setPathThumbnailSmall"+i);
			list.add(t);*/
			
			
			args2.add("张"+i);
			args2.add(i);
			args2.add(new Date());
			
			String sql = "insert into etl_config(name,gid,create_time) values ('a"+i+"','"+i+"',now())";
			//db.batchUpdate(sql);
			//sqls.add(sql);
		}
		//sqls.add("insert into uum_title(name,node_path,name_short,code,status,type,sn,remark,create_time,update_time) values('aaa','path','a','code:a',1,2,3,4,now(),now())");
		//db.batchUpdate(sqls.toArray(new String [sqls.size()]));
		//db.batchSave(list);
		db.batchUpdate("insert into etl_config(name,gid,create_time) values (?,?,?)", args2.toArray(new Object[args2.size()]));
	}
	
}
