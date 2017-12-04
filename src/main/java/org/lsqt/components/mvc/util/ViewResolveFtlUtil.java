package org.lsqt.components.mvc.util;

import static java.util.Objects.isNull;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.mvc.spi.UrlMappingDefinition;
import org.lsqt.components.util.file.PathUtil;
import org.lsqt.components.util.lang.StringUtil;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ViewResolveFtlUtil {
	private  static final Logger log = Logger.getLogger(ViewResolveFtlUtil.class.getName());
	
	private final static Configuration cfg = new Configuration();  
	static {
		try {
			cfg.setDefaultEncoding("UTF-8");
			cfg.setObjectWrapper(new DefaultObjectWrapper());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void resolve(Writer writer,UrlMappingDefinition urlMapDefinition,Object dataModel) throws IOException, TemplateException {
		if(isNull(urlMapDefinition) || isNull(urlMapDefinition.getMethod())) return ;
		
		RequestMapping requestMapping = urlMapDefinition.getMethod().getAnnotation(RequestMapping.class);
		if (isNull(requestMapping)  || requestMapping.view() != View.FTL) return;
		if (isNull(writer)) return;
		
		
		String tmpl = requestMapping.path();
		String fileName = tmpl.substring(tmpl.lastIndexOf("/")+1, tmpl.length());
		String fileDir = tmpl.substring(0,tmpl.lastIndexOf("/"));
		
		String root = ViewResolveFtlUtil.class.getResource("/").getPath();
		cfg.setDirectoryForTemplateLoading(new File(root+fileDir));
		Template temp = cfg.getTemplate(fileName);
		
		temp.process(dataModel, writer);
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
