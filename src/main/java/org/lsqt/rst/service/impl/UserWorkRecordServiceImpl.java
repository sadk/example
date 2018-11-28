package org.lsqt.rst.service.impl;


import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.rst.model.UserWorkRecord;
import org.lsqt.rst.model.UserWorkRecordQuery;
import org.lsqt.rst.service.UserWorkRecordService;

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
		if (model.getWeekday() == null) {
			SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");

			String xq = dateFm.format(convertDate(model.getRecordDate().toString()));

			model.setWeekday(WEEKDAY_MAP.get(xq));
		}

		if (model.getRecordDate() == null) {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			model.setRecordDate(Integer.valueOf(df.format(new Date())));
		}

		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(UserWorkRecord.class, Arrays.asList(ids).toArray());
	}
}
