package org.lsqt.sys.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.lsqt.sys.model.Column;
import org.lsqt.sys.model.Table;
import org.lsqt.sys.model.TableQuery;
import org.lsqt.sys.service.TableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TableServiceImpl implements TableService{
	static final Logger log = LoggerFactory.getLogger(TableServiceImpl.class);
	
	@Inject private Db db;
	
	public Page<Table>  queryForPage(TableQuery query) {
		if(query.getIsQueryDb()) {
			if (StringUtil.isNotBlank(query.getDataSourceCode())) {
				
				DataSourceFactory dbfactory = new DataSourceFactory();
				dbfactory.setBaseDb(db);
				javax.sql.DataSource ds = dbfactory.getDataSouce(query.getDataSourceCode());
				if (ds == null) {
					return db.getEmptyPage();
				}
				
				Connection con = db.getCurrentConnection();
				try {
					Connection switchConn = ds.getConnection();
					log.debug(" --- >>>>>>>>>>>> switchConnection prepare !!! (Thead-id:"+Thread.currentThread().getId()+" ,con:"+switchConn+")");
					try{
						db.setCurrentConnection(switchConn);
						return db.queryForPage("queryPageFromDb", query.getPageIndex(), query.getPageSize(), Table.class, query);
					}finally {
						log.debug(" --- >>>>>>>>>>>> switchConnection close !!! (Thead-id:"+Thread.currentThread().getId()+" ,con:"+switchConn+")");
						switchConn.close();
					}
					
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					db.setCurrentConnection(con);
				}
			}
			
			return db.queryForPage("queryPageFromDb", query.getPageIndex(), query.getPageSize(), Table.class, query);
		}else {
			return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Table.class, query); // 查sys_table定义的表元信息
		}
	}
	
	public List<Table> getAll(){
		  return db.queryForList("queryForPage", Table.class); 
	}
	
	public Table saveOrUpdate(Table model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		int cnt = 0;
		
		for(Long id : ids){
			Table table = db.getById(Table.class, id);
			if(table!=null) {
				// 删除表对应的“字段定义”
				int temp =db.executeUpdate("delete from "+db.getFullTable(Column.class)+" where db_name=? and definition_name=? and version=?", table.getDbName(),table.getTableName(),table.getVersion());
				cnt += temp;
			}
		}
		
		cnt += db.deleteById(Table.class, Arrays.asList(ids).toArray()); // 删除“表定义”
		
		return cnt;
	}

	public List<Table> getAllFormDb(String dbName){
		TableQuery query = new TableQuery();
		query.setDbName(dbName);
		return db.queryForList("queryPageFromDb", Table.class, query);
	}
	

	List<Table> getTableList(String dataSourceCode,String dbName, String tableName) {
		List<Table> result = new ArrayList<>();
		
		TableQuery query = new TableQuery();
		query.setDbName(dbName);
		query.setTableName(tableName);
		query.setIsQueryDb(true); // 从数据库里即时查表的元信息
		
		
		Connection con = db.getCurrentConnection();
		try {
			DataSourceFactory factory = new DataSourceFactory();
			factory.setBaseDb(db);

			javax.sql.DataSource ds = factory.getDataSouce(dataSourceCode);
			db.setCurrentConnection(ds.getConnection());
			
			try {
				result = db.queryForList("queryPageFromDb", Table.class, query); 
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
	
	public void impTable(String dataSourceCode,String dbName, String tableName) {
		/*
		TableQuery query = new TableQuery();
		query.setDbName(dbName);
		query.setTableName(tableName);
		query.setIsQueryDb(true); // 从数据库里即时查表的元信息
		List<Table> tableList = db.queryForList("queryPageFromDb", Table.class, query); 
		*/
		List<Table> tableList = getTableList(dataSourceCode, dbName, tableName);
		
		String version = "0.0.1."+System.currentTimeMillis();
		if(tableList!=null) {
			for(Table e: tableList) {
				e.setId(null);
				if(StringUtil.isBlank(e.getDataSourceCode(),dataSourceCode)) {
					e.setDataSourceCode("localhost");
				}else {
					e.setDataSourceCode(dataSourceCode);
				}
				
				if(StringUtil.isBlank(e.getAppCode())) {
					e.setAppCode(Application.APP_CODE_DEFAULT);
				}
				e.setVersion(version);
				e.setModelName(toModelName(tableName));
				e.setRemark(String.format("导入表定义'%s.%s'到管理系统",dbName,tableName));
				
				db.save(e);
			}
		}
	}
	
	public void impSyn(Long id) {
		Table table = db.getById(Table.class, id);
		if(table!=null) {
			TableQuery query = new TableQuery();
			query.setDbName(table.getDbName());
			query.setTableName(table.getTableName());

			List<Table> tableList = db.queryForList("queryPageFromDb", Table.class, query);
			if(tableList!=null && tableList.size()>0) {
				for(Table e: tableList) {
					e.setId(id);
					e.setDataSourceCode(table.getDataSourceCode());
					e.setAppCode(table.getAppCode());
					e.setVersion(table.getVersion());
					e.setModelName(table.getModelName());
					e.setRemark(String.format("同步已有的表定义'%s.%s'到管理系统,版本:%s",table.getDbName(),table.getTableName(),table.getVersion()));
					
					e.setGid(table.getGid());
					e.setCreateTime(table.getCreateTime());
					e.setUpdateTime(new java.util.Date());
					db.update(e);
				}
			}
		}
		
	}

	private static final Pattern pattern = Pattern.compile("_[a-z]{1}");
	/**
	 * 下划线转驼峰
	 * @param columnName
	 * @return
	 */
	private static String toModelName(String tableName) {
		if(StringUtil.isBlank(tableName)){
			return tableName;
		}
		Matcher m = pattern.matcher(tableName);
		while(m.find()) {
			String f = m.group();
			tableName = tableName.replace(f, f.replace("_", "").toUpperCase());
		}
		tableName = tableName.substring(0, 1).toUpperCase().concat(tableName.substring(1,tableName.length()));
		return tableName;
	}
	
	public String toORO(String dbName, String tableName, int tmplType, int tmplResolveType) {
		
		return null;
	}


}
