package org.lsqt.cms.service;

import java.util.Collection;

import org.lsqt.cms.model.Template;
import org.lsqt.cms.model.TemplateQuery;
import org.lsqt.components.db.Page;

public interface TemplateService {
	
	Page<Template> queryForPage(TemplateQuery query);

	Template saveOrUpdate(Template model);

	int deleteById(Long... ids);
	
	Collection<Template> getAll();
}
