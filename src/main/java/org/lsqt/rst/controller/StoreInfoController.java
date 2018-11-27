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
import org.lsqt.rst.model.StoreInfo;
import org.lsqt.rst.model.StoreInfoQuery;
import org.lsqt.rst.service.StoreInfoService;




@Controller(mapping={"/rst/storeInfo"})
public class StoreInfoController {
	
	@Inject private StoreInfoService storeInfoService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public StoreInfo getById(Long id) throws IOException {
		return storeInfoService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<StoreInfo> queryForPage(StoreInfoQuery query) throws IOException {
		return storeInfoService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<StoreInfo> getAll() {
		return storeInfoService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public StoreInfo saveOrUpdate(StoreInfo form) {
		return storeInfoService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return storeInfoService.deleteById(list.toArray(new Long[list.size()]));
	}
}
