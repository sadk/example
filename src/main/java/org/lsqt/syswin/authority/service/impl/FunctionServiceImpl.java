package org.lsqt.syswin.authority.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Page;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.authority.model.Function;
import org.lsqt.syswin.authority.model.FunctionQuery;
import org.lsqt.syswin.authority.service.FunctionService;

@Service
public class FunctionServiceImpl implements FunctionService{
	
	@Inject private PlatformDb db;
	
	public Page<Function>  queryForPage(FunctionQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Function.class, query);
	}

	public List<Function> getAll(){
		  return db.queryForList("getAll", Function.class);
	}
	
	public Function saveOrUpdate(Function model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		if (ids == null || ids.length == 0) {
			return 0;
		}
		for (Long id : ids) {
			// 删除按钮与菜单的关系
			db.executeUpdate("delete from t_power_menu_func where func_id=? ", id);

			// 删除菜单
			db.deleteById(Function.class, id);
		}
		return ids.length;
	}
	
	public List<Function> getFunctionListByRoleID(Long roleId) {
		if (roleId == null) {
			return new ArrayList<>();
		}

		FunctionQuery query = new FunctionQuery();
		query.setRoleId(roleId);
		return db.queryForList("getFunctionListByRoleID", Function.class, query);
	}

	public List<Function> getFunctionListByUserID(Long userId) {
		if (userId == null) {
			return new ArrayList<>();
		}
		
		FunctionQuery query = new FunctionQuery();
		query.setUserId(userId);
		return db.queryForList("getFunctionListByUserID", Function.class, query);
	}
}
