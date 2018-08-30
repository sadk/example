package org.lsqt.report.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.report.model.Resource;
import org.lsqt.report.model.ResourceQuery;
import org.lsqt.report.service.ResourceService;

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

	public void saveOrUpdate(List<Resource> models) {
		if(ArrayUtil.isNotBlank(models)) {
			for(Resource m: models) {
				db.saveOrUpdate(m);
			}
		}
	}
	
	public int deleteById(Long ... ids) {
		return db.deleteById(Resource.class, Arrays.asList(ids).toArray());
	}

	public Resource getById(Long id) {
		 
		return db.getById(Resource.class, id);
	}

	public List<Resource> queryForList(ResourceQuery query) {
		return db.queryForList("queryForPage", Resource.class, query);
	}
}
