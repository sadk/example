package org.lsqt.rst.service.impl;


import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.rst.model.Result;
import org.lsqt.rst.model.User;
import org.lsqt.rst.model.UserEntryInfo;
import org.lsqt.rst.model.UserEntryInfoQuery;
import org.lsqt.rst.model.UserQuery;
import org.lsqt.rst.model.UserWorkRecord;
import org.lsqt.rst.model.UserWorkRecordQuery;
import org.lsqt.rst.service.UserWorkRecordService;
import org.lsqt.sys.model.Dictionary;

@Service
public class UserWorkRecordServiceImpl implements UserWorkRecordService{
	
	@Inject private Db db;
	
	public UserWorkRecord getById(Long id) {
		return db.getById(UserWorkRecord.class, id) ;
	}
	
	public List<UserWorkRecord> queryForList(UserWorkRecordQuery query) {
		return db.queryForList("queryForPage", UserWorkRecord.class, query);
	}
	
	public Page<UserWorkRecord> queryForPage(UserWorkRecordQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), UserWorkRecord.class, query);
	}

	public List<UserWorkRecord> getAll(){
		  return db.queryForList("getAll", UserWorkRecord.class);
	}
	
	private static final Map<String,Integer> WEEKDAY_MAP = new HashMap<>();
	static {
		WEEKDAY_MAP.put("星期一", 1);
		WEEKDAY_MAP.put("星期二", 2);
		WEEKDAY_MAP.put("星期三", 3);
		WEEKDAY_MAP.put("星期四", 4);
		WEEKDAY_MAP.put("星期五", 5);
		WEEKDAY_MAP.put("星期六", 6);
		WEEKDAY_MAP.put("星期日", 7);
	}
	
	private static Date convertDate(String dateText) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		try {
			return fmt.parse(dateText);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return null;
	}
	
	
	public UserWorkRecord saveOrUpdate(UserWorkRecord model) {
		if (model.getRecordDate() == null) {
			throw new UnsupportedOperationException("考勤日期为空");
		}

		if (model.getUserCode() == null) {
			throw new UnsupportedOperationException("考勤用户不能为空");
		}

		if (StringUtil.isBlank(model.getTenantCode())) {
			throw new UnsupportedOperationException("租户码不能为空");
		}

		UserQuery uquery = new UserQuery();
		uquery.setCode(model.getUserCode());
		User user = db.queryForObject("queryForPage", User.class, uquery);

		if (user == null) {
			throw new UnsupportedOperationException("没有当前注册用户");
		}
		
		UserEntryInfoQuery entryQuery = new UserEntryInfoQuery();
		entryQuery.setUserCode(model.getUserCode());
		UserEntryInfo ueModel = db.queryForObject("queryForPage", UserEntryInfo.class, entryQuery);
		
		if (ueModel == null || ueModel.getEntryStatus() == null) {
			throw new UnsupportedOperationException("入职信息为空，不能记录考勤");
		}
		

		/*
		if(UserEntryInfo.ENTRY_STATUS_已入职 != ueModel.getEntryStatus()) {
			throw new UnsupportedOperationException("用户未入职不能进行考勤记录");
		}
		*/
		if(ueModel.getEntryTime() == null) {
			throw new UnsupportedOperationException("入职日期为空，不能记录工时");
		}
		
		if (new Date().compareTo(ueModel.getEntryTime()) < 0) {
			throw new UnsupportedOperationException("未到入职生效日期，不能记录工时");
		}
		
		if(StringUtil.isBlank(ueModel.getCompanyCode())) {
			throw new UnsupportedOperationException("没有找到注册用户的入职企业");
		}
		
		
		//不能修改历史（入职过的）公司的考勤数据。
		if (StringUtil.isNotBlank(model.getUserCode()) && model.getRecordDate() != null) {
			UserWorkRecordQuery q = new UserWorkRecordQuery();
			q.setUserCode(model.getUserCode());
			q.setRecordDate(model.getRecordDate());
			UserWorkRecord modelDb = db.queryForObject("queryForPage", UserWorkRecord.class, q);
			if (modelDb != null && (!ueModel.getCompanyCode().equals(modelDb.getCompanyCode()))) {
				throw new UnsupportedOperationException("已离职公司的考勤数据不能修改");
			}
		}
		
	 
		model.setCompanyCode(ueModel.getCompanyCode());
		model.setCompanyName(ueModel.getCompanyName());
		
		
		UserWorkRecordQuery query = new UserWorkRecordQuery();
		query.setRecordDate(model.getRecordDate());
		query.setUserCode(model.getUserCode());
		UserWorkRecord dbModel = db.queryForObject("queryForPage", UserWorkRecord.class , query);
		
		if (dbModel != null) {
			model.setId(dbModel.getId());
		}
		
		
		SimpleDateFormat dateFm = new SimpleDateFormat("EEEE",Locale.CHINESE);
		String xq = dateFm.format(convertDate(model.getRecordDate().toString()));
		model.setWeekday(WEEKDAY_MAP.get(xq));
		
		
		if (StringUtil.isBlank(model.getWorkingHours())) {
			model.setWorkingHours("0");
		}
		
		
		if(model.getWeekday() == 6 || model.getWeekday() == 7) { // 周六至周天
			model.setExtraHours(model.getWorkingHours());
			model.setWorkingHours("0");
			 
		} else  {
			Double wh = Double.valueOf(model.getWorkingHours());
			if (wh > 8) {
				model.setWorkingHours("8");
				model.setExtraHours((wh - 8) + "");
			} else {
				model.setExtraHours("0");
			}
		}
		
		if (StringUtil.isBlank(model.getLeaveHas()) || (Dictionary.NO+"").equals(model.getLeaveHas())) {
			model.setLeaveType(null);;
		}
		
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(UserWorkRecord.class, Arrays.asList(ids).toArray());
	}
}
