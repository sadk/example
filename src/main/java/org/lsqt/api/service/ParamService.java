package org.lsqt.api.service;

import java.util.Collection;

import org.lsqt.api.model.Param;
import org.lsqt.api.model.ParamQuery;
import org.lsqt.components.db.Page;

public interface ParamService {
	
	Param getById(Long id);
	
	Page<Param> queryForPage(ParamQuery query);

	Param saveOrUpdate(Param model);

	int deleteById(Long... ids);
	
	Collection<Param> getAll();
}
