package org.lsqt.act.service.support;

import java.io.BufferedWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.lsqt.act.model.ActRunningContext;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.Node;
import org.lsqt.act.model.NodeButton;
import org.lsqt.act.model.NodeButtonQuery;
import org.lsqt.act.model.NodeQuery;
import org.lsqt.act.model.ReDefinition;
import org.lsqt.act.model.RunInstance;
import org.lsqt.act.model.Task;
import org.lsqt.act.service.impl.ActFreemarkUtil;
import org.lsqt.components.db.Db;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.uum.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import freemarker.template.Template;

/**
 * 默认角本执行器
 * @author mmyuan
 *
 */
public class DefaultScriptExecutor {
	private static final Logger  log = LoggerFactory.getLogger(DefaultScriptExecutor.class);
	
 
	private void doExecuteFreemarkUrlScript(Map<String, Object> root, String templateText) {
		String urls = null;
		try {
			Template tmpl = new Template("redefinition_" + System.currentTimeMillis(),
					new StringReader(templateText), ActFreemarkUtil.FTL_CONFIGURATION);
			StringWriter stringWriter = new StringWriter();
			BufferedWriter writer = new BufferedWriter(stringWriter);
			tmpl.process(root, writer);

			writer.flush();
			writer.close();

			urls = stringWriter.toString().trim();
		} catch (Exception e) {
			//e.printStackTrace();
			log.error("Freemark解析url失败: "+e.getMessage()+", 角本参数值：" + root);
			//throw new RuntimeException("Freemark解析url失败", e);
		}
		
		String [] urlList = urls.split(";"); // 多个角本请用分号分割开
		
		for(String http: urlList) {
			HttpClientUtil.doGet(http);
		}
	}


	
	/**
	 * 针对流程定义，执行全局回调角本
	 * @param context
	 * @return
	 */
	public <T> T executeGlobalBeforeScript(ActRunningContext context) {
		final RunInstance processInstance = context.getCurrProcessInstance();
		final ReDefinition definition = context.getCurrReDefinion();
		final User loginUser = context.getLoginUser();
		
		log.debug(" ------------------------------- 执行全局前置角本instance=" + processInstance.getInstanceId() + "（回调）开始：--------------------------------------");

		if (StringUtil.isNotBlank(definition.getBeforeScript())) {
			if (NodeButton.SCRIPT_TYPE_URL.equals(definition.getBeforeScriptType())) {
				 
				final Map<String, Object> root = new HashMap<>();
				root.put("loginUser", loginUser);
				root.put("loginUserId", loginUser.getUserId());
				root.put("processInstanceId", processInstance.getInstanceId());
				root.put("processDefinitionId", processInstance.getProcessDefinitionId());
				root.put("businessKey", processInstance.getBusinessKey());
				//root.put("nextTaskCandidateUserIds", context.getNextTaskCandidateUserIds());
				
				printMapExcludeLoginUser(root);
				
				doExecuteFreemarkUrlScript(root, definition.getBeforeScript());
				
			} else if (NodeButton.SCRIPT_TYPE_JAVASCRIPT_CODE.equals(definition.getAfterScriptType())) {

			} else if (NodeButton.SCRIPT_TYPE_JAVA_CODE.equals(definition.getAfterScriptType())) {

			} else {

			}
		}
		
		log.debug(" ------------------------------- 执行全局前置角本instance=" + processInstance.getInstanceId() + "（回调）结束！--------------------------------------");

		return null;
	}

