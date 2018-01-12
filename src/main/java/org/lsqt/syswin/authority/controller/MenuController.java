package org.lsqt.syswin.authority.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.authority.model.Menu;
import org.lsqt.syswin.authority.model.MenuQuery;
import org.lsqt.syswin.authority.service.MenuService;

import com.alibaba.fastjson.JSON;

@Controller(mapping={"/syswin/menu","/nv2/syswin/menu"})
public class MenuController {
	
	@Inject private MenuService menuService; 
	@Inject private PlatformDb db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Menu> queryForPage(MenuQuery query) throws IOException {
		return menuService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Menu> getAll() {
		return menuService.getAll();
	}
	
	@RequestMapping(mapping = { "/all_selector", "/m/all_selector" })
	public Collection<Menu> getAll(MenuQuery query) {
		return menuService.queryForList(query);
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Menu saveOrUpdate(Menu form) {
		return menuService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/save_or_update_simple", "/m/save_or_update_simple" })
	public void saveOrUpdateSimple(String data) {
		if(StringUtil.isNotBlank(data)) {
			List<Menu> list = JSON.parseArray(data, Menu.class);
			for (Menu e: list) {
				db.saveOrUpdate(e, "name","code","sn","url");
			}
		}
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return menuService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/repair_node_path", "/m/repair_node_path" })
	public void repairNodePath() {
		menuService.repairNodePath();
	}
 
}
