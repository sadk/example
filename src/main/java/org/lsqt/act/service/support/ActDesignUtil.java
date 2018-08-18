package org.lsqt.act.service.support;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lsqt.act.model.Condition;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;

public class ActDesignUtil {
	public static final String CHILD_SHAPES = "childShapes";
	public static final String STENCIL = "stencil";
	
	/**
	 * 自动补全流程定义ID和名称
	 * @param map
	 */
	public static void autoFillProcessDefinitionKeyAndName(Map<String, Object> map,String defaultDefKey,String defaultName) {
		if (map == null) {
			return;
		}
		
		Object properties = map.get("properties");
		if (properties != null && properties instanceof Map) {
			Map<String, Object> propertiesMap = (Map<String, Object>) properties;
			Object processId = propertiesMap.get("process_id");
			Object name = propertiesMap.get("name");
			
			if(processId == null || StringUtil.isBlank(processId.toString())) {
				if(StringUtil.isBlank(defaultDefKey)) {
					propertiesMap.put("process_id", "process_"+System.currentTimeMillis());
				} else {
					propertiesMap.put("process_id", defaultDefKey);
				}
			}
			
			if(name==null || StringUtil.isBlank(name.toString())) {
				if(StringUtil.isBlank(defaultDefKey)) {
					propertiesMap.put("name", "process_"+System.currentTimeMillis());
				} else {
					propertiesMap.put("name", defaultName);
				}
			}
		}
	}
	
