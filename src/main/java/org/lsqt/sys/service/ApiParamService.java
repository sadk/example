package org.lsqt.sys.service;

import java.util.Collection;

import org.lsqt.sys.model.ApiParam;
import org.lsqt.sys.model.ApiParamQuery;
import org.lsqt.components.db.Page;

public interface ApiParamService {
	
	ApiParam getById(Long id);
	
	Page<ApiParam> queryForPage(ApiParamQuery query);

	ApiParam saveOrUpdate(ApiParam model);

	int deleteById(Long... ids);
	
	Collection<ApiParam> getAll();
}
