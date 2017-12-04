package org.lsqt.activiti;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.test.AbstractActivitiTestCase;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.junit.Test;

/**
 * 付款审批，代码驱动测试
 * @author 
 *
 */
public class DemoPay extends AbstractActivitiTestCase{
	public static void main(String[] args) {
		// 1.创建Activiti配置对象的实例
		ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();

		configuration.setJdbcUrl("jdbc:mysql://localhost:3306/test");
		 
		configuration.setJdbcDriver("com.mysql.jdbc.Driver");
		 
		configuration.setJdbcUsername("root");
		 
		configuration.setJdbcPassword("123456");

		// 如果不存在表就创建表，存在就直接使用
		configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

		// 2.使用配置对象创建流程引擎实例（检查数据库连接等环境信息是否正确）
		ProcessEngine processEngine = configuration.buildProcessEngine();
		
		
		RepositoryService repositoryService = processEngine.getRepositoryService();
		
		repositoryService.createDeployment().addClasspathResource("diagrams/pay.bpmn20.xml").name("mmyuan的布署付款流程").category("办公流程").tenantId("1000").deploy();
		 
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionVersion().desc().list();
		System.out.println(list.size());
		for(ProcessDefinition e: list) {
			System.out.println("name:"+e.getName() + " version "+e.getVersion());
		}
		
		
		
		List<ProcessInstance> list2= processEngine.getRuntimeService().createProcessInstanceQuery().listPage(0, 20);
		for(ProcessInstance e : list2) {
			System.out.println("instance:"+e.getId());
		}
		System.out.println(list2);
	}

	@Override
	protected void initializeProcessEngine() {
		
		
	}
	
	@Test
	@Deployment(resources="/diagrams/financialReport.bpmn20.xml")
	public void test() {
		String currentUserId = "admin";
		identityService.setAuthenticatedUserId(currentUserId);
		ProcessDefinition def = repositoryService.createProcessDefinitionQuery().processDefinitionKey("financialReport").singleResult();
		
	}
}
