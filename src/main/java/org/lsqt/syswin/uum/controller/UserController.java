package org.lsqt.syswin.uum.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.DbException;
import org.lsqt.components.db.Page;
import org.lsqt.components.db.Plan;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.util.ResourceUtil;
import org.lsqt.syswin.uum.model.Position;
import org.lsqt.syswin.uum.model.User;
import org.lsqt.syswin.uum.model.UserQuery;
import org.lsqt.syswin.uum.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@Controller(mapping={"/syswin/user"})
public class UserController {
	private static final Logger  log = LoggerFactory.getLogger(UserController.class);
	@Inject private UserService userService;
	
	@Inject private PlatformDb db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public User getById(Long userId) throws IOException {
		 return userService.getById(userId);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<User> queryForPage(UserQuery query) throws IOException {
		return userService.queryForPage(query); //  
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
	
	@RequestMapping(mapping = { "/add_user_positions", "/m/add_user_positions" },text="添加用户的岗位")
	public void addUserPositions(Long userId,String positionIds) {
		if(userId == null || StringUtil.isBlank(positionIds)){
			return ;
		}
		
		List<Long> list = StringUtil.split(Long.class, positionIds, ",");
		
		db.executePlan(true, new Plan(){
			@Override
			public void doExecutePlan() throws DbException {
				// 删除再添加
				String sql = "delete from t_user_duties where user_id=? and duties_id in (%s)";
				sql = String.format(sql, StringUtil.join(list, ","));
				db.executeUpdate(sql, userId);
				
				User user = db.getById(User.class, userId);
				if(user!=null) {
					sql = "insert into t_user_duties(duties_id,user_id,user_name,duties_type,create_date) values(?,?,?,?,?)";
					for(Long positionId: list) {
						db.executeUpdate(sql,positionId,userId,user.getUserName(),Position.POSTION_SECOND,new Date());
					}
				}
			}
			
		});
	}
	
	
	@RequestMapping(mapping = { "/remove_user_positions", "/m/remove_user_positions" },text="删除用户的岗位")
	public void removeUserPositions(Long userId,String positionIds) {
		if(userId == null || StringUtil.isBlank(positionIds)){
			return ;
		}
		
		List<Long> list = StringUtil.split(Long.class, positionIds, ",");
		
		db.executePlan(true, new Plan(){
			@Override
			public void doExecutePlan() throws DbException {
				
				String sql = "delete from t_user_duties where user_id=? and duties_id in (%s)";
				sql = String.format(sql, StringUtil.join(list, ","));
				db.executeUpdate(sql, userId);
				
				/*
				sql = "delete from t_power_duties_role where duties_id in (%s)";
				sql = String.format(sql, StringUtil.join(list, ","));
				db.executeUpdate(sql);
				*/
			}
			
		});
	}
	
	
	@RequestMapping(mapping = { "/login", "/m/login"},text="cooke的key是(登陆账号明文-->对称加密-->16进制串表示)后的散列值，value是（密码明文+盐分 的16进制串表示）后的散列值")
	public Result login(String username,String password) {
		if (StringUtil.isBlank(username)) {
			return Result.fail("登陆账号不能为空");
		}

		if (StringUtil.isBlank(password)) {
			return Result.fail("密码不能为空");
		}
		
		HttpServletResponse response = ContextUtil.getResponse();

		User user = db.executeQueryForObject("select * from t_user_info where login_no=?", User.class, username);
		if (user == null) {
			return Result.fail(String.format("没有找到登陆账号为%s的用户",username));
		}
		
		String key = CodeUtil.encode(user.getLoginNo());
		String value = CodeUtil.passwodEncrypt(password+user.getPwdSalt());
		
		if(!user.getPassword().equals(value)){
			return Result.fail("密码错误");
		}
		
		//写uid
		Cookie sid = new Cookie("sid", CodeUtil.encode(key));
		response.addCookie(sid);
		
		//写 cookie
		Cookie userCookie = new Cookie(key, value);
		userCookie.setPath("/");
		userCookie.setMaxAge(-1);//关闭即消失
		response.addCookie(userCookie);
		
		
		user.setPassword(null);
		user.setPwdSalt(null);
		return Result.ok("登陆成功",user);
	}
	

	
	@RequestMapping(mapping = { "/fix_uum_data", "/m/fix_uum_data" },text="整理UUM关系数据")
	public void fixUumData() {
		// 1. 清理岗位与角色的关系数据（有些实体岗已删除了，岗位和角色的关系还在）
		Set<Long> notExistsPositionIdList = new HashSet<>();
		String sql = " select duties_id positionId from T_POWER_DUTIES_ROLE where duties_id not in (select duties_id from t_org_duties_info)";
		List<Map<String,Object>> list = db.executeQuery(sql);
		for(Map<String,Object> m: list) {
			Object positionId = m.get("positionId");
			if(positionId!=null) {
				notExistsPositionIdList.add(Long.valueOf(positionId.toString()));
			}
		}
		
		if(!notExistsPositionIdList.isEmpty()) {
			sql="delete from T_POWER_DUTIES_ROLE where duties_id in ("+StringUtil.join(notExistsPositionIdList, ",")+")";
			db.executeUpdate(sql);
		}
		
		
		// 2.清理岗位与用户的数据关系(有些实体岗已删除了，岗位和用户的关系还在)
		notExistsPositionIdList = new HashSet<>();
		sql="select distinct(duties_id) positionId from t_user_duties where duties_id not in (  select duties_id from  t_org_duties_info) ";
		list = db.executeQuery(sql);
		for(Map<String,Object> m: list) {
			Object positionId = m.get("positionId");
			if(positionId!=null) {
				notExistsPositionIdList.add(Long.valueOf(positionId.toString()));
			}
		}
		
		if(!notExistsPositionIdList.isEmpty()) {
			sql="delete from t_user_duties where duties_id in ("+StringUtil.join(notExistsPositionIdList, ",")+")";
			db.executeUpdate(sql);
		}
		
	}
	
	
	@RequestMapping(mapping = { "/clear_user_cache", "/m/clear_user_cache" },text="清除用户菜单、功能、数据查询权限")
	public Map<String,Object> clearUserCache(String loginNo) throws Exception {
		String urlText = ResourceUtil.getValue("api.platform.uum") + "/api/user/clear_cache_by_login_no?loginNo=" + loginNo;
		URL urlObj = new URL(urlText);
		log.debug(urlText);

		HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();

		String json = "";
		connection.setRequestMethod("GET");

		Map<String, Object> rs = new HashMap<>();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			json = IOUtils.toString(connection.getInputStream(), "utf-8");
			rs.put("message", json);

			log.debug("刷新缓存URL: " + urlText + " , 返回的JSON:" + json);
		} else {
			String temp = "刷新缓存失败，Http状态码：" + connection.getResponseCode() + ", 详情：" + connection.getResponseMessage();
			rs.put("message", temp);

			log.debug(temp);
		}
		return rs;
	}
	
	@RequestMapping(mapping = { "/get_user_permission_sql", "/m/get_user_permission_sql" },text="获取用户的数据查询权限")
	public Result getUserPermissionSql(String userId,String moduleCode , String createby,String applyOrgId) throws Exception {
		if(StringUtil.isBlank(userId)) {
			return Result.fail("用户ID不能为空");
		}
		if(StringUtil.isBlank(moduleCode)) {
			return Result.fail("模块编码不能为空");
		}
		
		if(StringUtil.isBlank(createby)) {
			createby = "createby";
		}
		if(StringUtil.isBlank(applyOrgId)) {
			applyOrgId = "apply_org_id";
		}
		
		String orgIds = getConfigOrgIds(userId);
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" and (");
		
		if(StringUtil.isNotBlank(orgIds)) { //本人
			sqlBuilder.append(" ("+createby+"= "+userId+" and "+applyOrgId+" in ("+orgIds+"))"); 
		}
		
		if (hasPermission(userId, moduleCode, "2")) { // 直属下级
			List<String> userUnderMeList = getUserUnderMe(userId);
			if(userUnderMeList!=null && !userUnderMeList.isEmpty()) {
				sqlBuilder.append(" or ("+createby+" in ("+StringUtil.join(userUnderMeList, ",")+"))");  
			}
		}
		
		if(hasPermission(userId, moduleCode, "4")) { //所有
			if(StringUtil.isNotBlank(orgIds)) {
				sqlBuilder.append(" or ("+applyOrgId+" in  ("+orgIds+"))");  
			}
		}
		
		sqlBuilder.append(")");
		
		if(StringUtil.isBlank(orgIds)) {
			return Result.ok("当前用户没有授权任何组织节点权限","");
		}
		
		return Result.ok(sqlBuilder);
	}
	
