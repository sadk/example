package org.lsqt.act.controller;

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
import org.lsqt.act.model.ReDefinition;
import org.lsqt.act.model.ReDefinitionQuery;
import org.lsqt.act.service.ReDefinitionService;




@Controller(mapping={"/act/redefinition","/nv2/act/redefinition"})
public class ReDefinitionController {
	
	@Inject private ReDefinitionService reDefinitionService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public ReDefinition getById(Long id) {
		return db.getById(ReDefinition.class, id);
	}
	
	@RequestMapping(mapping = { "/get_by_definition_id", "/m/get_by_definition_id" })
	public ReDefinition getByDefinitionId(String definitionId) {
		ReDefinitionQuery query = new ReDefinitionQuery();
		query.setDefinitionId(definitionId);
		return  db.queryForObject("queryForPage", ReDefinition.class,query);
	}
	
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<ReDefinition> queryForPage(ReDefinitionQuery query) throws IOException {
		return reDefinitionService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<ReDefinition> getAll() {
		return reDefinitionService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public ReDefinition saveOrUpdate(ReDefinition form) {
		return reDefinitionService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return reDefinitionService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
