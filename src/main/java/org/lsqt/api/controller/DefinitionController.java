package org.lsqt.api.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.api.model.Definition;
import org.lsqt.api.model.DefinitionQuery;
import org.lsqt.api.service.DefinitionService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Controller(mapping={"/api/definition"})
public class DefinitionController {
	private static final Logger  log = LoggerFactory.getLogger(DefinitionController.class);
	@Inject private DefinitionService definitionService; 

	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public Definition getById(Long id) {
		return definitionService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Definition> queryForPage(DefinitionQuery query) throws IOException {
		return definitionService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Definition> getAll() {
		return definitionService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Definition saveOrUpdate(Definition form) {
		return definitionService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		if (StringUtil.isNotBlank(ids)) {
			List<Long> list = StringUtil.split(Long.class, ids, ",");
			return definitionService.deleteById(list.toArray(new Long[list.size()]));
		}
		return 0;
	}
}
