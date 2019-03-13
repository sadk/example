
package org.lsqt.sms.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * TODO(这里用一句话描述这个类的作用).
 *
 * @author mali
 * @version  v 1.0
 * @ClassName: DateUtil
 * @date: 2016-5-20 15:11:52
 */
public class DateUtil {
    
    /** The class instance. */
    private static DateUtil classInstance = new DateUtil();
    
    /** The Constant DATE_DAYS. */
    public static final int DATE_DAYS = 0;
    
    /** The Constant DATE_HOURS. */
    public static final int DATE_HOURS = 1;
    
    /** The Constant DATE_MINUTES. */
    public static final int DATE_MINUTES = 2;
    
    /** The Constant DATE_SECONDS. */
    public static final int DATE_SECONDS = 3;
    
    public static final String DFT = "yyyyMMddHHmm";
    
    public static final String DFT1 = "yyyy-MM-dd HH:mm:ss:SSS";
    
    public static final String DFT2 = "yyyy/MM/dd HH:mm:ss";

    
    /**
     * 每天的毫秒值
     */
    public static final int ONE_DAY_TOTAL_SECONDS = 24 * 60 * 60 * 1000;
    
    public static DateUtil getInstance() {
        return classInstance;
    }
    
    /**
     * 按照格式格式化时间数据.
     *
     * @author mali
     * @param date the date
     * @param pattern the pattern
     * @return String
     * @title: formatDate
     * @date: 2016-5-20 15:11:52
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            date = new Date(System.currentTimeMillis());
        }
        
        if (pattern == null) {
            pattern = "yyyyMMddHHmm";
        }
        return DateFormatUtils.format(date, pattern);
    }
    
    /**
     * 以默认格式"yyyy-MM-dd HH:mm"格式化时间.
     *
     * @author mali
     * @param date the date
     * @return String
     * @title: defaultFormat
     * @date: 2016-5-20 15:11:52
     */
    public static String defaultFormat(Date date) {
        return formatDate(date, null);
    }
    
    /**
     * 以格式"yyyy-MM-dd"格式化时间.
     *
     * @author mali
     * @param date the date
     * @return String
     * @title: defaultFormatYMD
     * @date: 2016-5-20 15:11:52
     */
    public static String defaultFormatYMD(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }
    
    /**
     * 取今天的0点时刻   即“date 00:00:00”.
     *
     * @author mali
     * @return Date
     * @title: parseDateFormat
     * @date: 2016-5-20 15:11:52
     */
    public static Date parseDateFormat() {
        SimpleDateFormat fo = new SimpleDateFormat();
        Date date = new Date(System.currentTimeMillis());
        fo.applyPattern("yyyy-MM-dd");
        
        try {
            date = fo.parse(DateFormatUtils.format(date, "yyyy-MM-dd"));
        } catch (Exception e) {
        }
        
        return date;
    }
    
