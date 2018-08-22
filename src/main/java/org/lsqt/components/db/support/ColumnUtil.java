package org.lsqt.components.db.support;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lsqt.components.util.lang.StringUtil;

public class ColumnUtil {
	private static final Pattern pattern = Pattern.compile("_[a-z]{1}");
	private static final Pattern dePattern = Pattern.compile("[a-z]{1}[A-Z]{1}");
	
	/**
	 * 下划线转驼峰
	 * @param columnName
	 * @return
	 */
	public static String toPropertyName(String columnName) {
		if(StringUtil.isBlank(columnName)){
			return columnName;
		}
		Matcher m = pattern.matcher(columnName);
		while(m.find()) {
			String f = m.group();
			columnName = columnName.replace(f, f.replace("_", "").toUpperCase());
		}
		return columnName;
	}
	
	/**
	 * 驼峰转下划线
	 * @param propertyName
	 * @return
	 */
	public static String toDbColumn(String propertyName) {
		if(StringUtil.isBlank(propertyName)){
			return propertyName;
		}
		Matcher m = dePattern.matcher(propertyName);
		while(m.find()) {
			String f = m.group();
			String upperString = String.valueOf(f.charAt(1));
			String value = f.replace(upperString, "_"+upperString.toLowerCase());
			propertyName = propertyName.replace(f, value);
		}
		return propertyName;
	}
}
