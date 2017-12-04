package org.lsqt.syswin.uum.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.syswin.uum.model.PositionCategoryData;
import org.lsqt.syswin.uum.model.PositionCategoryDataQuery;
import org.lsqt.syswin.uum.service.PositionCategoryDataService;

@Service
public class PositionCategoryDataServiceImpl implements PositionCategoryDataService{
	
	@Inject private Db db;
	
	public Page<PositionCategoryData>  queryForPage(PositionCategoryDataQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), PositionCategoryData.class, query);
	}

	public List<PositionCategoryData> getAll(){
		  return db.queryForList("getAll", PositionCategoryData.class);
	}
	
	public PositionCategoryData saveOrUpdate(PositionCategoryData model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(PositionCategoryData.class, Arrays.asList(ids).toArray());
	}
}
