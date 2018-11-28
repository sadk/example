package org.lsqt.components.log;

import java.lang.reflect.Method;
import java.util.List;

@SuppressWarnings("rawtypes")
public final class LogFactory {
	
	private LogFactory(){
		
	}
	
	public static Log getLog(Class clazz){
		
		LoggerWrapper proxy=new LoggerWrapper(clazz);
		
		return proxy;
	}
	
	final static class LoggerWrapper implements Log{
		private Class clazz;
		public LoggerWrapper(){}
		
		public LoggerWrapper(Class clazz){
			this.clazz=clazz;
			
			prepareLoggerImpl();
		}
		
		private  List<Log> list;
		
		private void prepareLoggerImpl(){
			this.list=Configuration.getLogs(clazz);
			if(this.list!=null && list.size()>0){
				for(Log e: list){
					if(e instanceof DbWriter){
						DbWriter write=(DbWriter)e;
						try {
							Method m = DbWriter.class.getMethod("prepareSqlExecutor");
							m.invoke(write);
						} catch (Exception e1) {
							e1.printStackTrace();
						} 
					}
				}
			}
		}
		
		public void error(String msg) {
			if(list!=null){
				for(Log e: list){
					e.error(msg);
				}
			}
		}

		public void error(String msg, Object... params) {
			if(list!=null){
				for(Log e: list){
					e.error(msg,params);
				}
			}
		}

		public void error(String msg, Throwable t) {
			if(list!=null){
				for(Log e: list){
					e.error(msg,t);
				}
			}
		}

		public void error(String msg, Throwable t, Object... params) {
			if(list!=null){
				for(Log e: list){
					e.error(msg,t,params);
				}
			}
		}

		public void info(String msg) {
			if(list!=null){
				for(Log e: list){
					e.info(msg);
				}
			}
		}

		public void info(String msg, Object... params) {
			if(list!=null){
				for(Log e: list){
					e.info(msg,params);
				}
			}
		}

		public void debug(String msg) {
			if(list!=null){
				for(Log e: list){
					e.debug(msg);
				}
			}
		}

		public void debug(String msg, Object... params) {
			if(list!=null){
				for(Log e: list){
					e.debug(msg,params);
				}
			}
		}

		public void debug(String msg, Throwable t) {
			if(list!=null){
				for(Log e: list){
					e.debug(msg,t);
				}
			}
		}

		public void debug(String msg, Throwable t, Object... params) {
			if(list!=null){
				for(Log e: list){
					e.debug(msg,t,params);
				}
			}
		}

		public void warn(String msg) {
			if(list!=null){
				for(Log e: list){
					e.warn(msg);
				}
			}
		}

		public void warn(String msg, Object... params) {
			if(list!=null){
				for(Log e: list){
					e.warn(msg,params);
				}
			}
		}

		public void warn(String msg, Throwable t) {
			if(list!=null){
				for(Log e: list){
					e.warn(msg,t);
				}
			}
		}

		public void warn(String msg, Throwable t, Object... params) {
			if(list!=null){
				for(Log e: list){
					e.warn(msg,t,params);
				}
			}
		}
	}
	
	

	

	
}
