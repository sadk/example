package org.lsqt.sys.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.sys.model.CacheUrlParam;
import org.lsqt.sys.model.CacheUrlParamQuery;
import org.lsqt.sys.service.CacheUrlParamService;

@Service
public class CacheUrlParamServiceImpl implements CacheUrlParamService{
	
	@Inject private Db db;
	
	public Page<CacheUrlParam>  queryForPage(CacheUrlParamQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), CacheUrlParam.class, query);
	}

	public List<CacheUrlParam> getAll(){
		  return db.queryForList("getAll", CacheUrlParam.class);
	}
	
	public CacheUrlParam saveOrUpdate(CacheUrlParam model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(CacheUrlParam.class, Arrays.asList(ids).toArray());
	}
}
