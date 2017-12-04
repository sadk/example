package org.lsqt.syswin.uum.controller;

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
import org.lsqt.syswin.uum.model.PositionCategoryData;
import org.lsqt.syswin.uum.model.PositionCategoryDataQuery;
import org.lsqt.syswin.uum.service.PositionCategoryDataService;




@Controller(mapping={"/position_category_data"})
public class PositionCategoryDataController {
	
	@Inject private PositionCategoryDataService positionCategoryDataService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<PositionCategoryData> queryForPage(PositionCategoryDataQuery query) throws IOException {
		return positionCategoryDataService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<PositionCategoryData> getAll() {
		return positionCategoryDataService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public PositionCategoryData saveOrUpdate(PositionCategoryData form) {
		return positionCategoryDataService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return positionCategoryDataService.deleteById(list.toArray(new Long[list.size()]));
	}

}
