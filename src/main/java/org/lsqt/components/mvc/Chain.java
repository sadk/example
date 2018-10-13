package org.lsqt.components.mvc;

/**
 * 请求链 (isNext是否执行下一步， getOrder 链的排序号, isEnable 是否启用)
 * @author mm
 *
 */
public interface Chain extends Order{
	/** 过滤器链初使态  **/
	int STATE_NO_WORK = -1;
	
	/** 过滤器链整链正在启动 */
	int STATE_IS_STARTING = 0;

	/** 静态资源或脱离容器的URI **/
	int STATE_IS_STATIC_OR_ESCAPE_ACCESS = 1;
	
	/** 已重定向过**/
	int STATE_IS_REDIRECTED = 2;
	
	/** 链继续往下执行 */
	int STATE_IS_CONTINUE_TO_EXECUTE = 3;
	
	/** 执行异常 **/
	int STATE_EXE_EXCEPTION = 4;
	
	
	/**
	 * 执行器链处理器是否启用
	 * 
	 * @return
	 */
	boolean isEnable();

	/**
	 * 执行器链的执行顺序
	 */
	int getOrder();

	/**
	 * 当前链处理器的执行状态
	 * 
	 * @return
	 */
	int getState();
	
	/**
	 * 当前请求链的处理
	 * @return 
	 */
	Object handle() throws Exception;
	
}

