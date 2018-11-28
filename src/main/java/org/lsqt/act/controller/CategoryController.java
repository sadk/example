package org.lsqt.act.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.act.model.Category;
import org.lsqt.act.model.CategoryQuery;
import org.lsqt.act.service.CategoryService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.Default;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;

/**
 * 流程类别管理相关
 * @author mmyuan
 *
 */
@Controller(mapping={"/act/category","/nv2/act/category"})
public class CategoryController {
	@Inject private CategoryService categroyService; 
	@Inject private org.lsqt.sys.service.CategoryService categoryService;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Category> queryForPage(CategoryQuery query) throws IOException {
		Page<Category> page = categroyService.queryForPage(query);
		
		Collection<org.lsqt.sys.model.Category> list = categoryService.getAll();
		
		// 加载分类名称
		for(Category e: page.getData()) {
			for(org.lsqt.sys.model.Category c: list){
				if(e.getCategoryCode()!=null && e.getCategoryCode().equals(c.getCode())) {
					e.setCategoryName(c.getName());
					break;
				}
			}
		}
		
		return page;
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Category> all(CategoryQuery query) {
		  return categroyService.queryForList(query);
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
	
	@RequestMapping(mapping = { "/option", "/m/option" })
	public List<Category> option(@Default("1000") String  appCode,String code) {
		if(StringUtil.isBlank(appCode)) {
			appCode = Application.APP_CODE_DEFAULT;
		}
		
		return categroyService.getOptionByCode(code, appCode);
	}
	
	@RequestMapping(mapping = { "/repair_node_path", "/m/repair_node_path" })
	public void repairNodePath() {
		categroyService.repairNodePath();
	}
	
}
