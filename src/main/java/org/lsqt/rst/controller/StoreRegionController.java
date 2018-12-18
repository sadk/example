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
import org.lsqt.rst.model.StoreRegion;
import org.lsqt.rst.model.StoreRegionQuery;
import org.lsqt.rst.service.StoreRegionService;


@Controller(mapping={"/rst/store_region"})
public class StoreRegionController {
	
	@Inject private StoreRegionService storeRegionService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public StoreRegion getById(Long id) throws IOException {
		return storeRegionService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<StoreRegion> queryForPage(StoreRegionQuery query) throws IOException {
		return storeRegionService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<StoreRegion> getAll() {
		return storeRegionService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public StoreRegion saveOrUpdate(StoreRegion form) {
		return storeRegionService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return storeRegionService.deleteById(list.toArray(new Long[list.size()]));
	}
}
