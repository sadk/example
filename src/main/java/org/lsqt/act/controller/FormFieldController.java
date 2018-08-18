package org.lsqt.act.controller;

import java.util.Collection;
import java.util.List;

import org.lsqt.act.model.FormField;
import org.lsqt.act.model.FormFieldQuery;
import org.lsqt.act.service.FormFieldService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;

import com.alibaba.fastjson.JSON;

@Controller(mapping={"/act/form_field"})
public class FormFieldController {
	
	@Inject private FormFieldService formFieldService; 
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<FormField> queryForPage(FormFieldQuery query) {
		return formFieldService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public List<FormField> queryForList(FormFieldQuery query) {
		return formFieldService.queryForList(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<FormField> getAll() {
		return formFieldService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public FormField saveOrUpdate(FormField form) {
		return formFieldService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return formFieldService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/imp_columns_by_table", "/m/imp_columns_by_table" })
	public void impColumsByTable(Long formId) {
		formFieldService.impColumsByTable(formId);
	}
	  
	@RequestMapping(mapping = { "/update_short", "/m/update_short" })
	public void updateShort(String  data) {
		List<FormField> list = JSON.parseArray(data, FormField.class);
		for(FormField e: list) {
			db.update(e,"fieldName","propertyName", "searchType","comment","javaType","oroColumnType","columnCodegenType","columnCodegenFormat","columnCodegenGroupCode");
		}
	}
	
}
