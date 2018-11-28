package activiti;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.lsqt.act.ActUtil;
import org.lsqt.components.util.lang.StringUtil;

public class PayTest2 {
	
	static IdentityService identityService = ActUtil.getIdentityService();
	static RepositoryService repositoryService = ActUtil.getRepositoryService();
	static FormService formService = ActUtil.getFormService();
	static HistoryService historyService = ActUtil.getHistoryService();
	static TaskService taskService = ActUtil.getTaskService();
	static RuntimeService runtimeService = ActUtil.getRuntimeService();
	
	static String currentUserId = "admin16";
	public static void main(String[] args) {

		identityService.setAuthenticatedUserId(currentUserId);
		
		repositoryService.createDeployment().addClasspathResource("diagrams/pay.bpmn20.xml").name("付款审批流程").category("办公流程").tenantId("1000").deploy();
		
		
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("pay").orderByProcessDefinitionVersion().desc().latestVersion().list().get(0);
		BpmnModel model = repositoryService.getBpmnModel(processDefinition.getId());  
		if(model != null) {  
		    Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();  
		    for(FlowElement e : flowElements) {  
		    	if(org.activiti.bpmn.model.UserTask.class.isAssignableFrom(e.getClass())) {
		    		UserTask task = (UserTask)e;
		    		//System.out.println(task.getId() + task.getFormKey()+"  "+task.getName());
		    		task.setAssignee(currentUserId);
		    		
		    		System.out.println("flowelement id:" + e.getId() + "  name:" + e.getName() + "   class:" + e.getClass().toString());
		    	}
		    }  
		}
		
		Map<String, Object> formMap = new HashMap<>();
		formMap.put("applyUserId", currentUserId);
		formMap.put("applyUserName", currentUserId);
		formMap.put("projectName", "项目名称xxxxxxxxxxx");
		formMap.put("contactNum", "合同编号");
		formMap.put("progressPayMonth", "进度款月份:三月");
		formMap.put("payRemark", "付款说明");

		formMap.put("createBy", "1");
		formMap.put("createByName", currentUserId);
		formMap.put("createDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

		formMap.put("updateBy", "1");
		formMap.put("updateByName", currentUserId);
		formMap.put("updateDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		// 保存业务表单
		String businessKey = "56"; // 表单数据ID

		//设置合约经办人节点用户
		Map<String, Object> variables = new HashMap<>();
		variables.put("applyUserId", currentUserId);
		variables.put("usertask2", Arrays.asList("sky30","sky31"));
		
		// 起动流程
		ProcessInstance instance = runtimeService.startProcessInstanceById(processDefinition.getId(),businessKey,variables);
		System.out.println(" --- 流程实例: "+ instance.getId());
		
		/*// 自动完成拟稿任务
		List<Task> list = taskService.createTaskQuery().taskCandidateOrAssigned(currentUserId).list();
		for (Task e : list) {
			if (e.getTaskDefinitionKey().equals(instance.getActivityId())) {
				
				
				System.out.println(" --- 准备完成任务=> " + e.getId() +":"+ e.getName());
				taskService.complete(e.getId(), variables);
				break;
			}
		}*/
		
		/*
		// 获取当前用户待办完成 “合约经办人审核”节点
		list = taskService.createTaskQuery().taskCandidateOrAssigned(currentUserId).list();
		for(Task e: list) {
			if(e.getTaskDefinitionKey().equals("usertask2")) {
				Map<String, Object> variables = new HashMap<>();
				variables.put("approveResult", "true");

				System.out.println(" --- 准备完成任务: " + e.getId() +":"+ e.getName());
				taskService.complete(e.getId(), variables);
				break;
			}
		}
*/
	}
	
}
