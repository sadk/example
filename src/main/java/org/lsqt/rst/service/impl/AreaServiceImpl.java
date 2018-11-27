package org.lsqt.rst.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.rst.model.Area;
import org.lsqt.rst.model.AreaQuery;
import org.lsqt.rst.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService{
	
	@Inject private Db db;
	
	public Area getById(Long id) {
		return db.getById(Area.class, id) ;
	}
	
	public List<Area> queryForList(AreaQuery query) {
		return db.queryForList("queryForPage", Area.class, query);
	}
	
	public Page<Area> queryForPage(AreaQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Area.class, query);
	}

	public List<Area> getAll(){
		  return db.queryForList("getAll", Area.class);
	}
	
	public Area saveOrUpdate(Area model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(Area.class, Arrays.asList(ids).toArray());
	}
}
