package org.lsqt.api.service.impl;

import java.util.Arrays;
import java.util.Collection;

import org.lsqt.api.model.Param;
import org.lsqt.api.model.ParamQuery;
import org.lsqt.api.service.ParamService;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;

@Service
public class ParamServiceImpl implements ParamService{
	@Inject private Db db;
	
	public Param getById(Long id) {
		return db.getById(Param.class, id);
	}
	
	public Page<Param> queryForPage(ParamQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Param.class, query);
	}

	public Param saveOrUpdate(Param model) {
		return db.saveOrUpdate(model);
	}
	
	public int deleteById(Long... ids) {
		return db.deleteById(Param.class,  Arrays.asList(ids).toArray());
	}
	
	public Collection<Param> getAll() {
		return db.queryForList("getAll", Param.class);
	}

}
