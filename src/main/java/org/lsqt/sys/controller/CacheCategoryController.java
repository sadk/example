package org.lsqt.sys.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.CacheCategory;
import org.lsqt.sys.model.CacheCategoryQuery;
import org.lsqt.sys.service.CacheCategoryService;




@Controller(mapping={"/cache_category"})
public class CacheCategoryController {
	
	@Inject private CacheCategoryService cacheCategoryService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<CacheCategory> queryForPage(CacheCategoryQuery query) throws IOException {
		return cacheCategoryService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<CacheCategory> getAll() {
		return cacheCategoryService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public CacheCategory saveOrUpdate(CacheCategory form) {
		return cacheCategoryService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return cacheCategoryService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/repair_node_path", "/m/repair_node_path" })
	public void repairNodePath() {
		cacheCategoryService.repairNodePath();
	}
	
}
