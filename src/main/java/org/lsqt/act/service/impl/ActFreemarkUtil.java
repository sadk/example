package org.lsqt.act.service.impl;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;

/**
 * <pre>
 * 专用于解析String里的语法.
 * 比如：流程前、后置角本解析；动态sql解析等
 * </pre>
 * @author mmyuan
 *
 */
public class ActFreemarkUtil {
	public static Configuration FTL_CONFIGURATION ;
	public static StringTemplateLoader FTL_STRING_TEMPLATE_LOADER ;
	
	static {
		FTL_CONFIGURATION = new Configuration();
		FTL_CONFIGURATION.setDefaultEncoding("UTF-8");
		FTL_CONFIGURATION.setNumberFormat("#"); // 数字格式化不打逗号,如: 123,335,23
		//FTL_CONFIGURATION.setClassicCompatible(true); // null则替换为空字符串
		
		FTL_STRING_TEMPLATE_LOADER = new StringTemplateLoader() ;
	}
	
	
	
}
