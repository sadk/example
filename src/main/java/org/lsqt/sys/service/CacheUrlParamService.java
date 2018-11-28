package org.lsqt.sys.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.sys.model.CacheUrlParam;
import org.lsqt.sys.model.CacheUrlParamQuery;

public interface CacheUrlParamService {
	
	Page<CacheUrlParam> queryForPage(CacheUrlParamQuery query);

	CacheUrlParam saveOrUpdate(CacheUrlParam model);

	int deleteById(Long... ids);
	
	Collection<CacheUrlParam> getAll();
}
