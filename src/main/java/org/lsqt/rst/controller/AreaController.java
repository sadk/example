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
import org.lsqt.rst.model.Area;
import org.lsqt.rst.model.AreaQuery;
import org.lsqt.rst.service.AreaService;




@Controller(mapping={"/rst/area"})
public class AreaController {
	
	@Inject private AreaService areaService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public Area getById(Long id) throws IOException {
		return areaService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Area> queryForPage(AreaQuery query) throws IOException {
		return areaService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Area> getAll() {
		return areaService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Area saveOrUpdate(Area form) {
		return areaService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return areaService.deleteById(list.toArray(new Long[list.size()]));
	}
}
