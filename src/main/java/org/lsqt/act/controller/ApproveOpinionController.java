package org.lsqt.act.controller;

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

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import org.eclipse.jetty.deploy.App;
import org.lsqt.act.FastDFSUtil;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.ApproveOpinionFile;
import org.lsqt.act.model.ApproveOpinionQuery;
import org.lsqt.act.model.NodeButton;
import org.lsqt.act.service.ApproveOpinionService;


@Controller(mapping={"/act/approve_opinion","/nv2/act/approve_opinion"})
public class ApproveOpinionController {
	private static final Logger  log = LoggerFactory.getLogger(ApproveOpinionController.class);
	
	@Inject private ApproveOpinionService approveOpinionService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<ApproveOpinion> queryForPage(ApproveOpinionQuery query) throws IOException {
		return approveOpinionService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<ApproveOpinion> getAll() {
		return approveOpinionService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public ApproveOpinion saveOrUpdate(ApproveOpinion form) {
		return approveOpinionService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/save_or_update_json", "/m/save_or_update_json" })
	public ApproveOpinion saveOrUpdate(String form) {
		if(StringUtil.isNotBlank(form)){
			ApproveOpinion model = JSON.parseObject(form, ApproveOpinion.class);
			return db.saveOrUpdate(model,"approveAction","approveResult","approveOpinion","businessType","remark");
		}
		return null;
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return approveOpinionService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	
	// ---------------------------------------   意见调整相关    ---------------------------------------------------------
	@RequestMapping(mapping = { "/save_or_update_short", "/m/save_or_update_short" },text="流程意见调整")
	public ApproveOpinion saveOrUpdateShort(ApproveOpinion form) {
		if(StringUtil.isNotBlank(form.getApproveAction())){
			if(form.getApproveAction().startsWith("button_type_")){
				form.setApproveAction(form.getApproveAction().substring("button_type_".length(),form.getApproveAction().length()));
			}
			form.setApproveResult(NodeButton.getApproveActionDesc(form.getApproveAction()));
		}
		
		if(form.getId()!=null){
			 db.saveOrUpdate(form,"approveUserId","approveUserName","approveTaskKey","approveTaskName","approveAction","approveOpinion","approveResult");
		} else {
			 db.save(form);
		}
		return form;
	}
	

	@RequestMapping(mapping = { "/upload", "/m/upload" },text="上传流程定义",view=View.JSON)
	public Map<String,Object>  uplodad() throws Exception{
		
		final String MULTIPART = "multipart/";
		final String METHOD_POST = "post";
		
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
        log.info(" --- 输出存放上传文件所到的路径(web服务器的路径)  :"+filePath);
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
		 
		ApproveOpinionFile model = resolveSaveInDb(mulit);
		
		/*
		String fastDFS = FastDFSUtil.upload(filePath);
		model.setPath(fastDFS);
		db.update(model, "path");
		*/
		
		rs.put("serverPath", "/upload/"+model.getStoredName());
		rs.put("originalName", model.getOriginalName());
		return rs;
	}
	
 
	/**
	 * 解析文件信息保存到数据库
	 * @param mulit
	 * @return
	 */
	private ApproveOpinionFile resolveSaveInDb(MultipartRequest mulit){
		String businessKey = mulit.getParameter("businessKey");  
		String opinionId = mulit.getParameter("opinionId");
		String definitionId = mulit.getParameter("definitionId");  
		String definitionName = mulit.getParameter("definitionName");  
		String definitionKey = mulit.getParameter("definitionKey");  
		String approveProcessInstanceId = mulit.getParameter("processInstanceId");  
		String approveTaskId = mulit.getParameter("approveTaskId");  
		String approveTaskKey = mulit.getParameter("approveTaskKey");  
		String uploadUserId = mulit.getParameter("uploadUserId");  
		String uploadUserName = mulit.getParameter("uploadUserName");  
		UploadedFile uf = resolveSingleFile(mulit) ;
		
		ApproveOpinionFile f = new ApproveOpinionFile();
		f.setStoredName(uf.FileName);
		f.setOriginalName(uf.OriginalFileName);
		
		f.setBusinessKey(businessKey);
		f.setOpinionId(opinionId);
		f.setDefinitionId(definitionId);
		f.setDefinitionName(definitionName);
		f.setDefinitionKey(definitionKey);
		f.setApproveProcessInstanceId(approveProcessInstanceId);
		f.setApproveTaskId(approveTaskId);
		f.setApproveTaskKey(approveTaskKey);
		f.setUploadUserId(uploadUserId);
		f.setUploadUserName(uploadUserName);
		db.save(f);
		
		
		return f;
	}
	
	/**
	 * 解析单个文件名
	 * @param mulit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private UploadedFile resolveSingleFile(MultipartRequest mulit){
		UploadedFile uf = new UploadedFile();

		Enumeration<String> filesname = mulit.getFileNames();// 取得上传的所有文件(相当于标识)
		while (filesname.hasMoreElements()) {
			String id = (String) filesname.nextElement();// 标识
			String fileName = mulit.getFilesystemName(id); // 取得文件名
			String contentType = mulit.getContentType(id);// 文件类型
			String originalFileName = mulit.getOriginalFileName(id); // 原始文件名
			if (fileName != null) {

				log.debug("文件名：" + fileName + " 文件类型： " + contentType + " 原始文件名：" + originalFileName);

				uf.FileName = fileName;
				uf.OriginalFileName = originalFileName;

				return uf;
			}
		}
		return null;
	}
	

	class UploadedFile{
		/**上传到web应用里的文件名**/
		private String FileName; 
		
		/**原始文件名**/
		private String OriginalFileName ;
	}
	

	@RequestMapping(mapping = { "/do_data", "/m/do_data" },text="流程意见批量处理数据，兼容新代码：fix上下节点死循环审批!!")
	public void doData() {
		String sql = "select distinct(process_instance_id) instanceId from ext_approve_opinion where approve_action = 'reject_to_choose_node' and (reject_re_run_complete_status is null or reject_re_run_complete_status='') ";
		List<Map<String,Object>> list = db.executeQuery(sql);
		
		List<String> instanceIdList = new ArrayList<>();
		for (Map<String, Object> row : list) {
			Object instanceId = row.get("instanceId");
			if (instanceId != null) {
				instanceIdList.add(instanceId.toString());
			}
		}
		
		if (ArrayUtil.isNotBlank(instanceIdList)) {
			ApproveOpinionQuery query = new ApproveOpinionQuery();
			query.setProcessInstanceIds(StringUtil.join(instanceIdList));
			
			List<ApproveOpinion> data = db.queryForList("doData", ApproveOpinion.class, query);
			Map<String,List<ApproveOpinion>> dataMap = new HashMap<>();
			
		
			if (ArrayUtil.isNotBlank(data)) {
				for (String instanceId : instanceIdList) {
					dataMap.put(instanceId, new ArrayList<>());
				}
				for(ApproveOpinion e: data) {
					dataMap.get(e.getProcessInstanceId()).add(e);
				}
				
				List<Long> updateList = new ArrayList<>();
				
				for(String key : dataMap.keySet()) {
					List<ApproveOpinion> blok = dataMap.get(key);
					for(int i=0;i<blok.size();i++) {
						ApproveOpinion curr = blok.get(i);
						if(curr.getProcessInstanceId().equals("8603081")) {
							System.out.println(curr);
						}
						if ("reject_to_choose_node".equals(curr.getApproveAction())) {
							int next = i+1;
							if (next < blok.size()) {
								ApproveOpinion nxtObj = blok.get(next);
								if ("agree".equals(nxtObj.getApproveAction())
										|| "resubmit".equals(nxtObj.getApproveAction())
										|| "start_user_reback".equals(nxtObj.getApproveAction())
										|| "reject_to_starter".equals(nxtObj.getApproveAction())
										|| "abort".equals(nxtObj.getApproveAction())) {
									updateList.add(curr.getId());
								}
							}
						}
					}
				}
				
				if(ArrayUtil.isNotBlank(updateList)) {
					//db.executeUpdate("update ext_approve_opinion set reject_re_run_complete_status=1 where id in ("+StringUtil.join(updateList)+")");
					System.out.println("update ext_approve_opinion set reject_re_run_complete_status=1 where id in ("+StringUtil.join(updateList)+")");
				}
			}
			 
		}
	}
}
