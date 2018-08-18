package org.lsqt.act.service.support;

import java.util.Map;

import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;

/**
 * 流程任意跳转核心类
 * @author mmyuan
 *
 */
public class JumpTaskCommand implements Command<java.lang.Void> {
	   
	protected String executionId;
	protected String activityId;
	protected Map<String, Object> variables;

	public JumpTaskCommand(String executionId, String activityId) {
		this.executionId = executionId;
		this.activityId = activityId;
	}

	public JumpTaskCommand(String executionId, String activityId, Map<String, Object> variables) {
		this.executionId = executionId;
		this.activityId = activityId;
		this.variables = variables;
	}
 
	@Override
	public Void execute(CommandContext commandContext) {
		for (TaskEntity taskEntity : Context.getCommandContext().getTaskEntityManager().findTasksByExecutionId(executionId)) {
			Context.getCommandContext().getTaskEntityManager().deleteTask(taskEntity, "jump", false);
		}
		ExecutionEntity executionEntity = Context.getCommandContext().getExecutionEntityManager().findExecutionById(executionId);
		ProcessDefinitionImpl processDefinition = executionEntity.getProcessDefinition();
		ActivityImpl activity = processDefinition.findActivity(activityId);

		activity.setVariables(variables);
		executionEntity.executeActivity(activity);
		return null;
	}
}