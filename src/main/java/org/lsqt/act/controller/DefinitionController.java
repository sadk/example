package org.lsqt.act.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.lsqt.act.ActUtil;
import org.lsqt.act.model.Category;
import org.lsqt.act.model.CategoryQuery;
import org.lsqt.act.model.Definition;
import org.lsqt.act.model.DefinitionQuery;
import org.lsqt.act.model.Node;
import org.lsqt.act.model.NodeUser;
import org.lsqt.act.model.NodeUserQuery;
import org.lsqt.act.model.ReDefinition;
import org.lsqt.act.model.ReDefinitionQuery;
import org.lsqt.act.service.CategoryService;
import org.lsqt.act.service.DefinitionService;
import org.lsqt.act.service.NodeService;
import org.lsqt.act.service.NodeUserService;
import org.lsqt.act.service.ReDefinitionService;
import org.lsqt.act.service.impl.NodeServiceImpl;
import org.lsqt.act.service.support.TaskQueryUtil;
import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.file.ZipUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

/**
 * 流程定义相关
 * 
 * @author mmyuan
 *
 */
@Controller(mapping={"/act/definition"})
public class DefinitionController {
	private static final Logger  log = LoggerFactory.getLogger(DefinitionController.class);
	
	@Inject private DefinitionService definitionService;
	@Inject private ReDefinitionService reDefinitionService ;
	
	@Inject private NodeUserService nodeUserService;
	@Inject private NodeService nodeService;
	
	@Inject private CategoryService categroyService; 
	
	@Inject private Db db;

	@RequestMapping(mapping = { "/save_or_update_json_short_name", "/m/save_or_update_json_short_name" })
	public void saveOrUpdateJSONShortName(String data) {
		if(StringUtil.isNotBlank(data)){
			List<Definition> list = JSON.parseArray(data,Definition.class);
			for(Definition def : list){
				if(StringUtil.isNotBlank(def.getId())){
					db.update(def, "shortName","enableMobile","enableNeighborJump");
				}
			}
		}
	}
	
