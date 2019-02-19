package org.lsqt.uum.util;

import java.io.BufferedWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.db.orm.ftl.FtlDbExecute;
import org.lsqt.components.mvc.util.RequestUtil;
import org.lsqt.components.util.bean.BeanUtil;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.Md5Util;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.uum.model.Res;
import org.lsqt.uum.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

/**
 * 绑定当前登陆用户的权限配置到查询对象
 * 注：查询对象暂时需要 PermissionSQL属性
 * @author mm
 *
 */
public class PermissionSQLUtil {
	private static final Logger log = LoggerFactory.getLogger(PermissionSQLUtil.class);
	
	static Configuration FTL_CONFIG ;
	static {
		FTL_CONFIG = new Configuration();
		FTL_CONFIG.setDefaultEncoding("UTF-8");
		FTL_CONFIG.setClassicCompatible(true);//处理空值为空字符串  
		FTL_CONFIG.setNumberFormat("#"); // 数字格式化不打逗号,如: 123,335,23
	}
	
	/**
	 * 给指定的query对象填充权限SQL语句
	 * @param query
	 * @throws Exception 
	 */
	public static void bindPermissionSQL(Object query) throws Exception {
		HttpServletRequest request = ContextUtil.getRequest();
		String uri = RequestUtil.getRequestURI(request); // 当前请求的URL
		
		List<Res> permissionList = new ArrayList<>();
		User loginUser = ContextUtil.getLoginUser();
		if (loginUser != null) {
			permissionList = loginUser.getMyResList();
		} else {
			log.info("没有绑定权数据，当前请求用户为空，有可能是匿名接口");
		}
		List<Res> dataQueryPermission = new ArrayList<>();
		for (Res e: permissionList) {
			if(e.getType() == null) continue;
			if (StringUtil.isBlank(e.getUrl())) continue;
			
			if (Res.TYPE_数据查询 == e.getType() && uri.startsWith(e.getUrl())) {
				dataQueryPermission.add(e);
			}
		}
		
		if (ArrayUtil.isBlank(dataQueryPermission)) {
			BeanUtil.forceSetProperty(query, "permissionSQL", "and 1=2");
			return;
		}
		
		StringBuilder permissionSQL = new StringBuilder();
		if (dataQueryPermission.size() > 1) {
			permissionSQL.append(" and (");
			for (int i = 0; i < dataQueryPermission.size(); i++) {
				Res res = dataQueryPermission.get(i);
				
				StringBuilder oneCondition = new StringBuilder();
				oneCondition.append(" (1=1 ");
				String p= renderSQL(res.getValue()).trim();
				if (p.startsWith("and ")) {
					oneCondition.append(p);
				} else {
					oneCondition.append("and ".concat(p));
				}
				oneCondition.append(" )");
				
				if (i != dataQueryPermission.size() - 1) {
					oneCondition.append(" or ");
				}

				permissionSQL.append(oneCondition);
			}
			
			permissionSQL.append(" )");
			BeanUtil.forceSetProperty(query, "permissionSQL", permissionSQL.toString());
		} else {
			String sql = renderSQL(dataQueryPermission.get(0).getValue());
			BeanUtil.forceSetProperty(query, "permissionSQL", sql);
		}
	}
	

	
	/**
	 * 默认取到当前登陆用户，渲染上下文SQL
	 * @param content
	 * @return
	 * @throws Exception
	 */
	private static String renderSQL(String content) throws Exception {
		String id = Md5Util.MD5Encode(content, "utf-8", false);
		
		Template tmpl = new Template(id, new StringReader(content), FTL_CONFIG);
		final Map<String, Object> root = new HashMap<>();
		root.put("loginUser", ContextUtil.getLoginUser()); //解析登陆用户

		StringWriter stringWriter = new StringWriter();
		BufferedWriter writer = new BufferedWriter(stringWriter);

		registStaticModel(root);
		
		tmpl.process(root, writer);

		writer.flush();
		writer.close();

		String sql = stringWriter.toString().trim();
		return sql;
	}

	/**
	 * 注册工具类方法到模板引擎调用(多个工具类以逗号分割)
	 * @param stmt
	 * @param root
	 * @throws TemplateModelException
	 */
	private static void registStaticModel(Map<String, Object> root) throws TemplateModelException {

		BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
		TemplateHashModel staticModels = wrapper.getStaticModels();
		
		String [] clazzes = (ArrayUtil.class.getName()+","+StringUtil.class.getName()).split(",");
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

