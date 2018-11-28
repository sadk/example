package activiti;

import static org.junit.Assert.assertNotNull;

import java.text.SimpleDateFormat;
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
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricFormProperty;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Test;
import org.lsqt.act.ActUtil;
import org.lsqt.components.util.lang.StringUtil;


public class PayTest extends AbstractTest{
	static IdentityService identityService = ActUtil.getIdentityService();
	static RepositoryService repositoryService = ActUtil.getRepositoryService();
	static FormService formService = ActUtil.getFormService();
	static HistoryService historyService = ActUtil.getHistoryService();
	static TaskService taskService = ActUtil.getTaskService();
	static RuntimeService runtimeService = ActUtil.getRuntimeService();
	//@Test
	//@Deployment(resources="diagrams/pay.bpmn20.xml")
	public static void main(String[] args) {
		if(true) {
			Task task = taskService.createTaskQuery().taskDefinitionKey("usertask3").singleResult();
			System.out.println(task.getId()+" ========= " + task.getTaskDefinitionKey());
			
			Map<String,Object> vars = new HashMap();
			vars.put("jlgcsApprove", "true");
			complete("80005", task.getId(), "哈哈2222-jlgcsApprove", vars);
			return ;
		}
		
		
		repositoryService.createDeployment().addClasspathResource("diagrams/pay.bpmn20.xml").name("付款审批流程").category("办公流程").tenantId("1000").deploy();
		
		String currentUserId = "admin";
		identityService.setAuthenticatedUserId(currentUserId);
		
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
		
		Map<String, String> formMap = new HashMap<>();
		formMap.put("applyUserId", currentUserId);
		formMap.put("applyUserName", currentUserId);
		formMap.put("projectName", "项目名称");
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
		String businessKey = "1";
		
		// 启动流程
		ProcessInstance processInstance = formService.submitStartFormData(processDefinition.getId(),businessKey,formMap);
		assertNotNull(processInstance);
		System.out.println("流程实例ID: "+processInstance.getId());
		Task task = taskService.createTaskQuery().taskDefinitionKey("usertask2").singleResult();
		System.out.println(task);
		//TaskFormData data = formService.getTaskFormData(taskId);
		
		
		if(processInstance.isEnded()) { // 获取已提交的表单数据,(流程必须要走完？）
			Map<String,String> dataMap = new HashMap<>();
			StartFormData data= formService.getStartFormData(processInstance.getId());
			System.out.println(data.getFormProperties());
			
			
			/*
			List<HistoricDetail> list = historyService.createHistoricDetailQuery().processInstanceId(processInstance.getId()).list();
			for(HistoricDetail e: list) {
				if(e instanceof HistoricFormProperty) { // 表单中的字段
					HistoricFormProperty field = (HistoricFormProperty) e;
					dataMap.put(field.getPropertyId(), field.getPropertyValue());
				}
				else if(e instanceof HistoricDetailVariableInstanceUpdateEntity) {
					HistoricDetailVariableInstanceUpdateEntity t = (HistoricDetailVariableInstanceUpdateEntity)e;
					dataMap.put(t.getName(), t.getTextValue());
				}
			}
			System.out.println(dataMap);
			 */
		}
	}
	
	public static void complete(String processInstanceId,String taskId,String opinion,Map<String,Object> vars) {
		
		StartFormData formData = formService.getStartFormData("pay:3:80004");
		if(formData!=null) {
			List<FormProperty> props = formData.getFormProperties();
			for(FormProperty p: props) {
				System.out.println(p.getName() + "==" + p.getValue());
			}
		}
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
		if(StringUtil.isNotBlank(opinion)) {
			taskService.addComment(taskId, processInstanceId, opinion);
		}
		task.setAssignee("admin");
		taskService.complete(taskId, vars);
	}
}
