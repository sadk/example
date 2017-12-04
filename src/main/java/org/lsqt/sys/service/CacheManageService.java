package org.lsqt.sys.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.sys.model.CacheManage;
import org.lsqt.sys.model.CacheManageQuery;

public interface CacheManageService {
	
	Page<CacheManage> queryForPage(CacheManageQuery query);

	CacheManage saveOrUpdate(CacheManage model);

	int deleteById(Long... ids);
	
	Collection<CacheManage> getAll();
}
