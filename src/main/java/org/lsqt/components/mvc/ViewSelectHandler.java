package org.lsqt.components.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lsqt.components.mvc.impl.UrlMappingDefinition;

/**
 * 视图处理器
 * @author mm
 *
 */
public class ViewSelectHandler implements ViewHandler {
	
	List<ViewHandler> selector = new ArrayList<>();
	
	public ViewSelectHandler(HttpServletRequest request,HttpServletResponse response) {
		JSONViewHandler jsonView = new JSONViewHandler(response);
		JSPViewHandler jspViewHandler = new JSPViewHandler(request, response);
		FreemarkViewHandler freemarkViewHandler = new FreemarkViewHandler(response);
		
		selector.add(jsonView);
		selector.add(jspViewHandler);
		selector.add(freemarkViewHandler);
	}
	
	/**
	 * 视图解析
	 * @param urlMapping
	 * @param model 模型数据
	 * @return
	 */
	public <T> T resolve(UrlMappingDefinition urlMapping,Object modelAndView) throws Exception {
		for (ViewHandler handler : selector) {
			handler.resolve(urlMapping, modelAndView);
		}
		return null;
	}
}

