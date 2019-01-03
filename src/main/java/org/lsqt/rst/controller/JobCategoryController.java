package org.lsqt.rst.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.After;
import org.lsqt.components.context.annotation.mvc.Match;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.rst.model.JobCategory;
import org.lsqt.rst.model.JobCategoryQuery;
import org.lsqt.rst.service.JobCategoryService;

@Controller(mapping={"/rst/job_category"})
public class JobCategoryController {
	@Inject private JobCategoryService jobCategoryService; 
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public JobCategory getById(Long id) throws IOException {
		return jobCategoryService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<JobCategory> queryForPage(JobCategoryQuery query) throws IOException {
		if(StringUtil.isBlank(query.getTenantCode())) {
			query.setTenantCode(ContextUtil.getLoginTenantCode());
		}
		return jobCategoryService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	@After(clazz=JobCategoryController.class,method="resultWrapper")
	public Collection<JobCategory> queryForList(JobCategoryQuery query) throws IOException {
		if(StringUtil.isBlank(query.getTenantCode())) {
			query.setTenantCode(ContextUtil.getLoginTenantCode());
		}
		
		List<JobCategory> data = jobCategoryService.queryForList(query);
		return data;
	}
	
	@Match(mapping = {"/m/list"},excludeTransaction = true)
	public org.lsqt.rst.model.Result<Object> resultWrapper(Object result) {
		return org.lsqt.rst.model.Result.ok(result);
	}
	
	@RequestMapping(mapping = { "/tree", "/m/tree" })
	public Collection<JobCategory> getTree(JobCategoryQuery query) throws IOException {
		if(StringUtil.isBlank(query.getTenantCode())) {
			query.setTenantCode(ContextUtil.getLoginTenantCode());
		}
		
		List<JobCategory> data = jobCategoryService.queryForList(query);
		
		JobCategory root = new JobCategory();
		root.setName("岗位分类导航");
		root.setId(-1L);
		root.setPid(-2L);
		
		data.add(root);
		return data;
	}
	 
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public JobCategory saveOrUpdate(JobCategory form) {
		form.setTenantCode(ContextUtil.getLoginTenantCode());
		return jobCategoryService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return jobCategoryService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/repair_node_path", "/m/repair_node_path" },text="修复节点路径")
	public void repairNodePath() {
		jobCategoryService.repairNodePath();
	}
	
}
