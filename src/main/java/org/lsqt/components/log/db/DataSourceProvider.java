package org.lsqt.components.log.db;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.lsqt.components.log.util.Loader;


/**
 * 数据源连接池供给器,优先使用Druid连接池，如果都没有，使用原生Connection
 * @author Sky
 *
 */
@SuppressWarnings("rawtypes")
public class DataSourceProvider {
	
	private static ThreadLocal<DataSource> threadLocalSource=new ThreadLocal<DataSource>();
	
	private static final String DATA_SOURCE_DRUID="com.alibaba.druid.pool.DruidDataSourceFactory" ;
	private static final String DATA_SOURCE_C3P0="com.mchange.v2.c3p0.ComboPooledDataSource" ;
	private static final String DATA_SOURCE_DBCP="org.apache.commons.dbcp.BasicDataSource";
	
	
	private static final String DATA_SOURCE_DRUID_KEY="druid";
	private static final String DATA_SOURCE_C3P0_KEY="c3p0";
	private static final String DATA_SOURCE_DBCP_KEY="dbcp";
	
	

	
	
	private static Map<String,DataSource> dbInstances=new ConcurrentHashMap<String,DataSource>();
	@SuppressWarnings("unchecked")
	public static DataSource attemptGetDRUID(String driverClassName,String url,String username,String password){
		Object instance=null;
		if(!dbInstances.isEmpty()  && dbInstances.containsKey(DATA_SOURCE_DRUID_KEY)){
			return dbInstances.get(DATA_SOURCE_DRUID_KEY);
		}
		
		Class clazz=null;
		try {
			clazz=Loader.classForName(DATA_SOURCE_DRUID);
		} catch (Exception e) {
			System.out.print("warn,try get druid datasource failed!");
		}
		
		if(clazz!=null){
			try {
				//初使化连接信息
				Properties p=new Properties();
				p.put("driverClassName", driverClassName);
				p.put("url", url);
				p.put("username", username);
				p.put("password" , password);
				
				p.put("initialSize", "2");
				p.put("minIdle", "1");
				p.put("maxActive", "20");
				p.put("testWhileIdle", "true");
				p.put("testOnBorrow", "true");
				p.put("testWhileIdle", "true");
				p.put("validationQuery", "select 1 from dual");
				
				Method m =  clazz.getMethod("createDataSource",Properties.class);
				
				instance = m.invoke(instance, p);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(instance!=null){
			return (DataSource)instance;
		}
		
		return null;
	}
	
	public static DataSource attemptGetC3P0(){
		
		return null;
	}
	
	public static DataSource attemptGetDBCP(){
		final String driverClassName="driverClassName";
		final String url="url";
		final String username="username";
		final String password="password";
		return null;
	}
	
	
	public static DataSource attemptGetNone(){
		
		return null;
	}
	
	public static DataSource getCatchedDataSource(String driverClassName,String url,String username,String password){
		DataSource ds=null;
		
		//优先加载cruid数据源,其次c3p0,dbcp,原生drivermanager
		ds=attemptGetDRUID(driverClassName,url,username,password);
		if(ds!=null)return ds;
		
		ds=attemptGetC3P0();
		if(ds!=null)return ds;
		
		
		ds=attemptGetDBCP();
		if(ds!=null)return ds;
		
		return ds;
	
	}
	
	
}
