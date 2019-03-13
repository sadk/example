package org.lsqt.sys.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.sys.model.ApiRequestMock;
import org.lsqt.sys.model.ApiRequestMockQuery;
import org.lsqt.sys.service.ApiRequestMockService;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;

@Service
public class ApiRequestMockServiceImpl implements ApiRequestMockService{
	
	@Inject private Db db;
	
	public Page<ApiRequestMock>  queryForPage(ApiRequestMockQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), ApiRequestMock.class, query);
	}

	public List<ApiRequestMock> getAll(){
		  return db.queryForList("getAll", ApiRequestMock.class);
	}
	
	public ApiRequestMock saveOrUpdate(ApiRequestMock model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(ApiRequestMock.class, Arrays.asList(ids).toArray());
	}

	public ApiRequestMock getById(Long id) {
		return db.getById(ApiRequestMock.class, id);
	}

	public List<ApiRequestMock> queryForList(ApiRequestMockQuery query) {
		return db.queryForList("queryForPage", ApiRequestMock.class, query);
	}
}
