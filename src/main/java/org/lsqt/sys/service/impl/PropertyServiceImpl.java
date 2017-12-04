package org.lsqt.sys.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.sys.model.Property;
import org.lsqt.sys.model.PropertyQuery;
import org.lsqt.sys.service.PropertyService;

@Service
public class PropertyServiceImpl implements PropertyService{
	
	@Inject private Db db;
	
	public Page<Property>  queryForPage(PropertyQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Property.class, query);
	}

	public List<Property> getAll(){
		  return db.queryForList("getAll", Property.class);
	}
	
	public Property saveOrUpdate(Property model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(Property.class, Arrays.asList(ids).toArray());
	}

 
}
