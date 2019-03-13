package org.lsqt.sys.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.sys.model.ApiDefinition;
import org.lsqt.sys.model.ApiDefinitionQuery;
import org.lsqt.sys.service.ApiDefinitionService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Controller(mapping={"/api/definition"})
public class ApiDefinitionController {
	private static final Logger  log = LoggerFactory.getLogger(ApiDefinitionController.class);
	
	@Inject private ApiDefinitionService definitionService; 

	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public ApiDefinition getById(Long id) {
		return definitionService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<ApiDefinition> queryForPage(ApiDefinitionQuery query) throws IOException {
		return definitionService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<ApiDefinition> getAll() {
		return definitionService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public ApiDefinition saveOrUpdate(ApiDefinition form) {
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
