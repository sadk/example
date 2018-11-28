package org.lsqt.act.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.lsqt.act.model.UserDataConfigParam;
import org.lsqt.act.model.UserDataConfigParamQuery;
import org.lsqt.act.service.UserDataConfigParamService;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;

@Service
public class UserDataConfigParamServiceImpl implements UserDataConfigParamService {
	@Inject private Db db;

	
	public Page<UserDataConfigParam> queryForPage(UserDataConfigParamQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), UserDataConfigParam.class, query);
	}
	
	public UserDataConfigParam saveOrUpdate(UserDataConfigParam model) {
		return db.saveOrUpdate(model);
	}
	
	public int deleteById(Long... ids) {
		return db.deleteById(UserDataConfigParam.class, Arrays.asList(ids).toArray());
	}

	
	public Collection<UserDataConfigParam> getAll() {
		return db.queryForList("getAll", UserDataConfigParam.class);
	}

	
	public List<UserDataConfigParam> queryForList(UserDataConfigParamQuery query) {
		return db.queryForList("queryForPage",UserDataConfigParam.class,query);
	}
	
}
