package org.lsqt.act.service.impl;

import java.io.BufferedWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lsqt.act.model.UserRule;
import org.lsqt.act.model.UserRuleQuery;
import org.lsqt.act.service.UserRuleService;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.uum.model.User;
import org.lsqt.syswin.uum.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

@Service
public class UserRuleServiceImpl implements UserRuleService{
	private static final Logger log = LoggerFactory.getLogger(UserRuleServiceImpl.class);
	@Inject private Db db;
	@Inject private PlatformDb db2;
	
	@Inject private UserService userService;
	
	public Page<UserRule>  queryForPage(UserRuleQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), UserRule.class, query);
	}

	public List<UserRule>  queryForList(UserRuleQuery query) {
		return db.queryForList("queryForPage",UserRule.class, query);
	}
	
	public List<UserRule> getAll(){
		  return db.queryForList("getAll", UserRule.class);
	}
	
	public UserRule saveOrUpdate(UserRule model) {
		return db.saveOrUpdate(model);
	}
	
	public UserRule saveOrUpdate(UserRule model,String ...props) {
		return db.saveOrUpdate(model,props);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(UserRule.class, Arrays.asList(ids).toArray());
	}

	// ----------------------------------------------------------------------------------

	public List<String> resolveUsers(Long loginUserId, Map<String,Object> variables,Long... ruleId) throws Exception {
		List<String> empty = new ArrayList<>(0);
		Set<String> userIds = new HashSet<>();
		
		if(ruleId == null || ruleId.length==0) return empty;

		Map<String, Object> root = new HashMap<>();
		if (loginUserId != null) {
			User loginUser = userService.getById(loginUserId);
			if (loginUser != null){
				root.put("loginUser", loginUser);
			}
		}
		root.putAll(variables);
		
		registStaticModel(root); //注册工具类到Freemark引擎
		
		
		
		for (Long id : ruleId) {
			UserRule rule = db.getById(UserRule.class, id);
			if (rule == null || rule.getResolveType() == null){
				continue;
			}
			/*
			if(rule.getEnable()!=null && rule.getEnable()== UserRule.ENABLE_ON) {
				
				continue;
			}*/
			
			log.debug(" --- 规则类型(1=freemark 2=velocity 3=javascript 4=groovy): "+rule.getResolveType());
			
			if(UserRule.RESOLVE_TYPE_FREEMARK == rule.getResolveType()) {
				String resolvedContent = resolveUserIdsFreemark(root,rule);
				
				log.debug(" --- 规则内容类型(静态文本=0 SQL语句=1  JavaScript代码=2  Groovy代码=3): "+rule.getContentType());
				
				if(UserRule.CONTENT_TYPE_SQL.equals(rule.getContentType())){ // sql
					List<Map<String, Object>> list = db2.executeQuery(resolvedContent);
					if (list != null && !list.isEmpty()) {
						for (Map<String, Object> row : list) {
							Collection<Object> values = row.values();
							if (values != null && !values.isEmpty()) {
								Object uid = values.iterator().next();
								if (uid != null) {
									userIds.add(uid.toString());
								}
							}

						}
					}
				}
				else if(UserRule.CONTENT_TYPE_STATIC_TEXT.equals(rule.getContentType())){ // 静态文本
					userIds.addAll(StringUtil.split(resolvedContent, ","));
				}
			}
			else if(UserRule.RESOLVE_TYPE_VELOCITY == rule.getResolveType()) {
				
			}else if(UserRule.RESOLVE_TYPE_JAVASCRIPT == rule.getResolveType()) {
				
			}else if(UserRule.RESOLVE_TYPE_GROOVY == rule.getResolveType()) {
				
			}
		}
		
		return new ArrayList<>(userIds) ;
	}
	
	String resolveUserIdsFreemark(Map<String,Object> root,UserRule rule) throws Exception{
		if(rule!=null){
			///log.debug(" --- 用户规则id:"+rule.getId()+",内容："+rule.getContent()+ ",变量:"+root);
		}
		Template tmpl = new Template(rule.getId()+""+System.currentTimeMillis(), new StringReader(rule.getContent()), ActFreemarkUtil.FTL_CONFIGURATION);
		StringWriter stringWriter = new StringWriter();
		BufferedWriter writer = new BufferedWriter(stringWriter);
		tmpl.process(root, writer);

		writer.flush();
		writer.close();

		return stringWriter.toString().trim();
	}
	
	
	/**
	 * 注册工具类StringUtil、ArrayUtil方法到模板引擎调用(多个工具类以逗号分割)
	 *
	 * @param root
	 * @throws TemplateModelException
	 */
	void registStaticModel(final Map<String, Object> root) throws TemplateModelException {
	 
		BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
		TemplateHashModel staticModels = wrapper.getStaticModels();
		
		String [] clazzes = new String[]{StringUtil.class.getName(),ArrayUtil.class.getName()};
		for(String impClazz: clazzes) {
			TemplateHashModel utilStatics = (TemplateHashModel) staticModels.get(impClazz);
			
			String util = null;
			if (impClazz.indexOf(".") != -1) {
				util = impClazz.substring(impClazz.lastIndexOf(".") + 1, impClazz.length());

			} else {
				util = impClazz;
			}

			if (util != null) {
				root.put(util, utilStatics);
			}
		}
	}
}
