package org.lsqt.components.mvc;

/**
 * WEB缓存处理，如果有多个缓存处理实现，请以宏的形式实现此接口
 * @author mm
 *
 */
public interface WebCacheHandler {
	/**
	 * WEB缓存处理
	 * @param context
	 * @return
	 * @throws Exception
	 */
	Object handle(Object context) throws Exception ;

	/**
	 * 处理器是否启用
	 * @return
	 */
	boolean isEnable() ;
	
	/**
	 * 
	 * @param key
	 */
	void setKey(String key);
}

