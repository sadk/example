package org.lsqt.components.context.permission;


/**
 * 验证的用户关联的数据加载器. 例如加载用户的部门、岗位、用户组、角色、
 * @author mm
 *
 */
public interface Handler {
	
	/**
	 * 用户数据加载器
	 * @param loader
	 * @return
	 */
	void regist(HandlerEntry entry);
	
	/**
	 * 逻辑处理
	 */
	Object handle(Object context)  throws Exception;
	
	/**
	 * 当前处理器是否启用
	 * @return
	 */
	boolean isEnable();
}