    /**
     * 根据格式得到时间对象.
     *
     * @author mali
     * @param dateString the date string
     * @param pattern the pattern
     * @return Date
     * @title: parseDateFormat
     * @date: 2016-5-20 15:11:52
     */
    public static Date parseDateFormat(String dateString, String pattern) {
        SimpleDateFormat fo = new SimpleDateFormat();
        fo.applyPattern(pattern);
        
        try {
            return fo.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 根据格式得到时间对象.
     *
     * @author mali
     * @param dateString the date string
     * @param pattern the pattern
     * @return Date
     * @title: parseDateFormat
     * @date: 2016-5-20 15:11:52
     */
    public static Date parseDateFormatSuffixStart(String dateString, String pattern) {
        SimpleDateFormat fo = new SimpleDateFormat();
        fo.applyPattern(pattern);
        
        try {
            return fo.parse(dateString + " 00:00:00");
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 根据格式得到时间对象.
     *
     * @author mali
     * @param dateString the date string
     * @param pattern the pattern
     * @return Date
     * @title: parseDateFormat
     * @date: 2016-5-20 15:11:52
     */
    public static Date parseDateFormatSuffixEnd(String dateString, String pattern) {
        SimpleDateFormat fo = new SimpleDateFormat();
        fo.applyPattern(pattern);
        
        try {
            return fo.parse(dateString + " 23:59:59");
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 根据类型返回日期格式.
     *
     * @author mali
     * @param date the date
     * @param pattern the pattern
     * @return String
     * @title: formatDate2String
     * @date: 2016-5-20 15:11:52
     */
    public static String formatDate2String(Date date, String pattern) {
        if (pattern == null) {
            pattern = "yyyy-MM-dd HH:mm";
        }
        return DateFormatUtils.format(date, pattern);
    }
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @author mali
     * @param str the str
     * @return Date
     * @title: string2Date
     * @date: 2016-5-20 15:11:52
     */
    public static Date string2Date(String str) {
        if (StringUtils.isEmpty(str))
            return new Date();
        return java.sql.Date.valueOf(str);
    }
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @author mali
     * @param date the date
     * @param oldPattern the old pattern
     * @param newPattern the new pattern
     * @return String
     * @title: formatStringToString
     * @date: 2016-5-20 15:11:52
     */
    public static String formatStringToString(String date, String oldPattern, String newPattern) {
        if (date == null || oldPattern == null || newPattern == null)
            return "";
        SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern); // 实例化模板对象    
        SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern); // 实例化模板对象    
        Date d = null;
        try {
            d = sdf1.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdf2.format(d);
    }
    
    /**
     * 字符串格式时间转换到对象时间,时分秒.
     *
     * @author mali
     * @param str the str
     * @return Date
     * @title: stringDateTime
     * @date: 2016-5-20 15:11:52
     */
    public static Date stringDateTime(String str) {
        if (StringUtils.isEmpty(str))
            return new Date();
        SimpleDateFormat fo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = fo.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    
    /**
     * 返回星期几.
     *
     * @author mali
     * @param date the date
     * @return String
     * @title: getWeekDayString
     * @date: 2016-5-20 15:11:52
     */
    public static String getWeekDayString(Date date) {
        String weekString = "";
        final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        if (date == null)
            date = new Date();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        weekString = dayNames[dayOfWeek - 1];
        return weekString;
        
    }
    
    /**
     * 判断某个时间是否在时间段内.
     *
     * @author mali
     * @param srcDate the src date
     * @param startDate the start date
     * @param endDate the end date
     * @return boolean
     * @title: between
     * @date: 2016-5-20 15:11:52
     */
    public static boolean between(Date srcDate, Date startDate, Date endDate) {
        if (startDate.after(srcDate))
            return false;
        if (endDate.before(srcDate))
            return false;
        return true;
    }
    
    /**
     * 返回两时间比较.
     *
     * @author mali
     * @param startDate the start date
     * @param endDate the end date
     * @return true:开始时间小于结束时间 false:开始时间大于结束时间
     * @title: between
     * @date: 2016-5-20 15:11:52
     */
    public static boolean between(Date startDate, Date endDate) {
        if (startDate.compareTo(endDate) <= 0)
            return true;
        return false;
    }
    
    /**
     * 获得剩余时间.
     *
     * @author mali
     * @param overTime            过期时间
     * @return 剩余时间
     * @title: marginTimeFormat
     * @date: 2016-5-20 15:11:52
     */
    public static String marginTimeFormat(Date overTime) {
        String marginTimeFormat = "";
        // 过期日期
        Calendar overDate = Calendar.getInstance();
        overDate.setTime(overTime);
        // 现在的日期
        Calendar nowDate = Calendar.getInstance();
        // 到现在一共的剩余多少秒
        long seconds = (overDate.getTime().getTime() - nowDate.getTime().getTime()) / 1000;
        if (seconds <= 0)
            return "过期";
        // 1(day)*24(hour)*60(minite)*60(seconds)
        // 天
        long dayMargin = seconds / 86400;
        // 小时
        seconds = seconds - (dayMargin * 86400);
        long hourMargin = seconds / 3600;
        // 分钟
        seconds = seconds - (hourMargin * 3600);
        long minuteMargin = seconds / 60;
        // 秒
        seconds = seconds - (minuteMargin * 60);
        // long secondMargin = seconds;
        
        // 双位显示,如: 01小时02分01秒
        // hstr = String.valueOf(hourMargin);
        // mstr = String.valueOf(minuteMargin);
        // sstr = String.valueOf(secondMargin);
        // hstr = String.valueOf(hourMargin).length() < 2 ? ("0" + hstr) : hstr;
        // mstr = String.valueOf(minuteMargin).length() < 2 ? ("0" + mstr) :
        // mstr;
        // sstr = String.valueOf(secondMargin).length() < 2 ? ("0" + sstr) :
        // sstr;
        
        if (dayMargin > 0) {
            marginTimeFormat = dayMargin + "天";
            return marginTimeFormat;
        } else if (hourMargin > 0) {
            marginTimeFormat = hourMargin + "小时";
            return marginTimeFormat;
        } else if (minuteMargin > 0) {
            marginTimeFormat = minuteMargin + "分钟";
            return marginTimeFormat;
        } else {
            marginTimeFormat = 1 + "分钟";
            return marginTimeFormat;
        }
    }
    
    /**
     * 获得剩余时间.
     *
     * @author mali
     * @param overTime            过期时间
     * @param pattern the pattern
     * @return 剩余时间
     * @title: marginTimeFormat
     * @date: 2016-5-20 15:11:52
     */
    public static String marginTimeFormat(Date overTime, int pattern) {
        StringBuilder result = new StringBuilder();
        // 过期日期
        Calendar overDate = Calendar.getInstance();
        overDate.setTime(overTime);
        // 现在的日期
        Calendar nowDate = Calendar.getInstance();
        // 到现在一共的剩余多少秒
        long seconds = (overDate.getTime().getTime() - nowDate.getTime().getTime()) / 1000;
        
        // 天
        long dayMargin = seconds / 86400;
        // 小时
        seconds = seconds - (dayMargin * 86400);
        long hourMargin = seconds / 3600;
        // 分钟
        seconds = seconds - (hourMargin * 3600);
        long minuteMargin = seconds / 60;
        // 秒
        seconds = seconds - (minuteMargin * 60);
        long secondMargin = seconds;
        switch (pattern) {
            case DATE_SECONDS:
                result.append(concat2(dayMargin, "天"))
                    .append(concat2(hourMargin, "小时"))
                    .append(concat2(minuteMargin, "分钟"))
                    .append(concat2(secondMargin, "秒"));
                break;
            case DATE_MINUTES:
                result.append(concat2(dayMargin, "天"))
                    .append(concat2(hourMargin, "小时"))
                    .append(concat2(minuteMargin, "分钟"));
                break;
            case DATE_HOURS:
                result.append(concat2(dayMargin, "天")).append(concat2(hourMargin, "小时"));
                break;
            case DATE_DAYS:
            default:
                result.append(concat2(dayMargin, "天"));
        }
        return result.toString();
    }
    
    /**
     * 双位显示,如: 01小时.
     *
     * @author mali
     * @param time the time
     * @param pattern the pattern
     * @return String
     * @title: concat2
     * @date: 2016-5-20 15:11:52
     */
    private static String concat2(long time, String pattern) {
        String timeStr = String.valueOf(time);
        return timeStr.length() >= 2 ? (timeStr + pattern) : ("0" + timeStr + pattern);
    }
    
    /**
     * 根据当前时间增加小时分.
     *
     * @author mali
     * @param date the date
     * @return Date
     * @title: addDateHM
     * @date: 2016-5-20 15:11:52
     */
    public static Date addDateHM(Date date) {
        Calendar cal = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
        return calendar.getTime();
    }
    
    /**
     * 增加修改小时 添加的时间为 h 分钟为当前时间.
     *
     * @author mali
     * @param date the date
     * @param h the h
     * @return Date
     * @title: addDateHMMinus1
     * @date: 2016-5-20 15:11:52
     */
    public static Date addDateHMMinus1(Date date, int h) {
        Calendar cal = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
        return calendar.getTime();
    }
    
    /**
     * 增加修改小时 主要适用拍卖.
     *
     * @author mali
     * @param date the date
     * @param h the h
     * @return Date
     * @title: addDateHM
     * @date: 2016-5-20 15:11:52
     */
    public static Date addDateHM(Date date, int h) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE, 0);
        return calendar.getTime();
    }
    
    /**
     * 由于用24小时 返回时间+1.
     *
     * @author mali
     * @param date the date
     * @return int
     * @title: getHoursAdd1
     * @date: 2016-5-20 15:11:52
     */
    public static int getHoursAdd1(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY) + 1;
    }
    
    /**
     * 返回小时.
     *
     * @author mali
     * @param date the date
     * @return int
     * @title: getHours
     * @date: 2016-5-20 15:11:52
     */
    public static int getHours(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
    
    /**
     * 有效时间为某天的最后时刻.
     *
     * @author mali
     * @param date            选择的时间
     * @return Date
     * @title: getOverTime
     * @date: 2016-5-20 15:11:52
     */
    public static Date getOverTime(Date date) {
        Date time = addDateHM(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
        
    }
    
    /**
     * 返回 分钟.
     *
     * @author mali
     * @param date the date
     * @return int
     * @title: getMinute
     * @date: 2016-5-20 15:11:52
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }
    
    /**
     * 时间是否相等 true:相等 false:不等.
     *
     * @author mali
     * @param startDate the start date
     * @param endDate the end date
     * @return boolean
     * @title: equalsDate
     * @date: 2016-5-20 15:11:52
     */
    public static boolean equalsDate(Date startDate, Date endDate) {
        if (startDate.compareTo(endDate) == 0)
            return true;
        return false;
    }
    
    /**
     * 是否周末.
     *
     * @author mali
     * @param day the day
     * @return boolean
     * @title: isWeekend
     * @date: 2016-5-20 15:11:52
     */
    public static boolean isWeekend(Date day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        int tmp = cal.get(Calendar.DAY_OF_WEEK);
        return (Calendar.SATURDAY == tmp || Calendar.SUNDAY == tmp) ? true : false;
    }
    
    /**
     * 获取日期差.
     *
     * @author mali
     * @param startTime the start time
     * @param endTime the end time
     * @return int
     * @title: getSubDays
     * @date: 2016-5-20 15:11:52
     */
    public static int getSubDays(Date startTime, Date endTime) {
        int day = 0;
        day = (int) ((endTime.getTime() - startTime.getTime()) / 86400000); // 24
                                                                            // *
                                                                            // 60
                                                                            // *
                                                                            // 60
                                                                            // *
                                                                            // 1000
        return day;
    }
    
    /**
     * Long转Date.
     *
     * @author mali
     * @param l the l
     * @param pattern the pattern
     * @return Date
     * @title: getLongTrunsDate
     * @date: 2016-5-20 15:11:52
     */
    public static Date getLongTrunsDate(Long l, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(l.toString());
        } catch (ParseException e) {
        }
        return null;
    }
    
    /**
    * 计算 日期 到 几月后的 天数.
    *
    * @author mali
    * @param date the date
    * @param offsetMonth the offset month
    * @return int
    * @title: daysBetween
    * @date: 2016-5-20 15:11:52
    */
    public static int daysBetween(String date, int offsetMonth) {
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(date));
            cal2.setTime(sdf.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        cal2.add(Calendar.MONTH, offsetMonth);
        
        long time1 = cal.getTimeInMillis();
        long time2 = cal2.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        
        return Integer.parseInt(String.valueOf(between_days));
    }
    
    /**
     * 计算 某个日期经过 offsetDays后的日期.
     *
     * @author mali
     * @param date  2015-01-27
     * @param offsetDays the offset days
     * @return  2015-01-27
     * @title: add_days
     * @date: 2016-5-20 15:11:52
     */
    public static String add_days(String date, int offsetDays) {
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        cal.add(Calendar.DATE, offsetDays);
        return sdf.format(cal.getTime());
    }
    
    /**
     * 返回当前日期的前(后)时间.
     *
     * @author mali
     * @param minute  15 分钟
     * @return long
     * @title: dateTimeBeforeMinute
     * @date: 2016-5-20 15:11:52
     */
    public static long dateTimeBeforeMinute(long minute) {
        SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date currentTime = new Date();
        long lDate = currentTime.getTime() + (1000L * 60L * minute);
        currentTime = new Date(lDate);
        return Long.parseLong(longDateFormat.format(currentTime));
    }
    
    /**
     * 格式化时间yyMMddHHmm.
     *
     * @author mali
     * @param date the date
     * @return String
     * @title: dateFmtYYMMDDMI
     * @date: 2016-5-20 15:11:52
     */
    public static String dateFmtYYMMDDMI(Date date) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyMMddHHmm");
        return fmt.format(date);
    }
    
    /**
     * 格式化时间yyMMddHHmmss,年月日时分秒.
     *
     * @author mali
     * @param date the date
     * @return String
     * @title: dateFmtYYMMDDHMS
     * @date: 2016-5-20 15:11:52
     */
    public static String dateFmtYYMMDDHMS(Date date) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyMMddHHmmss");
        return fmt.format(date);
    }
    
    /**
     * 格式化时间MMddHH,月日时.
     *
     * @author mali
     * @param date the date
     * @return String
     * @title: dateFmtMMDDHH
     * @date: 2016-5-20 15:11:52
     */
    public static String dateFmtMMDDHH(Date date) {
        SimpleDateFormat fmt = new SimpleDateFormat("MMddHH");
        return fmt.format(date);
    }
    
    /**
     * TODO(这里用一句话描述这个方法的作用).
     *
     * @author mali
     * @param date the date
     * @param fmt the fmt
     * @return String
     * @title: dateFmt
     * @date: 2016-5-20 15:11:52
     */
    public static String dateFmt(Date date, String fmt) {
        SimpleDateFormat fm = new SimpleDateFormat(fmt);
        return fm.format(date);
    }
    
    /**
     * 获取上月第一天.
     *
     * @author mali
     * @param date the date
     * @return Date
     * @title: getLastMonth_FirstDay
     * @date: 2016-5-20 15:11:52
     */
    public static Date getLastMonth_FirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        Date lastMonthDate = calendar.getTime();
        //上个月第一天
        GregorianCalendar gcLastMonth = (GregorianCalendar) Calendar.getInstance();
        gcLastMonth.setTime(lastMonthDate);
        gcLastMonth.set(Calendar.DAY_OF_MONTH, 1);
        String firstDay = formatDate2String(gcLastMonth.getTime(), "yyyy-MM-dd");
        StringBuffer firstDayStr = new StringBuffer().append(firstDay).append(" 00:00:00");
        return stringDateTime(firstDayStr.toString());
    }
    
    /**
     * 获取上月最后一天.
     *
     * @author mali
     * @param date the date
     * @return Date
     * @title: getLastMonth_LastDay
     * @date: 2016-5-20 15:11:52
     */
    public static Date getLastMonth_LastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        //上个月最后一天
        calendar.add(Calendar.MONTH, 1); //加一个月
        calendar.set(Calendar.DATE, 1); //设置为该月第一天
        calendar.add(Calendar.DATE, -1); //再减一天即为上个月最后一天
        String lastDay = formatDate2String(calendar.getTime(), "yyyy-MM-dd");
        StringBuffer lastDayStr = new StringBuffer().append(lastDay).append(" 23:59:59");
        return stringDateTime(lastDayStr.toString());
    }
    
