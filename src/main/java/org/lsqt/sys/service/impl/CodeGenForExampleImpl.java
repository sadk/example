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
import org.lsqt.sys.model.Table;

/**
 * example工程模板，代码生成器实现
 * @author mmyuan
 *
 */
@Component
public class CodeGenForExampleImpl {
	@Inject private Db db;
	
	/**
	 * 
	 * @param tableId
	 * @param groupId
	 * @param modules
	 * @param entityName
	 * @return 返回代码生成的root目录
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
		
		final String example="/example";
		File dir = new File(outputDirBase+example);
		if(dir.exists()) {
			System.out.println(" --- 清空录目: "+dir);
			FileUtil.deleteDir(dir);
		}
		
		// 1.生成Example工程标准的Controller代码
		String tmplDir = tmplDirBase+"/example/controller";
		
		String md = "";
		if(StringUtil.isNotBlank(modules)) {
			md = "/"+modules;
		}
		String fullOutFile = outputDirBase + "/example"+md+"/controller/"+entityName+"Controller.java";
		try { 
			FreemarkCodeGenUtil.toCode(tmplDir, "Controller.java", root, fullOutFile);
		} catch (Exception e) {
			throw new ApplicationException("生成Controller代码失败~!",e);
		}
		
		//  2.生成Example工程标准的Model代码
		tmplDir = tmplDirBase + "/example/model";
		fullOutFile = outputDirBase + "/example"+md+"/model/"+entityName+".java";
		try {
			FreemarkCodeGenUtil.toCode(tmplDir, "Model.java", root, fullOutFile);
		} catch (Exception e) {
			throw new ApplicationException("生成Model代码失败~!",e);
		}
		
		//  2.1生成Example工程标准的ModelQuery代码
		tmplDir = tmplDirBase + "/example/model";
		fullOutFile = outputDirBase + "/example"+md+"/model/"+entityName+"Query.java";
		try {
			FreemarkCodeGenUtil.toCode(tmplDir, "ModelQuery.java", root, fullOutFile);
		} catch (Exception e) {
			throw new ApplicationException("生成Model代码失败~!",e);
		}
		
		// 3.生成Example工程标准的ORO映射文件
		tmplDir = tmplDirBase + "/example/model";
		fullOutFile = outputDirBase + "/example"+md+"/model/"+entityName+".ftl.sql.xml";
		try {
			FreemarkCodeGenUtil.toCode(tmplDir, "Model.ftl.sql.xml", root, fullOutFile);
		} catch (Exception e) {
			throw new ApplicationException("生成SQL文件失败~!",e);
		}
		
		// 4.生成Service接口文件
		tmplDir = tmplDirBase + "/example/service";
		fullOutFile = outputDirBase + "/example"+md+"/service/"+entityName+"Service.java";
		try {
			FreemarkCodeGenUtil.toCode(tmplDir, "Service.java", root, fullOutFile);
		} catch (Exception e) {
			throw new ApplicationException("生成Service接口代码失败~!",e);
		}
		
		// 5.生成ServiceImpl文件
		tmplDir = tmplDirBase + "/example/service/impl";
		fullOutFile = outputDirBase + "/example"+md+"/service/impl/"+entityName+"ServiceImpl.java";
		try {
			FreemarkCodeGenUtil.toCode(tmplDir, "ServiceImpl.java", root, fullOutFile);
		} catch (Exception e) {
			throw new ApplicationException("生成ServiceImpl代码失败~!",e);
		}
		
		// 6.生成UI页面-list页
		tmplDir = tmplDirBase + "/example/webapp/single";
		fullOutFile = outputDirBase + "/example/webapp/single/index.jsp";
		try {
			FreemarkCodeGenUtil.toCode(tmplDir, "index.jsp", root, fullOutFile);
		} catch (Exception e) {
			throw new ApplicationException("生成UI页面-index.jsp代码失败~!",e);
		}
		
		// 7.生成UI页面-编辑页
		tmplDir = tmplDirBase + "/example/webapp/single";
		fullOutFile = outputDirBase + "/example/webapp/single/edit.jsp";
		try {
			FreemarkCodeGenUtil.toCode(tmplDir, "edit.jsp", root, fullOutFile);
		} catch (Exception e) {
			throw new ApplicationException("生成UI页面-edit.jsp代码失败~!",e);
		}
		
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 子表页面 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		/*
		TableSubQuery qr = new TableSubQuery();
		qr.setMainTableId(tableId);
		List<TableSub> ls = db.queryForList("queryForPage", TableSub.class, qr);
		if(ls == null || ls.size() ==0 )throw new ApplicationException("没有找到子表定义");
		
		for(TableSub t: ls) { // 加载子表的列定义
			List<Column> tmpList = db.queryForList("queryForPage", Column.class, query);
			t.setColumnList(tmpList);
		}
		root.put("subTableList", ls);
		
		tmplDir = tmplDirBase + "/example/webapp/main_sub";
		fullOutFile = outputDirBase + "/example/webapp/main_sub/index.jsp";
		try {
			FreemarkCodeGenUtil.toCode(tmplDir, "index.jsp", root, fullOutFile);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("生成UI页面-index_main_sub.jsp代码失败~!",e);
		}*/
		
		File rs = new File(outputDirBase+"/example");
		System.out.println(" --- 代码生成成功~!");
		System.out.println(" --- 代码路径: "+ rs);
		return rs.getAbsolutePath();
	}
}
