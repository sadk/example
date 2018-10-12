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
import org.lsqt.components.mvc.impl.UrlMappingDefinition;
import org.lsqt.components.util.file.PathUtil;
import org.lsqt.components.util.lang.StringUtil;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Deprecated
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
	
	public static void resolve(Writer writer,String path,Object dataModel) throws IOException, TemplateException {
		
		String fileName = path.substring(path.lastIndexOf("/")+1, path.length());
		String fileDir = path.substring(0,path.lastIndexOf("/"));
		
		String root = ViewResolveFtlUtil.class.getResource("/").getPath();
		cfg.setDirectoryForTemplateLoading(new File(root+fileDir));
		Template temp = cfg.getTemplate(fileName);
		
		temp.process(dataModel, writer);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String ...strings) throws Exception{
		System.out.println(ViewResolveFtlUtil.class.getResource("/").getPath());
		if(true)return;
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
