package org.lsqt.rst.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestPayload;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.rst.model.Result;
import org.lsqt.rst.model.UserWorkRecord;
import org.lsqt.rst.model.UserWorkRecordQuery;
import org.lsqt.rst.service.UserWorkRecordService;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.sys.service.DictionaryService;

import com.alibaba.fastjson.JSON;



/**
 * 正常工时=工作日的时间之和（周一到周五*8 - 请假时间)
 * 
 * @author mm
 *
 */
@Controller(mapping={"/rst/user_work_record"})
public class UserWorkRecordController {
	
	@Inject private UserWorkRecordService userWorkRecordService; 
	@Inject private DictionaryService dictionaryService;
	
	@Inject private Db db;
	
	private static final int DAY_WORK_HOUR_SETTING = 8; // 一天正常上班时间默认为8小时
	
	/**
	 * 查询一个月的考勤记录，每一天
	 * @param userCode
	 * @param date yyyyMM的格式
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(mapping = { "/wx/query_work" }, isTransaction = false, text = "每个月的考勤统计")
	public Result<Map<String,Object>> queryWork(String userCode,Integer date) throws Exception {
		if (StringUtil.isBlank(userCode)) {
			return Result.fail("用户编码不能为空");
		}

		if (date == null) {
			return Result.fail("考勤的具体年月不能为空");
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String now = sdf.format(new Date());
		
		
		// 一个月的加班、请假记录
		UserWorkRecordQuery query = new UserWorkRecordQuery();
		query.setUserCode(userCode);
		query.setRecordDateYearMonth(date);
		List<UserWorkRecord> data = db.queryForList("queryForPage", UserWorkRecord.class, query);

		Double normalTime = 0D;
		Double leaveTime = 0D;
		Double overTime =0D;
		Double totalTime=0D;
		
		List<Node> nodeList = buildMonthData(date,data); 
		List<Node> realList = new ArrayList<>();
		for (Node e: nodeList) {
			if(e.recordDate <= Integer.valueOf(now)) {
				realList.add(e);
			}
		}
		
		for (Node e: realList) {
			normalTime += e.workHours;
			leaveTime += e.leaveHours;
			overTime += e.extraHours;
		}
		totalTime = normalTime + overTime;
		
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("detail",realList);
		resultMap.put("normalTime",normalTime);
		resultMap.put("leaveTime",leaveTime);
		resultMap.put("overTime",overTime);
		resultMap.put("totalTime",totalTime);
		
		return Result.ok(resultMap);
	}
	
	private List<Node> buildMonthData(Integer date,List<UserWorkRecord> data) throws Exception {
		// 一个月的天数
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		int monthDays = getDaysOfMonth(sdf.parse(date.toString()));

		List<Node> result = new ArrayList<>();
		for (int i = 1; i <= monthDays; i++) {
			boolean isFind = false;
			
			for (UserWorkRecord e: data) {
				int d = getDay(e.getRecordDate());
				if (i == d) {
					isFind = true;
					
					Node n = new Node();
					n.recordDate = e.getRecordDate();
					n.day = i;
					n.weekday = e.getWeekday();
					
					n.workHours = StringUtil.isBlank(e.getWorkingHours()) ? 0D : Double.valueOf(e.getWorkingHours());
					
					// 周六周天只算加班；周一至周五算正常工时，超过8小时才能 加班
					if (e.getWeekday() == 6 || e.getWeekday() == 7) {
						n.leaveHours = 0D;
						n.extraHours = StringUtil.isBlank(e.getExtraHours())  ? 0D : Double.valueOf(e.getExtraHours());
						n.workHours = 0D;
					} else {
						n.extraHours = StringUtil.isBlank(e.getExtraHours()) ? 0D : Double.valueOf(e.getExtraHours());
						n.leaveHours = StringUtil.isBlank(e.getLeaveHours()) ? 0D : Double.valueOf(e.getLeaveHours());
						
						if (n.workHours > 0 || n.extraHours>0) { //有加班和正常上班，不能算请假
							n.leaveHours = 0D;
						} 
					}

					result.add(n);
					
					break;
				}
			}
			
			if (isFind == false) {
				Node n = new Node();
				n.day = i;

				String recordDate = date.toString();
				if (i < 10) {
					recordDate = recordDate + "0" + i;
				} else {
					recordDate = recordDate + i;
				}

				n.recordDate = Integer.valueOf(recordDate);

				SimpleDateFormat dateFm = new SimpleDateFormat("EEEE", Locale.CHINESE);
				String xq = dateFm.format(convertDate(n.recordDate.toString()));

				n.weekday = WEEKDAY_MAP.get(xq);

				n.extraHours = 0D;
				n.leaveHours = 0D;
				n.workHours = 0D;

				result.add(n);
			}
		}
		return result;
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
	
	public static class Node {
		public Integer recordDate;
		public Integer day ; //一个月的第几天，从1开始
		public Integer weekday; //星期几 ，从1开始是星期一
		
		public Double workHours; //正常上班工时
		public Double leaveHours; //请假工时
		public Double extraHours; //加班工时
	}
	
	/**
	 * 获取一个月的天数
	 * @param date
	 * @return
	 */
	private static int getDaysOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		
	}
	
	/**
	 * 获取一个月的第几天,从1开始
	 * @return
	 */
	private static int getDay(Integer yyyyMMdd) {
		LocalDate dd = LocalDate.parse(yyyyMMdd.toString(), DateTimeFormatter.ofPattern("yyyyMMdd"));
		return ( dd.get(ChronoField.DAY_OF_MONTH));
	}
	
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		System.out.println(getDaysOfMonth(sdf.parse("201801")));
		
		LocalDate dd = LocalDate.parse("20181107", DateTimeFormatter.ofPattern("yyyyMMdd"));
		System.out.println( dd.get(ChronoField.DAY_OF_MONTH));
	}
	
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
		/*if (form.getType() == null) {
			return Result.fail("考勤类型不能为空");
		}
		*/
		System.out.println(JSON.toJSONString(form, true));
		
		try {
			return Result.ok(userWorkRecordService.saveOrUpdate(form));
		} catch (Exception ex) {
			return Result.fail(ex.getMessage());
		}
	}
	
	@RequestMapping(mapping = { "/wx/get_by_date"},isTransaction = false)
	public Result<UserWorkRecord> getById4WX(String userCode,Integer recordDate)  {
		if (recordDate == null) {
			return Result.fail("考勤日期不能为空(格式为yyyyMMdd)");
		}
		
		if (StringUtil.isBlank(userCode)) {
			return Result.fail("用户ID不能为空");
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String now = sdf.format(new Date());
		if (recordDate > Integer.valueOf(now)) {
			return Result.fail("没有当前工时记录");
		}
		
		UserWorkRecordQuery query = new UserWorkRecordQuery();
		query.setRecordDate(recordDate);
		query.setUserCode(userCode);
		UserWorkRecord dbModel = db.queryForObject("queryForPage", UserWorkRecord.class , query);
		
		if (dbModel == null) { //如果没有，默认正常上班
			dbModel = new UserWorkRecord();
			dbModel.setRecordDate(recordDate);
			dbModel.setUserCode(userCode);
			
			SimpleDateFormat dateFm = new SimpleDateFormat("EEEE",Locale.CHINESE);
			String xq = dateFm.format(convertDate(recordDate.toString()));
			dbModel.setWeekday(WEEKDAY_MAP.get(xq)) ;
			
			dbModel.setLeaveHours("0");
			dbModel.setExtraHours("0");
			
			/*if(dbModel.getWeekday() == 6 || dbModel.getWeekday() == 7) {
				dbModel.setWorkingHours("0");
			} else {*/
				dbModel.setWorkingHours(DAY_WORK_HOUR_SETTING+"");
			//}
		}
		return Result.ok(dbModel);
	}
	
/*



	@RequestMapping(mapping = { "/wx/page" },isTransaction = false)
	public Result<Page<UserWorkRecord>> queryForPage4WX(UserWorkRecordQuery query) {
		if (StringUtil.isBlank(query.getUserCode())) {
			return Result.fail("用户编码不能为空");
		}
		return Result.ok(userWorkRecordService.queryForPage(query));
	}
	
	@RequestMapping(mapping = { "/wx/list"},isTransaction = false)
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
	*/
	
	
	// ---------------------------------------------- 以上是微信端接口  ----------------------------------------------
	
	
	
	
	@RequestMapping(mapping = { "/get_by_id", "/m/get_by_id" })
	public UserWorkRecord getById(Long id) throws IOException {
		return userWorkRecordService.getById(id);
	}
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<UserWorkRecord> queryForPage(UserWorkRecordQuery query) throws IOException {
		query.setTenantCode(ContextUtil.getLoginTenantCode());
		return userWorkRecordService.queryForPage(query); //  
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<UserWorkRecord> getAll() {
		return userWorkRecordService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public UserWorkRecord saveOrUpdate(UserWorkRecord form) {
		form.setTenantCode(ContextUtil.getLoginTenantCode());
		return userWorkRecordService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return userWorkRecordService.deleteById(list.toArray(new Long[list.size()]));
	}
	
}
