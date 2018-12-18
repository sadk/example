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
import org.lsqt.components.db.orm.ORMappingIdGenerator;
import org.lsqt.components.db.orm.ftl.FtlDbExecute;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.rst.model.Company;
import org.lsqt.rst.model.CompanyQuery;
import org.lsqt.rst.model.Result;
import org.lsqt.rst.model.User;
import org.lsqt.rst.model.UserEntryInfo;
import org.lsqt.rst.model.UserEntryInfoQuery;
import org.lsqt.rst.model.UserQuery;
import org.lsqt.rst.model.UserWorkRecord;
import org.lsqt.rst.model.UserWorkRecordQuery;
import org.lsqt.rst.service.UserWorkRecordService;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.sys.service.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;



/**
 * 正常工时=工作日的时间之和（周一到周五*8 - 请假时间)
 * 
 * @author mm
 *
 */
@Controller(mapping={"/rst/user_work_record"})
public class UserWorkRecordController {
	private static final Logger log = LoggerFactory.getLogger(UserWorkRecordController.class);
	
	@Inject private UserWorkRecordService userWorkRecordService; 
	@Inject private DictionaryService dictionaryService;
	
	@Inject private Db db;
	
	private static final int DAY_WORK_HOUR_SETTING = 8; // 一天正常上班时间默认为8小时
	
	@RequestMapping(mapping = { "/wx/big_test" }, isTransaction = false, text = "测试插入大数据，试试导出查询")
	public Result<Object> bathInsertData4BigTest() throws Exception {
		String userCode = new ORMappingIdGenerator().getUUID58().toString();
		int m = 20181206;
		for (int t=0;t<100;t++) {
			List<UserWorkRecord> uwr = new ArrayList<>();
			for (int i=0;i<10000;i++) {
				
				UserWorkRecord model = new UserWorkRecord();
				model.setAppCode("6000");
				model.setTenantCode("6000");
				model.setAttendanceMothCut("5");
				model.setCompanyCode("jbnG7uGdECBfbfx9wxzhc");
				model.setCompanyName("简历科技淘宝有限公司");
				model.setExtraHours("2");
				model.setRecordDate(m--);
				model.setShiftType("1");
				model.setUserCode(userCode);
				model.setUserName("考勤导出用户测试");
				model.setWeekday(4);
				model.setWorkingHours("8");
				uwr.add(model);
			}
			db.batchSave(uwr);
		}
		
		return Result.ok();
	}
	
	/**
	 * 查询一个月的考勤记录，每一天
	 * @param userCode
	 * @param date yyyyMM的格式
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(mapping = { "/wx/query_work" }, isTransaction = false, text = "每个月的考勤统计")
	public Result<Map<String,Object>> queryWork(String userCode,Integer date) throws Exception {
		System.out.println("userCode = "+userCode+" ,date = "+date);
		
		if (StringUtil.isBlank(userCode)) {
			return Result.fail("用户编码不能为空");
		}

		if (date == null) {
			return Result.fail("考勤的具体年月不能为空");
		}

		if (date.toString().length() != 6) {
			return Result.fail("日期格式必须为yyyyMM");
		}

		
		
		// 一个月的加班、请假记录
		UserWorkRecordQuery query = new UserWorkRecordQuery();
		query.setUserCode(userCode);
		query.setRecordDateYearMonth(date);
		List<UserWorkRecord> data = db.queryForList("queryForPage", UserWorkRecord.class, query);

		Double normalWorking = 0D; //正常工时
		Double normalExtra = 0D; //平时加班
		Double weekendExtra = 0D; //周末加班

		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String now = sdf.format(new Date());
		
		List<Node> nodeList = buildMonthData(date,data); //DB里的考勤数据
		
		List<Node> realList = new ArrayList<>(); //界面显示的考勤数据
		for (Node e: nodeList) {
			if(e.recordDate <= Integer.valueOf(now)) { //不显示未来的考勤数据
				realList.add(e);
			}
		}
		
		for (Node e: realList) {
			normalWorking += e.workHours;
			
			if (e.weekday == 6 || e.weekday == 7) {
				weekendExtra += e.extraHours;
			} else {
				normalExtra += e.extraHours;
			}
		}
		 
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("detail",realList);
		resultMap.put("normalWorking",normalWorking);
		resultMap.put("normalExtra",normalExtra);
		resultMap.put("weekendExtra",weekendExtra);
		 
		
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
					n.leaveHas = e.getLeaveHas();
					
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
		
		public String leaveHas;
		
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
		System.out.println(JSON.toJSONString(form, true));
		
		if (form.getRecordDate() != null && form.getRecordDate().toString().length() != 8) { // yyyyMMdd
			return Result.fail("日期格式必须为6位");
		}

		if (StringUtil.isBlank(form.getUserCode())) {
			return Result.fail("用户编码不能为空");
		}

		try {
			return Result.ok(userWorkRecordService.saveOrUpdate(form));
		} catch (Exception ex) {
			return Result.fail(ex.getMessage());
		}
	}
	
	@RequestMapping(mapping = { "/wx/get_user_company_monthcut_day"},isTransaction = false,text="获取用户所在企业的月切考勤日")
	public Result<Integer> getUserCompanyMonthCutDay(String userCode)  {
		if(StringUtil.isBlank(userCode)) {
			return Result.fail("用户编码不能为空");
		}
		
		UserQuery q = new UserQuery();
		q.setCode(userCode);
		User dbUser = db.queryForObject("queryForPage", User.class, q);
		if(dbUser == null) {
			return Result.fail("没有找到当前用户");
		}
		
		
		UserEntryInfoQuery entryQuery = new UserEntryInfoQuery();
		entryQuery.setUserCode(userCode);
		UserEntryInfo ueModel = db.queryForObject("queryForPage", UserEntryInfo.class, entryQuery);
		
		if (ueModel == null || ueModel.getEntryStatus() == null) {
			return Result.fail("入职信息为空，不能记录考勤");
		}
		
		if(UserEntryInfo.ENTRY_STATUS_已入职 != ueModel.getEntryStatus()) {
			return Result.fail("用户未入职不能进行考勤记录");
		}
		
		 
		if(StringUtil.isBlank(ueModel.getCompanyCode())) {
			return Result.fail("没有找到注册用户的入职企业");
		}
		
		
		
		CompanyQuery cq = new CompanyQuery();
		cq.setCode(ueModel.getCompanyCode());
		Company c = db.queryForObject("queryForPage", Company.class, cq);
		if(c == null) {
			return Result.fail("用户入职企业为空");
		}
		
		if (c.getAttendanceDay() == null) {
			return Result.fail("当前用户入职企业没有设置考勤关闭日");
		}
		
		return Result.ok(c.getAttendanceDay());
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
		
		if (dbModel == null) {
			dbModel = new UserWorkRecord();
			dbModel.setRecordDate(recordDate);
			dbModel.setUserCode(userCode);
			
			SimpleDateFormat dateFm = new SimpleDateFormat("EEEE",Locale.CHINESE);
			String xq = dateFm.format(convertDate(recordDate.toString()));
			dbModel.setWeekday(WEEKDAY_MAP.get(xq)) ;
			
			dbModel.setLeaveHours("0");
			dbModel.setExtraHours("0");
			dbModel.setAttendanceMothCut("0");
			dbModel.setWorkingHours(DAY_WORK_HOUR_SETTING+""); //如果数据库没有记录，默认给前端显示8小时，不需用户输入 
			
		}
		return Result.ok(dbModel);
	}
	
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
