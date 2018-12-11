package org.lsqt.rst.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.lsqt.rst.model.Result;
import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;

import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.rst.model.User;
import org.lsqt.rst.model.UserQuery;
import org.lsqt.rst.service.UserService;




@Controller(mapping={"/rst/user"})
public class UserController {
	
	@Inject private UserService userService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public User getById(Long id) throws IOException {
		return userService.getById(id);
	}
	
	@RequestMapping(mapping = { "/get_by_code", "/m/get_by_code" })
	public User getByCode(String code) throws IOException {
		UserQuery query = new UserQuery();
		query.setCode(code);;
		return db.queryForObject("queryForPage", User.class, query);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<User> queryForPage(UserQuery query) throws IOException {
		query.setTenantCode(ContextUtil.getLoginTenantCode());
		return userService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<User> getAll() {
		return userService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public User saveOrUpdate(User form) {
		form.setTenantCode(ContextUtil.getLoginTenantCode());
		return userService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return userService.deleteById(list.toArray(new Long[list.size()]));
	}

	/**
	 * 
	 * @param userCode 用户编码
	 * @param quitTime 离职时间: yyyyMMdd
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(mapping = { "/wx/quit_user", "/m/wx/quit_user" }, text = "用户离职")
	public Result<User> quitUser(String userCode,String quitDate)   {
		if (StringUtil.isBlank(userCode)) {
			return Result.fail("用户ID不能为空");
		}
		if (StringUtil.isBlank(quitDate)) {
			return Result.fail("离职时间不能为空");
		}
		
		try {
			UserQuery query = new UserQuery();
			query.setCode(userCode);
			User user = db.queryForObject("queryForPage", User.class, query);
			if (user == null) {
				return Result.fail("没有当前用户");
			}
			user.setEntryStatus(User.ENTRY_STATUS_离职);

			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			user.setQuitTime(df.parse(quitDate));
			
			db.update(user, "entryStatus", "quitTime");

			return Result.ok(user);
		} catch (Exception ex) {
			return Result.fail("离职失败: " + ex.getMessage());
		}
	}
	
	@RequestMapping(mapping = { "/batch_short_update", "/m/batch_short_update" }, text = "批量修改用户所在厂区和离职在职状态")
	public Result<Object> batchShortUpdate(String userIds, String entryStatus, String companyName, String companyCode) {
		if (StringUtil.isBlank(userIds)) {
			return Result.fail("用户ID不能为空");
		}
		
		List<Object> args = new ArrayList<>();
		
		String sql = "update bu_user_info set is_entry=? ";
		args.add(entryStatus);
		
		if (StringUtil.isNotBlank(companyName)) {
			sql+= ", depend_company_name = ? ";
			args.add(companyName);
		}
		
		if (StringUtil.isNotBlank(companyCode)) {
			sql += ", depend_company_code=? ";
			args.add(companyCode);
		}
		
		if (StringUtil.isNotBlank(entryStatus) && User.ENTRY_STATUS_入职.equals(entryStatus)) {
			 sql += ",quit_time = null ";
		}
		
		sql+=" where id in ("+ userIds +") ";
		
		Integer cnt = db.executeUpdate(sql , args.toArray());
		return Result.ok(cnt+"");
	}
	
}
