package org.lsqt.components.util.lang;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
	
	@SuppressWarnings("unchecked")
	public static <T> T getNow(String parttern, Class<T> requireType) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(parttern);
		LocalDateTime now = LocalDateTime.now();

		//返回数据型日期
		if (Long.class == requireType || long.class == requireType || Long.class.isAssignableFrom(requireType)) {
			return (T) Long.valueOf(formatter.format(now));
		}

		//返回字符型日期
		if (String.class == requireType) {
			return (T) formatter.format(now);
		}

		//返回原生日期类型
		if (Date.class == requireType) {
			return (T) new Date();
			
		} else if (java.sql.Date.class == requireType || java.sql.Date.class.isAssignableFrom(requireType)) {
			return (T) new java.sql.Date(System.currentTimeMillis());
		}
		
		return null;
	}
}
