package org.lsqt.sys.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.sys.model.ApiCategory;
import org.lsqt.sys.model.ApiCategoryQuery;
import org.lsqt.sys.service.ApiCategoryService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;

/**
 * 接口类别管理相关
 * @author mmyuan
 *
 */
@Controller(mapping={"/api/category"})
public class ApiCategoryController {
	@Inject private ApiCategoryService categroyService; 
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public ApiCategory getById(Long id) throws IOException {
		return categroyService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<ApiCategory> queryForPage(ApiCategoryQuery query) throws IOException {
		Page<ApiCategory> page = categroyService.queryForPage(query);
		return page;
	}
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public Collection<ApiCategory> queryForList(ApiCategoryQuery query) {
		  return categroyService.queryForList(query);
	}

	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<ApiCategory> getAll() {
		  return categroyService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public ApiCategory saveOrUpdate(ApiCategory form) {
		return categroyService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return categroyService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/repair_node_path", "/m/repair_node_path" })
	public void repairNodePath() {
		categroyService.repairNodePath();
	}
	
}
