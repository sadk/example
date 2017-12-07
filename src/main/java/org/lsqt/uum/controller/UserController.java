package org.lsqt.uum.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.After;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
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
import org.lsqt.uum.service.OrgService;
import org.lsqt.uum.service.UserService;

@SuppressWarnings("unchecked")
class RequestAfterProcess {
	
	public Object toPc(Object invokedValue, Long id, String name) {
		Page<User> page = (Page<User>)invokedValue;
		page.setTotal(10000);
		
		return page;
	}

	public Object toMobile(Object invokedValue, Date date) {
		Page<User> page = (Page<User>)invokedValue;
		page.setTotal(2000);
		return page;
	}
}

@Controller(mapping={"/user"})
public class UserController {
	    
	
	@Inject private UserService userService; 
	@Inject private OrgService orgService;
	@Inject private Db db;

	
	@RequestMapping(mapping = { "/page", "/m/page" })
	@After(clazz =(RequestAfterProcess.class),method="convert",args={Object.class,Long.class,String.class})
	public Page<User> queryForPage(UserQuery query,Boolean isAllChild) throws IOException {
		if (isAllChild != null && isAllChild) { 
			Long root = query.getOrgId();
			List<Org> list = orgService.getAllChildNodes(root);
			
			List<Long> ids = new ArrayList<>();
			for(Org e: list) {
				ids.add(e.getId());
			}
			
			query.setOrgId(null);
			query.setOrgIds(StringUtil.join(ids, ","));
		}
		return userService.queryForPage(query); 
	}
	
	@RequestMapping(mapping = { "/get_all_user_by_orgid", "/m/get_all_user_by_orgid" },text="获取部门下的用户(多层)")
	public Page<User> getAllUserByOrgId(UserQuery query){
		
		if(query.getOrgId()!=null) { 
			
			List<Org> list = orgService.getAllChildNodes(query.getOrgId());
			StringBuilder orgIds = new StringBuilder();
			for (int i = 0; i < list.size(); i++) {
				orgIds.append(list.get(i).getId());
				if(i!=list.size()-1){
					orgIds.append(",");
				}
			}
			
			String orgIdsText = orgIds.toString();
			if(StringUtil.isNotBlank(orgIdsText)) {
				query = new UserQuery();
				query.setOrgIds(orgIdsText);
				return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), User.class, query);
			}
			
		}
		return db.getEmptyPage();
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<User> getAll() {
		return userService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public User saveOrUpdate(User form) {
		return userService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return userService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	
	@RequestMapping(mapping = { "/get_role_list", "/m/get_role_list" },text="获取用户的角色")
	public Page<Role> getRoleList(RoleQuery query) {
		if(query.getUserId()!=null){
			return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Role.class, query);
		}
		return db.getEmptyPage();
	}
	
	@RequestMapping(mapping = { "/get_org_list", "/m/get_org_list" },text="获取用户的部门")
	public List<Org> getOrgList(OrgQuery query) {
		if(query.getUserId()!=null) {
			return db.queryForList("queryForPage", Org.class, query);
		}
		return new ArrayList<>();
	}
	
	@RequestMapping(mapping = { "/get_group_list", "/m/get_group_list" },text="获取用户的组")
	public List<Group> getGroupList(GroupQuery query) {
		if(query.getUserId()!=null) {
			return db.queryForList("queryForPage", Group.class, query);
		}
		return new ArrayList<>();
	}
	
	@RequestMapping(mapping = { "/get_title_list", "/m/get_title_list" },text="获取用户的称谓")
	public List<Title> getTitleList(TitleQuery query) {
		if(query.getUserId()!=null) {
			query.setObjType(Role.OBJ_TYPE_职称+","+Role.OBJ_TYPE_岗位);
			return db.queryForList("queryForPage", Title.class, query);
		}
		return new ArrayList<>();
	}
	
	@RequestMapping(mapping = { "/get_permission_list", "/m/get_permission_list" },text="获取用户的权限")
	public List<Res> getPermissionList(ResQuery query) {
		if(query.getUserId()!=null) {
			return db.queryForList("queryForPage", Res.class, query);
		}
		return new ArrayList<>();
	}
	
	@RequestMapping(mapping = { "/add_object_to_user", "/m/add_object_to_user" },text="给用户添加角色、组、部门、称谓")
	public int addObjectToUser(Long userId,String objectIds,String objType){
		Long cnt = 0L;
		if (userId != null && StringUtil.isNotBlank(objectIds)) {
			List<Long> list = StringUtil.split(Long.class, objectIds, ",");
			String sql = "select count(1) from uum_user_object where user_id=? and obj_id=? and obj_type=?";
			for (Long objId : list) {
				Integer ct = db.executeQueryForObject(sql, Integer.class, userId, objId,objType);
				if (ct != null && ct == 0) {
					String sql2 = "insert uum_user_object(obj_type,user_id,obj_id,obj_pid,obj_node_path) values(?,?,?,?,?)";
					if (Role.OBJ_TYPE_角色.equals(objType)) {
						long tmp = db.executeUpdate(sql2, objType, userId, objId, null,null);
						cnt += tmp;
					} 
					else if (Role.OBJ_TYPE_组.equals(objType)) {
						Group grp = db.getById(Group.class, objId);
						if(grp!=null) {
							long tmp = db.executeUpdate(sql2, objType, userId, objId, grp.getPid(),grp.getNodePath());
							cnt += tmp;
						}
					}
					else if (Role.OBJ_TYPE_部门.equals(objType)) {
						Org org = db.getById(Org.class, objId);
						if (org!=null) {
							long tmp = db.executeUpdate(sql2, objType, userId, objId, org.getPid(),org.getNodePath());
							cnt += tmp;
						}
					}
					else if ((Role.OBJ_TYPE_职称 +","+Role.OBJ_TYPE_岗位).equals(objType)) {
						Title title = db.getById(Title.class, objId);
						if (title!=null) {
							long tmp = db.executeUpdate(sql2, title.getType(), userId, objId, title.getPid(),title.getNodePath());
							cnt += tmp;
						}
					}
				}
			}
		}
		return cnt.intValue();
	}
	
	@RequestMapping(mapping = { "/delete_object_from_user", "/m/delete_object_from_user" },text="给用户删除一个或多个角色、组、部门、称谓")
	public int deleteObjectFromUser(Long userId,String objectIds,String objType){
		Long cnt = 0L;
		if(StringUtil.isNotBlank(objectIds) && userId!=null) {
			objectIds = StringUtil.escapeSql(objectIds);
			objType = StringUtil.escapeSql(objType);
			
			String sql ="delete from uum_user_object where obj_type in("+objType+") and user_id=? and obj_id in("+objectIds+")";
			Integer temp = db.executeUpdate(sql,userId);
			cnt+= temp;
		}
		
		return cnt.intValue();
	}
	
	
}