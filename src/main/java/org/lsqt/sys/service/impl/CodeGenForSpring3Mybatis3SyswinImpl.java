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
 * 思源系统，代码生成器实现
 * @author Administrator
 *
 */
@Component
public class CodeGenForSpring3Mybatis3SyswinImpl {
	@Inject private Db db;
	
	/**
	 * 
	 * @param tableId
	 * @param groupId
	 * @param modules
	 * @param entityName
	 * @return 返回代码生成root目录
	 */
	public String codegenForSingle(Long tableId,String groupId,String modules,String entityName) {
		System.out.println(" --- 正在生成代码~!");
		Table table = db.getById(Table.class, tableId);
		
		// 加载表元信息
		ColumnQuery query = new ColumnQuery();
		query.setTableId(tableId);
		List<Column> columnList = db.queryForList("queryForPage", Column.class, query);
		
		String clazzFirstLower = entityName.substring(0, 1).toLowerCase().concat(entityName.substring(1,entityName.length()));
		Map<String,Object> root = new HashMap<String,Object>();
		root.put("groupId", groupId);
		root.put("modules", modules);
		if(modules.indexOf(".")!=-1){
			String realModules = modules.substring(modules.lastIndexOf(".")+1,modules.length());
			root.put("realModules", realModules);
		}else{
			root.put("realModules", modules);
		}
		
		if (StringUtil.isNotBlank(modules)) {
			root.put("pkg", groupId.concat(".").concat(modules));
		} else {
			root.put("pkg", groupId);
		}
		
		root.put("tableName", table.getTableName());
		root.put("model", clazzFirstLower); // 小写模型类
		root.put("Model", entityName); // 大写模类名
		root.put("comment", table.getComment());// (主)表注释
		root.put("columnList", columnList);
		
		
		String tmplDirBase   = PathUtil.getAppRootDir()+File.separator+"src/main/resources/template/codegen";
		String outputDirBase = PathUtil.getAppRootDir()+File.separator+"src/main/resources/template/codegen-output";
		
		
		final String SPRING3_MYBATIS3="/spring3_mybatis3_syswin";
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
		
		File rs = new File(outputDirBase+SPRING3_MYBATIS3);
		System.out.println(" --- 代码生成成功~!");
		System.out.println(" --- 代码路径: "+ rs);
		return rs.getAbsolutePath();
	}
}
