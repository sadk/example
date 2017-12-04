package org.lsqt.act.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;

import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.ExceptionUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.uum.model.User;
import org.lsqt.syswin.uum.model.UserQuery;
import org.lsqt.syswin.uum.service.UserService;
import org.lsqt.act.ActUtil;
import org.lsqt.act.model.UserRule;
import org.lsqt.act.model.UserRuleQuery;
import org.lsqt.act.service.UserRuleService;




@Controller(mapping={"/act/user_rule"})
public class UserRuleController {
	
	@Inject private UserRuleService userRuleService; 
	@Inject private UserService userService;
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<UserRule> queryForPage(UserRuleQuery query) throws IOException {
		return userRuleService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<UserRule> getAll() {
		return userRuleService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public UserRule saveOrUpdate(UserRule form) {
		return userRuleService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return userRuleService.deleteById(list.toArray(new Long[list.size()]));
	}
	
	
	@RequestMapping(mapping = { "/view_user", "/m/view_user" })
	public Map<String,Object> viewUser(Long userId,Long createDeptId,Long ruleId) throws Exception {
		Map<String,Object> rs = new HashMap<String,Object>();
		if(ruleId == null) return rs;
		 
		List<User> data = new ArrayList<>();
		try{
			Map<String,Object> nodeUserVariable =  new HashMap<>();
			nodeUserVariable.put(ActUtil.VARIABLES_CREATE_DEPT_ID, createDeptId);
			List<String> userIds = userRuleService.resolveUsers(userId, nodeUserVariable, ruleId);
			UserQuery query = new UserQuery();
			query.setIds(StringUtil.join(userIds, ","));
			if(StringUtil.isNotBlank(query.getIds())) {
				data = userService.queryForList(query);
			} 
			rs.put("data",data);
			rs.put("message", " --- "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +" 预览成功！");
			rs.put("status", 0);
		}catch(Exception ex){
			rs.put("data",new ArrayList<>());
			rs.put("message", " --- "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ExceptionUtil.getStackTrace(ex));
			rs.put("status", -1);
			ex.printStackTrace();
		}
		return rs;
	}
}
