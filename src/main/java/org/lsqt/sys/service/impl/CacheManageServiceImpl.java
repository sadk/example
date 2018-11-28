package org.lsqt.sys.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.sys.model.CacheManage;
import org.lsqt.sys.model.CacheManageQuery;
import org.lsqt.sys.service.CacheManageService;

@Service
public class CacheManageServiceImpl implements CacheManageService{
	
	@Inject private Db db;
	
	public Page<CacheManage>  queryForPage(CacheManageQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), CacheManage.class, query);
	}

	public List<CacheManage> getAll(){
		  return db.queryForList("getAll", CacheManage.class);
	}
	
	public CacheManage saveOrUpdate(CacheManage model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(CacheManage.class, Arrays.asList(ids).toArray());
	}
}
