package org.lsqt.report.service;

import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.report.model.ExportTemplate;
import org.lsqt.report.model.ExportTemplateQuery;

public interface ExportTemplateService {

	ExportTemplate queryForObject(ExportTemplateQuery query);
	
	Page<ExportTemplate> queryForPage(ExportTemplateQuery query);

	List<ExportTemplate> getAll();

	ExportTemplate saveOrUpdate(ExportTemplate model);
	
	void saveOrUpdate(List<ExportTemplate> models);

	int deleteById(Long... ids);

	List<ExportTemplate> queryForList(ExportTemplateQuery query);

	ExportTemplate getById(Long id);
}
