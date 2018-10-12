package org.lsqt.components.mvc;

import static java.util.Objects.isNull;

import java.io.File;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.mvc.impl.UrlMappingDefinition;
import org.lsqt.components.mvc.util.ViewResolveFtlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * Freemark模板视图处理器
 * @author mm
 *
 */
public class FreemarkViewHandler implements ViewHandler{
	private static final Logger log = LoggerFactory.getLogger(FreemarkViewHandler.class);
	
	private HttpServletResponse response;

	private final static Configuration cfg = new Configuration();  
	static {
		try {
			cfg.setDefaultEncoding("UTF-8");
			cfg.setObjectWrapper(new DefaultObjectWrapper());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public FreemarkViewHandler(HttpServletResponse response) {
		this.response = response;
	}

	public <T> T resolve(UrlMappingDefinition urlMapping,Object model) throws Exception{
		if (isNull(urlMapping) || isNull(urlMapping.getMethod())){
			return null;
		}
			
		
		RequestMapping requestMapping = urlMapping.getMethod().getAnnotation(RequestMapping.class);
		if (isNull(requestMapping) || requestMapping.view() != View.FTL) {
			return null;
		}
		
		
		View view = requestMapping.view();
		if (view != View.FTL) {
			return null;
		}
		
		
		String tmpl = requestMapping.path();
		String fileName = tmpl.substring(tmpl.lastIndexOf("/")+1, tmpl.length());
		String fileDir = tmpl.substring(0,tmpl.lastIndexOf("/"));
		
		String root = ViewResolveFtlUtil.class.getResource("/").getPath();
		File templateFile = new File(root+fileDir);
		
		log.debug("loading freemark template path: {}",templateFile.getPath());
		
		cfg.setDirectoryForTemplateLoading(templateFile);
		Template temp = cfg.getTemplate(fileName);
		
		temp.process(model,response.getWriter());
		
		return null;
	}

	
	
	
	
	
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String ...strings) throws Exception{
		Map map = new HashMap();
		map.put("user", "lavasoft哈哈鞢");
		map.put("url", "http://www.baidu.com/");
		map.put("name", "百度");
		
		File dir = new File("/Users/yuanke/git/example/src/main/java/org/lsqt/components/mvc/util/");
		
		Configuration cfg = new Configuration();  
		cfg.setDefaultEncoding("UTF-8");
		cfg.setDirectoryForTemplateLoading(dir);
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		
		Template temp = cfg.getTemplate("test.ftl");
		temp.process(map, new OutputStreamWriter(System.out));
        
    
	}


}

