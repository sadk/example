package org.lsqt.activiti;

import java.util.Collection;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
public class Init {
	
	public static void main(String[] args) {
		// 1.创建Activiti配置对象的实例
		ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();

		// 2.设置数据库连接信息
		// 设置数据库地址
		configuration.setJdbcUrl("jdbc:mysql://172.31.118.73:3306/payment");
		// 数据库驱动
		configuration.setJdbcDriver("com.mysql.jdbc.Driver");
		// 用户名
		configuration.setJdbcUsername("root");
		// 密码
		configuration.setJdbcPassword("123456");

		// 如果不存在表就创建表，存在就直接使用
		configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

		// 3.使用配置对象创建流程引擎实例（检查数据库连接等环境信息是否正确）

		ProcessEngine processEngine = configuration.buildProcessEngine();
		BpmnModel model = processEngine.getRepositoryService().getBpmnModel("pay");
		if(model != null) {  
		    Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();  
		    for(FlowElement e : flowElements) {  
		        System.out.println("flowelement id:" + e.getId() + "  name:" + e.getName() + "   class:" + e.getClass().toString());  
		    }  
		}  
	}
}
