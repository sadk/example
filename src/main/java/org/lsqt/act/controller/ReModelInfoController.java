package org.lsqt.act.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
//import org.activiti.editor.constants.ModelDataJsonConstants;
//import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.lsqt.act.ActUtil;
import org.lsqt.act.model.Definition;
import org.lsqt.act.model.DefinitionQuery;
import org.lsqt.act.model.ReDefinition;
import org.lsqt.act.model.ReModelInfo;
import org.lsqt.act.model.ReModelInfoQuery;
import org.lsqt.act.service.DefinitionService;
import org.lsqt.act.service.ReDefinitionService;
import org.lsqt.act.service.ReModelInfoService;
import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;




@Controller(mapping={"/act/re_model_info"})
public class ReModelInfoController {
	private static final Logger  log = LoggerFactory.getLogger(ReModelInfoController.class);
	
	@Inject private ReModelInfoService reModelInfoService; 
	@Inject private DefinitionService definitionService;
	@Inject private ReDefinitionService reDefinitionService ;
	
	@Inject private Db db;
	
	RepositoryService repositoryService=ActUtil.getRepositoryService();
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<ReModelInfo> queryForPage(ReModelInfoQuery query) throws IOException {
		return reModelInfoService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<ReModelInfo> getAll() {
		return reModelInfoService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public ReModelInfo saveOrUpdate(ReModelInfo form) {
		return reModelInfoService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return reModelInfoService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	/**
	 * 
	 * @param ids 模型Id
	 * @param hisDefinitionId 历史流程定义版本（考贝已有的流程配置用）
	 */
	@RequestMapping(mapping = { "/deploy", "/m/deploy" },text="发布到流程定义")
	public void deploy(String ids,String hisDefinitionId) {
		if (StringUtil.isNotBlank(ids)) {
			synchronized (ReModelInfoController.class) {

				org.lsqt.act.model.Definition hisDefinition = null;
				if (StringUtil.isNotBlank(hisDefinitionId)) {
					hisDefinition = definitionService.getById(hisDefinitionId);
				}

				List<String> idList = StringUtil.split(ids, ",");
				for (String id : idList) {
					reModelInfoService.deploy(id);

					if (hisDefinition != null) {
						DefinitionQuery query = new DefinitionQuery();
						query.setKey(hisDefinition.getKey());
						query.setSortOrder("desc");
						query.setSortField("B.DEPLOY_TIME_");
						List<Definition> list = db.queryForList("queryForPage", Definition.class, query);
						if (list != null && !list.isEmpty()) {
							reDefinitionService.copySettings(hisDefinitionId, list.get(0).getId());
						}
					}
					
					
				}
			}
		}
	}
	
	
	@RequestMapping(mapping = { "/import_new", "/m/import_new" },text="导入新模型")
	public Deployment importNew(String serverPath,String categoryCode,String definitionName) throws Exception{
		
		HttpServletRequest request = ContextUtil.getRequest();
		String path = request.getServletContext().getRealPath("/");
		log.debug("web服务器的系统根路径:"+path);
		
		File fpath = new File(path+serverPath);
		log.debug(fpath.getAbsolutePath());
		
		if(StringUtil.isBlank(definitionName)) {
			definitionName = System.currentTimeMillis()+"";
		}
		
		
		
		
		// 添加模型
		ReModelInfo model = new ReModelInfo();
		model.setCategory(categoryCode);
		model.setName(definitionName);
		
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode editorNode = objectMapper.createObjectNode();
		editorNode.put("id", "canvas");
		editorNode.put("resourceId", "canvas");
		ObjectNode stencilSetNode = objectMapper.createObjectNode();
		stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
		editorNode.put("stencilset", stencilSetNode);
		org.activiti.engine.repository.Model modelData = repositoryService.newModel();

		String description = StringUtil.defaultString(model.getRemark());
		modelData.setKey(StringUtil.defaultString(model.getKey()));
		modelData.setName(model.getName());
		modelData.setCategory(model.getCategory());
		modelData.setVersion(Integer.parseInt(String.valueOf(repositoryService.createModelQuery().modelKey(modelData.getKey()).count()+1)));

		ObjectNode modelObjectNode = objectMapper.createObjectNode();
		//modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, model.getName());
		//modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, modelData.getVersion());
		//modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
		//modelData.setMetaInfo(modelObjectNode.toString());
			
		repositoryService.saveModel(modelData);

		
		InputStream inputStream = new FileInputStream(fpath); 
		BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
		BpmnModel bpmnModel = xmlConverter.convertToBpmnModel(() -> { return inputStream; }, false, false);
		inputStream.close();
		
		//BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
		//ObjectNode objectNode =jsonConverter.convertToJson(bpmnModel);
		
		
		//repositoryService.addModelEditorSource(modelData.getId(), objectNode.toString().getBytes("utf-8")); // 模型关联流程图
		
		
		// 发布流程图文件
		byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
		ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
		Deployment deployment = repositoryService.createDeployment().name(modelData.getName())
				.category(modelData.getCategory())
				.tenantId(Application.APP_CODE_DEFAULT)
				.addInputStream(definitionName, in).deploy();
 
		// 查询某分类下的流程最新版本
		DefinitionQuery defQuery = new DefinitionQuery();
		defQuery.setDeployCategory(categoryCode);
		defQuery.setIsDisplayNewest(true);
		List<Definition> list = definitionService.queryForList(defQuery);
		if (ArrayUtil.isNotBlank(list) && list.size() == 1) {
			Definition definit = list.get(0);
			if (definit != null) {
				definit.setModelId(modelData.getId());
				db.update(definit, "modelId");
			}
		}
		
		List<ReDefinition> extDefList = new ArrayList<>();
		List<Definition> defIdList = db.queryForList("getExtendDefinitionNotExists", Definition.class);//添加流程扩展定义
		if(ArrayUtil.isNotBlank(defIdList)) {
			for(Definition def: defIdList) {
				ReDefinition md = new ReDefinition();
				md.setAppCode(Application.APP_CODE_DEFAULT);
				md.setDefinitionId(def.getId());
				md.setDefinitionKey(def.getKey());
				md.setDefinitionName(StringUtil.isBlank(def.getName()) ? System.currentTimeMillis()+"":def.getName());
				md.setDefinitionShortName(md.getDefinitionName());
				extDefList.add(md);
			}
			db.batchSave(extDefList);
		}
		return deployment;
	}
	 

}
