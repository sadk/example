package org.lsqt.components.log;

public interface Log {

	    void error(String msg);
	    void error(String msg,Object ... params);
	    void error(String msg, Throwable e);
	    void error(String msg,Throwable e, Object ... params);
	    

	    void info(String msg);
	    void info(String msg,Object ... params);
	    
	    
	    void debug(String msg);
	    void debug(String msg,Object ... params);
	    void debug(String msg, Throwable e);
	    void debug(String msg, Throwable e,Object ... params);
	   

	    void warn(String msg);
	    void warn(String msg,Object ... params);
	    void warn(String msg, Throwable e);
	    void warn(String msg, Throwable e,Object ... params);
}
