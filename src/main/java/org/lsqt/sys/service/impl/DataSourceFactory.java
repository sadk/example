package org.lsqt.sys.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.lsqt.components.db.Db;
import org.lsqt.components.db.DbException;
import org.lsqt.components.db.Plan;
import org.lsqt.components.db.orm.SqlStatementBuilder;
import org.lsqt.components.db.orm.ftl.SqlStatementFactory;
import org.lsqt.sys.model.DataSource;
import org.lsqt.sys.model.DataSourceQuery;
import org.lsqt.sys.model.Property;
import org.lsqt.sys.model.PropertyQuery;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.druid.pool.DruidDataSourceFactory;



public class DataSourceFactory{
	static Map<String,javax.sql.DataSource> DS_HOLDER = new ConcurrentHashMap<>();
	static Map<String,Db> DB_HOLDER = new ConcurrentHashMap<>();
	// 基础数据库
	private Db baseDb;
	
	public Db getBaseDb() {
		return baseDb;
	}

	public void setBaseDb(Db baseDb) {
		this.baseDb = baseDb;
	}
	
	/**
	 * 获取真实的数据源
	 * @param code
	 * @return
	 */
	public javax.sql.DataSource getDataSouce(String code) {
		if(DS_HOLDER.containsKey(code)) {
			return DS_HOLDER.get(code);
		}
		
		DataSourceQuery query = new DataSourceQuery();
		query.setCode(code);
		
		//获取数据源连接字符串
		DataSource ds = baseDb.queryForObject("queryForPage", DataSource.class, query);
		
		// 获取数据源其它连接属性
		PropertyQuery pQuery = new PropertyQuery();
		List<Property> list = baseDb.queryForList("queryForPage", Property.class, pQuery);
		
		Map<String,Object> props = new HashMap<>();
		for(Property p : list) {
			props.put(p.getName(), p.getValue());
		}
		
		
		com.alibaba.druid.pool.DruidDataSource druid = new com.alibaba.druid.pool.DruidDataSource();
		druid.setUrl(ds.getUrl());
		druid.setUsername(ds.getLoginName());
		druid.setPassword(ds.getLoginPassword());
		druid.setDriverClassName(ds.getDriverClassName());
		try {
			DruidDataSourceFactory.config(druid, props);

			props.remove("init"); // 下一句代码主动实例化，不实例化两次!!!
			druid.init();
			
			DS_HOLDER.put(code, druid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return DS_HOLDER.get(code);
	}
	
	public static void main(String[] args) throws SQLException {
		ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
		
		
		Db db = context.getBean(Db.class);
		Connection con = db.getCurrentConnection();
		try {
			DataSourceFactory factory = new DataSourceFactory();
			factory.setBaseDb(db);
			
			javax.sql.DataSource ds = factory.getDataSouce("syswin");
			db.setCurrentConnection(ds.getConnection());
		} catch (Exception ex) {

		} finally {
			db.setCurrentConnection(con);
		}
		
		
		
		
		
		//tmp.setCurrentConnection(fac.getDataSouce("syswin").getConnection());
		//tmp.setDataSource(fac.getDataSouce("syswin"));
		//List list = tmp.executeQuery("SHOW TABLES");
		//System.out.println(list);
	}
}
