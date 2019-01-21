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
import org.lsqt.uum.model.Org;
import org.lsqt.uum.model.OrgQuery;
import org.lsqt.uum.model.Res;
import org.lsqt.uum.model.Role;
import org.lsqt.uum.model.User;
import org.lsqt.uum.service.OrgService;




@Controller(mapping={"/org"})
public class OrgController {
	
	@Inject private OrgService orgService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Org> queryForPage(OrgQuery query) throws IOException {
		return orgService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Org> getAll() {
		return orgService.getAll();
	}
	
	@Cache(Org.class)
	@RequestMapping(mapping = { "/all_selector", "/m/all_selector" },text="组织机构选择器用（过滤自己节点做为父节点等）")
	public Collection<Org> getAll(OrgQuery query) {
		return db.queryForList("queryForPage", Org.class, query);
	}
	
	@Cache(value = { User.class, Org.class, Role.class, Res.class }, evict = true)
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Org saveOrUpdate(Org form) {
		return orgService.saveOrUpdate(form);
	}
	
	@Cache(value = { User.class, Org.class, Role.class, Res.class }, evict = true)
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return orgService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@Cache(value = { User.class, Org.class, Role.class, Res.class }, evict = true)
	@RequestMapping(mapping = { "/add_role_to_org", "/m/add_role_to_org" },text="添加(一个或多个)角色给部门")
	public Long addRoleToOrg(Long orgId,String roleIds) {
		Long cnt = 0L;
		if(orgId!=null && StringUtil.isNotBlank(roleIds)) {
			String sql = "select count(1) cnt from uum_object_role where obj_type=? and obj_id=? and role_id=?";
			
			List<Long> rids = StringUtil.split( Long.class ,roleIds, ",");
			for(Long roleId: rids) {
				Integer count = db.executeQueryForObject(sql, Integer.class,Role.OBJ_TYPE_部门, orgId,roleId);
				if(count!=null && count==0) {
					String sql2 = "insert uum_object_role(role_id,obj_id,obj_type) values (?,?,?) ";
					Long tmp = db.executeUpdate(sql2, roleId,orgId,Role.OBJ_TYPE_部门);
					cnt += tmp;
				}
			}
		}
		return cnt;
	}
 
	@Cache(value = { User.class, Org.class }, evict = true)
	@RequestMapping(mapping = { "/add_user_to_org", "/m/add_user_to_org" },text="添加(一个或多个)用户到部门下")
	public Long addUserToOrg(Long orgId,String userIds) {
		Long cnt = 0L;
		if(orgId!=null && StringUtil.isNotBlank(userIds)) {
			String sql = "select count(1) cnt from uum_user_object where obj_type=? and obj_id=? and user_id=?";
			
			List<Long> uids = StringUtil.split( Long.class ,userIds, ",");
			for(Long userId: uids) {
				Integer count = db.executeQueryForObject(sql, Integer.class,Role.OBJ_TYPE_部门, orgId,userId);
				if(count!=null && count==0) {
					String sql2 = "insert uum_user_object(user_id,obj_type,obj_id) values (?,?,?) ";
					Long tmp = db.executeUpdate(sql2, userId,Role.OBJ_TYPE_部门,orgId);
					cnt += tmp;
				}
			}
		}
		return cnt;
	}
	
	@Cache(value = { User.class, Org.class, Role.class, Res.class }, evict = true)
	@RequestMapping(mapping = { "/remove_role_from_org", "/m/remove_role_from_org" },text="移除部门的(一个或多个)角色")
	public Integer removeRoleFromOrg(Long orgId,String roleIds) {
		Integer cnt = 0 ;
		if(orgId!=null && StringUtil.isNotBlank(roleIds)) {
			List<Long> rids = StringUtil.split(Long.class, roleIds, ",");
			String sql = "delete from uum_object_role where obj_type=? and obj_id=? and role_id=?";
			for(Long rid: rids) {
				int tmp = db.executeUpdate(sql, Role.OBJ_TYPE_部门,orgId,rid);
				cnt += tmp;
			}
		}
		return cnt;
	}
	
	@Cache(value = { User.class, Org.class, Role.class, Res.class }, evict = true)
	@RequestMapping(mapping = { "/remove_user_from_org", "/m/remove_user_from_org" },text="移除部门的(一个或多个)用户")
	public Integer removeUserFromOrg(Long orgId,String userIds) {
		Integer cnt = 0 ;
		if(orgId!=null && StringUtil.isNotBlank(userIds)) {
			List<Long> uids = StringUtil.split(Long.class, userIds, ",");
			String sql = "delete from uum_user_object where obj_type=? and obj_id=? and user_id=? ";
			for(Long userId: uids) {
				int tmp = db.executeUpdate(sql, Role.OBJ_TYPE_部门,orgId,userId);
				cnt += tmp;
			}
		}
		return cnt;
	}
}
