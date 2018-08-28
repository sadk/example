package org.lsqt.report.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.db.orm.util.ModelUtil;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.report.model.ColumnQuery;
import org.lsqt.report.model.Definition;
import org.lsqt.report.model.DefinitionQuery;
import org.lsqt.report.service.DefinitionService;
import org.lsqt.report.service.impl.support.FreemarkGenerateReportFile;
import org.lsqt.report.service.impl.support.SelectorData;
import org.lsqt.report.service.impl.support.SelectorDataFromJSArray;
import org.lsqt.report.service.impl.support.SelectorDataFromSQL;
import org.lsqt.report.service.impl.support.SelectorDataFromUrlHtml;
import org.lsqt.report.service.impl.support.SelectorDataFromUrlJson;
import org.lsqt.report.service.impl.support.SelectorDataFromUrlXml;
import org.lsqt.sys.model.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;




@Controller(mapping={"/report/definition"})
public class DefinitionController {
	private static final Logger log = LoggerFactory.getLogger(DefinitionController.class);
	
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
	
	
	/**
	 * 
	 * @param selectorDataFromType 数据来源类型： 0=URL(页面) 1=URL(返回JSON) 2=URL(返回XML) 3=代码片断(JavaScript)数组  4=SQL
	 * @param dataSourceCode 数据来源对应的具体数据源编码（如果是SQL的话）
	 * @param selectorDataFrom 选择器数据来源 (SQL、HTTPJSON Url、XML等)
	 */
	@RequestMapping(mapping = { "/display_column", "/m/display_column" },text="当字段为选择器数据来源于SQL的时候，定义显示的字段与值字段，如：性别=sex=0或1")
	public Page<Map<String,Object>> displayColumn(String selectorDataFromType,String selectorDataSourceCode,String selectorDataFrom) {
		Map<String, SelectorData<Map<String, Object>>> handlerMap = new HashMap<>();

		SelectorData<Map<String, Object>> sql = new SelectorDataFromSQL(db, selectorDataSourceCode, selectorDataFrom);
		SelectorData<Map<String, Object>> jsArray = new SelectorDataFromJSArray();
		SelectorData<Map<String, Object>> urlHtml = new SelectorDataFromUrlHtml();
		SelectorData<Map<String, Object>> urlJson = new SelectorDataFromUrlJson(selectorDataFrom);
		SelectorData<Map<String, Object>> urlXml = new SelectorDataFromUrlXml();

		handlerMap.put("0", urlHtml);
		handlerMap.put("1", urlJson);
		handlerMap.put("2", urlXml);
		handlerMap.put("3", jsArray);
		handlerMap.put("4", sql);

		return handlerMap.get(selectorDataFromType).getData();
	}
	
	@RequestMapping(mapping = { "/generate_report_file", "/m/generate_report_file" },text="生成报表文件,读取报表配置，利用freemark生成jsp文件")
	public void generateReportFile(Long id) throws Exception {
		definitionService.generateReportFile(id);
	}

	
	@RequestMapping(mapping = { "/search", "/m/search" }, text = "通用报表查询")
	public Object search(Long reportDefinitionId) throws Exception {
		Map<String, Object> formData = ContextUtil.getFormMap();
		return definitionService.search(reportDefinitionId, formData);
	}
}