	public <T> T executeGlobalAfterScript(ActRunningContext context)  {
		final RunInstance processInstance = context.getCurrProcessInstance();
		final ReDefinition definition = context.getCurrReDefinion();
		final User loginUser = context.getLoginUser();
		
		log.debug(" ------------------------------- 执行全局后置角本，instance=" + processInstance.getInstanceId() + "（回调）开始：--------------------------------------");

		if (definition!=null && StringUtil.isNotBlank(definition.getAfterScript())) {
			if (NodeButton.SCRIPT_TYPE_URL.equals(definition.getAfterScriptType())) {
				 
				final Map<String, Object> root = new HashMap<>();
				root.put("loginUser", loginUser);
				root.put("loginUserId", loginUser.getUserId());
				root.put("processInstanceId", processInstance.getInstanceId());
				root.put("processDefinitionId", processInstance.getProcessDefinitionId());
				root.put("businessKey", processInstance.getBusinessKey());
				root.put("nextTaskCandidateUserIds", context.getNextTaskCandidateUserIds());
				root.put("action", context.getApproveOpinion().getApproveAction());
				root.put("isInstanceEnded", TaskQueryUtil.isInstanceEnded(processInstance.getInstanceId()));
				
				printMapExcludeLoginUser(root);
				
				doExecuteFreemarkUrlScript(root, definition.getAfterScript());
				
			} else if (NodeButton.SCRIPT_TYPE_JAVASCRIPT_CODE.equals(definition.getAfterScriptType())) {

			} else if (NodeButton.SCRIPT_TYPE_JAVA_CODE.equals(definition.getAfterScriptType())) {

			} else {

			}
		}
		
		log.debug(" ------------------------------- 执行全局后置角本instance=" + processInstance.getInstanceId() + "（回调）结束！--------------------------------------");

		return null;
	}

	
	
	/**
	 * 执行按钮前置回调角本
	 * @param context
	 * @return
	 */
	public <T> T executeButtonBeforeScript(ActRunningContext context) {
		
		final RunInstance processInstance = context.getCurrProcessInstance();
		final ReDefinition definition = context.getCurrReDefinion();
		final Task runingCurrTask = context.getRuningCurrTask();
		final ApproveOpinion opinion = context.getApproveOpinion();
		final Db db = context.getDb();
		final Node draftNode = context.getDraftNode();
		final User loginUser = context.getLoginUser();

		log.debug(" ------------------------------- 执行按钮前置角本，instance=" + processInstance.getInstanceId() + "（回调）开始：--------------------------------------");

		
		NodeButtonQuery query = new NodeButtonQuery();
		query.setDefinitionId(processInstance.getProcessDefinitionId());
		query.setTaskKey(runingCurrTask.getTaskDefinitionKey());

		if (!opinion.getApproveAction().startsWith("button_type_")) {
			query.setBtnCode("button_type_" + opinion.getApproveAction());
		} else {
			query.setBtnCode(opinion.getApproveAction());
		}
		NodeButton button = db.queryForObject("queryForPage", NodeButton.class, query);
		if(button == null) {
			log.debug(" ------------------------------- 执行按钮前置角本，instance=" + processInstance.getInstanceId() + "（回调）结束！--------------------------------------");

			return null;
		}
		
		if (StringUtil.isNotBlank(button.getBeforeScript())) {
			if (NodeButton.SCRIPT_TYPE_URL.equals(button.getBeforeScriptType())) {
				
				Map<String, Object> root = new HashMap<>();
				root.put("loginUser", loginUser);
				root.put("loginUserId", loginUser.getUserId());
				root.put("processInstanceId", processInstance.getInstanceId());
				root.put("processDefinitionId", processInstance.getProcessDefinitionId());
				root.put("businessKey", processInstance.getBusinessKey());
				root.put("nextTaskCandidateUserIds", context.getNextTaskCandidateUserIds());
				root.put("action", opinion.getApproveAction());

				
				if (NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE.equals(opinion.getApproveAction())) { // 如果用户点击"退回到指定结点",选择退回到拟稿节点,内置变量驳回类型：actionRejectType=0  就是驳回到拟稿节点
					if (opinion.getRejectToChooseNodeTaskKey().equals(draftNode.getTaskKey())) {
						root.put("actionRejectType", Node.TASK_BIZ_TYPE_DRAFTNODE);
						root.put("actionRejectTypeDesc", "驳回到拟稿节点");
					} else {
						root.put("actionRejectType", Node.TASK_BIZ_TYPE_UNDRAFTNODE);
						root.put("actionRejectTypeDesc", "驳回到非拟稿节点");
					}
				}
				
				printMapExcludeLoginUser(root);
				doExecuteFreemarkUrlScript(root, button.getBeforeScript());
				
			} else if (NodeButton.SCRIPT_TYPE_JAVASCRIPT_CODE.equals(definition.getAfterScriptType())) {

			} else if (NodeButton.SCRIPT_TYPE_JAVA_CODE.equals(definition.getAfterScriptType())) {

			} else {

			}
		}
		log.debug(" ------------------------------- 执行按钮前置角本，instance=" + processInstance.getInstanceId() + "（回调）结束：--------------------------------------");

		
		return null;
	}



	

