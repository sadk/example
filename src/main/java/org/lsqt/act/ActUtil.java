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
	public static final String AUTO_JUMP_USER_ID = "-1";
	
	/**内置的流程变量key**/
	public static final String VARIABLES_START_USER_ID ="startUserId";   //流程发起人
	public static final String VARIABLES_BUSINESS_KEY = "businessKey"; // 业务主键 
	public static final String VARIABLES_BUSINESS_FLOW_NO = "flowNo"; // 业务流水号
	public static final String VARIABLES_TITLE="title";  // 流程标题
	public static final String VARIABLES_CREATE_DEPT_ID="createDeptId"; // 单据填制人部门，如果没有取用户主部门，哪果再没有取user.userOrgList的第一个
	public static final String VARIABLES_BUSINESS_TYPE = "businessType"; // 自定义的单据业务类型
	public static final String VARIABLES_PRINT_COMPANY="companyName"; // 用印公司名称
	
	public static final String VARIABLES_LOGIN_USER = "loginUser"; 
	public static final String VARIABLES_APPROVE_OPINION = "approveOpinion"; // 流程发起时，就传入审批意见
	
	
	/**流程是否结束 1=已结束  0=未结束 **/
	public static final String END_STATUS_已结束="1";
	public static final String END_STATUS_未结束="0";
	public static String getEndStatusDesc(String closeStatus) {
		if(StringUtil.isBlank(closeStatus)){
			return "";
		}
		if(END_STATUS_已结束.equals(closeStatus)) {
			return "已结束";
		}
		if(END_STATUS_未结束.equals(closeStatus)) {
			return "未结束";
		}
		return "";
	}
	
	/** 与预算系统的业务状态保持一致 :已通过=2  审批中=3  已撤回=6   已作废=7 已退回=8 **/
	public static final String BUSINESS_STATUS_已通过="2";
	public static final String BUSINESS_STATUS_审批中="3";
	public static final String BUSINESS_STATUS_已撤回="6";
	public static final String BUSINESS_STATUS_已作废="7";
	public static final String BUSINESS_STATUS_已退回="8"; //驳回到拟稿人或不同意，都是这个状态
	
 
	public static String getBusinessStatusDesc(String businessStatus) {
		if(StringUtil.isBlank(businessStatus)){
			return "";
		}
		if(BUSINESS_STATUS_已通过.equals(businessStatus)) {
			return "已通过";
		}
		if(BUSINESS_STATUS_审批中.equals(businessStatus)) {
			return "审批中";
		}
		if(BUSINESS_STATUS_已撤回.equals(businessStatus)) {
			return "已撤回";
		}
		if(BUSINESS_STATUS_已作废.equals(businessStatus)) {
			return "已作废";
		}
		if(BUSINESS_STATUS_已退回.equals(businessStatus)) {
			return "已退回";
		}
		return "";
	}
	 
	
	private ActUtil(){}
	private static ProcessEngine PROCESS_ENGINE= null;
	
	public static ProcessEngine getProcessEngine() {
		if(PROCESS_ENGINE != null) {
			return PROCESS_ENGINE;
		}
		/*
		PROCESS_ENGINE = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
		ProcessEngines.init();
		*/
		return PROCESS_ENGINE;
	}
	
	public static RepositoryService getRepositoryService() {
		ProcessEngine pe = getProcessEngine();
		if(pe!=null) {
			return pe.getRepositoryService();
		}
		return null;
	}

	public static RuntimeService getRuntimeService() {
		ProcessEngine pe = getProcessEngine();
		if(pe!=null) {
			return pe.getRuntimeService();
		}
		return null;
	}

	public static FormService getFormService() {
		ProcessEngine pe = getProcessEngine();
		if(pe!=null) {
			return pe.getFormService();
		}
		return null;
	}

	public static IdentityService getIdentityService() {
		ProcessEngine pe = getProcessEngine();
		if(pe!=null) {
			return pe.getIdentityService();
		}
		return null;
	}

	public static TaskService getTaskService() {
		ProcessEngine pe = getProcessEngine();
		if(pe!=null) {
			return pe.getTaskService();
		}
		return null;
	}

	public static HistoryService getHistoryService(){
		ProcessEngine pe = getProcessEngine();
		if(pe!=null) {
			return pe.getHistoryService();
		}
		return null;
	}
	
	public static ManagementService getManagementService(){
		ProcessEngine pe = getProcessEngine();
		if(pe!=null) {
			return pe.getManagementService();
		}
		return null;
	}
	
	
	// --------------------------  常用的流程引擎对象转自定义的模型对象 ---------------
	public static ProcessInstance convert(org.activiti.engine.runtime.ProcessInstance e) {
		if(e==null) return null;
		
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
