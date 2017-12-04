package org.lsqt.sys.service.impl;

import org.lsqt.components.util.lang.StringUtil;

public class CodeGenUti {
	/**
	 * 跟据表名获取表对应的类名(去下划线和表前缀，首字母大写)
	 * 
	 * @param tableName
	 * @return
	 */
	public static String prepareClazzNameByTableName(String tableName) {

		StringBuilder clazz = new StringBuilder();
		if (tableName.indexOf("_") != -1) {
			String[] arr = tableName.split("_");

			int i = 1;
			if (arr.length == 1) {
				i = 0;
			}

			for (; i < arr.length; i++) {
				String e = arr[i];

				if (StringUtil.isNotBlank(e)) {
					clazz.append(e.substring(0, 1).toUpperCase().concat(e.substring(1, e.length())));
				}

			}
		} else {
			clazz.append(tableName.substring(0, 1).toUpperCase().concat(tableName.substring(1, tableName.length())));
		}

		return clazz.toString();
	}
}
