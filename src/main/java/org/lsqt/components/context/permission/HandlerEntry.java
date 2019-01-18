package org.lsqt.components.context.permission;

import org.lsqt.components.context.Order;

/**
 * 用户关联数据加载器处理
 * @author mm
 *
 */
public interface HandlerEntry extends Order {
	/**
	 * 加载处理
	 * @param context
	 * @return
	 * @throws Exception
	 */
	Object handle(Object context) throws Exception ;
	
	/**
	 * 是否启用装载机
	 * @return
	 */
	boolean isEnable() ;
	
}

