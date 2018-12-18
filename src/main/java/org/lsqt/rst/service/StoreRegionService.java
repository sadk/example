package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.StoreRegion;
import org.lsqt.rst.model.StoreRegionQuery;

public interface StoreRegionService {
	
	StoreRegion getById(Long id);
	
	List<StoreRegion> queryForList(StoreRegionQuery query);
	
	Page<StoreRegion> queryForPage(StoreRegionQuery query);

	StoreRegion saveOrUpdate(StoreRegion model);

	int deleteById(Long... ids);
	
	Collection<StoreRegion> getAll();
}