	/**
	 * 执行按钮前后置角本
	 * @param context
	 * @return
	 */
	public <T> T executeButtonAfterScript(ActRunningContext context) {
		final RunInstance processInstance = context.getCurrProcessInstance();
		final ReDefinition definition = context.getCurrReDefinion();
		final Task runingCurrTask = context.getRuningCurrTask();
		final ApproveOpinion opinion = context.getApproveOpinion();
		final Db db = context.getDb();
		final Node draftNode = context.getDraftNode();
		final User loginUser = context.getLoginUser();

		log.debug(" ------------------------------- 执行按钮后置角本，instance=" + processInstance.getInstanceId() + " 流程节点="+runingCurrTask.getTaskDefinitionKey()+"（回调）开始：--------------------------------------");

		NodeButtonQuery query = new NodeButtonQuery();
		query.setDefinitionId(processInstance.getProcessDefinitionId());
		query.setTaskKey(runingCurrTask.getTaskDefinitionKey());

		if (!opinion.getApproveAction().startsWith("button_type_")) {
			query.setBtnCode("button_type_" + opinion.getApproveAction());
		} else {
			query.setBtnCode(opinion.getApproveAction());
		}
		NodeButton button = db.queryForObject("queryForPage", NodeButton.class, query);
		if(button == null) {
			log.info("节点"+runingCurrTask.getTaskDefinitionKey()+"没有 配置按钮");
			log.debug(" ------------------------------- 执行按钮后置角本，instance=" + processInstance.getInstanceId() + "（回调）结束！--------------------------------------");

			return null;
		}
		
		log.debug("AfterScript="+button.getAfterScript()+"  AfterScriptType="+button.getAfterScriptType());
		
		if (StringUtil.isNotBlank(button.getAfterScript())) {
			if (NodeButton.SCRIPT_TYPE_URL.equals(button.getAfterScriptType())) {
				
				Map<String, Object> root = new HashMap<>();
				root.put("loginUser", loginUser);
				root.put("loginUserId", loginUser.getUserId());
				root.put("processInstanceId", processInstance.getInstanceId());
				root.put("processDefinitionId", processInstance.getProcessDefinitionId());
				root.put("businessKey", processInstance.getBusinessKey());
				root.put("nextTaskCandidateUserIds", context.getNextTaskCandidateUserIds());
				root.put("action", opinion.getApproveAction());
				root.put("isInstanceEnded", TaskQueryUtil.isInstanceEnded(processInstance.getInstanceId()));
				
				if (NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE.equals(opinion.getApproveAction())) { // 如果用户点击"退回到指定结点",选择退回到拟稿节点,内置变量驳回类型：actionRejectType=0  就是驳回到拟稿节点
					if (opinion.getRejectToChooseNodeTaskKey().equals(draftNode.getTaskKey())) {
						root.put("actionRejectType", Node.TASK_BIZ_TYPE_DRAFTNODE);
						root.put("actionRejectTypeDesc", "驳回到拟稿节点");
					} else {
						root.put("actionRejectType", Node.TASK_BIZ_TYPE_UNDRAFTNODE);
						root.put("actionRejectTypeDesc", "驳回到非拟稿节点");
					}
				}

				printMapExcludeLoginUser(root);
				doExecuteFreemarkUrlScript(root, button.getAfterScript());
				
			} else if (NodeButton.SCRIPT_TYPE_JAVASCRIPT_CODE.equals(definition.getAfterScriptType())) {

			} else if (NodeButton.SCRIPT_TYPE_JAVA_CODE.equals(definition.getAfterScriptType())) {

			} else {

			}
		}
		
		log.debug(" ------------------------------- 执行按钮后置角本，instance=" + processInstance.getInstanceId() + "（回调）结束！--------------------------------------");

		return null;
	}
 
