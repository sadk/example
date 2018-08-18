package org.lsqt.act.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

//import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
//import org.apache.batik.transcoder.TranscoderInput;
//import org.apache.batik.transcoder.TranscoderOutput;
//import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.lsqt.act.ActUtil;
import org.lsqt.act.model.Condition;
import org.lsqt.act.model.Task;
import org.lsqt.act.service.support.ActDesignUtil;
import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 流程设计器专用
 * @author mmyuan
 *
 */
@Controller(mapping={"/service/act/re_model_info"})
public class ActServiceController {
	private static final Logger  log = LoggerFactory.getLogger(ReModelInfoController.class);
	
	RepositoryService repositoryService=ActUtil.getRepositoryService();
	
	private static  String CHILD_SHAPES = ActDesignUtil.CHILD_SHAPES;
	private static  String STENCIL = ActDesignUtil.STENCIL;
	
	private String parsePutJSON() { // put方法提交，解析参数用，框架暂时不支持
		HttpServletRequest request = ContextUtil.getRequest();
		String acceptjson = null;
		try {
			/*
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
			StringBuffer sb = new StringBuffer("");
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			br.close();*/
			
			acceptjson =java.net.URLDecoder.decode(IOUtils.toString(request.getInputStream(),"utf-8"),"utf-8");// sb.toString();
			System.out.print("----->222"+acceptjson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return acceptjson;

	}
 
	
	@SuppressWarnings("unchecked")
	@RequestMapping(mapping = { "/save_model", "/m/save_model" },text="流程设计器保存模型")
	  public void saveModel(String modelId, String json_xml,String svg_xml) {
	    try {
	      
	      Model model = repositoryService.getModel(modelId);
	      
	      Map<String,String> modelJson = JSON.parseObject(model.getMetaInfo(), Map.class);
	      
	     // modelJson.put(ModelDataJsonConstants.MODEL_NAME, modelJson.get("name"));
	     // modelJson.put(ModelDataJsonConstants.MODEL_DESCRIPTION,modelJson.get("description"));
	      model.setMetaInfo(JSON.toJSONString(modelJson));
	      model.setName(modelJson.get("name"));
	      
	      repositoryService.saveModel(model);
	      
	      
	      // ----------------------------- 处理默认添加流程候选人变量  Begin: -----------------------
	      	//File file =new File("D:\\workspace\\Activiti_5_19_0\\modules\\activiti-webapp-explorer2-ext\\src\\main\\resources\\json_xml.xml");
			//FileUtils.write(file, json_xml, "utf-8");
	      
		
			Map<String, Object> map = JSON.parseObject(json_xml, Map.class);
			autoFillCadidateUserExpress(map);
			ActDesignUtil.autoFillExclusiveGatewayFlowId(map);
			ActDesignUtil.autoFillProcessDefinitionKeyAndName(map,model.getKey(),model.getName());
	      // ----------------------------- 处理默认添加流程候选人变量  End!!! -----------------------
			String content =  JSON.toJSONString(map, true);
			//file =new File("D:\\workspace\\Activiti_5_19_0\\modules\\activiti-webapp-explorer2-ext\\src\\main\\resources\\json_xml2.xml");
			//FileUtils.write(file,content, "utf-8");
			//content = FileUtils.readFileToString(file, "utf-8");
	    
			
	      repositoryService.addModelEditorSource(model.getId(), content.getBytes("utf-8"));
	    
	      
	      InputStream svgStream = new ByteArrayInputStream(svg_xml.getBytes("utf-8"));
	     // TranscoderInput input = new TranscoderInput(svgStream);
	      
	    //  PNGTranscoder transcoder = new PNGTranscoder();
	      // Setup output
	      ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	    //  TranscoderOutput output = new TranscoderOutput(outStream);
	      
	      // Do the transformation
	    //  transcoder.transcode(input, output);
	      final byte[] result = outStream.toByteArray();
	      repositoryService.addModelEditorSourceExtra(model.getId(), result);
	      outStream.close();
	      
	    } catch (Exception e) {
	    	log.error("Error saving model", e);
	      throw new ActivitiException("Error saving model", e);
	    }
	  }
	
	/**
	 * 自动填充审批用户的表达式
	 * @param json_xml 流程图JSON
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private void autoFillCadidateUserExpress(final Map<String, Object> map) {
		List<String> resourceIdList = new ArrayList<>(); // 已存在的节点资源ID
		List<String> userTaskIdList = new ArrayList<>(); // UserTask节点ID
		int idCnt = 1;
		
		Object sharps = map.get(CHILD_SHAPES);
		if (sharps != null && (sharps instanceof List)) {
			List<Map<String, Object>> rs = (List<Map<String, Object>>) sharps;
			if (ArrayUtil.isNotBlank(rs)) {
				for (Map<String, Object> m : rs) {
					Object properties = m.get("properties");
					if (properties != null && (properties instanceof Map)) {
						Map<String, Object> propertiesMap = (Map<String, Object>) properties;
						if (propertiesMap != null) {
							Object taskId = propertiesMap.get("overrideid");
							if (taskId != null && StringUtil.isNotBlank(taskId.toString())) {
								resourceIdList.add(taskId.toString());
							}
						}
					}
				}
				
				for (Map<String, Object> m : rs) {
					boolean isUserTask = false;
					boolean isSequenceFlow = false;
					Object sencil = m.get(STENCIL);
					if (sencil != null && (sencil instanceof Map)) {
						Map<String, Object> sencilMap = (Map<String, Object>) sencil;
						Object userTask = sencilMap.get("id");
						if (userTask != null && "UserTask".equals(userTask.toString())) { // 流程任务节点
							isUserTask = true;
						}
						if (userTask != null && "SequenceFlow".equals(userTask.toString())) { // 流程线
							isSequenceFlow = true;
						}
					}
					


					if (isUserTask) {
						Object perperties = m.get("properties");
						if (perperties != null && (perperties instanceof Map)) {
							Map<String, Object> perpertiesMap = (Map<String, Object>) perperties;
							if (perpertiesMap != null) {
								// 使终添加候选人审批变量，自动补全
								Map<String,Object> assignmentMap = new HashMap<>();
								Map<String,List<Map<String,Object>>> candidateUsersMap = new HashMap<>();
								
								
								Object taskId = perpertiesMap.get("overrideid");
								Object name = perpertiesMap.get("name");
								Object usertaskassignmentMap = perpertiesMap.get("usertaskassignment");
								
								
								if (taskId == null || StringUtil.isBlank(taskId.toString())) {
									do {
										taskId = "usertask" + (idCnt++);
										if (!resourceIdList.contains(taskId)) { // ID要唯一
											break;
										}
										idCnt++;
									} while (true);
									
									perpertiesMap.put("overrideid", taskId);
									
									Map<String,Object> ele = new HashMap<>();
									ele.put("value", "${"+taskId+"}");
									
									candidateUsersMap.put("candidateUsers", Arrays.asList(ele));
									assignmentMap.put("assignment", candidateUsersMap);
									
									if(name!=null && name.toString().contains("拟稿")) { // 拟稿节点，设置为发起人变量！！
										assignmentMap.put("assignee", "${startUserId}");
									}
									perpertiesMap.put("usertaskassignment", assignmentMap);
									
								} else {
									
									/*
									//判断如果候选人变量已存在，则不自动补全候选人变量
									boolean isCandidateUsersExists = false;
									if (usertaskassignmentMap != null && (usertaskassignmentMap instanceof Map)) {
										Map<String, Object> userTaskAssignmentMap = (Map<String, Object>) usertaskassignmentMap;
										if (userTaskAssignmentMap.get("assignment") != null) {
											Map<String, Object> assMap = (Map<String, Object>) userTaskAssignmentMap.get("assignment");
											Object cadidateUserList = assMap.get("candidateUsers");
											if (cadidateUserList != null && (cadidateUserList instanceof List)) {
												List<Map<String, Object>> clist = (List<Map<String, Object>>) cadidateUserList;
												for (Map<String, Object> e : clist) {
													if (e.get("value") != null
															&& e.get("value").toString().startsWith("${")
															&& e.get("value").toString().endsWith("}")) {
														isCandidateUsersExists = true;
													}
												}
											}
										}
									}
									
									if(isCandidateUsersExists) {*/
										Map<String,Object> ele = new HashMap<>();
										ele.put("value", "${"+taskId+"}");
										
										candidateUsersMap.put("candidateUsers", Arrays.asList(ele));
										assignmentMap.put("assignment", candidateUsersMap);
										
										
										if(name!=null && name.toString().contains("拟稿")) { // 拟稿节点，设置为发起人变量！！
											assignmentMap.put("assignee", "${startUserId}");
										}
										
										perpertiesMap.put("usertaskassignment", assignmentMap);
									/*}*/
								}
								
								if(name == null || StringUtil.isBlank(name.toString())) {
									perpertiesMap.put("name", "审批节点"+idCnt);
								}
								
								userTaskIdList.add(taskId.toString()); //添加任务ID，用于后续检查是否有ID冲突
							}
						}
					}
					
					
					if(isSequenceFlow) { //自动检查修复(从eclpipse里画图，导入到在线流程设后）resourceId与overrideid不一致,Activiti设计器的原始Bug!!-
						Object resourceId = m.get("resourceId");
						Object properties = m.get("properties");
						Object overrideid = null;
						if (properties != null) {
							Map<String, Object> pMap = (Map<String, Object>) properties;
							overrideid = pMap.get("overrideid");
						}
						
						if ((overrideid != null && resourceId != null)
								&& StringUtil.isNotBlank(resourceId.toString(), overrideid.toString())) {
							if (!resourceId.toString().equals(overrideid.toString())) {
								
								overrideid = resourceId;
								
								Map<String, Object> pMap = (Map<String, Object>) properties;
								pMap.put("overrideid", overrideid);
							}
						}
					}
				}
				
				// 检查UserTask的Id是否有冲突
				if(ArrayUtil.isNotBlank(userTaskIdList)) {
					for(int n=0;n<userTaskIdList.size(); n++) {
						String curr = userTaskIdList.get(n);
						for(int p=0;p<userTaskIdList.size(); p++) {
							if(curr.equals(userTaskIdList.get(p)) && n!=p) {
								throw new RuntimeException("检测到任务节点ID有冲突，请检查节点ID="+curr+"的节点");
							}
						}
					}
				}
			}
		}
		
	}
}
