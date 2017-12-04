package org.lsqt.sys.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Column;
import org.lsqt.sys.model.ColumnQuery;
import org.lsqt.sys.service.ColumnService;

import com.alibaba.fastjson.JSON;

@Controller(mapping={"/column"})
public class ColumnController {
	
	@Inject private ColumnService columnService; 
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" }, view = View.JSON)
	public Page<Column> queryForPage(ColumnQuery query) throws IOException {
		return columnService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" },view = View.JSON)
	public Collection<Column> getAll() {
		return columnService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" }, view = View.JSON)
	public Column saveOrUpdate(Column form) {
		
		return columnService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" },view = View.JSON)
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return columnService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/imp_columns_by_table", "/m/imp_columns_by_table" },view = View.JSON)
	public void impColumsByTable(Long tableId) {
		 columnService.impColumsByTable(tableId);
	}
	  
	@RequestMapping(mapping = { "/update_short", "/m/update_short" })
	public void updateShort(String  data) {
		List<Column> list = JSON.parseArray(data, Column.class);
		for(Column e: list) {
			db.update(e,"name","propertyName", "searchType","comment","javaType","oroColumnType","columnCodegenType","columnCodegenFormat","columnCodegenGroupCode");
		}
		//System.out.println(data);
		
	}
	
}
