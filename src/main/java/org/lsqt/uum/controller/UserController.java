package org.lsqt.uum.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.After;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.db.orm.ftl.FtlDbExecute;
import org.lsqt.components.util.collection.ArrayUtil;
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
import org.lsqt.uum.util.CodeUtil;
import org.lsqt.uum.util.HttpUtil;
import org.lsqt.uum.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

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
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Inject private UserService userService; 
	@Inject private OrgService orgService;
	@Inject private Db db;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(mapping = { "/login", "/m/login"},text="cooke的key是(登陆账号明文-->对称加密-->16进制串表示)后的散列值，value是（密码明文+盐分 的16进制串表示）后的散列值")
	public Result login(String username,String password) {
		if (StringUtil.isBlank(username)) {
			return Result.fail("登陆账号不能为空");
		}

		if (StringUtil.isBlank(password)) {
			return Result.fail("密码不能为空");
		}
		
	
		
		//没有开启单点登陆服务验证用户，验证本地用户
		String ssoEnable = ResourceUtil.getValue("login.sso.enable");
		if (!"true".equalsIgnoreCase(ssoEnable)) {
			User user = db.executeQueryForObject("select * from uum_user where login_name=?", User.class, username);
			if (user == null) {
				return Result.fail(String.format("没有找到登陆账号为%s的用户", username));
			}

			String passwodEncrypted = CodeUtil.passwodEncrypt(password + User.PWD_SALT);

			if (!user.getLoginPwd().equals(passwodEncrypted)) {
				return Result.fail("密码错误");
			}
			
			writeCookie(username,password);
			return Result.ok(user);
		}
		
		
		String url = ResourceUtil.getValue("sso.login.url");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("username", username);
		paramMap.put("password", password);
		String json = HttpUtil.getResponse(url, paramMap);

		if (StringUtil.isBlank(json)) {
			return Result.fail("请求链路异常");
		}

		Map<String, Object> userMap = JSON.parseObject(json, Map.class);
		if (userMap.get("success") == null) {
			return Result.fail("请求链路异常");
		}
		boolean success = Boolean.valueOf(userMap.get("success").toString());
		if (!success) {
			return Result.fail("登陆失败：" + userMap.get("msg"));
		}
		
		
		UserQuery query = new UserQuery();
		query.setLoginName(username);;
		User dbUser = db.queryForObject("queryForPage", User.class, query);
		if (dbUser == null) {
			// 解析对象存到本地数据库（用于菜单授权使用）
			dbUser = new User();
			dbUser.setLoginName(username);
			Object name = userMap.get("userName");
			Object code = userMap.get("userCode");
			Object email = userMap.get("userEmail");
			Object mobile = userMap.get("userPhone");
			
			dbUser.setCode(code == null ? null : code.toString());
			dbUser.setName(name == null ? null : name.toString());
			dbUser.setEmail(email == null ? null : email.toString());
			dbUser.setMobile(mobile == null ? null : mobile.toString());
			dbUser.setStatus(User.status_激活);
			dbUser.setLoginPwd(CodeUtil.passwodEncrypt(password+User.PWD_SALT));
			db.saveOrUpdate(dbUser);
		}
		
		
		writeCookie(username, password);
		
	 
		return Result.ok("登陆成功",dbUser);
	}



	private void writeCookie(String username, String password) {
		HttpServletResponse response = ContextUtil.getResponse();

		String passwodEncrypted = CodeUtil.passwodEncrypt(password + User.PWD_SALT);
		long currTime = System.currentTimeMillis();
		// 第一个cookie存（uid）<==>（账号，密码，时间戳）
		List<String> uidValue = Arrays.asList(username, passwodEncrypted, currTime + "");
		Cookie uid = new Cookie("uid", CodeUtil.simpleEncode(StringUtil.join(uidValue)));
		uid.setPath("/");
		uid.setMaxAge(-1);
		response.addCookie(uid);

		// 第二个cookie存（账号，时间戳）<==> (密码，时间戳）
		Cookie userCookie = new Cookie(CodeUtil.simpleEncode(uidValue.get(0) + "," + uidValue.get(2)),
				CodeUtil.simpleEncode(uidValue.get(1) + "," + uidValue.get(2)));
		userCookie.setPath("/");
		userCookie.setMaxAge(-1);// 关闭即消失
		response.addCookie(userCookie);
	}

	
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	//@After(clazz =(RequestAfterProcess.class),method="convert",args={Object.class,Long.class,String.class})
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
		if (StringUtil.isNotBlank(form.getLoginPwd())) {
			form.setLoginPwd(CodeUtil.passwodEncrypt(form.getLoginPwd() + User.PWD_SALT));
		}
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
	
	@RequestMapping(mapping = { "/get_permission_list", "/m/get_permission_list" },text="获取用户的所有资源权限")
	public List<Res> getPermissionList(ResQuery query) {
		String enablePermission = ResourceUtil.getValue("user.permission.enable");
		if (StringUtil.isBlank(enablePermission)) {
			log.error("没有配置权限是否开启参数，见：config.properties");
			return new ArrayList<>();
		}

		if ("true".equalsIgnoreCase(enablePermission)) {
			if (query.getUserId() != null) {
				return db.queryForList("queryForPage", Res.class, query);
				
			} else {
				String loginName = ContextUtil.getLoginName();
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
