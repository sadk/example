package org.lsqt.sys.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.ExceptionUtil;
import org.lsqt.sys.model.CodeContent;
import org.lsqt.sys.model.CodeTemplate;
import org.lsqt.sys.model.CodeTemplateQuery;
import org.lsqt.sys.model.Column;
import org.lsqt.sys.model.ColumnQuery;
import org.lsqt.sys.model.Project;
import org.lsqt.sys.model.Table;
import org.lsqt.sys.model.Variable;
import org.lsqt.sys.model.VariableQuery;
import org.lsqt.sys.service.CodeTemplateService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class CodeTemplateServiceImpl implements CodeTemplateService{
	
	@Inject private CodeGenForSpring3Mybatis3Impl codeGenForSpring3Mybatis3;
	@Inject private CodeGenForExampleImpl codeGenForExample;
	@Inject private CodeGenForSpring3Mybatis3SyswinImpl codeGenForSyswinSpring3Mybatis3;

	@Inject private Db db;
	
	public Page<CodeTemplate>  queryForPage(CodeTemplateQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), CodeTemplate.class, query); 
	}
	
	public List<CodeTemplate> getAll(){
		return db.queryForList("queryForPage", CodeTemplate.class); 
	}
	
	public CodeTemplate saveOrUpdate(CodeTemplate model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(CodeTemplate.class, Arrays.asList(ids).toArray());
	}

	
	public String codegenForSingle(String codeGenType,Long tableId,String groupId,String modules,String entityName) throws Exception {
		Table table = db.getById(Table.class, tableId);

		// 单表一键生成
		if (CODEGEN_TYPE_ONECE_GEN_FOR_SINGLE.equals(codeGenType)) {
			if (Project.PROJECT_CODE_EXAMPLE.equals(table.getProjectCode())) {
				return codeGenForExample.codegenForSingle(tableId, groupId, modules, entityName);
			}
			else if(Project.PROJECT_CODE_SPRING3_MYBATIS3.equals(table.getProjectCode())) {
				return codeGenForSpring3Mybatis3.codegenForSingle(tableId, groupId, modules, entityName);
			}
			else if(Project.PROJECT_CODE_SPRING3_MYBATIS3_SYSWIN.equals(table.getProjectCode())) {
				return codeGenForSyswinSpring3Mybatis3.codegenForSingle(tableId, groupId, modules, entityName);
			}
		}

		// 主子表一键生成
		else if(CODEGEN_TYPE_ONECE_GEN_FOR_MAIN_SUB.equals(codeGenType)) {
			
		}
		
		return null;
	}

	
	@Override
	public void codegenForMainSubTable(Long tableId) {
		
	}

	@Override
	public void codegenForCustomer(Long tableId) {
		
	}

	public CodeContent codegenForTemplate(Long templateId,Long tableId) {
		if(templateId == null) return null;
		if(tableId == null) return null;
		
		String codeLog = "";
		String codeContent = "";
		String status = "";
		
		Table table = db.getById(Table.class, tableId);
		
		// 模板
		CodeTemplate tmpl = db.getById(CodeTemplate.class, templateId);
		
		// 模板占位符
		VariableQuery query = new VariableQuery();
		query.setObjId(templateId);
		query.setUseType(Variable.USE_TYPE_代码生成器变量);
		List<Variable> list = db.queryForList("queryForPage", Variable.class, query);
		
		
		// 固定加载表元信息
		ColumnQuery filter = new ColumnQuery();
		filter.setTableId(tableId);
		List<Column> columnList = db.queryForList("queryForPage", Column.class, filter);

		// 内置变量
		Map<String,Object> root = new HashMap<String,Object>();
		root.put("tableColumnList", columnList);
		root.put("tableName", table.getTableName());
		root.put("tableComment", table.getComment());
		
		// 用户自定义变量
		if(list!=null) {
			for (Variable e: list) {
				if(Variable.VALUE_TYPE_固定值.equals(e.getValueType())){
					root.put(e.getCode(), e.getValue());
				}
				else if(Variable.VALUE_TYPE_运行时.equals(e.getValueType())) {
					
				}
				
			}
		}
		try{
			codeContent = FreemarkCodeGenUtil.toCode(tmpl.getContent(), root);
			status = CodeContent.STATUS_生成成功;
			codeLog=String.format("生成代码成功(模板ID=%s , 表ID=%s)",templateId,tableId);
		}catch(Exception ex){
			status = CodeContent.STATUS_生成失败;
			codeLog =ExceptionUtil.getStackTrace(ex);
			System.out.println(ex);
		}
		
		CodeContent content = new CodeContent();
		content.setTemplateId(templateId);
		content.setTableId(tableId);
		content.setLog(codeLog);
		content.setContent(codeContent);
		content.setStatus(status);
		
		return content;
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~辅助方法,Begin~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~辅助方法,End~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}

class FreemarkCodeGenUtil {
	static final Configuration cfg;
	static {
		cfg = new Configuration() ;
		cfg.setDefaultEncoding("UTF-8");
		cfg.setClassicCompatible(true);
	}
	
	private static void prepareFileIfNotExists(String fullOutFile) throws IOException {
		String dirPath = fullOutFile.substring(0,fullOutFile.lastIndexOf("/"));
		File dir=new File(dirPath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		
		File file = new File(fullOutFile);
		if(file.exists()) {
			file.delete();
		}
		
		file.createNewFile();
	}
	
	/**
	 * 
	 * @param tmplDir 读取模板文件的目录   
	 * @param tmplFileName 模板文件名   
	 * @param model 
	 * @param fullOutPutName 输出的文件全路径
	 * @throws TemplateException 
	 * @throws Exception 
	 */
	public static void toCode(String tmplDir,String tmplFileName,Map<String,Object> model,String fullOutFile) throws IOException, TemplateException{
		prepareFileIfNotExists(fullOutFile);
		
		cfg.setDirectoryForTemplateLoading(new File(tmplDir)); // 设置
		Template t = cfg.getTemplate(tmplFileName); // 读取目录中文件名为ftl的模板

		Writer out = new OutputStreamWriter(new FileOutputStream(fullOutFile), "UTF-8"); // 输出流
		t.process(model, out);
		out.close();
		  
	}
	
	public static String toCode(String tmplContent,Map<String,Object> root) throws IOException, TemplateException{
		Template tmpl = new Template("name="+tmplContent.hashCode(), new StringReader(tmplContent), cfg);
		StringWriter stringWriter = new StringWriter();
		BufferedWriter writer = new BufferedWriter(stringWriter);
		
		
		tmpl.process(root, writer);
		
		writer.flush();
		writer.close();

		return stringWriter.toString().trim();
		
	}
	
}