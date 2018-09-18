package org.lsqt.uum.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.bean.BeanUtil;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.uum.model.Org;
import org.lsqt.uum.model.Role;
import org.lsqt.uum.model.RoleQuery;
import org.lsqt.uum.service.OrgService;
import org.lsqt.uum.service.RoleService;




@Controller(mapping={"/role"})
public class RoleController {
	
	@Inject private RoleService roleService; 
	@Inject private OrgService orgService;
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Role> queryForPage(RoleQuery query,Boolean isAllChild) throws IOException {
		
		if(isAllChild!=null && isAllChild) {
			Long root = query.getOrgId();
			List<Org> list = orgService.getAllChildNodes(root);
			
			List<Long> ids = new ArrayList<>();
			for(Org e: list) {
				ids.add(e.getId());
			}
			
			query.setOrgId(null);
			query.setOrgIds(StringUtil.join(ids, ","));
		}
		return roleService.queryForPage(query);
	}
	
	/**
	 * 用来构建设角色树
	 * @author mingmin.yuan
	 *
	 */
	public static class Node extends Role {
		private Long pid;

		public Long getPid() {
			return pid;
		}

		public void setPid(Long pid) {
			this.pid = pid;
		}
	}
	
	@RequestMapping(mapping = { "/tree", "/m/tree" })
	public Collection<Role> queryForTree(RoleQuery query) throws Exception {
		List<Role> nodeList = new ArrayList<>();

		Page<Role> page = roleService.queryForPage(query);
		if (ArrayUtil.isNotBlank(page.getData())) {
			for (Role e : page.getData()) {
				Node n = new Node();
				n.pid = -1L;
				BeanUtils.copyProperties(n, e);
				nodeList.add(n);
			}
		}
		return nodeList;
	}

	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Role> getAll() {
		return roleService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Role saveOrUpdate(Role form) {
		return roleService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return roleService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	
	@RequestMapping(mapping = { "/add_user_to_role", "/m/add_user_to_role" },text="给角色添加(一个或多个)用户")
	public Long addUserToRole(Long roleId,String userIds) {
		Long cnt = 0L;
		if(roleId!=null && StringUtil.isNotBlank(userIds)) {
			String sql = "select count(1) cnt from uum_user_object where obj_type=? and obj_id=? and user_id=?";
			
			List<Long> uids = StringUtil.split(Long.class ,userIds, ",");
			for(Long userId: uids) {
				Integer count = db.executeQueryForObject(sql, Integer.class,Role.OBJ_TYPE_角色, roleId,userId);
				if(count!=null && count==0) {
					String sql2 = "insert uum_user_object(user_id,obj_type,obj_id) values (?,?,?) ";
					Long tmp = db.executeUpdate(sql2, userId,Role.OBJ_TYPE_角色,roleId);
					cnt += tmp;
				}
			}
		}
		return cnt;
	}
	
	@RequestMapping(mapping = { "/remove_user_from_role", "/m/remove_user_from_role" },text="移除角色下的用户")
	public Integer removeUserFromRole(Long roleId,String userIds) {
		Integer cnt = 0 ;
		if(roleId!=null && StringUtil.isNotBlank(userIds)) {
			List<Long> rids = StringUtil.split(Long.class, userIds, ",");
			String sql = "delete from uum_user_object where obj_type=? and obj_id=? and user_id=?";
			for(Long rid: rids) {
				int tmp = db.executeUpdate(sql, Role.OBJ_TYPE_角色,roleId,rid);
				cnt += tmp;
			}
		}
		return cnt;
	}
	
	@RequestMapping(mapping = { "/add_res_to_role", "/m/add_res_to_role" },text="给角色添加(一个或多个)资源")
	public Long addResToRole(Long roleId,String resIds) {
		Long cnt = 0L;
		if(roleId!=null && StringUtil.isNotBlank(resIds)) {
			String sql = "select count(1) cnt from uum_role_res where res_id=? and role_id=?";
			
			List<Long> uids = StringUtil.split(Long.class ,resIds, ",");
			for(Long resId: uids) {
				Integer count = db.executeQueryForObject(sql, Integer.class, resId,roleId);
				if(count!=null && count==0) {
					String sql2 = "insert uum_role_res(res_id,role_id) values (?,?) ";
					Long tmp = db.executeUpdate(sql2, resId,roleId);
					cnt += tmp;
				}
			}
		}
		return cnt;
	}
	
	@RequestMapping(mapping = { "/delete_res_from_role", "/m/delete_res_from_role" },text="删除角色下(一个或多个)资源")
	public Integer deleteResFromRole(Long roleId,String resIds) {
		Integer cnt = 0;
		if(roleId!=null && StringUtil.isNotBlank(resIds)) {
			
			List<Long> uids = StringUtil.split(Long.class ,resIds, ",");
			for(Long resId: uids) {
				String sql2 = "delete from uum_role_res where role_id=? and res_id=? ";
				Integer tmp = db.executeUpdate(sql2, roleId,resId);
				cnt += tmp;
			}
		}
		return cnt;
	}
	
}
