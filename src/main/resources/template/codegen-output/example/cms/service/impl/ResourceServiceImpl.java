package org.lsqt.cms.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.cms.model.Resource;
import org.lsqt.cms.model.ResourceQuery;
import org.lsqt.cms.service.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService{
	
	@Inject private Db db;
	
	public Page<Resource>  queryForPage(ResourceQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Resource.class, query);
	}

	public List<Resource> getAll(){
		  return db.queryForList("getAll", Resource.class);
	}
	
	public Resource saveOrUpdate(Resource model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(Resource.class, Arrays.asList(ids).toArray());
	}
}
