package org.lsqt.syswin.authority.service.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.ExceptionUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.authority.model.RangeValue;
import org.lsqt.syswin.authority.model.RangeValueQuery;
import org.lsqt.syswin.authority.service.RangeValueService;
import org.lsqt.syswin.uum.model.User;
import org.lsqt.syswin.uum.service.UserService;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class RangeValueServiceImpl implements RangeValueService{
	
	@Inject private PlatformDb db;
	@Inject private UserService userService;
	
	public Page<RangeValue>  queryForPage(RangeValueQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), RangeValue.class, query);
	}

	public List<RangeValue> getAll(){
		  return db.queryForList("getAll", RangeValue.class);
	}
	
	public RangeValue saveOrUpdate(RangeValue model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(RangeValue.class, Arrays.asList(ids).toArray());
	}
	
	static Configuration FTL_CONFIG ;
	static {
		FTL_CONFIG = new Configuration();
		FTL_CONFIG.setDefaultEncoding("UTF-8");
		FTL_CONFIG.setNumberFormat("#"); // 数字格式化不打逗号,如: 123,335,23
	}
	static StringTemplateLoader FTL_STRINGLOADER = new StringTemplateLoader();
	
	public String viewSql(Long userId, String content) throws Exception {
		String rs = "-1";
		User user = userService.getById(userId);

		final Map<String, Object> root = new HashMap<>();
		root.put("loginUser", user);
		root.put("db", db);

		
		Template tmpl = new Template("tmpl_"+userId +""+ content.hashCode(), new StringReader(content), FTL_CONFIG);
		StringWriter stringWriter = new StringWriter();
		BufferedWriter writer = new BufferedWriter(stringWriter);
		tmpl.process(root, writer);

		writer.flush();
		writer.close();

		String sql = stringWriter.toString().trim();
		return sql;
	

		 
	}
}
