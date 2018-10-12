package org.lsqt.components.mvc;

import static java.util.Objects.isNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.mvc.impl.UrlMappingDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JSP视图处理器
 * @author mm
 *
 */
public class JSPViewHandler implements ViewHandler{
	private static final Logger log = LoggerFactory.getLogger(JSPViewHandler.class);
	
	HttpServletRequest request;
	private HttpServletResponse response;
	
	public JSPViewHandler (HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
	public  <T> T resolve(UrlMappingDefinition urlMapping,Object modelAndView) throws Exception {
		if (isNull(urlMapping) || isNull(urlMapping.getMethod())) {
			return null;
		}

		RequestMapping requestMapping = urlMapping.getMethod().getAnnotation(RequestMapping.class);
		if (isNull(requestMapping) || requestMapping.view() != View.JSP) {
			return null;
		}

		View view = requestMapping.view();
		if (view != View.JSP) {
			return null;
		}

		String viewPrefix = requestMapping.path();
		if (!viewPrefix.endsWith("/")) {
			viewPrefix += "/";
		}

		if (modelAndView instanceof String) {
			String template = (String) modelAndView;
			if (!template.endsWith(".jsp")) {
				template += ".jsp";
			}

			if (template.startsWith("redirect:")) {
				response.sendRedirect(viewPrefix + template);
			}
			request.getRequestDispatcher(viewPrefix + template).forward(request, response);
		} /*else {
			request.getRequestDispatcher("/400.jsp").forward(request, response);
		}*/
		return null;
	}

}

