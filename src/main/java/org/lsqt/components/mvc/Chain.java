package org.lsqt.components.mvc;

/**
 * 请求链 (isNext是否执行下一步， getOrder 链的排序号, isEnable 是否启用)
 * @author mm
 *
 */
public interface Chain extends Order{
	
	/** 过滤器链继续向下执行 */
	int STATE_DO_NEXT_CONTINUE = 1;

	/** 过滤器链执行中断,比如:已经redirected,dofilter(request,response)不在执行 **/
	int STATE_DO_NEXT_BREAK = 2;

	/** 过滤器链不允许执行,常用于初使状态  **/
	int STATE_DO_NEXT_NOT_ALLOW = 3;

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

