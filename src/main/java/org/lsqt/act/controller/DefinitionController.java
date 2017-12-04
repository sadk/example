package org.lsqt.act.controller;

import java.io.File;
import java.io.FileInputStream;
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

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;
import org.lsqt.act.ActUtil;
import org.lsqt.act.model.ApproveObject;
import org.lsqt.act.model.Definition;
import org.lsqt.act.model.DefinitionQuery;
import org.lsqt.act.model.Node;
import org.lsqt.act.model.NodeUser;
import org.lsqt.act.model.NodeUserQuery;
import org.lsqt.act.model.ReDefinition;
import org.lsqt.act.service.DefinitionService;
import org.lsqt.act.service.NodeService;
import org.lsqt.act.service.NodeUserService;
import org.lsqt.act.service.ReDefinitionService;
import org.lsqt.act.service.impl.DefinitionServiceImpl;
import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.file.FileUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

/**
 * 流程定义相关
 * @author mmyuan
 *
 */
@Controller(mapping={"/act/definition","/nv2/act/definition"})
public class DefinitionController {
	private static final Logger  log = LoggerFactory.getLogger(DefinitionController.class);
	
	@Inject private DefinitionService definitionService;
	@Inject private ReDefinitionService reDefinitionService ;
	
	@Inject private NodeUserService nodeUserService;
	@Inject private Db db;

