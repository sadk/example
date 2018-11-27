package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.rst.model.StoreManager;
import org.lsqt.rst.model.StoreManagerQuery;
import org.lsqt.rst.service.StoreManagerService;

@Service
public class StoreManagerServiceImpl implements StoreManagerService{
	
	@Inject private Db db;
	
	public StoreManager getById(Long id) {
		return db.getById(StoreManager.class, id) ;
	}
	
	public List<StoreManager> queryForList(StoreManagerQuery query) {
		return db.queryForList("queryForPage", StoreManager.class, query);
	}
	
	public Page<StoreManager> queryForPage(StoreManagerQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), StoreManager.class, query);
	}

	public List<StoreManager> getAll(){
		  return db.queryForList("getAll", StoreManager.class);
	}
	
	public StoreManager saveOrUpdate(StoreManager model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(StoreManager.class, Arrays.asList(ids).toArray());
	}
}