	@RequestMapping(mapping = { "/update_short", "/m/update_short" })
	public void updateShort(Definition data) {
		if(data!=null && StringUtil.isNotBlank(data.getId())){
			db.update(data, "shortName","enableMobile","enableNeighborJump");
			
			Definition defModel = db.getById(Definition.class, data.getId());
			
			// 检查流程定义是否有值
			ReDefinitionQuery query = new ReDefinitionQuery();
			query.setDefinitionId(data.getId());
			ReDefinition model = db.queryForObject("queryForPage", ReDefinition.class, query);
			if (model == null) {
				model = new ReDefinition();
				model.setDefinitionId(data.getId());

				if (defModel != null) {
					model.setDefinitionName(defModel.getName());
					model.setDefinitionKey(defModel.getKey());
					model.setDefinitionShortName(defModel.getShortName());
				}
				db.save(model);
			} else {
				model.setDefinitionName(defModel.getName());
				model.setDefinitionKey(defModel.getKey());
				model.setDefinitionShortName(defModel.getShortName());
				db.update(model);
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
	
	@RequestMapping(mapping = { "/get_node_list", "/m/get_node_list" },text="加载所有节点")
	public List<Node> getNodeList(NodeUserQuery query) {
		List<Node> result = new ArrayList<>();
		
		List<Node> fullNodeList = new ArrayList<>();
		if (StringUtil.isBlank(query.getDefinitionId())) {
			return result;
		}
		
		RepositoryService repositoryService = ActUtil.getRepositoryService();
		BpmnModel model = repositoryService.getBpmnModel(query.getDefinitionId());
		if (model != null) {
			Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
			for (FlowElement e : flowElements) {
				if (UserTask.class.isAssignableFrom(e.getClass())) {
					UserTask task = (UserTask) e;
					NodeObject st = new NodeObject();
					st.setCandidateGroups(task.getCandidateGroups());
					st.setCandidateUsers(task.getCandidateUsers());
					st.setFormKey(task.getFormKey());
					st.setFormProperties(task.getFormProperties());
					st.setTaskKey(task.getId());
					st.setTaskName(task.getName()+" - ("+task.getId()+")");
					st.setRemark(task.getDocumentation());
					st.setTaskBizType(Node.TASK_BIZ_TYPE_UNDRAFTNODE);
					fullNodeList.add(st);
				}
			}
		}
		
		if(!fullNodeList.isEmpty()) { // 加载节点审批对象和节点类型
			NodeServiceImpl util = new NodeServiceImpl();
			util.resolveStartEndNodeType(query.getDefinitionId(),fullNodeList);
			
			
			//NodeUserQuery query = new NodeUserQuery();
			//query.setDefinitionId(definitionId);
			List<NodeUser> list = nodeUserService.queryForList(query);
			
			for(Node e:fullNodeList) {
				List<String> approveIds = new ArrayList<>();
				List<String> approveNames = new ArrayList<>();
				List<String> approveTypes = new ArrayList<>();
				
				for(NodeUser u: list) {
					if(e.getTaskKey().equals(u.getTaskKey())){
						if(StringUtil.isNotBlank(u.getApproveObjectId() )) {
							approveIds.add(u.getApproveObjectId());
							approveNames.add(u.getName());
							approveTypes.add(NodeUser.getUserTypeDesc(u.getUserType()));
						}
						//result.add(e);
					}
				}
				NodeObject ele = (NodeObject)e;
				ele.setApproveObjectIds(StringUtil.join(approveIds, ","));
				ele.setApproveObjectNames(StringUtil.join(approveNames, ","));
				ele.setApproveObjectTypes(StringUtil.join(approveTypes, ","));
			}
		}
		
		if (StringUtil.isNotBlank(query.getTaskKey())) {
			for (Node e : fullNodeList) {
				if (e.getTaskKey().equals(query.getTaskKey())) {
					result.add(e);
				}
			}
		} else {
			result.addAll(fullNodeList);
		}
		nodeService.setMeetingNodeType(query.getDefinitionId(), result);
		
		return result;
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

	
    /**
     * 导出流程文件xml或图片
     *
     * @param processDefinitionId 流程定义
     * @param resourceType        资源类型(xml|image)
     * @throws Exception
     */
	@RequestMapping(mapping = { "/export_xml_or_img", "/m/export_xml_or_img" },text="导出流程文件")
    public void exportDefinitionXmlOrImg(String processDefinitionId, @RequestParam("resourceType") String resourceType) throws Exception {
		HttpServletResponse response = ContextUtil.getResponse();
		
		org.activiti.engine.RepositoryService repositoryService = ActUtil.getRepositoryService();
		
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        
        DefinitionQuery myQuery = new DefinitionQuery();
        myQuery.setId(processDefinitionId);
        Definition extDefinition = db.queryForObject("queryForPage",Definition.class,myQuery);
        
        String resourceName = "";
        if ("image".equals(resourceType)) {
            resourceName = processDefinition.getDiagramResourceName();
        } else if ("xml".equals(resourceType)) {
            resourceName = processDefinition.getResourceName();
        } else {
        	 resourceName = processDefinition.getResourceName(); //默认导出XML
        }
        
        InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
        
        response.setContentType("text/xml;charset=UTF-8"); 
        response.addHeader("Content-Disposition", "attachment; filename=" +URLEncoder.encode(processDefinition.getName(),"utf-8")+"_v"+extDefinition.getDeployVersion()+".bpmn20.xml");
    }
	
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
		
		// 自动导入节点和表单节点
		//nodeService.importNode(definitionId);
		/*
	    StreamSource xmlSource = new InputStreamSource(xmlStream);
	    BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xmlSource, false, false, processEngineConfiguration.getXmlEncoding());
	    return bpmnModel;
		*/
		
		List<ReDefinition> extDefList = new ArrayList<>();
		List<Definition> defIdList = db.queryForList("getExtendDefinitionNotExists", Definition.class);//添加流程扩展定义
		if(ArrayUtil.isNotBlank(defIdList)) {
			for(Definition def: defIdList) {
				ReDefinition model = new ReDefinition();
				model.setAppCode(Application.APP_CODE_DEFAULT);
				model.setDefinitionId(def.getId());
				model.setDefinitionKey(def.getKey());
				model.setDefinitionName(def.getName());
				model.setDefinitionShortName(def.getName());
				extDefList.add(model);
			}
			db.batchSave(extDefList);
		}
		return dep;
		
	}
	
	@RequestMapping(mapping = { "/deploy_copy_settings", "/m/deploy_copy_settings" },text="布署上传后的流程定义文件并拷贝流程设置")
	public ReDefinition deploy(String serverPath,String categoryCode,String definitionName,String hisDefinitionId) throws Exception{
		org.lsqt.act.model.Definition model = definitionService.getById(hisDefinitionId);
		
		if (model != null) {
			synchronized (DefinitionController.class) {
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
	
	
	/**
	 * 导出图片文件，以ZIP包的形式返回给客户
	 */
	@RequestMapping(mapping = { "/export_img", "/m/export_img" },text="导出流程图")
	public void exportImg(String processDefinitionId) throws IOException {
		
		HttpServletResponse response = ContextUtil.getResponse();
		
		if(StringUtil.isBlank(processDefinitionId) ) {
			return ;
		}
		
		org.activiti.engine.repository.ProcessDefinition processDefinition =  ActUtil.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		 
		InputStream resourceAsStream = ActUtil.getRepositoryService().getResourceAsStream(
				processDefinition.getDeploymentId(), processDefinition.getName());
		
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}
	
	
	/**
	 * 导出图片文件，以ZIP包的形式返回给客户
	 */
	@RequestMapping(mapping = { "/export_diagrams", "/m/export_diagrams" },text="导出流程图")
	public void exportDiagrams(String processDefinitionIds) throws IOException {
		if(StringUtil.isBlank(processDefinitionIds) ) {
			return ;
		}
		Set<String> processDefinitionIdSet = new HashSet<>();
		processDefinitionIdSet.addAll(StringUtil.split(processDefinitionIds, ","));
		
		HttpServletRequest request = ContextUtil.getRequest();
		HttpServletResponse response = ContextUtil.getResponse();
		
		String root = request.getServletContext().getRealPath("/");
		
	
		List<ProcessDefinition> list = ActUtil.getRepositoryService().createProcessDefinitionQuery().processDefinitionIds(processDefinitionIdSet).list();
		
		List<File> imgs = new ArrayList<>();
		for (ProcessDefinition processDefinition : list) {
			String diagramResourceName = processDefinition.getDiagramResourceName(); //导出图片，导出流程xml用 processDefinition.getResourceName
			String key = processDefinition.getKey();
			int version = processDefinition.getVersion();

			//System.out.println("++++"+ActUtil.getRepositoryService().getBpmnModel(processDefinition.getId()));
			
			InputStream resourceAsStream = ActUtil.getRepositoryService().getResourceAsStream(
					processDefinition.getDeploymentId(), diagramResourceName);
			
			 
			
			byte[] b = new byte[resourceAsStream.available()];

			resourceAsStream.read(b, 0, b.length);

			// create file if not exist
			String diagramDir = root+"/upload/" + key + "/" + version+".png";
			File file = new File(diagramDir);
			
			// wirte bytes to file
			FileUtils.writeByteArrayToFile(file, b);
			
			imgs.add(file);
		}
		
		
		for(File e: imgs) {
			log.debug("导出流程图路径："+e);
		}
		
		File zipFile = new File(root+"/upload/"+System.currentTimeMillis()+".zip");
		if(!zipFile.exists()) {
			zipFile.createNewFile();
		}
		ZipUtil.zipFiles(imgs.toArray(new File[imgs.size()]), zipFile);
		
		
		 // 输入流
        FileInputStream input = new FileInputStream(zipFile);

        // 设置头
        response.setHeader("Content-Type","application/x-zip-compressed");

        // 获取绑定了客户端的流
        ServletOutputStream output = response.getOutputStream();

        // 把输入流中的数据写入到输出流中
        IOUtils.copy(input,output);
        output.flush();
        input.close();
        output.close();
	}
	
	
	/**
	 *
	 */
	@RequestMapping(mapping = { "/build_tree_to_draw", "/m/build_tree_to_draw" },text="流程图在线配置")
	public List<SimpleNode> buildTreeToDraw() throws IOException {
		List<SimpleNode> list = new ArrayList<>();
		
		CategoryQuery query = new CategoryQuery();
		query.setDataType(Category.DATA_TYPE_FLOW);
		List<Category> data = categroyService.queryForList(query);
		if(ArrayUtil.isNotBlank(data)) {
			for(Category e: data) {
				SimpleNode node = new SimpleNode();
				node.id = e.getId()+"";
				node.pid = e.getPid()+"";
				node.name = e.getName();
				node.code = e.getCode();
				node.dataType = "1";
				list.add(node);
			}
		}
		
		
		List<Definition> defList = db.queryForList("getAll", Definition.class);
		if (ArrayUtil.isNotBlank(defList)) {
			List<SimpleNode> defNodeList = new ArrayList<>();
			
			for (SimpleNode e : list) {
				for(Definition def: defList) {
					if(e.code.equals(def.getCategory())) {
						SimpleNode subNode = new SimpleNode();
						subNode.code = def.getId();
						subNode.id = def.getId();
						subNode.pid = e.id;
						subNode.dataType = "2";
						
						// 改用时间擢作版本用于前端显示
						subNode.name = "版本（"+ new SimpleDateFormat("yyyyMMddHHmmss").format(def.getDeployTime())+"） - "+def.getName()+"（"+def.getId()+"）";
						subNode.shortName = def.getShortName();
						subNode.version = def.getVersion();
						subNode.deployTime = def.getDeployTime();
						defNodeList.add(subNode);
					}
				}
			}
			Collections.sort(defNodeList, (o1, o2) -> {
				return o2.deployTime.compareTo(o1.deployTime);
			});
			list.addAll( defNodeList);
		}
		
		return list;
	}
	
	public static class SimpleNode {
		public String id;
		public String pid;
		public String code;
		public String name;
		public String shortName;
		public String dataType; // 1=流程分类节点Category 2=流程定义结点
		public int version ; //流程定义版本
		public Date deployTime; //流程发布时间
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	void test2() {
		HttpServletRequest request = ContextUtil.getRequest();
		HttpServletResponse response = ContextUtil.getResponse();
		RepositoryService repositoryService = ActUtil.getRepositoryService();

		String id = request.getParameter("modelId");
		BufferedOutputStream bos = null;
		try {

			try {
				Model modelData = repositoryService.getModel(id);

				byte[] bpmnBytes = repositoryService.getModelEditorSource(modelData.getId());

				// 封装输出流
				bos = new BufferedOutputStream(response.getOutputStream());
				bos.write(bpmnBytes);// 写入流

				String filename = modelData.getId() + ".bpmn";
				response.setContentType("application/x-msdownload;");
				response.setHeader("Content-Disposition", "attachment; filename=" + filename);
				response.flushBuffer();

			} finally {
				bos.flush();
				bos.close();
			}

		} catch (Exception e) {
			log.error("导出流程文件失败");
			e.printStackTrace();
		}
	}
	
	
	void test(String procDefId,String proInstId) {
		org.activiti.engine.RepositoryService repositoryService = ActUtil.getRepositoryService();
		org.activiti.engine.RuntimeService runtimeService = ActUtil.getRuntimeService();
		
		List<ActivityImpl> actImpls = new ArrayList<ActivityImpl>();
		ProcessDefinition processDefinition = repositoryService .createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
		ProcessDefinitionImpl pdImpl = (ProcessDefinitionImpl) processDefinition;
		String processDefinitionId = pdImpl.getId();// 流程标识
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processDefinitionId);
		List<ActivityImpl> activitiList = def.getActivities();// 获得当前任务的所有节点
		List<String> activeActivityIds = runtimeService.getActiveActivityIds(proInstId);
		for (String activeId : activeActivityIds) {
			for (ActivityImpl activityImpl : activitiList) {
				String id = activityImpl.getId();
				if (activityImpl.isScope()) {
					if (activityImpl.getActivities().size() > 1) {
						List<ActivityImpl> subAcList = activityImpl.getActivities();
						for (ActivityImpl subActImpl : subAcList) {
							String subid = subActImpl.getId();
							System.out.println("subImpl:" + subid);
							if (activeId.equals(subid)) {// 获得执行到那个节点
								actImpls.add(subActImpl);
								break;
							}
						}
					}
				}
				if (activeId.equals(id)) {// 获得执行到那个节点
					actImpls.add(activityImpl);
					System.out.println(id);
				}
			}
		}
	}
}
