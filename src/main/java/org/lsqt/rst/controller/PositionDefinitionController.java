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
import org.lsqt.rst.model.PositionDefinition;
import org.lsqt.rst.model.PositionDefinitionQuery;
import org.lsqt.rst.service.PositionDefinitionService;




@Controller(mapping={"/rst/position_definition"})
public class PositionDefinitionController {
	
	@Inject private PositionDefinitionService positionDefinitionService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public PositionDefinition getById(Long id) throws IOException {
		return positionDefinitionService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<PositionDefinition> queryForPage(PositionDefinitionQuery query) throws IOException {
		return positionDefinitionService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<PositionDefinition> getAll() {
		return positionDefinitionService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public PositionDefinition saveOrUpdate(PositionDefinition form) {
		return positionDefinitionService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return positionDefinitionService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