	private String getConfigOrgIds(String userId) {
		StringBuilder orgIdsQuery = new StringBuilder();
		String sql = "select org_ids_query orgIds from t_power_duties_permit_config where duties_id in (SELECT duties_id FROM t_user_duties WHERE user_id = ?)";
		List<Map<String, Object>> list = db.executeQuery(sql, Long.valueOf(userId));
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> row = list.get(i);
			if (row.get("orgIds") != null) {
				orgIdsQuery.append(row.get("orgIds"));
				if (i < list.size() - 1) {
					orgIdsQuery.append(",");
				}
			}
		}
		return orgIdsQuery.toString();
	}
	
	private List<String> getUserUnderMe(String userId) {
		List<String> underMeUserIdList = new ArrayList<>();
		String sql = "select user_id userId from t_user_duties where  duties_id in( select duties_id from t_org_duties_info where direct_duties_id in (SELECT duties_id FROM t_user_duties WHERE user_id = ? ))";
		List<Map<String, Object>> rs = db.executeQuery(sql, Long.valueOf(userId));
		for (Map<String, Object> m : rs) {
			Object userIdObj = m.get("userId");
			if (userIdObj != null) {
				underMeUserIdList.add(userIdObj.toString());
			}
		}
		return underMeUserIdList;
	}
	
	
	@RequestMapping(mapping = { "/get_useness_sql", "/m/get_useness_sql" }, text = "获取用户的使用权限SQL")
	public Result getUsenessSql(String userId,String orgUnitId) throws Exception {
		if (StringUtil.isBlank(userId)) {
			return Result.fail("用户ID不能为空");
		}
		if(StringUtil.isBlank(orgUnitId)) {
			orgUnitId = "org_unit_id";
		}
		
		StringBuilder sb = new StringBuilder(" and "+orgUnitId+" in ( ");
		String sql = "select org_ids_useness orgIds from t_power_duties_permit_config where duties_id in (SELECT duties_id FROM t_user_duties WHERE user_id = ?)";
		
		List<Map<String, Object>> listMap = db.executeQuery(sql, userId);
		for (int i=0;i<listMap.size();i++ ) {
			
			Object orgIds = listMap.get(i).get("orgIds");
			if (orgIds != null) {
				sb.append(orgIds.toString());
				if(i<listMap.size()-1) {
					sb.append(",");
				}
			}
		}

		sb.append(" ) ");
		
		if(listMap==null || listMap.isEmpty()) {
			return Result.fail("没有任务数据使用权限","");
		}
		return Result.ok(sb);
	}
	
	/**
	 * 是否有： 1=本人 2=直属下级 3=所有下级  4=所有  权限
	 * @return
	 */
	private boolean hasPermission(String userId,String moduleCode,String level) {
		String sql = "select count(1) cnt from t_power_duties_module_config where level=? and  module_code=? and duties_id in (select duties_id from t_user_duties where user_id=?)";
		Integer cnt = db.executeQueryForObject(sql, Integer.class, level,moduleCode,userId);
		if(cnt!=null) {
			return cnt>0;
		}
		return false;
	}
	
	@RequestMapping(mapping = { "/get_user_org_tree_sql", "/m/get_user_org_tree_sql" },text="获取用户的查看权限")
	public Result getUserOrgTreeSql(String userId,String orgUnitId) throws Exception {
		if (StringUtil.isBlank(userId)) {
			return Result.fail("用户ID不能为空");
		}

		if (StringUtil.isBlank(orgUnitId)) {
			orgUnitId = "org_unit_id";
		}

		String orgIds = getConfigOrgIds(userId);

		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" and " + orgUnitId + " in (");
		sqlBuilder.append(orgIds);
		sqlBuilder.append(")");

		if (StringUtil.isBlank(orgIds)) {
			return Result.ok("当前用户没有授权任何组织节点权限", "");
		}

		return Result.ok(sqlBuilder);
	}
}

 
