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
import org.lsqt.sys.model.Dictionary;
import org.lsqt.sys.model.DictionaryQuery;
import org.lsqt.report.model.Variable;
import org.lsqt.report.model.VariableQuery;
import org.lsqt.report.service.VariableService;




@Controller(mapping={"/report/variable"})
public class VariableController {
	
	@Inject private VariableService variableService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public Variable getById(Long id) throws IOException {
		return variableService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Variable> queryForPage(VariableQuery query) throws IOException {
		return variableService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public List<Variable> queryForList(VariableQuery query) throws IOException {
		List<Variable> data = variableService.queryForList(query);
		for (Variable v : data) {
			if (v.getType() != null && Variable.TYPE_数据字典 == v.getType()) {
				if (StringUtil.isNotBlank(v.getValue())) {
					DictionaryQuery q = new DictionaryQuery();
					q.setIdList(StringUtil.split(Long.class,v.getValue(), ","));
					List<Dictionary> dictData = db.queryForList("queryForPage", Dictionary.class, q);
					StringBuilder sb = new StringBuilder();
					for(Dictionary e: dictData) {
						sb.append(e.getName()+",");
					}
					v.setValueText(sb.toString());
				}
			}
		}
		return data;
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Variable> getAll() {
		return variableService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Variable saveOrUpdate(Variable form) {
		return variableService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return variableService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
