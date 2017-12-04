package org.lsqt.activiti;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;

public class Demo2 {
	public static void main(String[] args) throws IOException {
		ProcessEngine processEngine = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();

		
		// Deploy the process definition
				// 部署相关的流程配置
		processEngine.getRepositoryService().createDeployment().addClasspathResource("diagrams/pay.bpmn20.xml").name("mmyuan的发布").category("办公流程").tenantId("1000").deploy();
		
		
		List<ProcessDefinition> list1 = processEngine.getRepositoryService().createProcessDefinitionQuery()
				// 查询条件
				.processDefinitionKey("pay")// 按照流程定义的key
				// .processDefinitionId("helloworld")//按照流程定义的ID
				.orderByProcessDefinitionVersion().desc()// 排序
				// 返回结果
				// .singleResult()//返回惟一结果集
				// .count()//返回结果集数量
				// .listPage(firstResult, maxResults)
				.list();// 多个结果集

		String deploymentId = null ;
		if (list1 != null && list1.size() > 0) {
			for (ProcessDefinition pd : list1) {
				System.out.println("流程定义的ID：" + pd.getId());
				System.out.println("流程定义的名称：" + pd.getName());
				System.out.println("流程定义的Key：" + pd.getKey());
				System.out.println("流程定义的部署ID：" + pd.getDeploymentId());
				System.out.println("流程定义的资源名称：" + pd.getResourceName());
				System.out.println("流程定义的版本：" + pd.getVersion());
				System.out.println("########################################################");
				
				deploymentId = pd.getDeploymentId();
			}
		}
		
	//	processEngine.getRepositoryService().deleteDeployment("2501", true);
		
		
		
	
		
		// 获取的资源名称
		List<String> list = processEngine.getRepositoryService().getDeploymentResourceNames(deploymentId);
		// 获得资源名称后缀.png
		String resourceName = "";
		if (list != null && list.size() > 0) {
			for (String name : list) {
				if (name.indexOf(".png") >= 0) {// 返回包含该字符串的第一个字母的索引位置
					resourceName = name;
				}
			}
		}

		// 获取输入流，输入流中存放.PNG的文件
		InputStream in = processEngine.getRepositoryService().getResourceAsStream(deploymentId, resourceName);

		// 将获取到的文件保存到本地
		FileUtils.copyInputStreamToFile(in, new File("D:/" + resourceName));

		System.out.println("文件保存成功！"); 
	}
}
