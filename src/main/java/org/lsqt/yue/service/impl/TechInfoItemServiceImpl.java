package org.lsqt.yue.service.impl;

import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.yue.model.TechInfoItem;
import org.lsqt.yue.model.TechInfoItemQuery;
import org.lsqt.yue.service.TechInfoItemService;

@Service
public class TechInfoItemServiceImpl implements TechInfoItemService{
	
	@Inject private Db db;
	
	public Page<TechInfoItem>  queryForPage(TechInfoItemQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), TechInfoItem.class, query);
	}

	public List<TechInfoItem> getAll(){
		  return db.queryForList("getAll", TechInfoItem.class);
	}
	
	public TechInfoItem saveOrUpdate(TechInfoItem model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(TechInfoItem.class, Arrays.asList(ids).toArray());
	}
}
