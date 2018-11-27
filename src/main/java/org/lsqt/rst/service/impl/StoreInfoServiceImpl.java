package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.rst.model.StoreInfo;
import org.lsqt.rst.model.StoreInfoQuery;
import org.lsqt.rst.service.StoreInfoService;

@Service
public class StoreInfoServiceImpl implements StoreInfoService{
	
	@Inject private Db db;
	
	public StoreInfo getById(Long id) {
		return db.getById(StoreInfo.class, id) ;
	}
	
	public List<StoreInfo> queryForList(StoreInfoQuery query) {
		return db.queryForList("queryForPage", StoreInfo.class, query);
	}
	
	public Page<StoreInfo> queryForPage(StoreInfoQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), StoreInfo.class, query);
	}

	public List<StoreInfo> getAll(){
		  return db.queryForList("getAll", StoreInfo.class);
	}
	
	public StoreInfo saveOrUpdate(StoreInfo model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(StoreInfo.class, Arrays.asList(ids).toArray());
	}
}
