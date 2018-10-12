package org.lsqt.components.mvc;

import org.lsqt.components.mvc.impl.UrlMappingDefinition;

/**
 * 视图处理器
 * @author mm
 *
 */
public interface ViewHandler {
	/**
	 * 视图解析
	 * @param urlMapping
	 * @param model 模型数据
	 * @return
	 */
	<T> T resolve(UrlMappingDefinition urlMapping,Object modelAndView) throws Exception ;
}

