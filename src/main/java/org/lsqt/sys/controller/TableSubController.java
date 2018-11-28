package org.lsqt.sys.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.sys.model.TableSub;
import org.lsqt.sys.model.TableSubQuery;
import org.lsqt.sys.service.TableSubService;




@Controller(mapping={"/table_sub"})
public class TableSubController {
	
	@Inject private TableSubService tableSubService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<TableSub> queryForPage(TableSubQuery query) throws IOException {
		return tableSubService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<TableSub> getAll() {
		return tableSubService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public TableSub saveOrUpdate(TableSub form) {
		return tableSubService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return tableSubService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
