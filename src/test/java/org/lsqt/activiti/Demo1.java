package org.lsqt.activiti;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public class Demo1 {
	public static void main(String[] args) {
		ProcessEngine processEngine = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
		ProcessEngines.init();
		
		RepositoryService repositoryService = processEngine.getRepositoryService();
		RuntimeService runtimeService = processEngine.getRuntimeService();

		// Deploy the process definition
		// 部署相关的流程配置
		repositoryService.createDeployment().addClasspathResource("diagrams/financialReport.bpmn20.xml").deploy();

		// Start a process instance
		// 获取流程实例
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("financialReport");
		System.out.println("流程实例ID：" + pi.getId());//流程实例ID：101  
		System.out.println("流程实例ID：" + pi.getProcessInstanceId());//流程实例ID：101  
		System.out.println("流程定义ID:" + pi.getProcessDefinitionId());//myMyHelloWorld:1:4  

		
		// Get the first task
		TaskService taskService = processEngine.getTaskService();
		
		// 获取accountancy组可能要操作的任务
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("accountancy").list();
		for (Task task : tasks) {
			System.out.println("Following task is available for accountancy group: " + task.getName());
			System.out.println("任务名称:"+task.getName()+"流程定义ID:"+task.getProcessDefinitionId());
			System.out.println("流程实例ID:"+task.getProcessInstanceId()+"流程任务ID:"+task.getId());
			// 设置fozzie代办 claim it
			//taskService.claim(task.getId(), "fozzie");
		}

		//processEngine.getTaskService().complete("5008");
	}
}
