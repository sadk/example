package org.lsqt.sys.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Category;
import org.lsqt.sys.model.CategoryQuery;
import org.lsqt.sys.service.CategoryService;

@Controller(mapping={"/category"})
public class CategoryController {
	
	@Inject private CategoryService categoryService; 
	
	@RequestMapping(mapping = { "/page", "/m/page" }, view = View.JSON)
	public Page<Category> queryForPage(CategoryQuery query) throws IOException {
		return categoryService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" },view = View.JSON)
	public Collection<Category> all(CategoryQuery query) {
		  return categoryService.queryForList(query);
	}
	
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" }, view = View.JSON)
	public Category saveOrUpdate(Category form) {
		return categoryService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" },view = View.JSON)
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return categoryService.deleteById(list.toArray(new Long[list.size()]));
	}
}
