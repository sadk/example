package org.lsqt.act.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.act.model.PrintInfo;
import org.lsqt.act.model.PrintInfoQuery;
import org.lsqt.act.service.PrintInfoService;

@Service
public class PrintInfoServiceImpl implements PrintInfoService{
	
	@Inject private Db db;
	
	public Page<PrintInfo>  queryForPage(PrintInfoQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), PrintInfo.class, query);
	}

	public List<PrintInfo> getAll(){
		  return db.queryForList("getAll", PrintInfo.class);
	}
	
	public PrintInfo saveOrUpdate(PrintInfo model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(PrintInfo.class, Arrays.asList(ids).toArray());
	}
}
