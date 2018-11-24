package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.WorkAddress;
import org.lsqt.rst.model.WorkAddressQuery;

public interface WorkAddressService {
	
	WorkAddress getById(Long id);
	
	List<WorkAddress> queryForList(WorkAddressQuery query);
	
	Page<WorkAddress> queryForPage(WorkAddressQuery query);

	WorkAddress saveOrUpdate(WorkAddress model);

	int deleteById(Long... ids);
	
	Collection<WorkAddress> getAll();
}
