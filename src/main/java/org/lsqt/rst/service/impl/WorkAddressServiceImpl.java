package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.rst.model.WorkAddress;
import org.lsqt.rst.model.WorkAddressQuery;
import org.lsqt.rst.service.WorkAddressService;

@Service
public class WorkAddressServiceImpl implements WorkAddressService{
	
	@Inject private Db db;
	
	public WorkAddress getById(Long id) {
		return db.getById(WorkAddress.class, id) ;
	}
	
	public List<WorkAddress> queryForList(WorkAddressQuery query) {
		return db.queryForList("queryForPage", WorkAddress.class, query);
	}
	
	public Page<WorkAddress> queryForPage(WorkAddressQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), WorkAddress.class, query);
	}

	public List<WorkAddress> getAll(){
		  return db.queryForList("getAll", WorkAddress.class);
	}
	
	public WorkAddress saveOrUpdate(WorkAddress model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(WorkAddress.class, Arrays.asList(ids).toArray());
	}
}
