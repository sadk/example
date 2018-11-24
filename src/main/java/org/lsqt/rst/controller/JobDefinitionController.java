package org.lsqt.rst.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.rst.model.JobDefinition;
import org.lsqt.rst.model.JobDefinitionQuery;
import org.lsqt.rst.service.JobDefinitionService;




@Controller(mapping={"/rst/job_definition"})
public class JobDefinitionController {
	
	@Inject private JobDefinitionService jobDefinitionService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public JobDefinition getById(Long id) throws IOException {
		return jobDefinitionService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<JobDefinition> queryForPage(JobDefinitionQuery query) throws IOException {
		return jobDefinitionService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<JobDefinition> getAll() {
		return jobDefinitionService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public JobDefinition saveOrUpdate(JobDefinition form) {
		return jobDefinitionService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return jobDefinitionService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
