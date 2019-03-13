package org.lsqt.sys.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.sys.model.ApiRequestMock;
import org.lsqt.sys.model.ApiRequestMockQuery;
import org.lsqt.components.db.Page;

public interface ApiRequestMockService {
	
	ApiRequestMock getById(Long id);
	
	Page<ApiRequestMock> queryForPage(ApiRequestMockQuery query);
	
	List<ApiRequestMock> queryForList(ApiRequestMockQuery query);

	ApiRequestMock saveOrUpdate(ApiRequestMock model);

	int deleteById(Long... ids);
	
	Collection<ApiRequestMock> getAll();
}
