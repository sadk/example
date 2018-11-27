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
import org.lsqt.sys.model.Dictionary;
import org.lsqt.rst.model.StoreManager;
import org.lsqt.rst.model.StoreManagerQuery;
import org.lsqt.rst.service.StoreManagerService;




@Controller(mapping={"/rst/store_manager"})
public class StoreManagerController {
	
	@Inject private StoreManagerService storeManagerService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public StoreManager getById(Long id) throws IOException {
		return storeManagerService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<StoreManager> queryForPage(StoreManagerQuery query) throws IOException {
		return storeManagerService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<StoreManager> getAll() {
		return storeManagerService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public StoreManager saveOrUpdate(StoreManager form) {
		return storeManagerService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return storeManagerService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
