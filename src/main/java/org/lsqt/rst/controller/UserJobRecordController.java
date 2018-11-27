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
import org.lsqt.rst.model.UserJobRecord;
import org.lsqt.rst.model.UserJobRecordQuery;
import org.lsqt.rst.service.UserJobRecordService;




@Controller(mapping={"/rst/user_job_record"})
public class UserJobRecordController {
	
	@Inject private UserJobRecordService userJobRecordService; 
	
	@Inject private Db db;
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public UserJobRecord getById(Long id) throws IOException {
		return userJobRecordService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<UserJobRecord> queryForPage(UserJobRecordQuery query) throws IOException {
		return userJobRecordService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<UserJobRecord> getAll() {
		return userJobRecordService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public UserJobRecord saveOrUpdate(UserJobRecord form) {
		return userJobRecordService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return userJobRecordService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