    /**
     * 判断是否当月第一天.
     *
     * @author mali
     * @param date the date
     * @return boolean
     * @title: isFirstDayOfMonth
     * @date: 2016-5-20 15:11:52
     */
    @SuppressWarnings("static-access")
    public static boolean isFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int today = calendar.get(calendar.DAY_OF_MONTH);
        if (today == 1) {
            return true;
        }
        return false;
    }
    
    /**
     * 
     * 结束时间endDate与开始时间startDate相隔的天数
     * @author jiangqiubai    
     * @date: 2016年5月26日 下午6:39:04
     * @Title: daysBetween    
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return long 返回值 endDate 与  startDate相隔天数，如  2016-05-27 23:59:59 与 2016-05-26 00:00:00相隔一天
     */
    public static long daysBetween(Date startDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate2 = sdf.parse(sdf.format(startDate));
            Date endDate2 = sdf.parse(sdf.format(endDate));
            return (endDate2.getTime() - startDate2.getTime()) / ONE_DAY_TOTAL_SECONDS;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 
     * 结束时间endDate与开始时间startDate相隔的天数
     * @author sundanlang    
     * @date: 2016年7月1日 下午15:54:01
     * @Title: daysBetween    
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return long 返回值 endDate 与  startDate相隔天数，精确到秒.
     */
    public static long daysBetweenToSeconds(Date startDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startDate2 = sdf.parse(sdf.format(startDate));
            Date endDate2 = sdf.parse(sdf.format(endDate));
            long timestemp = endDate2.getTime() - startDate2.getTime();
            long daystemp = timestemp / ONE_DAY_TOTAL_SECONDS;
            return daystemp;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 
     * TODO(得到指定日期两个月后的日期)    
     * @author niujianbang    
     * @date: 2016年5月30日 下午3:50:35
     * @Title: addDateTwoMonth    
     * @param d1
     * @return Date 返回值
     */
    public static Date addDateTwoMonth(Date d1) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(d1);
        gc.add(2, +2);
        gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));
        Date d2 = gc.getTime();
        return d2;
    }
    
    /**
     * 
     * TODO(获取每天开始的时刻，就是00：00:00)    
     * @author niujianbang    
     * @date: 2016年5月30日 下午4:30:51
     * @Title: getStartTimeOfDay    
     * @return Date 返回值
     */
    public static Date getStartTimeOfDay() {
        Calendar c1 = new GregorianCalendar();
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        Date startTimeOfDay = c1.getTime();
        return startTimeOfDay;
    }
    
    /**
    * 获得指定日期的前一天
    * @param specifiedDay
    * @return
    * @throws Exception
    */
    public static String getSpecifiedDayBefore(String specifiedDay, String formatStr, int beforeDays) {
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isBlank(formatStr)) {
            formatStr = "yyyy-MM-dd";
        }
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(formatStr).parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - beforeDays);
        
        String dayBefore = new SimpleDateFormat(formatStr).format(c.getTime());
        return dayBefore;
    }
    
    /**
    * 获得指定日期的后一天
    * @param specifiedDay
    * @return
    */
    public static String getSpecifiedDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);
        
        String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayAfter;
    }
    
    /**
     * 获取当前时间并格式化   
     * @author mali    
     * @date: 2017年3月24日 下午2:26:12
     * @Title: getNow    
     * @return String 返回值
     */
    public static String getNow() {
        return formatDate(new Date(), DateUtil.DFT1);
    }
    
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        //        System.out.println(DateUtil.daysBetween("2015-01-27", 2));
        //        System.out.println(DateUtil.add_days("2015-01-27", 2));
        //        
        //        Date das = stringDateTime("2015-03-01 00:00:00");
        //        
        //        DateUtil.getLastMonth_LastDay(das);
        //        DateUtil.getLastMonth_FirstDay(das);
        //        
        //        System.out.println(isFirstDayOfMonth(das));
        
        System.out.println(formatDate(new Date(), null));
    }
}
