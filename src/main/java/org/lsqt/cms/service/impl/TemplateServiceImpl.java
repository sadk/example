package org.lsqt.cms.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.cms.model.Template;
import org.lsqt.cms.model.TemplateQuery;
import org.lsqt.cms.service.TemplateService;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;

@Service
public class TemplateServiceImpl implements TemplateService{
	
	@Inject private Db db;
	
	public Page<Template>  queryForPage(TemplateQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Template.class, query);
	}

	public List<Template> getAll(){
		  return db.queryForList("getAll", Template.class);
	}
	
	public Template saveOrUpdate(Template model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(Template.class, Arrays.asList(ids).toArray());
	}
}
