package org.lsqt.uum.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Cache;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.uum.model.Group;
import org.lsqt.uum.model.GroupQuery;
import org.lsqt.uum.model.Org;
import org.lsqt.uum.model.Res;
import org.lsqt.uum.model.Role;
import org.lsqt.uum.model.User;
import org.lsqt.uum.model.UserQuery;
import org.lsqt.uum.service.GroupService;




@Controller(mapping={"/group"})
public class GroupController {
	
	@Inject private GroupService groupService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Group> queryForPage(GroupQuery query) throws IOException {
		return groupService.queryForPage(query); //  
	}
	
	@Cache(Group.class)
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Group> getAll() {
		return groupService.getAll();
	}
	
	@Cache(Group.class)
	@RequestMapping(mapping = { "/all_selector", "/m/all_selector" },text="组选择器用（过滤自己节点做为父节点等）")
	public Collection<Group> getAll(GroupQuery query) {
		return db.queryForList("queryForPage", Group.class, query);
	}
	
	@Cache(value = { User.class, Role.class, Res.class ,Group.class}, evict = true)
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Group saveOrUpdate(Group form) {
		return groupService.saveOrUpdate(form);
	}
	
	@Cache(value = { User.class, Role.class, Res.class ,Group.class}, evict = true)
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return groupService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@Cache(value = { User.class, Group.class}, evict = true)
	@RequestMapping(mapping = { "/add_user_to_group", "/m/add_user_to_group" },text="添加多个用户到一个组")
	public int addUserToGroup(Long groupId,String userIds) {
		if(StringUtil.isNotBlank(userIds) && groupId!=null) {
			List<String> uids1 = StringUtil.split(String.class, userIds, ",");

			List<String> uids2 = new ArrayList<>();
			UserQuery query = new UserQuery();
			query.setGroupId(groupId);
			List<User> list = db.queryForList("queryForPage", User.class, query);
			for (User u : list) {
				uids2.add(u.getId().toString());
			}

			
			for(String id: uids1) {
				boolean isExists = false;
				for(String u: uids2){
					if(id.equals(u)) {
						isExists = true;
						break;
					}
				}
				if(!isExists) {
					String sql = "insert into uum_user_object (obj_id,user_id,obj_type) values(?,?,?)";
					db.executeUpdate(sql, groupId,Long.valueOf(id),Role.OBJ_TYPE_组);
				}
			}
		}
		
		return 0;
	}
	
	@Cache(value = { User.class, Group.class}, evict = true)
	@RequestMapping(mapping = { "/delete_user_from_group", "/m/delete_user_from_group" })
	public int deleteUserFromGroup(Long groupId,String userIds) {
		if(StringUtil.isNotBlank(userIds) && groupId!=null) { // 删除某组的用户(一层)
			
				userIds = StringUtil.escapeSql(userIds);
				List<String> args = StringUtil.split(String.class, userIds, ",");
				userIds = StringUtil.join(args, ",");

				String sql=String.format("delete from uum_user_object where obj_id=? and user_id in (%s) and obj_type=?",userIds);
				return db.executeUpdate(sql, groupId,Role.OBJ_TYPE_组);
			
		}
		
		return 0;
	}
	
	@Cache(value = { User.class, Role.class, Res.class, Group.class }, evict = true)
	@RequestMapping(mapping = { "/add_role_to_group", "/m/add_role_to_group" },text="添加多个角色到一个组")
	public int addRoleToGroup(Long groupId,String roleIds) {
		int cnt = 0 ;
		if(StringUtil.isNotBlank(roleIds) && groupId!=null) {
			List<Long> rList = StringUtil.split(Long.class, roleIds, ",");
			
			// 先删除，后添加
			String sql = "delete from uum_object_role where role_id=? and obj_id=? and obj_type=?";
			String sql2= "insert into uum_object_role(role_id,obj_id,obj_type) value (?,?,?)";
			for (Long rid: rList) {
				db.executeUpdate(sql, rid,groupId,Role.OBJ_TYPE_组);
				db.executeUpdate(sql2, rid,groupId,Role.OBJ_TYPE_组);
				cnt += 2;
			}
		}
		
		return cnt;
	}
	
	@Cache(value = { User.class, Role.class, Res.class, Group.class }, evict = true)
	@RequestMapping(mapping = { "/remove_role_from_group", "/m/remove_role_from_group" },text="删除组的角色")
	public int removeRoleFromGroup(Long groupId,String roleIds) {
		int cnt = 0 ;
		if(StringUtil.isNotBlank(roleIds) && groupId!=null) {
			List<Long> rList = StringUtil.split(Long.class, roleIds, ",");
			
			// 先删除，后添加
			String sql = "delete from uum_object_role where role_id=? and obj_id=? and obj_type=?";
			
			for (Long rid: rList) {
				db.executeUpdate(sql, rid,groupId,Role.OBJ_TYPE_组);
				cnt += 1;
			}
		}
		
		return cnt;
	}
	
}
