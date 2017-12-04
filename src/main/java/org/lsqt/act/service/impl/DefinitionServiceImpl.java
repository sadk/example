package org.lsqt.act.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.lsqt.act.ActUtil;
import org.lsqt.act.model.Definition;
import org.lsqt.act.model.DefinitionQuery;
import org.lsqt.act.model.Task;
import org.lsqt.act.service.DefinitionService;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.MapUtil;
import org.lsqt.components.util.lang.StringUtil;

/**
 * 流程定义相关,见表：act_re_procdef 和 act_re_deployment
 * @author mmyuan
 *
 */
@Service
public class DefinitionServiceImpl implements DefinitionService{
	private RepositoryService repositoryService = ActUtil.getRepositoryService();
	@Inject private Db db;
	
	public void saveOrUpdate(Definition model,String ...prop) {
		db.saveOrUpdate(model,prop);
	}

	public void delete(String id) {
		Definition actDefinition = getById(id);
		if(actDefinition!=null && StringUtil.isNotBlank(actDefinition.getDeploymentId())) {
			repositoryService.deleteDeployment(actDefinition.getDeploymentId());
		}
	}

	public void delete(String id, boolean cascade) {
		Definition actDefinition = getById(id);
		if (actDefinition != null && StringUtil.isNotBlank(actDefinition.getDeploymentId())) {
			repositoryService.deleteDeployment(actDefinition.getDeploymentId(), cascade);
		}
		// 删除节点设置、节点按钮设置、节点表单设置、节点审批用户设置
		db.executeUpdate("delete from ext_node        where definition_id=?", id);
		db.executeUpdate("delete from ext_node_button where definition_id=?", id);
		db.executeUpdate("delete from ext_node_form   where definition_id=?", id);
		db.executeUpdate("delete from ext_node_user   where definition_id=?", id);
		
	}
	
	public void delete(List<String> ids) {
		if(ids == null) {
			return ;
		}
		for(String id: ids) {
			if(StringUtil.isBlank(id)) {
				continue;
			}
			delete(id);
		}
	}

	public void delete(List<String> ids, boolean cascade) {
		if(ids == null) {
			return ;
		}
		for(String id: ids) {
			if(StringUtil.isBlank(id)) {
				continue;
			}
			delete(id,cascade);
		}
		
		//删除节点设置、人员设置、表单设置、按钮设置
		String sql1 = "delete from ext_node where definition_id=?" ;
		String sql2 = "delete from ext_node_button where definition_id=?" ;
		String sql3 = "delete from ext_node_form where definition_id=?" ;
		String sql4 = "delete from ext_node_user where definition_id=?";
		for(String id: ids){
			if(StringUtil.isNotBlank(id)){
				db.executeUpdate(sql1, id);
				db.executeUpdate(sql2, id);
				db.executeUpdate(sql3, id);
				db.executeUpdate(sql4, id);
			}
		}
	}
	

	
	// ---------------------------------------------- 查询 --------------------------------------------------------------------
	public Definition getById(String id) {
		ProcessDefinitionQuery query = ActUtil.getRepositoryService().createProcessDefinitionQuery() ;
		ProcessDefinition actDefinition = query.processDefinitionId(id).singleResult();
		
		Definition def = convert(actDefinition);
		
		return def;
	}


