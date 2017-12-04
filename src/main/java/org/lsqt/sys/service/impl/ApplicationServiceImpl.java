package org.lsqt.sys.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.sys.model.Application;
import org.lsqt.sys.model.ApplicationQuery;
import org.lsqt.sys.service.ApplicationService;

@Service
public class ApplicationServiceImpl implements ApplicationService{
	
	@Inject private Db db;
	
	public Page<Application>  queryForPage(ApplicationQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Application.class, query);
	}

	public List<Application> getAll(){
		  return db.queryForList("getAll", Application.class);
	}
	
	public Application saveOrUpdate(Application model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(Application.class, Arrays.asList(ids).toArray());
	}
}
