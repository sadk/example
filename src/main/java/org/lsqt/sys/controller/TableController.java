package org.lsqt.sys.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Table;
import org.lsqt.sys.model.TableQuery;
import org.lsqt.sys.service.TableService;

import com.alibaba.fastjson.JSON;

@Controller(mapping={"/table"})
public class TableController {
	
	@Inject private TableService tableService; 
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Table> queryForPage(TableQuery query) throws IOException {
		return tableService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Table> getAll() {
		return tableService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Table saveOrUpdate(Table form) {
		 if(form.getId() == null && StringUtil.isBlank(form.getVersion())) {
			 String version = "0.0.1."+System.currentTimeMillis();
			 form.setVersion(version);
		 }
		return tableService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/update_project_code", "/m/update_project_code" })
	public Table updateProjectCode(Table form) {
		if(form.getId()!=null) {
			String sql = "update %s set project_code=? where id=?";
			sql = String.format(sql, db.getFullTable(Table.class));
			db.executeUpdate(sql,form.getProjectCode(),form.getId());
			
			return db.getById(Table.class, form.getId());
		}
		return form;
	}
	
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return tableService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/imp_table", "/m/imp_table" })
	public void impTable(String dataSourceCode,String dbName,String tableName) {
		tableService.impTable(dataSourceCode,dbName, tableName);
	}
	
	
	@RequestMapping(mapping = { "/imp_syn", "/m/imp_syn" })
	public void impSyn(Long id) {
		
		tableService.impSyn(id);
	}
	
	
	@RequestMapping(mapping = { "/all_from_db", "/m/all_from_db" })
	public Collection<Table> getAllFromDb(String dbName) {
		return tableService.getAllFormDb(dbName);
	}
	
	@RequestMapping(mapping = { "/update_short", "/m/update_short" })
	public void updateShort(String  data) {
		List<Table> list = JSON.parseArray(data, Table.class);
		for(Table e: list) {
			db.update(e,"remark","modelName");
		}
		
	}
}
