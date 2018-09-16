package org.lsqt.api.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.api.model.Category;
import org.lsqt.api.model.CategoryQuery;
import org.lsqt.api.service.CategoryService;
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
public class CategoryController {
	@Inject private CategoryService categroyService; 
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public Category getById(Long id) throws IOException {
		return categroyService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Category> queryForPage(CategoryQuery query) throws IOException {
		Page<Category> page = categroyService.queryForPage(query);
		return page;
	}
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public Collection<Category> queryForList(CategoryQuery query) {
		  return categroyService.queryForList(query);
	}

	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Category> getAll() {
		  return categroyService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Category saveOrUpdate(Category form) {
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
