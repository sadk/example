package org.lsqt.rst.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.rst.model.Area;
import org.lsqt.rst.model.AreaQuery;

public interface AreaService {
	
	Area getById(Long id);
	
	List<Area> queryForList(AreaQuery query);
	
	Page<Area> queryForPage(AreaQuery query);

	Area saveOrUpdate(Area model);

	int deleteById(Long... ids);
	
	Collection<Area> getAll();
}
