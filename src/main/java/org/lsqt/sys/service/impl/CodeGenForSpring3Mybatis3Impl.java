package org.lsqt.sys.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lsqt.components.context.annotation.Component;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.db.Db;
import org.lsqt.components.mvc.spi.exception.ApplicationException;
import org.lsqt.components.util.file.FileUtil;
import org.lsqt.components.util.file.PathUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Column;
import org.lsqt.sys.model.ColumnQuery;
import org.lsqt.sys.model.Project;
import org.lsqt.sys.model.Table;

/**
 * 标准的spring3(mvc) + mybatis3 代码生成器实现
 * @author mmyuan
 *
 */
@Component
public class CodeGenForSpring3Mybatis3Impl {
	@Inject private Db db;
	
	/**
	 * 
	 * @param tableId 
	 * @param groupId 不能为空
	 * @param modules 可以为空或NULL
	 */
	public String codegenForSingle(Long tableId,String groupId,String modules,String entityName) {
		Table table = db.getById(Table.class, tableId);
		if(table == null) return null;
		
		if(StringUtil.isBlank(groupId)) {
			groupId = "org.lsqt.XXXX";
		}
		
		if(StringUtil.isNotBlank(entityName)) { // 类名（大写)
			entityName = CodeGenUti.prepareClazzNameByTableName(entityName);
		}else{
			entityName = CodeGenUti.prepareClazzNameByTableName(table.getTableName());
		}
		
		String projectCode = table.getProjectCode();
		if(!Project.PROJECT_CODE_SPRING3_MYBATIS3.equals(projectCode)) {
			throw new RuntimeException("请选择标准Spring3Mybatis3的工程模板");
		}
		
		//String clazz = table.getModelName();
		
		// 加载表元信息
		ColumnQuery query = new ColumnQuery();
		query.setTableId(tableId);
		List<Column> list = db.queryForList("queryForPage", Column.class, query);

		String clazzFirstLower = entityName.substring(0, 1).toLowerCase().concat(entityName.substring(1,entityName.length()));
		Map<String,Object> root = new HashMap<String,Object>();
		root.put("groupId", groupId);

		if (StringUtil.isNotBlank(modules)) {
			root.put("pkg", groupId.concat(".").concat(modules));
		} else {
			root.put("pkg", groupId);
		}
		
		root.put("tableName", table.getTableName());
		root.put("model", clazzFirstLower); // 小写模型类
		root.put("Model", entityName); // 大写模类名
		root.put("comment", table.getComment());// (主)表注释
		root.put("columnList", list);
		
		
		String tmplDirBase   = PathUtil.getAppRootDir()+File.separator+"src/main/resources/template/codegen";
		String outputDirBase = PathUtil.getAppRootDir()+File.separator+"src/main/resources/template/codegen-output";
		
		
		final String SPRING3_MYBATIS3="/spring3_mybatis3";
		File dir = new File(outputDirBase+SPRING3_MYBATIS3);
		if(dir.exists()) {
			System.out.println(" --- 清空录目: "+dir);
			FileUtil.deleteDir(dir);
		}
		
		
		// 1.生成Example工程标准的Controller代码
		String tmplDir = tmplDirBase+SPRING3_MYBATIS3+"/controller";
		
		String md = "";
		if(StringUtil.isNotBlank(modules)) {
			md = "/"+modules;
		}
		
		String fullOutFile = outputDirBase +SPRING3_MYBATIS3+md+"/controller/"+entityName+"Controller.java";
		try { 
			FreemarkCodeGenUtil.toCode(tmplDir, "Controller.java", root, fullOutFile);
		} catch (Exception e) {
			throw new ApplicationException("生成Controller代码失败~!",e);
		}
		
		//  2.生成工程标准的Model代码
		tmplDir = tmplDirBase + SPRING3_MYBATIS3 + "/model";
		fullOutFile = outputDirBase + SPRING3_MYBATIS3 + md + "/model/"+entityName+".java";
		try {
			FreemarkCodeGenUtil.toCode(tmplDir, "Model.java", root, fullOutFile);
		} catch (Exception e) {
			throw new ApplicationException("生成Model代码失败~!",e);
		}
		
		//  2.1生成工程标准的ModelQuery代码
		tmplDir = tmplDirBase + SPRING3_MYBATIS3 +"/model";
		fullOutFile = outputDirBase + SPRING3_MYBATIS3 + md + "/model/" + entityName + "Query.java";
		try {
			FreemarkCodeGenUtil.toCode(tmplDir, "ModelQuery.java", root, fullOutFile);
		} catch (Exception e) {
			throw new ApplicationException("生成Model代码失败~!",e);
		}
		
		// 3.生成工程标准的ORO映射文件
		tmplDir = tmplDirBase + SPRING3_MYBATIS3 +"/mapper";
		fullOutFile = outputDirBase + SPRING3_MYBATIS3 + md + "/mapper/" + entityName + "Mapper.xml";
		try {
			FreemarkCodeGenUtil.toCode(tmplDir, "Mapper.xml", root, fullOutFile);
		} catch (Exception e) {
			throw new ApplicationException("生成SQL文件失败~!",e);
		}
		
		// 3.1生成工程标准的ORO映射文件
		tmplDir = tmplDirBase + SPRING3_MYBATIS3 +"/mapper";
		fullOutFile = outputDirBase + SPRING3_MYBATIS3 + md + "/mapper/" + entityName + "Mapper.java";
		try {
			FreemarkCodeGenUtil.toCode(tmplDir, "Mapper.java", root, fullOutFile);
		} catch (Exception e) {
			throw new ApplicationException("生成SQL文件失败~!",e);
		}
		
		// 4.生成Service接口文件
		tmplDir = tmplDirBase + SPRING3_MYBATIS3 +"/service";
		fullOutFile = outputDirBase + SPRING3_MYBATIS3 + md + "/service/" + entityName + "Service.java";
		try {
			FreemarkCodeGenUtil.toCode(tmplDir, "Service.java", root, fullOutFile);
		} catch (Exception e) {
			throw new ApplicationException("生成Service接口代码失败~!",e);
		}
		
		// 5.生成ServiceImpl文件
		tmplDir = tmplDirBase + SPRING3_MYBATIS3 +"/service/impl";
		fullOutFile = outputDirBase + SPRING3_MYBATIS3 + md + "/service/impl/" + entityName + "ServiceImpl.java";
		try {
			FreemarkCodeGenUtil.toCode(tmplDir, "ServiceImpl.java", root, fullOutFile);
		} catch (Exception e) {
			throw new ApplicationException("生成ServiceImpl代码失败~!",e);
		}
		
		/*
		// 6.生成UI页面-list页
		tmplDir = tmplDirBase + SPRING3_MYBATIS3 +"/webapp/single";
		fullOutFile = outputDirBase + SPRING3_MYBATIS3 + "/webapp/single/index.jsp";
		try {
			FreemarkCodeGenUtil.toCode(tmplDir, "index.jsp", root, fullOutFile);
		} catch (Exception e) {
			throw new ApplicationException("生成UI页面-index.jsp代码失败~!",e);
		}
		
		// 7.生成UI页面-编辑页
		tmplDir = tmplDirBase + SPRING3_MYBATIS3 + "/webapp/single";
		fullOutFile = outputDirBase + SPRING3_MYBATIS3 + "/webapp/single/edit.jsp";
		try {
			FreemarkCodeGenUtil.toCode(tmplDir, "edit.jsp", root, fullOutFile);
		} catch (Exception e) {
			throw new ApplicationException("生成UI页面-edit.jsp代码失败~!",e);
		}*/
		
		File rs = new File(tmplDirBase+SPRING3_MYBATIS3);
		System.out.println(" --- 代码生成成功~!");
		System.out.println(" --- 代码路径: "+ rs);
		return rs.getAbsolutePath();
	}

}
