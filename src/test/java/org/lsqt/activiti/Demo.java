package org.lsqt.activiti;

import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;


public class Demo {
	public static void getDefinitionList(RepositoryService repositoryService) {
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery() ;
	    		//.latestVersion().orderByProcessDefinitionKey().asc();
		
		
		List<ProcessDefinition> list = processDefinitionQuery.orderByProcessDefinitionVersion().desc().list();
		for(ProcessDefinition e: list) {
			System.out.println("流程定义名称:"+e.getName()+"  流程定义版本:"+e.getVersion() + "  流程定义Key:" + e.getKey());
			
			/*processDefinitionQuery.processDefinitionCategoryLike("category").processDefinitionId("id").processDefinitionKeyLike("key")
			.processDefinitionNameLike("name")
			.processDefinitionResourceNameLike("resname").processDefinitionTenantId("appcode").processDefinitionVersion(234);
			*/
		}
		/*
		String category = null; // 流程分类
		if (StringUtil.isNotBlank(category)) {
			processDefinitionQuery.processDefinitionCategory(category);
		}
		*/
	}
	public static void main(String[] args) {
		ProcessEngine processEngine = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
		ProcessEngines.init();
		
		RepositoryService repositoryService = processEngine.getRepositoryService();
		
		//
		//getDefinitionList(repositoryService);
		//if(true) return ;
		RuntimeService runtimeService = processEngine.getRuntimeService();

		// Deploy the process definition
		// 部署相关的流程配置
		repositoryService.createDeployment().addClasspathResource("diagrams/financialReport.bpmn20.xml").name("mmyuan的发布").category("办公流程").tenantId("1000").deploy();

		// Start a process instance
		// 获取流程实例
		String procId = runtimeService.startProcessInstanceByKey("financialReport").getId();
		
		// Get the first task
		TaskService taskService = processEngine.getTaskService();
		
		// 获取accountancy组可能要操作的任务
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("accountancy").list();
		for (Task task : tasks) {
			System.out.println("Following task is available for accountancy group: " + task.getName());

			// 设置fozzie代办 claim it
			taskService.claim(task.getId(), "fozzie");
		}

		// Verify Fozzie can now retrieve the task
		// 审核fozzie当前的获取的任务数量
		tasks = taskService.createTaskQuery().taskAssignee("fozzie").list();
		for (Task task : tasks) {
			System.out.println("Task for fozzie: " + task.getName());

			// Complete the task
			// 设置forzze完毕
			taskService.complete(task.getId());
		}

		System.out.println("Number of tasks for fozzie: " + taskService.createTaskQuery().taskAssignee("fozzie").count());

		// Retrieve and claim the second task
		// 管理者审核报告并让kermit代办
		tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
		for (Task task : tasks) {
			System.out.println("Following task is available for accountancy group: " + task.getName());
			taskService.claim(task.getId(), "kermit");
		}

		// Completing the second task ends the process
		// 完成报告
		for (Task task : tasks) {
			taskService.complete(task.getId());
			
			
		}

		// verify that the process is actually finished
		// 查询流程实例完成事件
		HistoryService historyService = processEngine.getHistoryService();
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(procId).singleResult();
		System.out.println("Process instance end time: " + historicProcessInstance.getEndTime());
	}
}
