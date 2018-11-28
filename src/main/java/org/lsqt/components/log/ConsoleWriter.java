package org.lsqt.components.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class ConsoleWriter implements Log{
	private String className;
	
	public ConsoleWriter(Class clazz){
		this.className=clazz.getName();
	}
	private static final String FORMAT_MSG_PREFIX=" ---- " ;
	private static final String FORMAT_PARAM_PREFIX=" 参数: [";
	private static final String FORMAT_PARAM_END="]";
	
	private static final String FORMAT_PARAM_TYPE_PREFIX=" 参数类型: [";
	private static final String FORMAT_PARAM_TYPE_END="]";
	
	private static final String FORMAT_ERROR_PREFIX=" 错误: \"";
	private static final String FORMAT_ERROR_END="\"";
	
	private static final String LOG_WRITER_CONSOLE_LEVEL="log.writer.console.level";
	private static final String LEVEL_DEBUG="debug";
	private static final String LEVEL_WARN="warn";
	private static final String LEVEL_INFO="info";
	private static final String LEVEL_ERROR="error";
	//private static final String LEVEL_ALL="all";
	
	//判断哪些日志级别启用
	private static boolean assertEnabled(String level){
		Map<String,String> configMap= Configuration.getConfigMap();
		 String value=configMap.get(LOG_WRITER_CONSOLE_LEVEL);
		 if(value!=null){
			 for(String e:value.split(",")){
				 if(level.equalsIgnoreCase(e)){
					 return true;
				 }
			 }
		 }
		 return false;
	}
	
	private  String print(String level,String msg, Throwable e, Object... params){
		Date dt=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss S ");
		
		StringBuilder sb=new StringBuilder();
		
		sb.append(FORMAT_MSG_PREFIX);
		
		sb.append("[".concat(level.toUpperCase()).concat("] "));
		if(LEVEL_INFO.equalsIgnoreCase(level)){
			sb.append(" ");
		}
		sb.append(df.format(dt));
		
		/**/
		sb.append(className.concat("  ").concat(msg).concat("  "));
		
		if(params!=null && params.length>0){
			sb.append(FORMAT_PARAM_PREFIX);		//参数值
			for(int i=0;i<params.length;i++){  
				if(params[i]==null){
					sb.append("null");
				}else{
					sb.append(params[i]);
				}
				
				if(i!=params.length-1){
					sb.append(",");
				}
			}
			sb.append(FORMAT_PARAM_END);
			
			
			sb.append(FORMAT_PARAM_TYPE_PREFIX);		//参数类型
			for(int i=0;i<params.length;i++){  
				if(params[i]==null){
					sb.append("null");
				}else{
					sb.append(params[i].getClass().getSimpleName());
				}
				
				if(i!=params.length-1){
					sb.append(",");
				}
			}
			sb.append(FORMAT_PARAM_TYPE_END);
		}
		
		if(e!=null){
			sb.append(FORMAT_ERROR_PREFIX).append(e.getMessage()).append(FORMAT_ERROR_END);
			StackTraceElement [] ts= e.getStackTrace();
			if(ts!=null && ts.length>0){
				int line=ts[0].getLineNumber();
				sb.append(" ("+line+")");
			}
		}
		
		System.out.println(sb);
		if(e!=null)e.printStackTrace();
		return sb.toString();
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
	
	
	public void error(String msg) {
		if(!isErrorEnabled()) return ;
		print(LEVEL_ERROR,msg,null);
	}

	public void error(String msg, Object... params) {
		if(!isErrorEnabled()) return ;
		print(LEVEL_ERROR,msg, null, params);
		
	}

	public void error(String msg, Throwable e) {
		if(!isErrorEnabled()) return ;
		print(LEVEL_ERROR,msg,e);
	}

	public void error(String msg, Throwable e, Object... params) {
		if(!isErrorEnabled()) return ;
		print(LEVEL_ERROR,msg,e,params);
	}

	public void info(String msg) {
		if(!isInfoEnabled()) return ;
		
		print(LEVEL_INFO,msg,null);
	}

	public void info(String msg, Object... params) {
		if(!isInfoEnabled()) return ;
		
		print(LEVEL_INFO,msg,null,params);
	}

	public void debug(String msg) {
		if(!isDebugEnabled()) return ;
		
		print(LEVEL_DEBUG,msg,null);
	}

	public void debug(String msg, Object... params) {
		if(!isDebugEnabled()) return ;
		
		print(LEVEL_DEBUG,msg,null,params);
	}

	public void debug(String msg, Throwable e) {
		if(!isDebugEnabled()) return ;
		
		print(LEVEL_DEBUG,msg,e);
	}

	public void debug(String msg, Throwable e, Object... params) {
		if(!isDebugEnabled()) return ;
		
		print(LEVEL_DEBUG,msg,e,params);
	}

	public void warn(String msg) {
		if(!isWarnEnabled()) return ;
		
		print(LEVEL_WARN.concat(" "),msg,null);
	}

	public void warn(String msg, Object... params) {
		if(!isWarnEnabled()) return ;
		
		print(LEVEL_WARN.concat(" "),msg,null,params);
	}

	public void warn(String msg, Throwable e) {
		if(!isWarnEnabled()) return ;
		
		print(LEVEL_WARN.concat(" "),msg,e);
	}

	public void warn(String msg, Throwable e, Object... params) {
		if(!isWarnEnabled()) return ;
		
		print(LEVEL_WARN.concat(" "),msg,e,params);
	}

}
