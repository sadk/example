package org.lsqt.components.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 全局前置处理:整个请求生命周期的前置处理
 * @author mm
 *
 */
public class GlobalBeforeRequestChain implements Chain{
	private static final Logger log = LoggerFactory.getLogger(GlobalBeforeRequestChain.class);
	
	private boolean enable = true;
	private int order = 800;
	private int state = STATE_NO_WORK;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	
	public GlobalBeforeRequestChain(HttpServletRequest request,HttpServletResponse response) {
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
	public Object handle() throws Exception{
		//暂时没有!!!
		
		this.state = STATE_IS_CONTINUE_TO_EXECUTE;
		return null;
	}

}

