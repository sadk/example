package org.lsqt.syswin.uum.service.impl;

import java.util.List;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.DbException;
import org.lsqt.components.db.Page;
import org.lsqt.components.db.Plan;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.authority.model.Function;
import org.lsqt.syswin.authority.model.FunctionQuery;
import org.lsqt.syswin.authority.model.Menu;
import org.lsqt.syswin.authority.model.MenuQuery;
import org.lsqt.syswin.authority.model.RangeValue;
import org.lsqt.syswin.authority.model.RangeValueQuery;
import org.lsqt.syswin.authority.model.Role;
import org.lsqt.syswin.authority.model.RoleQuery;
import org.lsqt.syswin.uum.model.Org;
import org.lsqt.syswin.uum.model.OrgQuery;
import org.lsqt.syswin.uum.model.Position;
import org.lsqt.syswin.uum.model.PositionQuery;
import org.lsqt.syswin.uum.model.User;
import org.lsqt.syswin.uum.model.UserQuery;
import org.lsqt.syswin.uum.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Inject private PlatformDb db;
	
	
	public User getById(Long userId) {
		User user = db.getById(User.class, userId);
		
		// 获取用户的岗位
		PositionQuery query = new PositionQuery();
		query.setUserId(userId);
		user.setUserPositionList(db.queryForList("queryForPage", Position.class, query));
		
		// 获取用户的角色
		RoleQuery query2=new RoleQuery();
		query2.setUserId(userId);
		user.setUserRoleList(db.queryForList("queryForPage", Role.class, query2));
		
		// 获取用户的部门
		OrgQuery query3 = new OrgQuery();
		query3.setUserId(userId);
		user.setUserOrgList(db.queryForList("queryForPage", Org.class, query3));
		
		// 获取用户授权的菜单
		MenuQuery query4 = new MenuQuery();
		query4.setUserId(userId);
		user.setUserMenuList(db.queryForList("queryForPage", Menu.class, query4));
		
		// 用户授权的功能按钮
		FunctionQuery query5 = new FunctionQuery();
		query5.setUserId(userId);
		user.setUserFunctionList(db.queryForList("queryForPage", Function.class, query5));
		
		// 用户的数据值权限
		RangeValueQuery query6 = new RangeValueQuery();
		query6.setUserId(userId);
		user.setUserRangeValueList(db.queryForList("queryForPage", RangeValue.class, query6));
		
		// 用户的主部门
		user.setUserMainOrg(db.queryForObject("getUserMainOrg", Org.class, userId));
		
		// 用户的主岗位
		user.setUserMainPosition(db.queryForObject("getUserMainPosition", Position.class, userId));
		return user;
	}
	
	public Page<User>  queryForPage(UserQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), User.class, query);
	}

	public List<User> getAll(){
		  return db.queryForList("getAll", User.class);
	}
	
	public User saveOrUpdate(User model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		if(ids==null || ids.length ==0) return 0;
		db.executePlan(true,new Plan(){

			@Override
			public void doExecutePlan() throws DbException {
				for(Long id: ids) {
					// 删除用户与岗位的关系
					//String sql = "delete from  t_user_duties where user_id=?";
					//db.executeUpdate(sql, id);
					
					// 删除用户(不做物理删除)
					//db.deleteById(User.class, id);
					String sql2 = String.format("update %s set del_flag=0 where user_id=?",db.getFullTable(User.class));
					db.executeUpdate(sql2, id);
				}
			}
		});

		return ids.length;
	}

	public List<User> queryForList(UserQuery query) {
		 
		return db.queryForList("queryForPage", User.class,query);
	}

}
