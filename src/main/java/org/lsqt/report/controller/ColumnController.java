package org.lsqt.report.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.report.model.Column;
import org.lsqt.report.model.ColumnQuery;
import org.lsqt.report.service.ColumnService;
import org.lsqt.report.service.DefinitionService;

import com.alibaba.fastjson.JSON;




@Controller(mapping={"/report/column"})
public class ColumnController {
	
	@Inject private ColumnService columnService; 
	@Inject private DefinitionService definitionService;
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Column> queryForPage(ColumnQuery query) throws IOException {
		return columnService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Column> getAll() {
		return columnService.getAll();
	}
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public Column getById(Long id) {
		return columnService.getById(id);
	}
	
	@RequestMapping(mapping = { "/list", "/m/list" })
	public Collection<Column> queryForList(ColumnQuery query) {
		return columnService.queryForList(query);
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Column saveOrUpdate(Column form) {
		return columnService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return columnService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(mapping = { "/save_or_update_json", "/m/save_or_update_json" })
	public List<Column> saveOrUpdateJson(String data) {
		if(StringUtil.isNotBlank(data)) {
			List<Column> list = JSON.parseArray(data, Column.class);
			for(Column e: list) {
				db.update(e);
			}
			return list;
		}
		return ArrayUtil.EMPTY_LIST;
	}
	
	@RequestMapping(mapping = { "/copy_import", "/m/copy_import" },text="从报表展示字段复制导入")
	public void copyImport(Long definitionId) {
		if (definitionId == null) {
			return;
		}

		ColumnQuery query = new ColumnQuery();
		query.setDefinitionId(definitionId);
		query.setDataType(Column.DATA_TYPE_REPORT_SHOW);
		List<Column> data = columnService.queryForList(query);
		
		if (ArrayUtil.isNotBlank(data)) {
			for (Column e: data) {
				e.setId(null);
				e.setDataType(Column.DATA_TYPE_IMPORT);
			}
			String sql = String.format("delete from %s where definition_id=? and data_type=?", db.getFullTable(Column.class));
			db.executeUpdate(sql, definitionId,Column.DATA_TYPE_IMPORT) ;
			db.batchSave(data);
		}
	}
}
