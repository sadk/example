package org.lsqt.components.mvc;

import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lsqt.components.context.ContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 表单数据处理器链
 * @author mm
 *
 */
public class FormMapBindingChain implements Chain{
	private static final Logger log = LoggerFactory.getLogger(FormMapBindingChain.class);
	
	private boolean enable = true;
	private int order = 500;
	private int state = STATE_NO_WORK;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	
	public FormMapBindingChain(HttpServletRequest request,HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}


	@Override
	public boolean isEnable() {
		return this.enable;
	}


	@Override
	public int getOrder() {
		 
		return this.order;
	}


	@Override
	public int getState() {
		 
		return this.state;
	}


	@Override
	public Object handle() throws Exception {
		this.state = STATE_IS_CONTINUE_TO_EXECUTE;
		
		Set<Entry<String, String[]>> set = request.getParameterMap().entrySet();
		if (set == null) {
			return null;
		}

		for (Entry<String, String[]> e : set) {
			if (e.getValue() != null && e.getValue().length == 1) {
				ContextUtil.getFormMap().put(e.getKey(), e.getValue()[0]);
				
			} else if (e.getValue() != null && e.getValue().length > 1) {
				// bug fix: 请求的地址为 http://localhost:8080/table/page?isQueryDb=true ，表单里也有 isQueryDb=true ,
				// <form action="http://localhost:8080/table/page?isQueryDb=true">
				//	<input name="isQueryDb" value="true">
				// </form>
				Set<String> temp = new HashSet<>();
				for (int i=0;i<e.getValue().length;i++) {
					temp.add(e.getValue()[i]);
				}
				if(temp.size()>1) {
					ContextUtil.getFormMap().put(e.getKey(), e.getValue());
				} else {
					ContextUtil.getFormMap().put(e.getKey(), e.getValue()[0]);
				}
				
			}
		}
		
		return null;
	}
	
}

