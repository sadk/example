package org.lsqt.uum.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.uum.model.Org;
import org.lsqt.uum.model.OrgQuery;
import org.lsqt.uum.model.Title;
import org.lsqt.uum.model.TitleQuery;
import org.lsqt.uum.model.User;
import org.lsqt.uum.model.UserQuery;
import org.lsqt.uum.service.TitleService;




@Controller(mapping={"/title"})
public class TitleController {
	
	@Inject private TitleService titleService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Title> queryForPage(TitleQuery query) throws IOException {
		return titleService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<Title> getAll() {
		return titleService.getAll();
	}
	
	@RequestMapping(mapping = { "/all_selector", "/m/all_selector" },text="组织机构选择器用（过滤自己节点做为父节点等）")
	public Collection<Title> getAll(TitleQuery query) {
		return db.queryForList("queryForPage", Title.class, query);
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public Title saveOrUpdate(Title form) {
		return titleService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return titleService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	@RequestMapping(mapping = { "/add_user_to_title", "/m/add_user_to_title" },text="添加多个用户到一个称谓")
	public int addUserToTitle(Long titleId,String type,String userIds) {
		int cnt = 0;
		
		if(StringUtil.isNotBlank(userIds,type) && titleId!=null) {
			List<Long> uids = StringUtil.split(Long.class, userIds, ",");
			
			String sql = "delete from uum_user_object where user_id=? and obj_type=? and obj_id=?";
			String sql2 = "insert into uum_user_object (user_id,obj_type,obj_id,obj_pid,obj_node_path) values(?,?,?,?,?)";
			
			final Title title = db.getById(Title.class, titleId);
			for (Long userId : uids) {
				int tmp = db.executeUpdate(sql, userId,type,titleId);
				cnt += tmp;
				
				if (title != null) {
					Long pk = db.executeUpdate(sql2, userId,title.getType(),title.getId(),title.getPid(),title.getNodePath());
					if(pk!=null && pk>0) cnt+=1;
				}
			}
			
		}
		return cnt;
	}
	
	
	@RequestMapping(mapping = { "/remove_user_from_title", "/m/remove_user_from_title" },text="移除称谓下的用户")
	public int removeUserFromTitle(Long titleId,String type,String userIds) {
		int cnt = 0;
		if(StringUtil.isNotBlank(userIds,type) && titleId!=null) {
			List<Long> uids = StringUtil.split(Long.class, userIds, ",");
			
			String sql = "delete from uum_user_object where user_id=? and obj_type=? and obj_id=?";
			for (Long userId : uids) {
				int tmp = db.executeUpdate(sql, userId,type,titleId);
				cnt += tmp;
			}
		}
		return cnt;
	}
	
	@RequestMapping(mapping = { "/add_role_to_title", "/m/add_role_to_title" },text="添加多个角色到一个称谓")
	public int addRoleToTitle(Long titleId,String roleIds) {
		int cnt = 0;
		
		if(StringUtil.isNotBlank(roleIds) && titleId!=null) {
			List<Long> roleIdList = StringUtil.split(Long.class, roleIds, ",");
			
			String sql = "delete from uum_object_role where role_id=? and obj_id=? and obj_type=?";
			String sql2 = "insert into uum_object_role (role_id,obj_id,obj_type) values (?,?,?)";
			
			final Title title = db.getById(Title.class, titleId);
			if(title== null )return cnt;
			
			for (Long roleId: roleIdList) {
				int tmp = db.executeUpdate(sql,roleId,titleId, title.getType());
				cnt += tmp;
				
				Long pk = db.executeUpdate(sql2, roleId,titleId,title.getType());
				if(pk>0) cnt += 1;
			}
			
		}
		return cnt;
	}
	
	@RequestMapping(mapping = { "/remove_role_from_title", "/m/remove_role_from_title" },text="删除称谓的角色")
	public int removeRoleFromTitle(Long titleId,String roleIds) {
		int cnt = 0;
		
		if(StringUtil.isNotBlank(roleIds) && titleId!=null) {
			List<Long> roleIdList = StringUtil.split(Long.class, roleIds, ",");
			
			final Title title = db.getById(Title.class, titleId);
			if(title== null )return cnt;
			
			for (Long roleId: roleIdList) {
				String sql = "delete from uum_object_role where role_id=? and obj_id=? and obj_type=?";
			
				int tmp = db.executeUpdate(sql,roleId,titleId, title.getType());
				cnt += tmp;
			}
		}
		return cnt;
	}
	
}
