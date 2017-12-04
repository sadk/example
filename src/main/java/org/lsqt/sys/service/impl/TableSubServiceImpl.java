package org.lsqt.sys.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.sys.model.TableSub;
import org.lsqt.sys.model.TableSubQuery;
import org.lsqt.sys.service.TableSubService;

@Service
public class TableSubServiceImpl implements TableSubService{
	
	@Inject private Db db;
	
	public Page<TableSub>  queryForPage(TableSubQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), TableSub.class, query);
	}

	public List<TableSub> getAll(){
		  return db.queryForList("getAll", TableSub.class);
	}
	
	public TableSub saveOrUpdate(TableSub model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(TableSub.class, Arrays.asList(ids).toArray());
	}
}
