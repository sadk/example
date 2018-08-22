package org.lsqt.report.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.report.model.Definition;
import org.lsqt.report.model.DefinitionQuery;
import org.lsqt.report.service.DefinitionService;




@Controller(mapping={"/report/definition"})
public class DefinitionController {
	
	@Inject private DefinitionService definitionService; 
	
	@Inject private Db db;
	
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
		if (StringUtil.isNotBlank(form.getColumnSql())) {
			form.setColumnSql(form.getColumnSql().trim());
		}
		if (StringUtil.isNotBlank(form.getReportSql())) {
			form.setReportSql(form.getReportSql().trim());
		}
		return definitionService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public Definition getById(Long id) {
		return definitionService.getById(id);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return definitionService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/import_column", "/m/import_column" },text="导入报表的列")
	public void importColumn(Long id) {
		definitionService.importColumn(id);
	}
	
	@RequestMapping(mapping = { "/display_column", "/m/display_column" },text="当字段为选择器数据来源于SQL的时候，定义显示的字段与值字段，如：性别=sex=0或1")
	public void displayColumn(String selectorDataFromType,String dataSourceCode) {
		
	}
}
