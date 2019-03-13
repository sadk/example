package org.lsqt.sys.service.impl;

import java.util.Arrays;
import java.util.Collection;

import org.lsqt.sys.model.ApiParam;
import org.lsqt.sys.model.ApiParamQuery;
import org.lsqt.sys.service.ApiParamService;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;

@Service
public class ApiParamServiceImpl implements ApiParamService{
	@Inject private Db db;
	
	public ApiParam getById(Long id) {
		return db.getById(ApiParam.class, id);
	}
	
	public Page<ApiParam> queryForPage(ApiParamQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), ApiParam.class, query);
	}

	public ApiParam saveOrUpdate(ApiParam model) {
		return db.saveOrUpdate(model);
	}
	
	public int deleteById(Long... ids) {
		return db.deleteById(ApiParam.class,  Arrays.asList(ids).toArray());
	}
	
	public Collection<ApiParam> getAll() {
		return db.queryForList("getAll", ApiParam.class);
	}

}