	public Page<Definition> queryForPage(DefinitionQuery query) {
		if(!query.getIsDisplayNewest()) {
			return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Definition.class, query);
		}else {
			Page<Definition> page = new Page.PageModel<>();
			 
			ProcessDefinitionQuery dbQuery = ActUtil.getRepositoryService().createProcessDefinitionQuery();
			dbQuery = dbQuery.latestVersion();
			long total = dbQuery.count();
			
			final long pageCount = Double.valueOf(Math.ceil(total * 1.000 / query.getPageSize())).longValue();
			
			// 封装分页对象
			page.setTotal(total);
			page.setPageCount(pageCount);
			page.setPageIndex(query.getPageIndex());
			page.setPageSize(query.getPageSize());
			page.setHasNext(query.getPageIndex() + 1 < pageCount);
			page.setHasPrevious(query.getPageIndex() > 0 && query.getPageIndex() < pageCount - 1);
			
			List<ProcessDefinition> temp = dbQuery.listPage(query.getPageIndex()* query.getPageSize(), query.getPageSize());
			page.setData(convert(temp));

			return page;
		}
	}

	
	public Collection<Definition> getAll() {
		ProcessDefinitionQuery dbQuery = ActUtil.getRepositoryService().createProcessDefinitionQuery();
		List<ProcessDefinition> list = dbQuery.list();
		
		return convert(list);
	}

	public Collection<Definition> getAll(boolean newest) {
		ProcessDefinitionQuery dbQuery = ActUtil.getRepositoryService().createProcessDefinitionQuery(); 
		List<ProcessDefinition> list = new ArrayList<>();
		if(newest){
			list = dbQuery.latestVersion().list();
		}else {
			list = dbQuery.list();
		}
		return convert(list);
	}
	
	public List<Definition> queryForList(DefinitionQuery query) {
		ProcessDefinitionQuery dbQuery = ActUtil.getRepositoryService().createProcessDefinitionQuery();

		Map<String,ProcessDefinition> data = new LinkedHashMap<String,ProcessDefinition>();
		Set<ProcessDefinition> temp = new HashSet<ProcessDefinition>();
		
		// 关键字查询
		if (StringUtil.isNotBlank(query.getKeyWord())) {
			// id
			List<ProcessDefinition> list = dbQuery.processDefinitionId(query.getKeyWord()).list();
			temp.addAll(list);
			
			list = dbQuery.processDefinitionCategoryLike(query.getKeyWord()).list();
			temp.addAll(list);
			
			list = dbQuery.processDefinitionKeyLike(query.getKeyWord()).list();
			temp.addAll(list);
			
			list = dbQuery.processDefinitionNameLike(query.getKeyWord()).list();
			temp.addAll(list);
			
			list = dbQuery.processDefinitionResourceNameLike(query.getKeyWord()).list();
			temp.addAll(list);
			
			list = dbQuery.processDefinitionTenantId(query.getKeyWord()).list();
			temp.addAll(list);
			
			for(ProcessDefinition def: list){
				data.put(def.getId(), def);
			}
			
			return convert(MapUtil.toValueList(data));
		}

		if (StringUtil.isNotBlank(query.getId())) {
			dbQuery = dbQuery.processDefinitionId(query.getId());
		}
		if (StringUtil.isNotBlank(query.getIds())) {
			dbQuery = dbQuery.processDefinitionIds(new HashSet<>(StringUtil.split(query.getIds(), ",")));
		}
		/*if (StringUtil.isNotBlank(query.getCategory())) {
			dbQuery = dbQuery.processDefinitionCategoryLike(query.getCategory());
		}*/
		if (query.getIsDisplayNewest()) {
			dbQuery = dbQuery.latestVersion();
		}
		if (StringUtil.isNotBlank(query.getKey())) {
			dbQuery = dbQuery.processDefinitionKeyLike(query.getKey());
		}
		if (StringUtil.isNotBlank(query.getResourceName())) {
			dbQuery = dbQuery.processDefinitionResourceNameLike(query.getResourceName());
		}
		if (StringUtil.isNotBlank(query.getTenantId())) {
			dbQuery = dbQuery.processDefinitionTenantId(query.getTenantId());
		}
		
		List<Definition> rs =  convert(dbQuery.list());
		
		// 加载流程定义短名称、手机端是否启用
		List<Definition> list = db.queryForList("queryForPage", Definition.class);
		if(list!=null){
			for(Definition e: list) {
				for(Definition c: rs) {
					if(e.getId().equals(c.getId())){
						c.setShortName(e.getShortName());
						c.setEnableMobile(e.getEnableMobile());
						break;
					}
				}
			}
		}
		return rs;
	}

	
	
	
	// -------------------------------------- 辅助方法 --------------------------------------------
	
	Definition convert(ProcessDefinition actDefinition) {
		Definition def = new Definition();
		//def.setCategory(actDefinition.getCategory());
		def.setDeploymentId(actDefinition.getDeploymentId());
		def.setId(actDefinition.getId());
		def.setKey(actDefinition.getKey());
		def.setName(actDefinition.getName());
		def.setResourceName(actDefinition.getResourceName());
		def.setTenantId(actDefinition.getTenantId());
		def.setVersion(actDefinition.getVersion());
		def.setDgrmResourceName(actDefinition.getDiagramResourceName());
		def.setHasGraphicalNotation(actDefinition.hasGraphicalNotation() ? 1:0);
		def.setHasStartFormKey(actDefinition.hasStartFormKey() ? 1:0);
		def.setSuspensionState(actDefinition.isSuspended() ? 1:2);
		return def;
	}

	List<Definition> convert(List<ProcessDefinition> actDefinitions) {
		List<Definition> list = new ArrayList<>();
		if(actDefinitions == null || actDefinitions.size()==0) return list;
		
		for(ProcessDefinition d: actDefinitions) {
			list.add(convert(d));
		}
		
		return list;
	}

}
