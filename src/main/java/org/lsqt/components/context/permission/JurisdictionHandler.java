package org.lsqt.components.context.permission;

/**
 * 数据查询权限处理器
 * @author mm
 *
 */
public interface JurisdictionHandler{
	/**
	 * 数据查询权限处理
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

}

