package org.lsqt.report.service.impl.support;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 用于生成报表文件
 * @author mingmin.yuan
 *
 */
public class FreemarkGenerateReportFile {
	public static final Configuration cfg;
	static {
		cfg = new Configuration() ;
		cfg.setDefaultEncoding("UTF-8");
		cfg.setClassicCompatible(true); // null值显示空白字符
		cfg.setNumberFormat("#");  //数字格式化不打逗号,如: 123,335,23
	}
	

	/**
	 * @param input 模板文件(全路径)
	 * @param out 生成的文件(全路径)
	 * @param mode 模型数据
	 * @throws Exception 
	 * 
	 */
	public static void generate(String input,String out,Map<String,Object> model) throws Exception {
		String inputFileName = input.substring(input.lastIndexOf("/")+1,input.length());
		String dirPath = input.substring(0,input.lastIndexOf("/"));
		File dir=new File(dirPath);
		
		prepareFileIfNotExists(out);
		
		cfg.setDirectoryForTemplateLoading(dir);
		Template t = cfg.getTemplate(inputFileName); // 读取目录中文件名为ftl的模板

		Writer writer = new OutputStreamWriter(new FileOutputStream(out), "UTF-8"); // 输出流
		t.process(model, writer);
		writer.close();
	}
	
	private static void prepareFileIfNotExists(String fullOutFile) throws IOException {
		String dirPath = fullOutFile.substring(0, fullOutFile.lastIndexOf("/"));
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File file = new File(fullOutFile);
		if (file.exists()) {
			file.delete();
		}

		file.createNewFile();
	}
}
