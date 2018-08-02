package org.lsqt.yue.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.yue.model.TechInfo;
import org.lsqt.yue.model.TechInfoQuery;
import org.lsqt.yue.service.TechInfoService;

@Service
public class TechInfoServiceImpl implements TechInfoService{
	
	@Inject private Db db;
	
	public Page<TechInfo>  queryForPage(TechInfoQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), TechInfo.class, query);
	}

	public List<TechInfo> getAll(){
		  return db.queryForList("getAll", TechInfo.class);
	}
	
	public TechInfo saveOrUpdate(TechInfo model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(TechInfo.class, Arrays.asList(ids).toArray());
	}
}