	private void printMapExcludeLoginUser(Map<String, Object> root) {
		if (root == null) {
			log.error("root对象不能为空");
			return;
		}

		Map<String, Object> debugMap = new HashMap<>();
		debugMap.putAll(root);
		debugMap.remove("loginUser");

		log.debug("参数: " + JSON.toJSONString(debugMap, true));
	}
	
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 节点脚本执行 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	/**
	 * 执行节点前置回调角本
	 * @param context
	 * @return
	 */
	public <T> T executeNodeBeforeScript(ActRunningContext context) {
		
		final RunInstance processInstance = context.getCurrProcessInstance();
		final ReDefinition definition = context.getCurrReDefinion();
		final Task runingCurrTask = context.getRuningCurrTask();
		final ApproveOpinion opinion = context.getApproveOpinion();
		final Db db = context.getDb();
		final Node draftNode = context.getDraftNode();
		final User loginUser = context.getLoginUser();

		log.debug(" ------------------------------- 执行节点前置角本，instance=" + processInstance.getInstanceId() + "（回调）开始：--------------------------------------");

		
		NodeQuery query = new NodeQuery();
		query.setDefinitionId(TaskQueryUtil.getPreparedDefinitionId(context));
		query.setTaskKey(runingCurrTask.getTaskDefinitionKey());
		Node node = db.queryForObject("queryForPage", Node.class, query);
		
		if(node == null) {
			log.info("节点"+runingCurrTask.getTaskDefinitionKey()+"没有 配置按钮");
			log.debug(" ------------------------------- 执行节点前置角本，instance=" + processInstance.getInstanceId() + "（回调）结束！--------------------------------------");
			return null;
		}
		
		if (StringUtil.isNotBlank(node.getBeforeScript())) {
			if (Node.SCRIPT_TYPE_URL.equals(node.getBeforeScriptType())) {
				
				Map<String, Object> root = new HashMap<>();
				root.put("loginUser", loginUser);
				root.put("loginUserId", loginUser.getUserId());
				root.put("processInstanceId", processInstance.getInstanceId());
				root.put("processDefinitionId", processInstance.getProcessDefinitionId());
				root.put("businessKey", processInstance.getBusinessKey());
 
				
				printMapExcludeLoginUser(root);
				doExecuteFreemarkUrlScript(root, node.getBeforeScript());
				
			} else if (NodeButton.SCRIPT_TYPE_JAVASCRIPT_CODE.equals(node.getBeforeScriptType())) {

			} else if (NodeButton.SCRIPT_TYPE_JAVA_CODE.equals(node.getBeforeScriptType())) {

			} else {

			}
		}
		log.debug(" ------------------------------- 执行节点前置角本，instance=" + processInstance.getInstanceId() + "（回调）结束：--------------------------------------");

		
		return null;
	}
	
	
	/**
	 * 执行节点前置回调角本
	 * @param context
	 * @return
	 */
	public <T> T executeNodeAfterScript(ActRunningContext context) {
		
		final RunInstance processInstance = context.getCurrProcessInstance();
		final ReDefinition definition = context.getCurrReDefinion();
		final Task runingCurrTask = context.getRuningCurrTask();
		final ApproveOpinion opinion = context.getApproveOpinion();
		final Db db = context.getDb();
		final Node draftNode = context.getDraftNode();
		final User loginUser = context.getLoginUser();

		log.debug(" ------------------------------- 执行节点后置角本，instance=" + processInstance.getInstanceId() + "（回调）开始：--------------------------------------");

		
		NodeQuery query = new NodeQuery();
		query.setDefinitionId(TaskQueryUtil.getPreparedDefinitionId(context));
		query.setTaskKey(runingCurrTask.getTaskDefinitionKey());
		Node node = db.queryForObject("queryForPage", Node.class, query);
		
		if(node == null) {
			log.debug("节点"+runingCurrTask.getTaskDefinitionKey()+"没有配置后置角本");
			log.debug(" ------------------------------- 执行节点后置角本，instance=" + processInstance.getInstanceId() + "（回调）结束！--------------------------------------");
			return null;
		}
		
		if (StringUtil.isNotBlank(node.getAfterScript())) {
			if (Node.SCRIPT_TYPE_URL.equals(node.getAfterScriptType())) {
				
				Map<String, Object> root = new HashMap<>();
				root.put("loginUser", loginUser);
				root.put("loginUserId", loginUser.getUserId());
				root.put("processInstanceId", processInstance.getInstanceId());
				root.put("processDefinitionId", processInstance.getProcessDefinitionId());
				root.put("businessKey", processInstance.getBusinessKey());
 
				
				printMapExcludeLoginUser(root);
				doExecuteFreemarkUrlScript(root, node.getAfterScript());
				
			} else if (NodeButton.SCRIPT_TYPE_JAVASCRIPT_CODE.equals(node.getAfterScriptType())) {

			} else if (NodeButton.SCRIPT_TYPE_JAVA_CODE.equals(node.getAfterScriptType())) {

			} else {

			}
		}
		log.debug(" ------------------------------- 执行节点前置角本，instance=" + processInstance.getInstanceId() + "（回调）结束：--------------------------------------");

		
		return null;
	}
	
