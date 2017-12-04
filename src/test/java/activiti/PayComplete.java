package activiti;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.lsqt.act.ActUtil;

public class PayComplete {
	
	static IdentityService identityService = ActUtil.getIdentityService();
	static RepositoryService repositoryService = ActUtil.getRepositoryService();
	static FormService formService = ActUtil.getFormService();
	static HistoryService historyService = ActUtil.getHistoryService();
	static TaskService taskService = ActUtil.getTaskService();
	static RuntimeService runtimeService = ActUtil.getRuntimeService();
	
	public static void main(String[] args) {
		Task task = taskService.createTaskQuery().taskId("197508").singleResult();
		
		Map<String, Object> variables = new HashMap<>();
		variables.put("usertask3", Arrays.asList("sky60","sky61")); // 设置监理工程师节点用户
		variables.put("approveResult", "false");
		taskService.addComment(task.getId(), task.getProcessInstanceId(), "这里是监理工程师的意见");
		taskService.complete(task.getId(), variables);
	}
}
