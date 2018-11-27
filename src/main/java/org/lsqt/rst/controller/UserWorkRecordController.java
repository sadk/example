package org.lsqt.rst.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.lsqt.components.context.Result;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestPayload;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.rst.model.UserWorkRecord;
import org.lsqt.rst.model.UserWorkRecordQuery;
import org.lsqt.rst.service.UserWorkRecordService;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.sys.service.DictionaryService;




@Controller(mapping={"/rst/user_work_record"})
public class UserWorkRecordController {
	
	@Inject private UserWorkRecordService userWorkRecordService; 
	@Inject private DictionaryService dictionaryService;
	
	@Inject private Db db;
	
	
	@RequestMapping(mapping = { "/wx/option" }, isTransaction = false, text = "微信小程序端的字典")
	public Result<List<Dictionary>> option(String code) {
		if (StringUtil.isBlank(code)) {
			return Result.fail("字典编码不能为空");
		}
		return Result.ok(dictionaryService.getOptionByCode(code, null, null));
	}
	
	@RequestMapping(mapping = { "/wx/save_or_update"})
	@RequestPayload
	public Result<UserWorkRecord> saveOrUpdate4WX(UserWorkRecord form) {
		if (form.getType() == null) {
			return Result.fail("考勤类型不能为空");
		}
		
		if (StringUtil.isBlank(form.getShiftType())) {
			return Result.fail("班次类型不能为空");
		}
		
		return Result.ok(userWorkRecordService.saveOrUpdate(form));
	}
	
	@RequestMapping(mapping = { "/wx/get_by_id"})
	public Result<UserWorkRecord> getById4WX(Long id) throws IOException {
		if (id == null) {
			return Result.fail("id不能为空");
		}
		return Result.ok(userWorkRecordService.getById(id));
	}
	
	@RequestMapping(mapping = { "/wx/page"})
	public Result<Page<UserWorkRecord>> queryForPage4WX(UserWorkRecordQuery query) throws IOException {
		if (StringUtil.isBlank(query.getUserCode())) {
			return Result.fail("用户编码不能为空");
		}
		return Result.ok( userWorkRecordService.queryForPage(query)); 
	}
	
	@RequestMapping(mapping = { "/wx/list"})
	public Result<List<UserWorkRecord>> queryForList4WX(UserWorkRecordQuery query) throws IOException {
		if (StringUtil.isBlank(query.getUserCode())) {
			return Result.fail("用户编码不能为空");
		}
		return Result.ok(userWorkRecordService.queryForList(query));
	}
	
	@RequestMapping(mapping = { "/wx/delete"})
	public Result<Integer> delete4WX(String ids) {
		if (StringUtil.isBlank(ids)) {
			return Result.fail("id不能为空");
		}
		
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return Result.ok(userWorkRecordService.deleteById(list.toArray(new Long[list.size()])));
	}
	
	// ---------------------------------------------- 以上是微信端接口  ----------------------------------------------
	
	
	
	
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
