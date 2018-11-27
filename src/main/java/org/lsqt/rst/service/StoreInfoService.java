package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.StoreInfo;
import org.lsqt.rst.model.StoreInfoQuery;

public interface StoreInfoService {
	
	StoreInfo getById(Long id);
	
	List<StoreInfo> queryForList(StoreInfoQuery query);
	
	Page<StoreInfo> queryForPage(StoreInfoQuery query);

	StoreInfo saveOrUpdate(StoreInfo model);

	int deleteById(Long... ids);
	
	Collection<StoreInfo> getAll();
}
