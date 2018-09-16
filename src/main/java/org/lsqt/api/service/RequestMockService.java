package org.lsqt.api.service;

import java.util.Collection;
import java.util.List;

import org.lsqt.api.model.RequestMock;
import org.lsqt.api.model.RequestMockQuery;
import org.lsqt.components.db.Page;

public interface RequestMockService {
	
	RequestMock getById(Long id);
	
	Page<RequestMock> queryForPage(RequestMockQuery query);
	
	List<RequestMock> queryForList(RequestMockQuery query);

	RequestMock saveOrUpdate(RequestMock model);

	int deleteById(Long... ids);
	
	Collection<RequestMock> getAll();
}