	/**
	 * 执行节点一次性角本
	 * @param context
	 * @return
	 */
	public <T> T executeOnceScript(ActRunningContext context) {
		
		final RunInstance processInstance = context.getCurrProcessInstance();
		final ReDefinition definition = context.getCurrReDefinion();
		///final Task runingCurrTask = context.getRuningCurrTask();
		final Task inputTask = context.getInputTask();
		final ApproveOpinion opinion = context.getApproveOpinion();
		final Db db = context.getDb();
		final Node draftNode = context.getDraftNode();
		final User loginUser = context.getLoginUser();

		log.debug(" ------------------------------- 执行节点后置角本，instance=" + processInstance.getInstanceId() + "（回调）开始：--------------------------------------");

		
		NodeQuery query = new NodeQuery();
		query.setDefinitionId(TaskQueryUtil.getPreparedDefinitionId(context));
		query.setTaskKey(inputTask.getTaskDefinitionKey());
		Node node = db.queryForObject("queryForPage", Node.class, query);
		
		if(node == null) {
			log.debug("节点"+inputTask.getTaskDefinitionKey()+"没有配置后置角本");
			log.debug(" ------------------------------- 执行节点后置角本，instance=" + processInstance.getInstanceId() + "（回调）结束！--------------------------------------");
			return null;
		}
		
		if (StringUtil.isNotBlank(node.getOnceScriptType())) {
			if (Node.SCRIPT_TYPE_URL.equals(node.getOnceScriptType())) {
				
				Map<String, Object> root = new HashMap<>();
				root.put("loginUser", loginUser);
				root.put("loginUserId", loginUser.getUserId());
				root.put("processInstanceId", processInstance.getInstanceId());
				root.put("processDefinitionId", processInstance.getProcessDefinitionId());
				root.put("businessKey", processInstance.getBusinessKey());
 
				
				printMapExcludeLoginUser(root);
				doExecuteFreemarkUrlScript(root, node.getAfterScript());
				
			} else if (Node.SCRIPT_TYPE_JAVASCRIPT_CODE.equals(node.getOnceScriptType())) {

			} else if (Node.SCRIPT_TYPE_JAVA_CODE.equals(node.getOnceScriptType())) {

			} else {

			}
		}
		log.debug(" ------------------------------- 执行节点一次性角本，instance=" + processInstance.getInstanceId() + "（回调）结束：--------------------------------------");

		
		return null;
	}
}
