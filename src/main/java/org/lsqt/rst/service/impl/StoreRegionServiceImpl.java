package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.rst.model.StoreRegion;
import org.lsqt.rst.model.StoreRegionQuery;
import org.lsqt.rst.service.StoreRegionService;

@Service
public class StoreRegionServiceImpl implements StoreRegionService{
	
	@Inject private Db db;
	
	public StoreRegion getById(Long id) {
		return db.getById(StoreRegion.class, id) ;
	}
	
	public List<StoreRegion> queryForList(StoreRegionQuery query) {
		return db.queryForList("queryForPage", StoreRegion.class, query);
	}
	
	public Page<StoreRegion> queryForPage(StoreRegionQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), StoreRegion.class, query);
	}

	public List<StoreRegion> getAll(){
		  return db.queryForList("getAll", StoreRegion.class);
	}
	
	public StoreRegion saveOrUpdate(StoreRegion model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(StoreRegion.class, Arrays.asList(ids).toArray());
	}
}
