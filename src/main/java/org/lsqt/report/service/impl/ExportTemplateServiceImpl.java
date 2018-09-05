package org.lsqt.report.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.report.model.ExportTemplate;
import org.lsqt.report.model.ExportTemplateQuery;
import org.lsqt.report.service.ExportTemplateService;

@Service
public class ExportTemplateServiceImpl implements ExportTemplateService {
	@Inject private Db db;

	public Page<ExportTemplate> queryForPage(ExportTemplateQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), ExportTemplate.class, query);
	}

	public List<ExportTemplate> getAll() {
		return db.queryForList("getAll", ExportTemplate.class);
	}

	public ExportTemplate saveOrUpdate(ExportTemplate model) {
		return db.saveOrUpdate(model);
	}
	
	public void saveOrUpdate(List<ExportTemplate> list) {
		if (ArrayUtil.isNotBlank(list)) {
			for (ExportTemplate e : list) {
				saveOrUpdate(e);
			}
		}
	}

	public int deleteById(Long... ids) {
		return db.deleteById(ExportTemplate.class, Arrays.asList(ids).toArray());
	}

	public List<ExportTemplate> queryForList(ExportTemplateQuery query) {
		return db.queryForList("queryForPage", ExportTemplate.class, query);
	}

	public ExportTemplate getById(Long id) {
		return db.getById(ExportTemplate.class, id);
	}

	public ExportTemplate queryForObject(ExportTemplateQuery query) {
		return db.queryForObject("queryForPage", ExportTemplate.class, query);
	}
	
	
}
