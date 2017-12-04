package org.lsqt.components.log;

import java.util.Date;
import java.util.Map;

import javax.sql.DataSource;

import org.lsqt.components.log.db.DataSourceProvider;
import org.lsqt.components.log.db.SqlExecutor;


/**
 * 
 * @author Sky
 *
 */
@SuppressWarnings("rawtypes")
public class DbWriter implements Log{
	
	private String className;
	private static final String LOG_WRITER_DATABASE_URL="log.writer.database.url";
	private static final String LOG_WRITER_DATABASE_DRIVER="log.writer.database.driver";
	private static final String LOG_WRITER_DATABASE_USER="log.writer.database.user";
	private static final String LOG_WRITER_DATABASE_PASSWORD="log.writer.database.password";
	private static final String LOG_WRITER_DATABASE_SQL="log.writer.database.sql";
	
	
	private static final String LOG_WRITER_DATABASE_LEVEL="log.writer.database.level";
	private static final String LEVEL_DEBUG="debug";
	private static final String LEVEL_WARN="warn";
	private static final String LEVEL_INFO="info";
	private static final String LEVEL_ERROR="error";
	//private static final String LEVEL_ALL="all";
	
	
	private SqlExecutor sqlExecutor;
	
	private String insertSql;
	@SuppressWarnings("unused")
	private DbWriter (){}
	public DbWriter(Class clazz){
		this.className=clazz.getName();
	}
	
	public void prepareSqlExecutor(){
		Map<String,String> map=Configuration.getConfigMap();
		
		String driverClassName=map.get(LOG_WRITER_DATABASE_DRIVER);
		String url=map.get(LOG_WRITER_DATABASE_URL);
		String username=map.get(LOG_WRITER_DATABASE_USER);
		String password=map.get(LOG_WRITER_DATABASE_PASSWORD );
		
		DataSource ds=DataSourceProvider.getCatchedDataSource(driverClassName,url,username,password);
		this.sqlExecutor = new SqlExecutor(ds);
		
		this.insertSql=map.get(LOG_WRITER_DATABASE_SQL);
	}
	
	//判断哪些日志级别启用
	private static boolean assertEnabled(String level){
		Map<String,String> configMap= Configuration.getConfigMap();
		 String value=configMap.get(LOG_WRITER_DATABASE_LEVEL);
		 if(value!=null){
			 for(String e:value.split(",")){
				 if(level.equalsIgnoreCase(e)){
					 return true;
				 }
			 }
		 }
		 return false;
	}
	
	public boolean isDebugEnabled() {
		return assertEnabled(LEVEL_DEBUG);
	}

	public boolean isInfoEnabled() {
		return assertEnabled(LEVEL_INFO);
	}

	public boolean isWarnEnabled() {
		return assertEnabled(LEVEL_WARN);
	}

	public boolean isErrorEnabled() {
		return assertEnabled(LEVEL_ERROR);
	}
	

	private  Object [] prepareParams(String level,String msg,Throwable e, Object... params){
		
		
		StringBuilder pNames= new StringBuilder();
		StringBuilder pValues= new StringBuilder();
		
		if(params!=null && params.length>0){
			
			for(int i=0;i<params.length;i++){
				if(params[i]==null){
					pValues.append("null");
					pNames.append("null");
				}else{
					pValues.append(params[i]);
					pNames.append(params[i].getClass().getName());
				}
				
				if(i!=params.length-1){
					pNames.append(",");
					pValues.append(",");
				}
			}
		}
		
		String pv=pValues.toString();
		if("".equals(pv)) pv=null;
		 
		String pn=pNames.toString();
		if("".equals(pn)) pn=null;
		
		Object [] inserts={level,className,msg,pv,pn,new Date()};
		return inserts;
	}
	
		
	public void error(String msg) {
		if(!isErrorEnabled()) return ;
		
		sqlExecutor.executeUpdate(
				this.insertSql,
				prepareParams(LEVEL_ERROR, msg, null));
	}

	public void error(String msg, Object... params) {
		if(!isErrorEnabled()) return ;
		
		sqlExecutor.executeUpdate(
				this.insertSql,
				prepareParams(LEVEL_ERROR, msg, null,params));
	}

	public void error(String msg, Throwable e) {
		if(!isErrorEnabled()) return ;
		sqlExecutor.executeUpdate(
				this.insertSql,
				prepareParams(LEVEL_ERROR, msg, e));
	}

	public void error(String msg, Throwable e, Object... params) {
		if(!isErrorEnabled()) return ;
		
		sqlExecutor.executeUpdate(
				this.insertSql,
				prepareParams(LEVEL_ERROR, msg, e,params));
	}

	public void info(String msg) {
		if(!isInfoEnabled()) return ;
		
		sqlExecutor.executeUpdate(
				this.insertSql,
				prepareParams(LEVEL_INFO, msg,null));
	}

	public void info(String msg, Object... params) {
		if(!isInfoEnabled()) return ;
		
		sqlExecutor.executeUpdate(
				this.insertSql,
				prepareParams(LEVEL_INFO, msg,null,params));
	}

	public void debug(String msg) {
		if(!isDebugEnabled()) return ;
		
		sqlExecutor.executeUpdate(
				this.insertSql,
				prepareParams(LEVEL_DEBUG, msg,null));
	}

	public void debug(String msg, Object... params) {
		if(!isDebugEnabled()) return ;
		
		sqlExecutor.executeUpdate(
				this.insertSql,
				prepareParams(LEVEL_DEBUG, msg,null,params));
	}

	public void debug(String msg, Throwable e) {
		if(!isDebugEnabled()) return ;
		
		sqlExecutor.executeUpdate(
				this.insertSql,
				prepareParams(LEVEL_DEBUG, msg,e));
	}

	public void debug(String msg, Throwable e, Object... params) {
		if(!isDebugEnabled()) return ;
		
		sqlExecutor.executeUpdate(
				this.insertSql,
				prepareParams(LEVEL_DEBUG, msg,e,params));
	}

	public void warn(String msg) {
		if(!isWarnEnabled()) return ;
		
		sqlExecutor.executeUpdate(
				this.insertSql,
				prepareParams(LEVEL_WARN,msg,null));
	}

	public void warn(String msg, Object... params) {
		if(!isWarnEnabled()) return ;
		
		sqlExecutor.executeUpdate(
				this.insertSql,
				prepareParams(LEVEL_WARN,msg,null,params));
	}

	public void warn(String msg, Throwable e) {
		if(!isWarnEnabled()) return ;
		
		sqlExecutor.executeUpdate(
				this.insertSql,
				prepareParams(LEVEL_WARN,msg,e));
	}

	public void warn(String msg, Throwable e, Object... params) {
		if(!isWarnEnabled()) return ;
		
		sqlExecutor.executeUpdate(
				this.insertSql,
				prepareParams(LEVEL_WARN,msg,e,params));
	}

}

