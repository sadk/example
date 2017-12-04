package org.lsqt.syswin.authority.service.impl;

import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.DbException;
import org.lsqt.components.db.Page;
import org.lsqt.components.db.Plan;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.authority.model.Role;
import org.lsqt.syswin.authority.model.RoleQuery;
import org.lsqt.syswin.authority.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Inject private PlatformDb db;
	
	public Page<Role>  queryForPage(RoleQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Role.class, query);
	}

	public List<Role> getAll(){
		  return db.queryForList("getAll", Role.class);
	}
	
	public Role saveOrUpdate(Role model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long... ids) {
		if(ids==null || ids.length ==0) return 0;
		
		db.executePlan(true, new Plan() {
			
			@Override
			public void doExecutePlan() throws DbException {
				for (Long id : ids) {
					// 1.删除角色与岗位的关系
					String sql = "delete from T_POWER_DUTIES_ROLE where role_id=?";
					db.executeUpdate(sql, id);
					
					// 2.删除角色与菜单的关系
					String sql2="delete from T_POWER_ROLE_MENU_OPERATION where role_id=?";
					db.executeUpdate(sql2, id);
					
					// 3.删除角色与功能按导的关系
					String sql3="delete from T_POWER_ROLE_MENU_FUNC_OPERATION where role_id=?";
					db.executeUpdate(sql3, id);
					
					// 4.删除角色与数据范围的关系
					String sql4="delete from T_POWER_ROLE_RANGE_RES where role_id=?";
					db.executeUpdate(sql4, id);
					
					// 5.删除角色！！！
					db.deleteById(Role.class,id);
					
				}
			}
		});

		return ids.length;
	}
}
