package activiti;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.lsqt.act.ActUtil;

/**
 * 简单的报销申请
 * @author mmyuan
 *
 */
public class FinancialTest {
	private static String KEY_财务报销="financial" ;
	
	public static void main(String[] args) {
		//deploy();
		start();
		//complete();
		//reject();
	}
	
	public static void reject() {
		String taskId="162502";
		
		Task task = ActUtil.getTaskService().createTaskQuery().taskId(taskId).singleResult();
		
		TaskServiceImpl taskServiceImpl=(TaskServiceImpl)ActUtil.getTaskService();
	 
		taskServiceImpl.getCommandExecutor().execute(new JumpTaskCmd(task.getExecutionId(), "jlsp"));  
	}

	public static void complete() {
		String taskId="252519";
		
		Integer money = 1500;
		String action="agree";
		
		Map<String,Object> variables= new HashMap<>();
		variables.put("money",money);
		variables.put("action", action);
		
		Task task = ActUtil.getTaskService().createTaskQuery().taskId(taskId).singleResult();
		
		org.activiti.engine.runtime.ProcessInstance inst = ActUtil.getRuntimeService().createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
		System.out.println(" --- 获取整个实例的流程变量: "+inst.getProcessVariables());
		
		System.out.println(" --- 获取当前任务流程变量："+task.getProcessVariables());
		
	
		System.out.println(" --- 获取流程变量3： " + ActUtil.getTaskService().getVariables(task.getId()));
		ActUtil.getTaskService().setVariable(task.getId(), "startTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		
		System.out.println(" --- completing:"+task);
		ActUtil.getTaskService().complete(taskId,variables);
	}
	
	
	
	public static void start() {
		ProcessDefinition def = ActUtil.getRepositoryService().createProcessDefinitionQuery().latestVersion()
				.processDefinitionKey(KEY_财务报销).singleResult();
		
		Map<String,Object> variables = new HashMap<>();
		variables.put("money", 1500);
		variables.put("deptName", "研发部");
		variables.put("applyUserName", "张三");
		variables.put("startTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		ProcessInstance instance = ActUtil.getRuntimeService().startProcessInstanceById(def.getId(),"63",variables);
		//instance.getProcessVariables().putAll(variables);
		System.out.println(" --- 流程启动成功："+instance.getActivityId() + " = "+instance.getId()+"  ==> 流程定义ID="+def.getId());
	}
	
	public static void deploy() {
		Deployment dep = ActUtil.getRepositoryService().createDeployment().addClasspathResource("diagrams/financial.bpmn20.xml")
				.name("sky的测试").category("办公流程").tenantId("1000").deploy();
		
		System.out.println(" ---- 布署成功："+dep.getId());
	}
	
	
	public static class JumpTaskCmd implements Command<java.lang.Void> {
	   
		protected String executionId;  
	    protected String activityId;  
	      
	    
	    public JumpTaskCmd(String executionId, String activityId) {  
	        this.executionId = executionId;  
	        this.activityId = activityId;  
	    }  
	    
		@Override
		public Void execute(CommandContext commandContext) {
	        for (TaskEntity taskEntity : Context.getCommandContext().getTaskEntityManager().findTasksByExecutionId(executionId)) {  
	            Context.getCommandContext().getTaskEntityManager().deleteTask(taskEntity, "jump", false);  
	        }
	        ExecutionEntity executionEntity = Context.getCommandContext().getExecutionEntityManager().findExecutionById(executionId);  
	        ProcessDefinitionImpl processDefinition = executionEntity.getProcessDefinition();  
	        ActivityImpl activity = processDefinition.findActivity(activityId);  
	        executionEntity.executeActivity(activity);  
	        return null;  
	    }
	}
}
