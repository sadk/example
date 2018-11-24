package org.lsqt.rst.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;

import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.rst.model.UserWorkRecord;
import org.lsqt.rst.model.UserWorkRecordQuery;
import org.lsqt.rst.service.UserWorkRecordService;




@Controller(mapping={"/rst/user_work_record"})
public class UserWorkRecordController {
	
	@Inject private UserWorkRecordService userWorkRecordService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public UserWorkRecord getById(Long id) throws IOException {
		return userWorkRecordService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<UserWorkRecord> queryForPage(UserWorkRecordQuery query) throws IOException {
		return userWorkRecordService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<UserWorkRecord> getAll() {
		return userWorkRecordService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public UserWorkRecord saveOrUpdate(UserWorkRecord form) {
		return userWorkRecordService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return userWorkRecordService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
