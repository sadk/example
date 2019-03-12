package org.lsqt.uum.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Cache;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.lsqt.uum.model.Group;
import org.lsqt.uum.model.GroupQuery;
import org.lsqt.uum.model.Org;
import org.lsqt.uum.model.OrgQuery;
import org.lsqt.uum.model.Res;
import org.lsqt.uum.model.ResQuery;
import org.lsqt.uum.model.Role;
import org.lsqt.uum.model.RoleQuery;
import org.lsqt.uum.model.Title;
import org.lsqt.uum.model.TitleQuery;
import org.lsqt.uum.model.User;
import org.lsqt.uum.model.UserQuery;
import org.lsqt.uum.service.UserService;
import org.lsqt.uum.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserServiceImpl implements UserService{
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Inject private Db db;

	@Cache(User.class)
	public User getById(Long id) {
		return db.getById(User.class, id);
	}
	
	@Cache(User.class)
	public User getById(Long id,boolean cascade) {
		User user = db.getById(User.class, id);
		if (user == null) return null;
		
		if(cascade) {
			OrgQuery orgQuery = new OrgQuery();
			orgQuery.setUserId(id);
			user.setMyOrgList(db.queryForList("queryForPage",Org.class,orgQuery));
			
			GroupQuery grpQuery = new GroupQuery();
			grpQuery.setUserId(id);
			user.setMyGroupList(db.queryForList("queryForPage",Group.class,grpQuery));
			
			TitleQuery titleQuery = new TitleQuery();
			titleQuery.setUserId(id);
			titleQuery.setObjType(Role.OBJ_TYPE_职称+","+Role.OBJ_TYPE_岗位);
			user.setMyTitleList(db.queryForList("queryForPage", Title.class, titleQuery));
			
			RoleQuery roleQuery = new RoleQuery();
			roleQuery.setUserId(id);
			user.setMyRoleList(db.queryForList("queryForPage", Role.class, roleQuery));
			
			ResQuery resQuery = new ResQuery();
			resQuery.setUserId(id);
			user.setMyResList(getResList(resQuery));
		}
		
		return user;
	}
	
	/**
	 * 获取用户的权限数据
	 * @param query
	 * @return
	 */
	@Cache(User.class)
	public List<Res> getResList(ResQuery query) {
		String enablePermission = ResourceUtil.getValue("user.permission.enable");
		if (StringUtil.isBlank(enablePermission)) {
			log.error("没有配置权限是否开启参数，见：config.properties");
			return new ArrayList<>();
		}

		if ("true".equalsIgnoreCase(enablePermission)) {
			if (query.getUserId() != null) {
				return db.queryForList("queryForPage", Res.class, query);
				
			} else {
				String loginName = ContextUtil.getLoginAccount();
				if (StringUtil.isBlank(loginName)) {
					throw new NullPointerException("登陆账号为空");
				}
				
				if("admin".equals(loginName)) { // 写死如果是超级管理员admin,显示全部菜单，
					query.setUserId(null);
					return db.queryForList("queryForPage", Res.class,query);
				}
				
				UserQuery q = new UserQuery();
				q.setLoginName(loginName);
				User user = db.queryForObject("queryForPage", User.class, q);
				if (user != null) {
					query.setUserId(user.getId());
					return db.queryForList("queryForPage", Res.class, query);
				}
			}
			return new ArrayList<>();
		} else {
			return db.queryForList("queryForPage", Res.class,query);
		}
	}
	
	public Page<User>  queryForPage(UserQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), User.class, query);
	}

	@Cache(User.class)
	public List<User> getAll(){
		  return db.queryForList("queryForPage", User.class);
	}
	
	@Cache(value = User.class , evict = true)
	public User saveOrUpdate(User model) {
		if(StringUtil.isBlank(model.getAppCode())){
			model.setAppCode(Application.APP_CODE_DEFAULT);
		}
		return db.saveOrUpdate(model);
	}
	
	@Cache(value = User.class , evict = true)
	public int deleteById(Long ... ids) {
		return db.deleteById(User.class, Arrays.asList(ids).toArray());
	}

 

}