	@RequestMapping(mapping = { "/save_or_update_json_short_name", "/m/save_or_update_json_short_name" })
	public void saveOrUpdateJSONShortName(String data) {
		if(StringUtil.isNotBlank(data)){
			List<Definition> list = JSON.parseArray(data,Definition.class);
			for(Definition def : list){
				if(StringUtil.isNotBlank(def.getId())){
					db.update(def, "shortName","enableMobile");
				}
			}
		}
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public void delete(String ids,Boolean cascade) {
		List<String> list = StringUtil.split(String.class, ids, ",");
		
		definitionService.delete(list, cascade == null ? false: cascade);
	}
	
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Definition> queryForPage(DefinitionQuery query) throws IOException {
		return definitionService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Definition> all(Boolean isDisplayNewest) {
		if (isDisplayNewest != null) {
			return definitionService.getAll(isDisplayNewest);
		}
		
		return definitionService.getAll();
	}
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public Collection<Definition> queryForList(DefinitionQuery query) {
		return definitionService.queryForList(query);
	}
	
	
	@RequestMapping(mapping = { "/get_node_list", "/m/get_node_list" })
	public List<NodeObject> getNodeList(String definitionId) {
		List<NodeObject> rs = new ArrayList<>();
		if (StringUtil.isBlank(definitionId)) {
			return rs;
		}
		
		RepositoryService repositoryService = ActUtil.getRepositoryService();
		BpmnModel model = repositoryService.getBpmnModel(definitionId);
		if (model != null) {
			Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
			for (FlowElement e : flowElements) {
				//System.out.println(e);
				if (UserTask.class.isAssignableFrom(e.getClass())) {
					UserTask task = (UserTask) e;
					NodeObject st = new NodeObject();
					st.setCandidateGroups(task.getCandidateGroups());
					st.setCandidateUsers(task.getCandidateUsers());
					st.setFormKey(task.getFormKey());
					st.setFormProperties(task.getFormProperties());
					st.setTaskKey(task.getId());
					st.setTaskName(task.getName());
					
					rs.add(st);
				}
			}
		}
		
		if(!rs.isEmpty()) { // 加载节点审批对象
			NodeUserQuery query = new NodeUserQuery();
			query.setDefinitionId(definitionId);
			List<NodeUser> list = nodeUserService.queryForList(query);
			
			for(NodeObject e:rs) {
				List<String> approveIds = new ArrayList<>();
				List<String> approveNames = new ArrayList<>();
				List<String> approveTypes = new ArrayList<>();
				
				for(NodeUser u: list) {
					if(e.getTaskKey().equals(u.getTaskKey())){
						approveIds.add(u.getApproveObjectId());
						approveNames.add(u.getName());
						approveTypes.add(NodeUser.getUserTypeDesc(u.getUserType()));
					}
				}
				
				e.setApproveObjectIds(StringUtil.join(approveIds, ","));
				e.setApproveObjectNames(StringUtil.join(approveNames, ","));
				e.setApproveObjectTypes(StringUtil.join(approveTypes, ","));
			}
		}
		return rs;
	}
	
	public static class NodeObject extends Node {
		private String approveObjectIds ;
		private String approveObjectNames;
		private String approveObjectTypes;
		
		public String getTaskNameDesc(){
			return getTaskName()+"（"+getTaskKey()+"）";
		}
		
		public String getApproveObjectIds() {
			return approveObjectIds;
		}
		public void setApproveObjectIds(String approveObjectIds) {
			this.approveObjectIds = approveObjectIds;
		}
 
		public String getApproveObjectTypes() {
			return approveObjectTypes;
		}
		public void setApproveObjectTypes(String approveObjectTypes) {
			this.approveObjectTypes = approveObjectTypes;
		}
		public String getApproveObjectNames() {
			return approveObjectNames;
		}
		public void setApproveObjectNames(String approveObjectNames) {
			this.approveObjectNames = approveObjectNames;
		}
	}
	// -------------------------------------------------  流程上传、布署相关 --------------------------------------
	static final String MULTIPART = "multipart/";
	static final String METHOD_POST = "post";
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(mapping = { "/upload", "/m/upload" },text="上传流程定义",view=View.JSON)
	public Map<String,Object>  uplodad() throws Exception{
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
        System.out.println(filePath);//输出存放上传文件所到的路径  
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
			rs.put("serverPath", "/upload/"+fileName);
		}
		log.debug("共上传" + cnt + "个文件！");
		return rs;
	}
	
	
	@RequestMapping(mapping = { "/deploy", "/m/deploy" },text="布署上传后的流程定义文件")
	public Deployment deploy(String serverPath,String categoryCode,String definitionName) throws Exception{
		HttpServletRequest request = ContextUtil.getRequest();
		String path = request.getServletContext().getRealPath("/");
		log.debug("web服务器的系统根路径:"+path);
		
		File fpath = new File(path+serverPath);
		log.debug(fpath.getAbsolutePath());
		
		if(StringUtil.isBlank(definitionName)) {
			definitionName = System.currentTimeMillis()+"";
		}
		//String xml = IOUtils.toString(new FileInputStream(fpath));
		Deployment dep= ActUtil.getRepositoryService().createDeployment()
				.addInputStream(fpath.getAbsolutePath(), new FileInputStream(fpath))
				//.addString(definitionName,xml )'
				.name(definitionName)
				.category(categoryCode)
				.tenantId("1000").deploy();
		
		//String sql = String.format("update %s set NAME_=? where ID_=?",db.getFullTable(Definition.class));
		//db.executeUpdate(sql, serverPath,dep.getId());
		
		return dep;
		
	}
	
	@RequestMapping(mapping = { "/deploy_copy_settings", "/m/deploy_copy_settings" },text="布署上传后的流程定义文件并拷贝流程设置")
	public ReDefinition deploy(String serverPath,String categoryCode,String definitionName,String hisDefinitionId) throws Exception{
		org.lsqt.act.model.Definition model = definitionService.getById(hisDefinitionId);
		
		if (model != null) {
			synchronized (this) {
				Deployment dep = deploy(serverPath, categoryCode, definitionName);
				log.debug("布署成功?:"+(dep!=null));
				DefinitionQuery query = new DefinitionQuery();
				query.setIsDisplayNewest(true);
				query.setKey(model.getKey());
				List<Definition> list = definitionService.queryForList(query);
				if (list != null && !list.isEmpty()) {
					return reDefinitionService.copySettings(hisDefinitionId, list.get(0).getId());
				}
			}
		}
		return null;
	}
	
}
