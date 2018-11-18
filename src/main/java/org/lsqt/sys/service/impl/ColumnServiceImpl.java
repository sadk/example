package org.lsqt.sys.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.db.support.ColumnUtil;
import org.lsqt.components.db.support.MySQLTypeMapping;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.lsqt.sys.model.Column;
import org.lsqt.sys.model.ColumnQuery;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.sys.model.Table;
import org.lsqt.sys.service.ColumnService;

@Service
public class ColumnServiceImpl implements ColumnService{
	
	@Inject private Db db;
	
	public Column getById(Long id) {
		return db.getById(Column.class, id);
	}
	
	public Page<Column>  queryForPage(ColumnQuery query) {
 
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Column.class, query); // 查sys_table定义的表元信息
	}
	
	public List<Column> getAll(){
		  return db.queryForList("queryForPage", Column.class); 
	}
	
	public Column saveOrUpdate(Column model) {
		/*
		if (model.getId() != null) {
			if(model.getColumnCodegenType()!=null) {
				if (model.getColumnCodegenType() == Column.COLUMN_CODEGEN_TYPE_选择器) {
					if (Column.SELECTOR_DATA_FROM_TYPE_URL_页面.equals(model.getSelectorDataFromType())) {
						db.update(model, "selectorDataFrom","selectorDataFromType");
						
					} else if(Column.SELECTOR_DATA_FROM_TYPE_URL_返回JSON.equals(model.getSelectorDataFromType())
							|| Column.SELECTOR_DATA_FROM_TYPE_URL_返回XML.equals(model.getSelectorDataFromType())
							|| Column.SELECTOR_DATA_FROM_TYPE_代码片断_JAVASCRIPT或数组.equals(model.getSelectorDataFromType())) {
						
						if (StringUtil.isBlank(model.getSelectorMultilSelect())){//默认可多选
							model.setSelectorMultilSelect(Column.YES+"");
						}
						db.update(model, "selectorDataFrom","selectorMultilSelect","selectorTextCols","selectorValueCols","selectorDataFromType");
						
						
					} else if(Column.SELECTOR_DATA_FROM_TYPE_SQL.equals(model.getSelectorDataFromType())) {
						if (StringUtil.isBlank(model.getSelectorMultilSelect())){//默认可多选
							model.setSelectorMultilSelect(Column.YES+"");
						}
						if (StringUtil.isNotBlank(model.getSelectorValueCols())) {
							// 值列只能是一个,如果前端不小心填了多列，以第一个为准
							if(model.getSelectorValueCols().indexOf(",")!=-1) {
								String tmp = model.getSelectorValueCols().split(",")[0];
								model.setSelectorValueCols(tmp);
							}
						}
						db.update(model, "selectorDataFrom","selectorMultilSelect","selectorTextCols","selectorValueCols","selectorDataFromType");
						
					}
				}
			}
			return model;
		} else {
			db.save(model);
			return model;
		}*/
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(Column.class, Arrays.asList(ids).toArray());
	}

	public List<Column> getAllFormDb(ColumnQuery query){
		return db.queryForList("getAllFromDb", Column.class, query); // 从数据库里即时查表的元信息
	}

	List<Column> getColumnList(Long tableId) {
		List<Column> result = new ArrayList<Column>();
		
		Table table = db.getById(Table.class, tableId);
		
		Connection con = db.getCurrentConnection();
		try {
			DataSourceFactory factory = new DataSourceFactory();
			factory.setBaseDb(db);

			javax.sql.DataSource ds = factory.getDataSouce(table.getDataSourceCode());
			db.setCurrentConnection(ds.getConnection());
			
			try {
				result = db.queryForList("queryColumnsByTable", Column.class, table.getDbName(),table.getTableName());
			} finally {
				db.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			db.setCurrentConnection(con);
		}
		return result;
	}
	
	@Override
	public void impColumsByTable(Long tableId) {
		Table table = db.getById(Table.class, tableId);
		/*
		List<Column> list = db.queryForList("queryColumnsByTable", Column.class, table.getDbName(),table.getTableName());
		*/
		
		List<Column> list = getColumnList(tableId);
		if(list == null || list.isEmpty()) {
			return ;
		}
		
		db.executeUpdate("delete from "+db.getFullTable(Column.class)+" where definition_id=?", tableId);
		
		// 补全字段 java_type
		// 补全字段 oro_column_type  (ORMapping映射的字段类型)：gid=全局唯一码 updateTime=更新时间 createTime=创建时间
		// 补全字段 columnCodegenType 字段的代码生成器类型 1=选择器 2=字典 3=外键 4=text 5=long 6=double 7=date 8=file
		
		// 补全外键 、选择器、字典信息
		for(Column e: list) {
			e.setDataType(Column.DATA_TYPE_REPORT_SHOW);
			e.setDefinitionId(tableId);
			e.setDefinitionName(table.getTableName());
			e.setDbName(table.getDbName());
			e.setVersion(table.getVersion());
			e.setPropertyName(ColumnUtil.toPropertyName(e.getName()));
			e.setOptLog("从DB导入字段信息");
			
			if(StringUtil.isBlank(e.getAppCode())){
				e.setAppCode(Application.APP_CODE_DEFAULT);
			}
			
			resolveJavaType(e);
			resolveOroColumnType(e);
			resolveColumnCodegenType(e);
			resolveSearchType(e);
			db.save(e);
		}
		
	}
	
	private void resolveSearchType(Column e) {
		if(Column.YES == e.getPrimaryKey()) {
			e.setSearchType(Column.NO);
			return ;
		}
		
		// 字符默认可查询
		if(e.getJavaType() == Column.JAVA_TYPE_JAVA_LANG_STRING) {
			if(e.getOroColumnType()!=Column.ORO_COLUMN_TYPE_GID) {
				e.setSearchType(Column.YES);
				return ;
			}
		}
		
		e.setSearchType(Column.NO);
	}
	
	private void resolveJavaType(Column e) {
		if(StringUtil.isNotBlank(e.getDbType())){
			e.setJavaType(MySQLTypeMapping.guessJavaType(e.getDbType()));
		}
	}
	
	private void resolveOroColumnType(Column e) {
		// 优先从注释里解析gid、createTime、updateTime
		if (StringUtil.isNotBlank(e.getComment())) {
			if(e.getComment().startsWith("gid")) {
				e.setOroColumnType(Column.ORO_COLUMN_TYPE_GID);
				return ;
			}
			else if(e.getComment().startsWith("createTime")) {
				e.setOroColumnType(Column.ORO_COLUMN_TYPE_CREATE_TIME);
				return ;
			}
			else if(e.getComment().startsWith("updateTime")) {
				e.setOroColumnType(Column.ORO_COLUMN_TYPE_UPDATE_TIME);
				return ;
			}
		}
		
		if("createTime".equals(e.getPropertyName())){
			e.setOroColumnType(Column.ORO_COLUMN_TYPE_CREATE_TIME);
			return ;
		}
		if("updateTime".equals(e.getPropertyName())) {
			e.setOroColumnType(Column.ORO_COLUMN_TYPE_UPDATE_TIME);
			return ;
		}
		if("gid".equals(e.getPropertyName())) {
			e.setOroColumnType(Column.ORO_COLUMN_TYPE_GID);
			return ;
		}
		
		// 解析普通与主键映射类型
		if(e.getPrimaryKey()!=null && e.getPrimaryKey() == Column.NO) {
			e.setOroColumnType(Column.ORO_COLUMN_TYPE_ORDINARY);
		}
		else if(e.getPrimaryKey()!=null && e.getPrimaryKey() == Column.YES) {
			e.setOroColumnType(Column.ORO_COLUMN_TYPE_PRIMARY);
		}
		
		// 解析不到，就视为常规字段
		else {
			e.setOroColumnType(Column.ORO_COLUMN_TYPE_ORDINARY);
		}
	}
	
	private void resolveColumnCodegenType(Column e) {
		if(StringUtil.isNotBlank(e.getDbType())) {
			
			Set<Entry<String,String>> set =MySQLTypeMapping.DB_JAVA_MAPPING.entrySet();
			for(Entry<String,String> m: set) {
				if(e.getDbType().equals(m.getKey())) {
					String javaType = m.getValue();
					if("java.lang.String".equals(javaType)) {
						e.setColumnCodegenType(Column.COLUMN_CODEGEN_TYPE_文本);
					}
					else if("java.lang.Integer".equals(javaType) || "java.lang.Long".equals(javaType)) {
						e.setColumnCodegenType(Column.COLUMN_CODEGEN_TYPE_数字整型);
					}
					else if("java.lang.Double".equals(javaType)|| "java.lang.Float".equals(javaType)){
						e.setColumnCodegenType(Column.COLUMN_CODEGEN_TYPE_数字精度型);
					}
					else if("java.util.Date".equals(javaType)) {
						e.setColumnCodegenType(Column.COLUMN_CODEGEN_TYPE_日期_超始框);
						e.setColumnCodegenFormat("yyyy-MM-dd HH:mm:ss");
					}
					else if("java.lang.byte[]".equals(javaType)) {
						e.setColumnCodegenType(Column.COLUMN_CODEGEN_TYPE_文件上传);
					}
					else {
						e.setColumnCodegenType(Column.COLUMN_CODEGEN_TYPE_未知);
					}
				}
			}
		}
		
	}
}



 

