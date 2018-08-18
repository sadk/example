package org.lsqt.act.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
//import org.activiti.editor.constants.ModelDataJsonConstants;
//import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.lsqt.act.ActUtil;
import org.lsqt.act.model.Condition;
import org.lsqt.act.model.ReModelInfo;
import org.lsqt.act.model.ReModelInfoQuery;
import org.lsqt.act.service.ReModelInfoService;
import org.lsqt.act.service.support.ActDesignUtil;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class ReModelInfoServiceImpl implements ReModelInfoService{
	private static final Logger  log = LoggerFactory.getLogger(ReModelInfoServiceImpl.class);
	@Inject private Db db;
	//@Inject private ReDefinitionService reDefinitionService;
	
	private RepositoryService repositoryService = ActUtil.getRepositoryService();
	
	
	public Page<ReModelInfo>  queryForPage(ReModelInfoQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), ReModelInfo.class, query);
	}

	public List<ReModelInfo> getAll(){
		  return db.queryForList("getAll", ReModelInfo.class);
	}
	
	public ReModelInfo saveOrUpdate(ReModelInfo model) {
		
		if (StringUtil.isBlank(model.getId())) {
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
			modelData.setMetaInfo(modelObjectNode.toString());
				
			repositoryService.saveModel(modelData);
			try {
				repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			model.setId(modelData.getId());
		}
		return model;
		//return db.saveOrUpdate(model);
	}


	
	
// -----------------------------------------------------------------------------------------------------------------	
	/**
	 * 根据modelId部署流程
	 */
	@SuppressWarnings("unchecked")
	public Deployment deploy(String id) {
		
		Deployment deployment = null;
		try {
			org.activiti.engine.repository.Model modelData = repositoryService.getModel(id);
			//BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
			
			// 用于解析流程网关的条件
			byte [] json_byte = repositoryService.getModelEditorSource(modelData.getId());
			String jsonText = new String(json_byte,"utf-8");
			final Map<String,Object> jsonMap = JSON.parseObject(jsonText, Map.class);
			
			ObjectNode editorNode = (ObjectNode) new ObjectMapper().readTree(json_byte);
			//BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
			BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
			//byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
			
			String processName = modelData.getName();
			if (!StringUtils.endsWith(processName, ".bpmn20.xml")){
				processName += ".bpmn20.xml";
			}
//			System.out.println("========="+processName+"============"+modelData.getName());
			//ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
			//deployment = repositoryService.createDeployment().name(modelData.getName()).category(modelData.getCategory())
				//	.addInputStream(processName, in).deploy();
//					.addString(processName, new String(bpmnBytes)).deploy();
			
			// 设置流程分类
			List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
			for (ProcessDefinition processDefinition : list) {
				repositoryService.setProcessDefinitionCategory(processDefinition.getId(), modelData.getCategory());
				log.info("部署成功，流程ID=" + processDefinition.getId());
				
				//当前流程定义添加条件
				List<Condition> conditions = ActDesignUtil.getExclusiveGatewayExpress(jsonMap);
				if(ArrayUtil.isNotBlank(conditions)) {
					for(Condition e: conditions) {
						e.setDefinitionId(processDefinition.getId());
						e.setRemark(e.getRemark());
					}
					db.batchSave(conditions);
				}
				
				// 扩展字段
				org.lsqt.act.model.Definition def = db.getById(org.lsqt.act.model.Definition.class, processDefinition.getId());
				if(def!= null) {
					def.setModelId(id);;
					db.update(def, "modelId");
				}
			}
			if (list.size() == 0){
				log.info("部署失败，没有流程。");
			}
			
			
		} catch (Exception e) {
			throw new ActivitiException("设计模型图不正确，检查模型正确性，模型ID="+id, e);
		}
		return deployment;
	}
	
	/**
	 * 导出model的xml文件
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	public void export(String id, HttpServletResponse response) {
		try {
			org.activiti.engine.repository.Model modelData = repositoryService.getModel(id);
		//BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
			ObjectNode editorNode = (ObjectNode)new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
			//BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
			BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
			//byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

			//ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
			//IOUtils.copy(in, response.getOutputStream());
		//	String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
			//response.setHeader("Content-Disposition", "attachment; filename=" + filename);
			response.flushBuffer();
		} catch (Exception e) {
			throw new ActivitiException("导出model的xml文件失败，模型ID="+id, e);
		}
		
	}

	/**
	 * 更新Model分类
	 */
	public void updateCategory(String id, String category) {
		org.activiti.engine.repository.Model modelData = repositoryService.getModel(id);
		modelData.setCategory(category);
		repositoryService.saveModel(modelData);
	}
	
	/**
	 * 删除模型
	 * @param id
	 * @return
	 */
	public int deleteById(Long ... ids) {
		int cnt = 0;
		for(Long id: ids) {
			repositoryService.deleteModel(id.toString());
			cnt ++;
		}
		return cnt;
	}
	
	/**
	 * 删除模型,并删除模型下的流程定义
	 * @param id
	 * @return
	 */
	public int deleteById(boolean isCascade,Long ... ids) {
		int cnt = 0;
		for(Long id: ids) {
			repositoryService.deleteModel(id.toString());
			cnt ++;
		}
		
		// 删除流程定义...
		
		return cnt;
	}
}
