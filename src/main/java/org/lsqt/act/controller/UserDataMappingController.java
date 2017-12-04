package org.lsqt.act.controller;

import java.util.Date;
import java.util.List;

import org.lsqt.act.model.UserDataMapping;
import org.lsqt.act.model.UserDataMappingQuery;
import org.lsqt.act.service.TaskService;
import org.lsqt.act.service.UserDataMappingService;
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
@Controller(mapping={"/act/user_data_mapping"})
public class UserDataMappingController {
	@Inject private TaskService taskService;
	@Inject private ApplicationService applicationService;
	@Inject private UserDataMappingService userDataMappingService;
	@Inject private Db db;

	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public void delete(String ids) {
		if (StringUtil.isNotBlank(ids)) {
			List<Long> list = StringUtil.split(Long.class, ids, ",");
			Object[] idArray = list.toArray(new Long[list.size()]);
			db.deleteById(UserDataMapping.class,idArray);
		}
	}
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public UserDataMapping getById(Long id) {
		return db.getById(UserDataMapping.class, id);
	}
	
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public List<UserDataMapping> saveOrUpdate(String data) {
		
		List<UserDataMapping> list = JSON.parseArray(data,UserDataMapping.class);
		for(UserDataMapping e: list) {
			e.setUpdateTime(new Date());
			db.saveOrUpdate(e);
		}
		return list;
	}
	
	@RequestMapping(mapping = { "/save_or_update_signle", "/m/save_or_update_signle" })
	public UserDataMapping saveOrUpdate(UserDataMapping form) {
		form.setUpdateTime(new Date());
		db.saveOrUpdate(form);
		return form;
	}
	
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<UserDataMapping> queryForPage(UserDataMappingQuery query) {
		return userDataMappingService.queryForPage(query);
	}

	@RequestMapping(mapping = { "/list", "/m/list" })
	public List<UserDataMapping> queryForList(UserDataMappingQuery query) {
		return userDataMappingService.queryForList(query);
	}
	
}
