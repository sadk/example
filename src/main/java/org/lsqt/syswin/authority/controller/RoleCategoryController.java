package org.lsqt.syswin.authority.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.authority.model.RoleCategory;
import org.lsqt.syswin.authority.model.RoleCategoryQuery;
import org.lsqt.syswin.authority.service.RoleCategoryService;




@Controller(mapping={"/syswin/role_category","/nv2/syswin/role_category"})
public class RoleCategoryController {
	
	@Inject private RoleCategoryService roleCategoryService; 
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<RoleCategory> queryForPage(RoleCategoryQuery query) throws IOException {
		return roleCategoryService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public Collection<RoleCategory> queryForList(RoleCategoryQuery query) {
		return roleCategoryService.queryForList(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<RoleCategory> getAll() {
		return roleCategoryService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public RoleCategory saveOrUpdate(RoleCategory form) {
		return roleCategoryService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return roleCategoryService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/repair_node_path", "/m/repair_node_path" })
	public void repairNodePath() {
		 roleCategoryService.repairNodePath();
	}
}
