package org.lsqt.act.controller;

import java.util.Date;
import java.util.List;

import org.lsqt.act.model.UserDataConfigParam;
import org.lsqt.act.model.UserDataConfigParamQuery;
import org.lsqt.act.service.TaskService;
import org.lsqt.act.service.UserDataConfigParamService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.service.ApplicationService;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author mmyuan
 *
 */
@Controller(mapping={"/act/user_data_config_param"})
public class UserDataConfigParamController {
	@Inject private TaskService taskService;
	@Inject private ApplicationService applicationService;
	@Inject private UserDataConfigParamService userDataConfigParamService;
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public void delete(String ids) {
		if (StringUtil.isNotBlank(ids)) {
			List<Long> list = StringUtil.split(Long.class, ids, ",");
			Object[] idArray = list.toArray(new Long[list.size()]);
			db.deleteById(UserDataConfigParam.class,idArray);
		}
	}
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public UserDataConfigParam getById(Long id) {
		return db.getById(UserDataConfigParam.class, id);
	}
	
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public List<UserDataConfigParam> saveOrUpdate(String data) {
		
		List<UserDataConfigParam> list = JSON.parseArray(data,UserDataConfigParam.class);
		for(UserDataConfigParam e: list) {
			e.setUpdateTime(new Date());
			db.saveOrUpdate(e);
		}
		return list;
	}
	
	@RequestMapping(mapping = { "/save_or_update_signle", "/m/save_or_update_signle" })
	public UserDataConfigParam saveOrUpdate(UserDataConfigParam form) {
		form.setUpdateTime(new Date());
		db.saveOrUpdate(form);
		return form;
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<UserDataConfigParam> queryForPage(UserDataConfigParamQuery query) {
		return userDataConfigParamService.queryForPage(query);
	}

	@RequestMapping(mapping = { "/list", "/m/list" })
	public List<UserDataConfigParam> queryForList(UserDataConfigParamQuery query) {
		return userDataConfigParamService.queryForList(query);
	}
	
}
