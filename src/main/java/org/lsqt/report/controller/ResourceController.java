package org.lsqt.report.controller;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.report.model.Resource;
import org.lsqt.report.model.ResourceQuery;
import org.lsqt.report.service.ResourceService;

import com.alibaba.fastjson.JSON;


@Controller(mapping={"/report/resource"})
public class ResourceController {
	
	@Inject private ResourceService resourceService; 
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public Resource getById(Long id)  {
		return resourceService.getById(id) ;
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Resource> queryForPage(ResourceQuery query)  {
		return resourceService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Resource> getAll() {
		return resourceService.getAll();
	}
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public Collection<Resource> queryForList(ResourceQuery query) {
		return resourceService.queryForList(query);
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Resource saveOrUpdate(Resource form) {
		return resourceService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/save_or_update_json", "/m/save_or_update_json" })
	public Resource saveOrUpdate(String data) {
		if (StringUtil.isNotBlank(data)) {
			List<Resource> models = JSON.parseArray(data, Resource.class);
			resourceService.saveOrUpdate(models);
		}
		return null;
	}

	@RequestMapping(mapping = { "/delete_by_ids", "/m/delete_by_ids" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return resourceService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
