package org.lsqt.api.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.api.model.RequestMock;
import org.lsqt.api.model.RequestMockQuery;
import org.lsqt.api.service.RequestMockService;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;

@Service
public class RequestMockServiceImpl implements RequestMockService{
	
	@Inject private Db db;
	
	public Page<RequestMock>  queryForPage(RequestMockQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), RequestMock.class, query);
	}

	public List<RequestMock> getAll(){
		  return db.queryForList("getAll", RequestMock.class);
	}
	
	public RequestMock saveOrUpdate(RequestMock model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(RequestMock.class, Arrays.asList(ids).toArray());
	}

	public RequestMock getById(Long id) {
		return db.getById(RequestMock.class, id);
	}

	public List<RequestMock> queryForList(RequestMockQuery query) {
		return db.queryForList("queryForPage", RequestMock.class, query);
	}
}
