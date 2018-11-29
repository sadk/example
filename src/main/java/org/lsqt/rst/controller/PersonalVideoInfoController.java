package org.lsqt.rst.controller;

import java.io.IOException;
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
import org.lsqt.rst.model.PersonalVideoInfo;
import org.lsqt.rst.model.PersonalVideoInfoQuery;
import org.lsqt.rst.service.PersonalVideoInfoService;




@Controller(mapping={"/rst/personal_video_info"})
public class PersonalVideoInfoController {
	
	@Inject private PersonalVideoInfoService personalVideoInfoService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public PersonalVideoInfo getById(Long id) throws IOException {
		return personalVideoInfoService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<PersonalVideoInfo> queryForPage(PersonalVideoInfoQuery query) throws IOException {
		query.setTenantCode(ContextUtil.getLoginTenantCode());
		return personalVideoInfoService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<PersonalVideoInfo> getAll() {
		return personalVideoInfoService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public PersonalVideoInfo saveOrUpdate(PersonalVideoInfo form) {
		form.setTenantCode(ContextUtil.getLoginTenantCode());
		return personalVideoInfoService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/save_or_update_short", "/m/save_or_update_short" })
	public PersonalVideoInfo check(PersonalVideoInfo form) {
		form.setTenantCode(ContextUtil.getLoginTenantCode());
		if (form.getId() != null) {
			db.update(form, "reason","status","tenantCode");
		}
		return form;
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return personalVideoInfoService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