	/**
	 * 自动填充流程条件网关的条件线ID和名称. 注：条件线的id， 里面必须要有"gateway"字样，id必须以"flow"开头,用于前端
	 * @param json_xml_map 流程图map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static void autoFillExclusiveGatewayFlowId(Map<String, Object> map) {
		//List<Condition> list = new ArrayList<>();
		
		int idCnt = 1;
		Object sharps = map.get(CHILD_SHAPES);
		if (sharps != null && (sharps instanceof List)) {
			Set<String> resourceIdSet = new HashSet<>();
			
			List<Map<String, Object>> rs = (List<Map<String, Object>>) sharps;
			if (ArrayUtil.isNotBlank(rs)) {
				for (Map<String, Object> m : rs) {
					Object stencil = m.get("stencil");
					Object outgoing = m.get("outgoing"); // 网关的分支线(也就是条件线)
					
					Object properties = m.get("properties");
					
					if (stencil != null && (stencil instanceof Map)) {
						Map<String, Object> stencilMap = (Map<String, Object>) stencil;
						if (stencilMap != null) {
							Object exclusiveGateway = stencilMap.get("id");
							if (exclusiveGateway != null 
									&& StringUtil.isNotBlank(exclusiveGateway.toString())
									&& "ExclusiveGateway".equals(exclusiveGateway.toString())) { // 刚好是网关元素
								
								// 填充网关元素ID
								if(properties!=null && (properties instanceof Map)) {
									Map<String,Object> propertiesMap = (Map<String,Object>)properties;
									propertiesMap.put("overrideid", "gateway"+idCnt);
									
									Object gateWayName = propertiesMap.get("name");
									if(gateWayName == null || StringUtil.isBlank(gateWayName.toString())) {
										propertiesMap.put("name", "条件分支"+idCnt);
									}
									idCnt++ ;
								}
								
								 if(outgoing!=null && (outgoing instanceof List)) {
									 List<Map<String,Object>> outList = (List<Map<String,Object>>) outgoing;
									 if(ArrayUtil.isNotBlank(outList)) {
										 for(Map<String,Object> o: outList) {
											 if(o.get("resourceId")!=null) {
												 resourceIdSet.add(o.get("resourceId").toString());
											 }
										 }
									 }
								 }
								 
							}
						}
					}
				}
				
				for (Map<String, Object> m : rs) {
					Object resourceId = m.get("resourceId");
					if (resourceId != null && StringUtil.isNotBlank(resourceId.toString())) {
						for(String rid: resourceIdSet) {
							if(rid.equals(resourceId.toString())) {
								Object properties = m.get("properties");
								if(properties!=null && (properties instanceof Map)) {
									
									// 填充条件线的ID
									Map<String,Object> propertiesMap = (Map<String,Object>)properties;
									
									if(resourceId.toString().startsWith("flow")) {
										propertiesMap.put("overrideid", resourceId.toString());
									} else {
										propertiesMap.put("overrideid", "flow"+idCnt);
									} 
									 
									// 填充条件线的名字
									Object flowName = propertiesMap.get("name");
									if(flowName == null || StringUtil.isBlank(flowName.toString())) {
										propertiesMap.put("name",propertiesMap.get("overrideid"));
									}
									
									/*
									Object conditionsequenceflow = propertiesMap.get("conditionsequenceflow");
									if(!(conditionsequenceflow == null || StringUtil.isBlank(conditionsequenceflow.toString()))) {
										Condition condit = new Condition();
										condit.setCode(propertiesMap.get("overrideid").toString());
										condit.setName(propertiesMap.get("name").toString());
										condit.setExpress(conditionsequenceflow.toString());
										
										list.add(condit);
									}
									*/
									idCnt ++;
								}
								break;
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 获取流程图里的条件
	 * @param json_xml_map 流程图map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Condition> getExclusiveGatewayExpress(Map<String, Object> map) {
		List<Condition> list = new ArrayList<>();

		Object sharps = map.get(CHILD_SHAPES);
		if (sharps != null && (sharps instanceof List)) {
			Set<String> resourceIdSet = new HashSet<>();
			
			List<Map<String, Object>> rs = (List<Map<String, Object>>) sharps;
			if (ArrayUtil.isNotBlank(rs)) {
				for (Map<String, Object> m : rs) {
					Object stencil = m.get("stencil");
					Object outgoing = m.get("outgoing"); // 网关的分支线(也就是条件线)
					
					Object properties = m.get("properties");
					
					if (stencil != null && (stencil instanceof Map)) {
						Map<String, Object> stencilMap = (Map<String, Object>) stencil;
						if (stencilMap != null) {
							Object exclusiveGateway = stencilMap.get("id");
							if (exclusiveGateway != null 
									&& StringUtil.isNotBlank(exclusiveGateway.toString())
									&& "ExclusiveGateway".equals(exclusiveGateway.toString())) { // 刚好是网关元素
								
 
								 if(outgoing!=null && (outgoing instanceof List)) {
									 List<Map<String,Object>> outList = (List<Map<String,Object>>) outgoing;
									 if(ArrayUtil.isNotBlank(outList)) {
										 for(Map<String,Object> o: outList) {
											 if(o.get("resourceId")!=null) {
												 resourceIdSet.add(o.get("resourceId").toString());
											 }
										 }
									 }
								 }
								 
								 
							}
						}
					}
				}
				
				int conditionId = 10000;
				for (Map<String, Object> m : rs) {
					Object resourceId = m.get("resourceId");
					if (resourceId != null && StringUtil.isNotBlank(resourceId.toString())) {
						for(String rid: resourceIdSet) {
							if(rid.equals(resourceId.toString())) {
								Object properties = m.get("properties");
								if(properties!=null && (properties instanceof Map)) {
									
									// 获取条件表达式
									Map<String,Object> propertiesMap = (Map<String,Object>)properties;
									
									Object conditionsequenceflow = propertiesMap.get("conditionsequenceflow");
									if(!(conditionsequenceflow == null || StringUtil.isBlank(conditionsequenceflow.toString()))) {
										Condition condit = new Condition();
										condit.setCode(propertiesMap.get("overrideid").toString());
										
										Object name = propertiesMap.get("name");
										condit.setName(name == null ? ("条件"+conditionId++):name.toString());
										
										condit.setExpress(conditionsequenceflow.toString());
										list.add(condit);
									}
									
								}
								break;
							}
						}
					}
				}
			}
		}
		return list;
	}
}
