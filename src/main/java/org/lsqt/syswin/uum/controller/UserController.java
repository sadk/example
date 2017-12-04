package org.lsqt.syswin.uum.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.DbException;
import org.lsqt.components.db.Page;
import org.lsqt.components.db.Plan;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.uum.model.Position;
import org.lsqt.syswin.uum.model.User;
import org.lsqt.syswin.uum.model.UserQuery;
import org.lsqt.syswin.uum.service.UserService;




@Controller(mapping={"/syswin/user"})
public class UserController {
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
			return Result.fail("没有找到登陆账号为%s的用户");
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
	

	

}

 
