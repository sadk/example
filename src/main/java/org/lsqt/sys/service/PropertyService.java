package org.lsqt.sys.service;

import java.util.Collection;

import org.lsqt.components.db.Page;
import org.lsqt.sys.model.Property;
import org.lsqt.sys.model.PropertyQuery;

public interface PropertyService {
	
	Page<Property> queryForPage(PropertyQuery query);

	Property saveOrUpdate(Property model);

	int deleteById(Long... ids);
	
	Collection<Property> getAll();
	
}
