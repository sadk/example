package org.lsqt.act.service.impl;

import java.util.Collection;
import java.util.List;

import org.lsqt.act.model.UserDataMapping;
import org.lsqt.act.model.UserDataMappingQuery;
import org.lsqt.act.service.UserDataMappingService;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;

@Service
public class UserDataMappingServiceImpl implements UserDataMappingService {
	@Inject private Db db;

	
	public Page<UserDataMapping> queryForPage(UserDataMappingQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), UserDataMapping.class, query);
	}
	
	public UserDataMapping saveOrUpdate(UserDataMapping model) {
		return db.saveOrUpdate(model);
	}
	
	public int deleteById(Long... ids) {
		return db.deleteById(UserDataMapping.class, ids);
	}

	
	public Collection<UserDataMapping> getAll() {
		return db.queryForList("getAll", UserDataMapping.class);
	}

	
	public List<UserDataMapping> queryForList(UserDataMappingQuery query) {
		return db.queryForList("queryForPage",UserDataMapping.class,query);
	}
	
}
