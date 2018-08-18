package org.lsqt.act.service.support;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.lsqt.act.ActUtil;
import org.lsqt.act.model.ActRunningContext;
import org.lsqt.act.model.ApproveObject;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.NodeButton;
import org.lsqt.act.model.RunInstance;
import org.lsqt.act.model.RunTaskAssignForwardCc;
import org.lsqt.act.model.RunTaskAssignForwardCcQuery;
import org.lsqt.act.model.Task;
import org.lsqt.components.db.Db;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.uum.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 常规流转
 * @author mmyuan
 *
 */
public class GeneralCompleteHandler {
	private static final Logger  log = LoggerFactory.getLogger(GeneralCompleteHandler.class);
	
	ActRunningContext context;

	public void setActRunningConext(ActRunningContext context) {
		this.context = context;
	}

	public void handle() {
		final org.activiti.engine.TaskService actTaskService = ActUtil.getTaskService();
		final ApproveOpinion opinion = context.getApproveOpinion();
		final Task inputTask = context.getInputTask();
		final User loginUser = context.getLoginUser();
		final Map<String, List<ApproveObject>> nodeUserMap = context.getNodeUserMap();
		final DefaultScriptExecutor scriptExecutor = new DefaultScriptExecutor();
		final Db db = context.getDb();
		final RunInstance processInstance= context.getCurrProcessInstance();
		
		//执行节点前置脚本和一次性脚本
		scriptExecutor.executeNodeBeforeScript(context);
		scriptExecutor.executeOnceScript(context);
		
		//执行前置脚本
		scriptExecutor.executeGlobalBeforeScript(context);
		scriptExecutor.executeButtonBeforeScript(context);
		
		
		log.debug("常规同意: "+JSON.toJSONString(context.getCompleteVariable(),true));
		actTaskService.complete(context.getRuningCurrTask().getId(), context.getCompleteVariable());
		
		TaskQueryUtil.doAutoJumpIfExist(context); //假定流程未结束还有自动跳过
		
		
		boolean isEnded = TaskQueryUtil.isInstanceEnded(processInstance.getInstanceId());
		if (isEnded) {
			context.setNextTaskCandidateUserIds(StringUtil.EMPTY);
		} else {
			context.setNextTaskCandidateUserIds(TaskQueryUtil.getNextTaskCandidateUserIds(context));
			
			if (StringUtil.isBlank(context.getNextTaskCandidateUserIds())) {
				String msg = "";
				if(context.getRuningCurrTask()!=null) {
					msg = "-结点名称="+context.getRuningCurrTask().getName()+",编码="+context.getRuningCurrTask().getTaskDefinitionKey();
				}
				throw new RuntimeException("没有找到审批用户"+msg);
			}
		}
		
		//执行后置脚本
		scriptExecutor.executeGlobalAfterScript(context);  
		log.debug("输入任务="+inputTask.getTaskDefinitionKey()+" -- 当前正在执行的任务="+context.getRuningCurrTask().getTaskDefinitionKey());
		if(isEnded) {
			log.debug(" --- 流程已结束，准备执行结束按钮后置角本"+" -- inputTask="+inputTask.getTaskDefinitionKey() +" -- currRunningTask="+context.getRuningCurrTask());
			scriptExecutor.executeButtonAfterScript(context);
		}
		
	}
}
