package activiti;

import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.lsqt.act.ActUtil;
import org.lsqt.components.util.lang.StringUtil;

/**
 * 流程定义的相关接口
 * @author yuanmm
 */
public class DefinitionTest {
	public static void main(String[] args) {
		RepositoryService repositoryService = ActUtil.getRepositoryService();
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		// .latestVersion().orderByProcessDefinitionKey().asc();

		List<ProcessDefinition> list = processDefinitionQuery.orderByProcessDefinitionVersion().desc().list();
		for (ProcessDefinition e : list) {
			System.out.println("流程定义名称:" + e.getName() + "  流程定义版本:" + e.getVersion() + "  流程定义Key:" + e.getKey());

			
			processDefinitionQuery.processDefinitionCategoryLike("category").processDefinitionId("id")
					.processDefinitionKeyLike("key").processDefinitionNameLike("name")
					.processDefinitionResourceNameLike("resname").processDefinitionTenantId("appcode")
					.processDefinitionVersion(234);
			  
		}
		
		String category = null; // 流程分类
		if (StringUtil.isNotBlank(category)) {
			processDefinitionQuery.processDefinitionCategory(category);
		}
		 
	}
}
