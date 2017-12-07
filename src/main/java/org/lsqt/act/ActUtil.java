package org.lsqt.act;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.lsqt.act.model.ProcessInstance;
import org.lsqt.act.model.ProcessInstanceHis;
import org.lsqt.components.util.lang.StringUtil;


/**
 * 
 * @author mmyuan
 *
 */
public final class ActUtil {
	/**内置的流程变量key**/
	public static final String VARIABLES_START_USER_ID ="startUserId";   //流程发起人
	public static final String VARIABLES_BUSINESS_KEY = "businessKey"; // 业务主键 
	public static final String VARIABLES_TITLE="title";  // 流程标题
	public static final String VARIABLES_CREATE_DEPT_ID="createDeptId"; // 单据填制人部门，如果没有取用户主部门，哪果再没有取user.userOrgList的第一个
	public static final String VARIABLES_BUSINESS_TYPE = "businessType"; // 自定义的单据业务类型
	
	/** 暂自定义整个流程的业务状态 :待发起=0 待审批=1 审批中=3 审批通过=2 未通过=-1 已完成=4**/
	public static final String BUSINESS_STATUS_待发起="0";
	public static final String BUSINESS_STATUS_待审批="1";
	public static final String BUSINESS_STATUS_审批中="3";
	public static final String BUSINESS_STATUS_审批通过="2";
	public static final String BUSINESS_STATUS_未通过="-1";
	public static final String BUSINESS_STATUS_已完成="4";
	public static String getBusinessStatusDesc(String businessStatus) {
		if(StringUtil.isBlank(businessStatus)){
			return "";
		}
		if(BUSINESS_STATUS_待发起.equals(businessStatus)) {
			return "待发起";
		}
		if(BUSINESS_STATUS_待审批.equals(businessStatus)) {
			return "待审批";
		}
		if(BUSINESS_STATUS_审批中.equals(businessStatus)) {
			return "审批中";
		}
		if(BUSINESS_STATUS_审批通过.equals(businessStatus)) {
			return "审批通过";
		}
		if(BUSINESS_STATUS_未通过.equals(businessStatus)) {
			return "未通过";
		}
		if(BUSINESS_STATUS_已完成.equals(businessStatus)) {
			return "已完成";
		}
		return "";
	}
	
	private ActUtil(){}
	private static ProcessEngine PROCESS_ENGINE= null;
	
	public static ProcessEngine getProcessEngine() {
		if(PROCESS_ENGINE != null) {
			return PROCESS_ENGINE;
		}
		
		PROCESS_ENGINE = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
		ProcessEngines.init();
		
		return PROCESS_ENGINE;
	}
	
	public static RepositoryService getRepositoryService() {
		return getProcessEngine().getRepositoryService();
	}

	public static RuntimeService getRuntimeService() {
		return getProcessEngine().getRuntimeService();
	}

	public static FormService getFormService() {
		return getProcessEngine().getFormService();
	}

	public static IdentityService getIdentityService() {
		return getProcessEngine().getIdentityService();
	}

	public static TaskService getTaskService() {
		return getProcessEngine().getTaskService();
	}

	public static HistoryService getHistoryService(){
		return getProcessEngine().getHistoryService();
	}
	
	public static ManagementService getManagementService(){
		return getProcessEngine().getManagementService();
	}
	
	
	// --------------------------  常用的流程引擎对象转自定义的模型对象 ---------------
	public static ProcessInstance convert(org.activiti.engine.runtime.ProcessInstance e) {
		ProcessInstance model = new ProcessInstance();
		model.setActivityId(e.getActivityId());
		model.setBusinessKey(e.getBusinessKey());
		model.setDeploymentId(e.getDeploymentId());
		model.setId(e.getId());
		model.setIsEnded(e.isEnded());
		model.setIsSuspended(e.isSuspended());
		model.setName(e.getName());
		model.setParentId(e.getParentId());
		model.setProcessDefinitionId(e.getProcessDefinitionId());
		model.setProcessDefinitionKey(e.getProcessDefinitionKey());
		model.setProcessDefinitionName(e.getProcessDefinitionName());
		model.setProcessDefinitionVersion(e.getProcessDefinitionVersion());
		model.setProcessInstanceId(e.getProcessInstanceId());
		model.setProcessVariables(e.getProcessVariables());
		model.setTenantId(e.getTenantId());
		return model;
	}
	
	public static List<ProcessInstance> convert(List<org.activiti.engine.runtime.ProcessInstance> list) {
		List<ProcessInstance> rs = new ArrayList<>();
		if(list == null) return rs;
		for(org.activiti.engine.runtime.ProcessInstance p : list) {
			rs.add(convert(p));
		}
		return rs;
	}
	
	public static ProcessInstanceHis convertHis(org.activiti.engine.history.HistoricProcessInstance e) {
		ProcessInstanceHis model = new ProcessInstanceHis();
		model.setBusinessKey(e.getBusinessKey());
		model.setDeleteReason(e.getDeleteReason());
		model.setDurationInMillis(e.getDurationInMillis());
		model.setEndTime(e.getEndTime());
		model.setId(e.getId());
		model.setName(e.getName());
		model.setProcessDefinitionId(e.getProcessDefinitionId());
		model.setProcessVariables(e.getProcessVariables());
		model.setStartActivityId(e.getStartActivityId());
		model.setStartTime(e.getStartTime());
		model.setStartUserId(e.getStartUserId());
		model.setSuperProcessInstanceId(e.getSuperProcessInstanceId());
		model.setTenantId(e.getTenantId());
		return model;
	}
	
	public static List<ProcessInstanceHis> convertHis(List<org.activiti.engine.history.HistoricProcessInstance> list) {
		List<ProcessInstanceHis> rs = new ArrayList<>();
		if(list == null) return rs;
		for(org.activiti.engine.history.HistoricProcessInstance p : list) {
			rs.add(convertHis(p));
		}
		return rs;
	}
}