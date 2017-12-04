package org.lsqt.syswin.authority.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.DbException;
import org.lsqt.components.db.Page;
import org.lsqt.components.db.Plan;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.authority.model.Range;
import org.lsqt.syswin.authority.model.Role;
import org.lsqt.syswin.authority.model.RoleQuery;
import org.lsqt.syswin.authority.service.RoleService;




@Controller(mapping={"/syswin/role","/nv2/syswin/role"})
public class RoleController {
	
	@Inject private RoleService roleService; 
	@Inject private PlatformDb db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<Role> queryForPage(RoleQuery query) throws IOException {
		return roleService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all","/xxx/ppp" })
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
	
	
	// ------------------------------------------------ 角色关联菜单、功能按钮操作 -------
	
	@RequestMapping(mapping = { "/save_position_roles", "/m/save_position_roles" },text="给一个岗位添加多个角色")
	public void savePositionRoles(Long positionId,String roleIds) {
		final List<Long> rids = StringUtil.split(Long.class,roleIds, ",");
		
		db.executePlan(true, new Plan(){
			@Override
			public void doExecutePlan() throws DbException {
				String sql = String.format("delete from t_power_duties_role where duties_id=? and role_id in(%s)",StringUtil.join(rids, ","));
				db.executeUpdate(sql, positionId);
				
				final String insertSql = "insert t_power_duties_role(role_id,duties_id,role_type,create_date) values(?,?,?,?)";
				for(Long rid: rids) {
					Role r= db.getById(Role.class, rid);
					db.executeUpdate(insertSql, rid,positionId ,r.getType(),new Date());
				}
			}
			
		});
	}
	
	@RequestMapping(mapping = { "/delete_position_roles", "/m/delete_position_roles" },text="删除一个岗位上的角色")
	public void deletePositionRoles(Long positionId,String roleIds) {
		if(positionId!=null && StringUtil.isNotBlank(roleIds)){
			final List<Long> rids = StringUtil.split(Long.class,roleIds, ",");
			
			db.executePlan(true, new Plan(){
				@Override
				public void doExecutePlan() throws DbException {
					String sql = String.format("delete from t_power_duties_role where duties_id=? and role_id in(%s)",StringUtil.join(rids, ","));
					db.executeUpdate(sql, positionId);
				}
			});
		}
	}
	
	
	@RequestMapping(mapping = { "/delete_role_menus", "/m/delete_role_menus" },text="删除角色下的菜单")
	public void deleteRoleMenus(Long roleId,String menuIds) {
		if(roleId!=null && StringUtil.isNotBlank(menuIds)){
			final List<Long> mids = StringUtil.split(Long.class,menuIds, ",");
			
			db.executePlan(true, new Plan(){
				@Override
				public void doExecutePlan() throws DbException {
					String sql = String.format("delete from T_POWER_ROLE_MENU_OPERATION where role_id=? and menu_id in(%s)",StringUtil.join(mids, ","));
					db.executeUpdate(sql, roleId);
				}
			});
		}
	}
	
	@RequestMapping(mapping = { "/save_role_menus", "/m/save_role_menus" },text="添加角色的菜单")
	public void saveRoleMenus(Long roleId,String menuIds) {
		if(roleId!=null && StringUtil.isNotBlank(menuIds)){
			final List<Long> mids = StringUtil.split(Long.class,menuIds, ",");
			
			db.executePlan(true, new Plan(){
				@Override
				public void doExecutePlan() throws DbException {
					String sql = String.format("delete from T_POWER_ROLE_MENU_OPERATION where role_id=? and menu_id in(%s)",StringUtil.join(mids, ","));
					db.executeUpdate(sql, roleId);
					
					sql="insert into T_POWER_ROLE_MENU_OPERATION (role_id,menu_id,create_date) values (?,?,?)";
					for(Long mid: mids) {
						db.executeUpdate(sql, roleId,mid,new Date());
					}
				}
			});
		}
	}
 
	
	@RequestMapping(mapping = { "/save_role_functions", "/m/save_role_functions" },text="添加角色的功能")
	public void saveRoleFunctions(Long roleId,String menuFuncIds) {
		if(roleId!=null && StringUtil.isNotBlank(menuFuncIds)){
			final List<Long> mids = StringUtil.split(Long.class,menuFuncIds, ",");
			
			db.executePlan(true, new Plan(){
				@Override
				public void doExecutePlan() throws DbException {
					String sql = String.format("delete from T_POWER_ROLE_MENU_FUNC_OPERATION where role_id=? and menu_func_id in(%s)",StringUtil.join(mids, ","));
					db.executeUpdate(sql, roleId);
					
					sql="insert into T_POWER_ROLE_MENU_FUNC_OPERATION (role_id,MENU_FUNC_ID,create_date) values (?,?,?)";
					for(Long mid: mids) {
						db.executeUpdate(sql, roleId,mid,new Date());
					}
				}
			});
		}
	}
	
	@RequestMapping(mapping = { "/save_role_range_values", "/m/save_role_range_values" },text="添加角色的数据查询范围")
	public void saveRoleRangeValues(Long roleId,String rangeIds) {
		if(roleId!=null && StringUtil.isNotBlank(rangeIds)){
			final List<Long> mids = StringUtil.split(Long.class,rangeIds, ",");
			
			db.executePlan(new Plan(){
				@Override
				public void doExecutePlan() throws DbException {
					String sql = String.format("delete from t_power_role_range_res where role_id=? and range_id in(%s)",StringUtil.join(mids, ","));
					db.executeUpdate(sql, roleId);
					
					sql="insert into t_power_role_range_res (role_id,range_id,range_value,create_date,code,range_name) values (?,?,?,?,?,?)";
					
					Long maxId=db.executeQueryForObject("select max(pk) from t_power_role_range_res", Long.class);
					
					for(Long mid: mids) {
						Range range = db.getById(Range.class, mid);
						db.executeUpdate(sql, roleId,mid,range.getValue(),new Date(),range.getCode()+"."+(++maxId),range.getName());
					}
				}
			});
		}
	}
	/*
	@RequestMapping(mapping = { "/delete_role_range_values", "/m/delete_role_range_values" },text="删除角色的数据查询范围")
	public void deleteRoleRangeValues(Long roleId,String rangeIds) {
		if(roleId!=null && StringUtil.isNotBlank(rangeIds)){
			final List<Long> mids = StringUtil.split(Long.class,rangeIds, ",");
			
			db.executePlan(true, new Plan(){
				@Override
				public void doExecutePlan() throws DbException {
					String sql = String.format("delete from t_power_role_range_res where role_id=? and range_id in(%s)",StringUtil.join(mids, ","));
					db.executeUpdate(sql, roleId);
				}
			});
		}
	}*/
}
